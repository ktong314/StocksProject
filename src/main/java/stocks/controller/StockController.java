package stocks.controller;

import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import stocks.domain.Product;
import stocks.domain.StockObject;
import stocks.domain.TimeSeries;
import stocks.domainBean.StockObjectBean;
import stocks.repository.ProductRepository;
import stocks.repository.StockRepository;
import stocks.repository.TimeSeriesRepository;
import stocks.service.StockReader;
import stocks.service.StockService;

@RestController
@RequestMapping(path="/stocks", produces=MediaType.APPLICATION_JSON_VALUE)
public class StockController {
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	private final TimeSeriesRepository timeSeriesRepository;
	private StockService stockService;
	
	
	public StockController(ProductRepository productRepository, StockRepository stockRepository, TimeSeriesRepository timeSeriesRepository) {
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
		this.timeSeriesRepository = timeSeriesRepository;
		stockService = new StockService(stockRepository);
	}
	
	@PostMapping("/newstocks")
	public List<StockObject> postStocks(@RequestBody String[] tickers){
		return stockService.createStocks(tickers);
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/savedstocks")
	public List<StockObject> getStockObject(){
		return stockRepository.findAll();
	}
	
	@DeleteMapping("/deletestocks")
	public List<StockObject> deleteStocks(@RequestBody String[] tickers) {
		return stockService.deleteStocks(tickers);
		
	}
	
	@GetMapping("/alltickers")
	public List<StockObjectBean> getTickers(){
		ArrayList<StockObjectBean> stockList = new ArrayList<>();
		List<StockObject> tickers = stockService.getStocks();
		//List<TimeSeries> tsList = timeSeriesRepository.findAll();
		
		for (StockObject ticker : tickers) {
			StockObjectBean stockBean = new StockObjectBean();
			  stockBean.setId(ticker.getId());
			  stockBean.setTicker(ticker.getTicker());
			  stockBean.setCompanyName(ticker.getCompanyName());
			 
			  stockList.add(stockBean);
	       	 
			}
		
		return stockList;
		
	}
	

	
	
}
