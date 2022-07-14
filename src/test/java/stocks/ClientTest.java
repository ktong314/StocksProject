package stocks;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import stocks.domain.Client;

class ClientTest {

	@Test
	void testGetInterva_DefaultValuel() {
		
		Client client = new Client();
		Assert.assertEquals("1day", client.getInterval());
	}
	
	@Test
	void testGetOutputSize_DefaultValuel() {
		
		Client client = new Client();
		Assert.assertEquals("5", client.getOutputsize());
	}
	
	@Test
	void testGetOutputSize_NonDefaultValue() {
		
		Client client = new Client();
		client.initParams();
		Assert.assertEquals("3", client.getOutputsize());
	}	
	
	@Test
	void testGetOutputSize_NoPropProvided() {
		
		Properties props = new Properties();
		props.put("kevin", "100");
		
		Client client = new Client();
		client.readExtProps(props);
		Assert.assertEquals("5", client.getOutputsize());
	}
	
	@Test
	void testGetOutputSize_aPropProvided() {
		
		Properties props = new Properties();
		props.put("outputsize", "100");
		
		Client client = new Client();
		client.readExtProps(props);
		Assert.assertEquals("100", client.getOutputsize());
	}	

}
