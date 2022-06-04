package main.java.stocks;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class StockReader {
	
    private static final String API_KEY = "e62aab1c906045bdb4eb84f23b6e3bbe";
 
    public static void main(String[] args) throws Exception {
    	System.out.println("input ticker:");
    	Scanner in = new Scanner(System.in);
    	String ticker = in.next().toUpperCase();
    	String interval = "1day";
    	String outputsize = "12";
    	String URL_request = "https://api.twelvedata.com/time_series?symbol=" + ticker + "&interval=" + interval 
    			+ "&outputsize=" + outputsize + "&apikey=" + API_KEY;
        URL requestURL = new URL(URL_request);
        HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
        StringBuffer responseData = new StringBuffer();
        JSONParser parser = new JSONParser();
 
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "twelve_java/1.0");
        connection.connect();
 
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
        }
 
        Scanner scanner = new Scanner(requestURL.openStream());
        while (scanner.hasNextLine()) {
            responseData.append(scanner.nextLine());
        }
        
        System.out.println(responseData);
 
        JSONObject json = (JSONObject) parser.parse(responseData.toString());
        JSONObject meta = (JSONObject) json.get("meta");
        JSONArray values = (JSONArray) json.get("values");
        
        System.out.println("meta:" + meta);
        System.out.println("values: " + values.toString());
       
        connection.disconnect();
    }
}