package stocks.controller;

import org.springframework.http.MediaType;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import stocks.domain.Product;
import stocks.domain.StockObject;
import stocks.repository.ProductRepository;
import stocks.repository.StockRepository;

@RestController
@RequestMapping(path="/stocks", produces=MediaType.APPLICATION_JSON_VALUE)
public class StockController {
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	
	public StockController(ProductRepository productRepository, StockRepository stockRepository) {
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/stockdata")
	public List<StockObject> getTimeSeries(){
		return stockRepository.findAll();
	}
}
