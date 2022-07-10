package stocks;

/**
 * A class that records a specific Time Series data
 * @author ktong
 *
 */
public class TimeSeries {
	private transient String time;
	private transient double open;
	private transient double close;
	private transient double high;
	private transient double low;
	
	/**
	 * Constructor of a time series.
	 * 
	 * @param time		The representation of the date.
	 * @param open		The opening value of the date
	 * @param close		The value at the closing.
	 * @param high		The highest value during the time period	
	 * @param low		the lowest value during the time period
	 */
	public TimeSeries (String time, double open, double close, double high, double low) {
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	
	/**
	 * returns date and time
	 * @return time
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * returns open
	 * @return open
	 */
	public double getOpen() {
		return open;
	}
	
	/**
	 * returns close
	 * @return close
	 */
	public double getClose() {
		return close;
	}
	
	/**
	 * returns high
	 * @return high
	 */
	public double getHigh() {
		return high;
	}
	
	/**
	 * returns low
	 * @return low
	 */
	public double getLow() {
		return low;
	}
	
	/**
	 * overrides toString() for better formatting when printing
	 */
	public String toString() {
		return "date and time: " + this.time + ", " +
				"open: " + this.open + ", " +
				"close: " + this.close + ", " +
				"high: " + this.high + ", " +
				"low: " + this.low + "\n";
	}
}
