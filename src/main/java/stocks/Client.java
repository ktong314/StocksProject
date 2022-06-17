package stocks;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Client {
	private String interval = "1day";
	private String outputsize = "5";
	File configFile = new File("config.properties");
	
	public Client() {
		setParams();
	}
	
	public void setParams() {
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			
			this.interval = props.getProperty("interval");
			this.outputsize = props.getProperty("outputsize");
			reader.close();
		} catch (Exception e ) {
			
		}
	}
	
	public String getInterval() {
		return interval;
	}
	
	public String getOutputsize() {
		return outputsize;
	}
}
