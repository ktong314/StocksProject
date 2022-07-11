package stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


 /**
  * StockProject main class
  * @author ktong
  *
  */
@SpringBootApplication
@RestController
public class Main {
	
	/**
	 * the api key
	 */
	public static String API_KEY = "e62aab1c906045bdb4eb84f23b6e3bbe";
   
	/**
	 * starts the program
	 * @param args				command line arguments
	 * @throws Exception		whenever exceptions are thrown within the StockReader class
	 */
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Main.class, args);
    	StockReader.start();
    }
    
    @GetMapping("/output")
	public String output() {
    	
		return StockReader.output;
	}
}