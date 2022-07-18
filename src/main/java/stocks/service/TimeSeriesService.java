package stocks.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import stocks.repository.StockRepository;
import stocks.repository.TimeSeriesRepository;
import stocks.domain.TimeSeries;
import stocks.Main;
import stocks.domain.Client;
import stocks.domain.StockObject;

public class TimeSeriesService {
	private final TimeSeriesRepository timeSeriesRepository;
	private final StockRepository stockRepository;
	
	@Autowired
	public TimeSeriesService(TimeSeriesRepository timeSeriesRepository, StockRepository stockRepository) {
		this.timeSeriesRepository = timeSeriesRepository;
		this.stockRepository = stockRepository;
	}
	
	public List<TimeSeries> createTimeSeries() throws Exception{
		List<StockObject> stocks = stockRepository.findAll();
		try {
			int counter = 1; //NOPMD
			for(StockObject currentStock : stocks){
				String ticker = currentStock.getTicker();
	    		obtainTimeSeries(ticker, currentStock);
	    		if(counter%4 == 0 && counter < stocks.size()) {
					TimeUnit.MINUTES.sleep(1);
				}
	    		counter++;
	    	}
		} catch (Exception e) {
			System.out.println("error");
		}
		return timeSeriesRepository.findAll();
	}
	
	private void obtainTimeSeries(String ticker, StockObject currentStock) throws Exception{
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
        JSONArray values = (JSONArray) json.get("values");
        for(int i = 0; i < values.size(); i++) {
        	JSONObject currentvalue = (JSONObject) values.get(i);
        	TimeSeries tsValue = new TimeSeries((String)currentvalue.get("datetime"), Double.parseDouble((String)currentvalue.get("open")), 
        			Double.parseDouble((String)currentvalue.get("close")), Double.parseDouble((String)currentvalue.get("high")), 
        			Double.parseDouble((String)currentvalue.get("low")), currentStock);
        	timeSeriesRepository.save(tsValue);
        }
        
       
        connection.disconnect();
	}
}
