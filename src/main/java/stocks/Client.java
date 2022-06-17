package stocks;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Client {
	private String interval = "1day";
	private String outputsize = "5";
	File configFile = new File("config.properties");
	
	
	public Client() {
	
	}
	
	public void initParams() {
		setParams();
	}
	
	public void setParams() {
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			readExtProps(props);
			
			reader.close();
		} catch (Exception e ) {
			System.out.println("Error reading input properties");
			System.out.println(e);
		}
	}
	
	void readExtProps(Properties props) {
		this.interval = props.getProperty("interval") !=null? props.getProperty("interval") : interval;
		this.outputsize = props.getProperty("outputsize") != null? props.getProperty("outputsize") : outputsize;
	}

	void setInterval(String interv) {
		this.interval = interv;
	}
	
	public String getInterval() {
		return interval;
	}
	
	public String getOutputsize() {
		return outputsize;
	}
}
