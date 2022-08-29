package stocks.domainBean;

public class TimeSeriesBean {
	private long id;
	private String date;
	private String ticker;
	private double open;
	private double close;
	private double high;
	private double low;
	private String companyName;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public void setOpen(double open) {
		this.open = open;
	}
	
	public double getOpen() {
		return open;
	}
	
	public void setClose(double close) {
		this.close = close;
	}
	
	public double getClose() {
		return close;
	}
	
	public void setHigh(double high) {
		this.high = high;
	}
	
	public double getHigh() {
		return high;
	}
	
	public void setLow(double low) {
		this.low = low;
	}
	
	public double getLow() {
		return low;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
