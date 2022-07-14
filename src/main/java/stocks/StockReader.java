package stocks;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVWriter;

import stocks.domain.Client;
import stocks.domain.StockObject;
import stocks.domain.TimeSeries;

/**
 * This represents a component that can reead the input sticker codes
 * and query their information from the external API.
 * 
 * @author Kevin Tong
 *
 */
public class StockReader {
	/**
	 * an arraylist of StockObjects
	 */
	public static ArrayList<StockObject> stocks = new ArrayList<StockObject>();
	/**
	 * an output String
	 */
	public static String output = "Data Loading..."; 
	private static File tickerFile = new File("Tickers.txt");
	
	/**
	 * This is the entry point to process the input stickers and extract the information.
	 * @throws Exception   When the external API is down, or no input file was provided.
	 */
	public static void start() throws Exception {
		populateData();
		writeCSV();
	}
	
	/**
	 * Obtains data for the company's specified in the tickerFile
	 * 
	 * @throws Exception	when exceptions are thrown in methods
	 */
	public static void populateData() throws Exception {
		try(Scanner in = new Scanner(tickerFile);){
			int limiter = 1; //NOPMD
			while(in.hasNextLine()){
				String ticker = in.nextLine().toUpperCase(Locale.getDefault());
	    		obtainTimeSeries(ticker);
	    		if(limiter%4 == 0 && in.hasNextLine()) {
					TimeUnit.MINUTES.sleep(1);
				}
	    		limiter++;
	    	}
		}
		output = "";
		for(int i = 0; i < stocks.size(); i++) {
    		output = output + (stocks.get(i)) + "\n";
    	}
		System.out.println(output);
	}
	
	/**
	 * Uses a request URL to obtain time series data from an API
	 * @param ticker		the ticker symbol obtained from the ticker file
	 * @throws Exception	the url requested was incorrect
	 */
	private static void obtainTimeSeries(String ticker) throws Exception{
		Client c = new Client();
		c.initParams();
    	String TimeSeriesURL = "https://api.twelvedata.com/time_series?symbol=" + ticker + "&interval=" + c.getInterval()
    			+ "&outputsize=" + c.getOutputsize() + "&apikey=" + Main.API_KEY;
        URL tsRequest = new URL(TimeSeriesURL);
        HttpURLConnection connection = (HttpURLConnection)tsRequest.openConnection();
        StringBuffer responseData = new StringBuffer();
        JSONParser parser = new JSONParser();
 
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "twelve_java/1.0");
        connection.connect();
        
        int error = 200;
        if (connection.getResponseCode() != error) {
            throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
        }
 
        Scanner scanner = new Scanner(tsRequest.openStream());
        while (scanner.hasNextLine()) {
            responseData.append(scanner.nextLine());
        }
        scanner.close();
        System.out.println(responseData);
        
        JSONObject json = (JSONObject) parser.parse(responseData.toString());
        JSONObject meta = (JSONObject) json.get("meta");
        JSONArray values = (JSONArray) json.get("values");
        ArrayList<TimeSeries> tsData = new ArrayList<TimeSeries>();
        for(int i = 0; i < values.size(); i++) {
        	JSONObject currentvalue = (JSONObject) values.get(i);
        	TimeSeries tsValue = new TimeSeries((String)currentvalue.get("datetime"), Double.parseDouble((String)currentvalue.get("open")), 
        			Double.parseDouble((String)currentvalue.get("close")), Double.parseDouble((String)currentvalue.get("high")), 
        			Double.parseDouble((String)currentvalue.get("low")));
        	tsData.add(tsValue);
        }
        
       
        
        StockObject stock = new StockObject((String)meta.get("symbol"), tsData);
        stocks.add(stock);
        
       
        connection.disconnect();
	}
	
	/**
	 * writes the data to the csv file
	 */
	public static void writeCSV() {
		File file = new File("output.csv");
		try (FileWriter outputfile = new FileWriter(file); CSVWriter writer = new CSVWriter(outputfile)){
			
			String[] header = { "Ticker", "Date/Time", "Open", "Close", "High", "Low" };
			writer.writeNext(header);
			for(int i = 0; i < stocks.size(); i++) {
	    		StockObject current = stocks.get(i);
	    		for(int j = 0; j < current.getTimeSeries().size(); j++) {
	    			TimeSeries currentData = current.getTimeSeries().get(j);
	    			String[] data = {current.getTicker(), currentData.getTime() + "", currentData.getopenPrice() + "", currentData.getclosePrice() + "",
	    					currentData.gethighPrice() + "", currentData.getlowPrice() + ""};
	    			writer.writeNext(data);
	    		}
	    	}
		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
