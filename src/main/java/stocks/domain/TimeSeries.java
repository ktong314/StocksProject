package stocks.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A class that records a specific Time Series data
 * @author ktong
 *
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeSeries {
	
	
	@Id
	@GeneratedValue
	private long id;
	
	private String time;
	private double openPrice;
	private double closePrice;
	private double highPrice;
	private double lowPrice;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "stock_id", nullable = false)
	private StockObject stock;
	
	public TimeSeries() {}
	
	/**
	 * Constructor of a time series.
	 * 
	 * @param time		The representation of the date.
	 * @param openPrice		The openPriceing value of the date
	 * @param closePrice		The value at the closing.
	 * @param highPrice		The highPriceest value during the time period	
	 * @param lowPrice		the lowPriceest value during the time period
	 */
	public TimeSeries (String time, double openPrice, double closePrice, double highPrice, double lowPrice, StockObject stock) {
		this.time = time;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.stock = stock;
	}
	
	/**
	 * Constructor of a time series.
	 * 
	 * @param time		The representation of the date.
	 * @param openPrice		The openPriceing value of the date
	 * @param closePrice		The value at the closing.
	 * @param highPrice		The highPriceest value during the time period	
	 * @param lowPrice		the lowPriceest value during the time period
	 */
	public TimeSeries (String time, double openPrice, double closePrice, double highPrice, double lowPrice) {
		this.time = time;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * returns date and time
	 * @return time
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * returns openPrice
	 * @return openPrice
	 */
	public double getopenPrice() {
		return openPrice;
	}
	
	/**
	 * returns closePrice
	 * @return closePrice
	 */
	public double getclosePrice() {
		return closePrice;
	}
	
	/**
	 * returns highPrice
	 * @return highPrice
	 */
	public double gethighPrice() {
		return highPrice;
	}
	
	/**
	 * returns lowPrice
	 * @return lowPrice
	 */
	public double getlowPrice() {
		return lowPrice;
	}
	
	/**
	 * overrides toString() for better formatting when printing
	 */
	public String toString() {
		return "date and time: " + this.time + ", " +
				"openPrice: " + this.openPrice + ", " +
				"closePrice: " + this.closePrice + ", " +
				"highPrice: " + this.highPrice + ", " +
				"lowPrice: " + this.lowPrice + "\n";
	}
}
