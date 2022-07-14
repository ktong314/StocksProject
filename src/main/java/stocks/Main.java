package stocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import stocks.domain.Product;
import stocks.domain.StockObject;
import stocks.repository.ProductRepository;
import stocks.repository.StockRepository;


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
    	StockReader.start();
    	SpringApplication.run(Main.class, args);
    }
    
    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
		return args -> {
			
			Product product1 = new Product("Xbox 360");
			product1.setPrice(323);
			productRepository.save(product1);
			
			Product product2 = new Product("Wii");
			product2.setPrice(124);
			productRepository.save(product2);
			
			Product product3 = new Product("Chess");
			product3.setPrice(24);
			productRepository.save(product3);
			
		};
    	
    }
    
    @Bean
    CommandLineRunner stockRunner(StockRepository stockRepository) {
		return args -> {
			
			for(int i = 0; i < StockReader.stocks.size(); i++) {
				StockObject currentStockObj = StockReader.stocks.get(i);
	    		stockRepository.save(new StockObject(currentStockObj.getTicker(), currentStockObj.getTsData()));
	    	}
			
		};
    	
    }
    
  
}