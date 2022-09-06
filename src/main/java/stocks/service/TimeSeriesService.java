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
import stocks.domainBean.StockObjectBean;
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
	
	public boolean deleteTimeSeriesByStocks(List<StockObjectBean> stocks) {
		for(StockObjectBean currentstock : stocks) {
			StockObject stock = stockRepository.findByTicker(currentstock.getTicker());
			if(stock != null){
				List<TimeSeries> ts = timeSeriesRepository.findByStock(stock);
				for(TimeSeries timeSeries: ts) {
					timeSeriesRepository.deleteById(timeSeries.getId());
				}
			
				stockRepository.deleteById(stock.getId());

			}
			
		}
		return true;
	}

	public List<TimeSeries> fetchStocks(List<StockObjectBean> stocks) throws Exception{
		List<TimeSeries> result= new ArrayList<TimeSeries>();

		List<TimeSeries> timeSeries = createTimeSeries(stocks);
		for(StockObjectBean currentstock : stocks){
			StockObject stock = stockRepository.findByTicker(currentstock.getTicker());
			if(stock != null){
				List<TimeSeries> ts = timeSeriesRepository.findByStock(stock);
				result.addAll(ts);
			}
		}
		return result;
	}


	public List<TimeSeries> createTimeSeries(List<StockObjectBean> stocks) throws Exception{

		try {
			int counter = 1; //NOPMD
			for(StockObjectBean currentstock : stocks){
				StockObject stock = stockRepository.findByTicker(currentstock.getTicker());
				if(stock == null){
					stock = stockRepository.save(new StockObject(currentstock.getTicker(), currentstock.getCompanyName()));
				}
	    		obtainTimeSeries(stock.getTicker(), stock);
	    		if(counter%4 == 0 && counter < stocks.size()) {
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
        double previousClosePrice = 0;
        for(int i = values.size() - 1; i >= 0; i--) {
        	JSONObject currentvalue = (JSONObject) values.get(i);
        	TimeSeries tsValue = new TimeSeries((String)currentvalue.get("datetime"), Double.parseDouble((String)currentvalue.get("open")), 
        			Double.parseDouble((String)currentvalue.get("close")), Double.parseDouble((String)currentvalue.get("high")), 
        			Double.parseDouble((String)currentvalue.get("low")), currentStock);
        	
        	double changes = previousClosePrice != 0 ? (tsValue.getclosePrice() - previousClosePrice)/previousClosePrice : 0;
        	tsValue.setChanges(Double.parseDouble(String.format("%.4f", changes)));
        	previousClosePrice = tsValue.getclosePrice();
        	timeSeriesRepository.save(tsValue);
        	
        }
        
       
        connection.disconnect();
	}
}
