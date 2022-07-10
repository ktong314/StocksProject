package stocks;

import java.util.ArrayList;

/**
 * A class that will contain the data for various Stocks
 * @author ktong
 *
 */
public class StockObject {
	private transient String ticker;
	//private double dividend;
	private transient ArrayList<TimeSeries> tsData;
	
	/**
	 * the StockObject constructor
	 * @param ticker		the company's ticker
	 * @param values		an arraylist of TimeSeries objects
	 */
	public StockObject (String ticker, ArrayList<TimeSeries> values) {
		this.ticker = ticker;
		this.tsData = values;
	}
	
	/**
	 * returns ticker
	 * @return ticker
	 */
	public String getTicker() {
		return ticker;
	}
	
	/**
	 * returns tsData
	 * @return tsData
	 */
	public ArrayList<TimeSeries> getTSValues() {
		return tsData;
	}
	
	/**
	 * overrrides toString()
	 */
	public String toString() {
		StringBuffer data = new StringBuffer();
		for(int i = 0; i < tsData.size(); i++) {
			data.append(tsData.get(i));
		}
		return "Ticker: " + this.ticker + "\n" +
				data;
	}
}
