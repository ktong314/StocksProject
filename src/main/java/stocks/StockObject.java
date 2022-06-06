package main.java.stocks;

import java.util.ArrayList;

public class StockObject {
	private String ticker;
	private double dividend;
	private ArrayList<TimeSeries> tsData;
	
	public StockObject (String ticker, ArrayList<TimeSeries> values) {
		this.ticker = ticker;
		this.tsData = values;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public ArrayList<TimeSeries> getValues() {
		return tsData;
	}
	
	public String toString() {
		StringBuffer data = new StringBuffer();
		for(int i = 0; i < tsData.size(); i++) {
			data.append(tsData.get(i));
		}
		return "Ticker: " + this.ticker + "\n" +
				data;
	}
}
