package stocks.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;



import stocks.domain.StockObject;

import stocks.repository.StockRepository;

public class StockService {
	private final StockRepository stockRepository;
	
	@Autowired
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	
	public List<StockObject> createStocks(String[] stocks) {
		for(String ticker : stocks) {
			StockObject currentStock = new StockObject(ticker.toUpperCase());
			if(stockRepository.findByTicker(currentStock.getTicker()) == null) {
				currentStock = stockRepository.save(currentStock);
			}
		}
		return stockRepository.findAll();
	}
	
	public List<StockObject> deleteStocks(String[] tickers) {
		for(String ticker : tickers) {
			//stockRepository.deleteByTicker(ticker);
		}
		return stockRepository.findAll();
	}
		
}
