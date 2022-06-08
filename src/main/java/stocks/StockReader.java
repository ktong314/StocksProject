package main.java.stocks;


import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class StockReader {
	public static ArrayList<StockObject> stocks = new ArrayList<StockObject>();
	public static File tickerFile = new File("Tickers.txt");
	
	public static void start() throws Exception {
		obtainTimeSeries();
	}
	
	public static void obtainTimeSeries() throws Exception {
		
		Scanner in = new Scanner(tickerFile);
		
		while(in.hasNextLine()){
    		
        	String ticker = in.nextLine().toUpperCase();
        	String TimeSeriesURL = "https://api.twelvedata.com/time_series?symbol=" + ticker + "&interval=" + Config.INTERVAL 
        			+ "&outputsize=" + Config.OUTPUT_SIZE + "&apikey=" + Config.API_KEY;
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
            
           
            
            StockObject Stock = new StockObject((String)meta.get("symbol"), tsData);
            stocks.add(Stock);
            
           
            connection.disconnect();
    	}
		for(int i = 0; i < stocks.size(); i++) {
    		System.out.println(stocks.get(i));
    	}
	}
}
