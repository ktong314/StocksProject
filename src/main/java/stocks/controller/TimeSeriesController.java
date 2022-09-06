package stocks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stocks.domain.TimeSeries;
import stocks.domainBean.StockObjectBean;
import stocks.domainBean.TimeSeriesBean;
import stocks.repository.ProductRepository;
import stocks.repository.StockRepository;
import stocks.repository.TimeSeriesRepository;
import stocks.service.TimeSeriesService;

@RestController
@RequestMapping(path="/stocks", produces=MediaType.APPLICATION_JSON_VALUE)
public class TimeSeriesController {
	private final ProductRepository productRepository;
	private final StockRepository stockRepository;
	private final TimeSeriesRepository timeSeriesRepository;
	private TimeSeriesService timeSeriesService;	
	
	public TimeSeriesController(ProductRepository productRepository, StockRepository stockRepository, TimeSeriesRepository timeSeriesRepository) {
		this.productRepository = productRepository;
		this.stockRepository = stockRepository;
		this.timeSeriesRepository = timeSeriesRepository;
		timeSeriesService = new TimeSeriesService(timeSeriesRepository, stockRepository);
	}

	@PostMapping("/timeseries")
	public List<TimeSeriesBean> getTimeSeries(@RequestBody List<StockObjectBean> stocks) throws Exception{


		List<TimeSeriesBean> timeSeriesList = new ArrayList<>();
		List<TimeSeries> allTimeSeries = timeSeriesService.fetchStocks(stocks);

		for(TimeSeries timeSeries : allTimeSeries) {
			TimeSeriesBean timeSeriesBean = new TimeSeriesBean();
			timeSeriesBean.setId(timeSeries.getId());
			timeSeriesBean.setTicker(timeSeries.getStock().getTicker());
			timeSeriesBean.setDate(timeSeries.getTime());
			timeSeriesBean.setOpen(timeSeries.getopenPrice());
			timeSeriesBean.setClose(timeSeries.getclosePrice());
			timeSeriesBean.setHigh(timeSeries.gethighPrice());
			timeSeriesBean.setLow(timeSeries.getlowPrice());
			timeSeriesBean.setCompanyName(timeSeries.getStock().getCompanyName());
			timeSeriesBean.setChanges(timeSeries.getChanges().doubleValue());
			timeSeriesList.add(timeSeriesBean);
		}
		return timeSeriesList;
	}
	
	@DeleteMapping("/timeseries")
	public boolean deleteTimeSeriesByTickers(@RequestBody List<StockObjectBean> stocks) {
		return timeSeriesService.deleteTimeSeriesByStocks(stocks);
	}
	
	

}
