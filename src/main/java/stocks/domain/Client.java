package stocks.domain;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Client object that initializes various variables
 * 
 * @author ktong
 *
 */
public class Client {
	private String interval = "1day"; 
	private String outputsize = "5";
	private File configFile = new File("config.properties");
	
	/**
	 * Client constructor
	 */
	public Client() {
		
	}
	
	/**
	 * calls setParams()
	 */
	public void initParams() {
		setParams();
	}
	
	/**
	 * obtains properties by reading from config.properties
	 */
	public void setParams() {
		
		try (FileReader reader = new FileReader(configFile)){
			Properties props = new Properties();
			props.load(reader);
			readExtProps(props);		
		} catch (Exception e ) {
			System.out.println("Error reading input properties");
			System.out.println(e);
		} 
	
	}
	
	/**
	 * sets the interval and outputsize using props
	 * if the read props are null the interval and outputsize are made to be default
	 * @param props			read properties from config.properties
	 */
	public void readExtProps(Properties props) {
		this.interval = props.getProperty("interval") !=null? props.getProperty("interval") : interval;
		this.outputsize = props.getProperty("outputsize") != null? props.getProperty("outputsize") : outputsize;
	}
	
	/**
	 * sets the interval
	 * @param interv
	 */
	void setInterval(String interv) {
		this.interval = interv;
	}
	
	/**
	 * sets the outputsize
	 * @param outputsize
	 */
	void setOutputsize(String outputsize) {
		this.outputsize = outputsize;
	}
	

	/**
	 * returns interval
	 * @return interval
	 */
	public String getInterval() {
		return interval;
	}
	
	/**
	 * returns the outputsize
	 * @return outputsize
	 */
	public String getOutputsize() {
		return outputsize;
	}
}
