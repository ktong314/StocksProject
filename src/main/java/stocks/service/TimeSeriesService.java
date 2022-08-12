package stocks.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
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
	
	public void deleteTimeSeriesByStocks(String[] tickers) {
		for(String ticker : tickers) {
			StockObject stock = stockRepository.findByTicker(ticker);
			if(stock != null){
				List<TimeSeries> ts = timeSeriesRepository.findByStock(stock);
				for(TimeSeries timeSeries: ts) {
					timeSeriesRepository.deleteById(timeSeries.getId());
				}
			
				stockRepository.deleteById(stock.getId());

			}
			
		}
	}

	public List<TimeSeries> fetchStocks(String[] tickers) throws Exception{
		List<TimeSeries> result= new ArrayList<TimeSeries>();

		List<TimeSeries> timeSeries = createTimeSeries(tickers);
		for(String ticker : tickers){
			StockObject stock = stockRepository.findByTicker(ticker);
			if(stockRepository.findByTicker(ticker) != null){
				List<TimeSeries> ts = timeSeriesRepository.findByStock(stock);
				result.addAll(ts);
			}
		}
		return result;
	}


	public List<TimeSeries> createTimeSeries(String[] tickers) throws Exception{

		try {
			int counter = 1; //NOPMD
			for(String ticker : tickers){
				StockObject stock = stockRepository.findByTicker(ticker);
				if(stock == null){
					stock = stockRepository.save(new StockObject(ticker));
				}
	    		obtainTimeSeries(ticker, stock);
	    		if(counter%4 == 0 && counter < tickers.length) {
					TimeUnit.MINUTES.sleep(1);
				}
	    		counter++;
	    	}
		} catch (Exception e) {
			System.out.println(e);
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
