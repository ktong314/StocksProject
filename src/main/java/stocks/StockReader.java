package stocks;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVWriter;


public class StockReader {
	public static ArrayList<StockObject> stocks = new ArrayList<StockObject>();
	public static File tickerFile = new File("Tickers.txt");
	
	public static void start() throws Exception {
		obtainTimeSeries();
		writeCSV();
	}
	
	public static void obtainTimeSeries() throws Exception {
		
		Scanner in = new Scanner(tickerFile);
		
		while(in.hasNextLine()){
    		
			Client c = new Client();
        	String ticker = in.nextLine().toUpperCase();
        	String TimeSeriesURL = "https://api.twelvedata.com/time_series?symbol=" + ticker + "&interval=" + c.getInterval()
        			+ "&outputsize=" + c.getOutputsize() + "&apikey=" + Main.API_KEY;
            URL tsRequest = new URL(TimeSeriesURL);
            HttpURLConnection connection = (HttpURLConnection)tsRequest.openConnection();
            StringBuffer responseData = new StringBuffer();
            JSONParser parser = new JSONParser();
     
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "twelve_java/1.0");
            connection.connect();
     
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
            }
     
            Scanner scanner = new Scanner(tsRequest.openStream());
            while (scanner.hasNextLine()) {
                responseData.append(scanner.nextLine());
            }
            
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
		for(int i = 0; i < stocks.size(); i++) {
    		System.out.println(stocks.get(i));
    	}
	}
	public static void writeCSV() {
		File file = new File("output.csv");
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			String[] header = { "Ticker", "Date/Time", "Open", "Close", "High", "Low" };
			writer.writeNext(header);
			for(int i = 0; i < stocks.size(); i++) {
	    		StockObject current = stocks.get(i);
	    		for(int j = 0; j < current.getTSValues().size(); j++) {
	    			TimeSeries currentData = current.getTSValues().get(j);
	    			String[] data = {current.getTicker(), currentData.getTime() + "", currentData.getOpen() + "", currentData.getClose() + "",
	    					currentData.getHigh() + "", currentData.getLow() + ""};
	    			writer.writeNext(data);
	    		}
	    	}
			writer.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
