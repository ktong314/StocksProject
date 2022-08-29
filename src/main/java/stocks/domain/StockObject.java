package stocks.domain;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A class that will contain the data for various Stocks
 * @author ktong
 *
 */
@Entity
@Table(name = "stocks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StockObject {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String ticker;
	//private double dividend;
	
	private String companyName;
	
	private ArrayList<TimeSeries> timeSeries;
	
	public StockObject() {}
	
	/**
	 * the StockObject constructor
	 * @param ticker		the company's ticker
	 * @param values		an arraylist of TimeSeries objects
	 */
	public StockObject (String ticker) {
		this.ticker = ticker;
	}
	
	public StockObject (String ticker, String companyName) {
		this.ticker = ticker;
		this.companyName = companyName;
	}
	
	/**
	 * the StockObject constructor
	 * @param ticker		the company's ticker
	 * @param values		an arraylist of TimeSeries objects
	 */
	public StockObject (String ticker, ArrayList<TimeSeries> values) {
		this.ticker = ticker;
		this.timeSeries = values;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
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
	public ArrayList<TimeSeries> getTimeSeries() {
		return timeSeries;
	}
	

	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * overrrides toString()
	 */
	public String toString() {
		StringBuffer data = new StringBuffer();
		for(int i = 0; i < timeSeries.size(); i++) {
			data.append(timeSeries.get(i));
		}
		return "Ticker: " + this.ticker + "\n" +
				data;
	}
	
	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public void setTimeSeries(ArrayList<TimeSeries> tsData) {
		this.timeSeries = tsData;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
