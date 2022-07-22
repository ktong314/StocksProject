package stocks.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stocks.domain.TimeSeries;
import stocks.domainBean.TimeSeriesBean;
import stocks.repository.ProductRepository;
import stocks.repository.StockRepository;
import stocks.repository.TimeSeriesRepository;
import stocks.service.TimeSeriesService;

@RestController
@RequestMapping(path="/timeseries", produces=MediaType.APPLICATION_JSON_VALUE)
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

	@GetMapping("/alltimeseries")
	public List<TimeSeriesBean> getTimeSeries() throws Exception{
		List<TimeSeriesBean> timeSeriesList = new ArrayList<>();
		List<TimeSeries> allTimeSeries = timeSeriesService.createTimeSeries();
		for(TimeSeries timeSeries : allTimeSeries) {
			TimeSeriesBean timeSeriesBean = new TimeSeriesBean();
			timeSeriesBean.setId(timeSeries.getId());
			timeSeriesBean.setTicker(timeSeries.getStock().getTicker());
			timeSeriesBean.setDate(timeSeries.getTime());
			timeSeriesBean.setOpen(timeSeries.getopenPrice());
			timeSeriesBean.setClose(timeSeries.getclosePrice());
			timeSeriesBean.setHigh(timeSeries.gethighPrice());
			timeSeriesBean.setLow(timeSeries.getlowPrice());
			timeSeriesList.add(timeSeriesBean);
		}
		return timeSeriesList;
	}
	

}
