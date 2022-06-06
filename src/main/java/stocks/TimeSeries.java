package main.java.stocks;

public class TimeSeries {
	private String time;
	private double open;
	private double close;
	private double high;
	private double low;
	
	public TimeSeries (String time, double open, double close, double high, double low) {
		this.time = time;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	
	public String toString() {
		return "date and time: " + this.time + ", " +
				"open: " + this.open + ", " +
				"close: " + this.close + ", " +
				"high: " + this.high + ", " +
				"low: " + this.low + "\n";
	}
}
