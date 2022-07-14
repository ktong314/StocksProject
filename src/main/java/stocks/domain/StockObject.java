package stocks.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * A class that will contain the data for various Stocks
 * @author ktong
 *
 */
@Entity
public class StockObject {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private transient String ticker;
	//private double dividend;
	@ManyToMany
	private transient ArrayList<TimeSeries> tsData;
	
	public StockObject() {
		
	}
	
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
	public ArrayList<TimeSeries> getTsData() {
		return tsData;
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
		for(int i = 0; i < tsData.size(); i++) {
			data.append(tsData.get(i));
		}
		return "Ticker: " + this.ticker + "\n" +
				data;
	}
}
