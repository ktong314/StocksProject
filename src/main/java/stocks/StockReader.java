package main.java.stocks;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class StockReader {
	
    private static final String API_KEY = "e62aab1c906045bdb4eb84f23b6e3bbe";
    private static ArrayList<StockObject> stocks = new ArrayList<StockObject>();
 
    public static void main(String[] args) throws Exception {
    	
    	while(true){
    		System.out.println("input ticker:");
        	Scanner in = new Scanner(System.in);
        	String ticker = in.next().toUpperCase();
        	if(ticker.equals("000")) {
        		break;
        	}
    		String interval = "1min";
        	String outputsize = "3";
        	String TimeSeriesURL = "https://api.twelvedata.com/time_series?symbol=" + ticker + "&interval=" + interval 
        			+ "&outputsize=" + outputsize + "&apikey=" + API_KEY;
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
            
            String DividendURL = "https://api.twelvedata.com/dividends?symbol=" + ticker + "&apikey=" + API_KEY;
            URL divRequest = new URL(DividendURL);
            connection = (HttpURLConnection)divRequest.openConnection();
            responseData = new StringBuffer();
            
            scanner = new Scanner(divRequest.openStream());
            while (scanner.hasNextLine()) {
                responseData.append(scanner.nextLine());
            }
            
            System.out.println(responseData);
            
            StockObject Stock = new StockObject((String)meta.get("symbol"), tsData);
            stocks.add(Stock);
            
           
            connection.disconnect();
    	}
    	for(int i = 0; i < stocks.size(); i++) {
    		System.out.println(stocks.get(i));
    	}
    	
    }
}