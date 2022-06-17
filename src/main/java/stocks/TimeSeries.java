package stocks;

public class TimeSeries {
	private String time;
	private double open;
	private double close;
	private double high;
	private double low;
	
	/**
	 * Constructor of a time series.
	 * 
	 * @param time		The representation of the date.
	 * @param open		The opening value of the date
	 * @param close		The value at the closing.
	 * @param high
	 * @param low
	 */
	public TimeSeries (String time, double open, double close, double high, double low) {
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	
	public String getTime() {
		return time;
	}
	
	public double getOpen() {
		return open;
	}
	
	public double getClose() {
		return close;
	}
	
	public double getHigh() {
		return high;
	}
	
	public double getLow() {
		return low;
	}
	
	public String toString() {
		return "date and time: " + this.time + ", " +
				"open: " + this.open + ", " +
				"close: " + this.close + ", " +
				"high: " + this.high + ", " +
				"low: " + this.low + "\n";
	}
}
