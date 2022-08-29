import React, {Component, Fragment} from 'react';
import Select, {components} from 'react-select';
import stockList from './tickers.json';
import './StockDetailManager.css';
import { toHaveAccessibleDescription } from '@testing-library/jest-dom/dist/matchers';


export default class StockDetailManager extends Component{
  constructor(props){

    super(props);
    this.state = {
      stockDetail: [],
      ticker: '',
      tickers: [],
      availableTickers: stockList,
      selectedTicker: null,
      tickerList:[],
      message: '',
      companyData: {

      }
    }
  }

  componentDidMount() {
    fetch('stocks/alltickers')
    .then(response => response.json())
    .then(
      (data) => {
        this.setState({
          tickerList: data
        })
      },
      (error) => {

      }
    )
    
  }

  handleTickerInput = e => {
    this.setState({
      ticker: e.target.value,
    })
  }

  handleAddTicker = () => {
    const {selectedOption, tickerList} = this.state;
    if(typeof selectedOption === 'undefined'){
      return;
    }
    const index = tickerList.findIndex(ticker=> ticker.ticker === selectedOption.value);

    const tickerListUpdate = [...tickerList];

    if(index >= 0) {
      tickerListUpdate[index].companyName = selectedOption ? selectedOption.label : '';
      this.setState({
        message: 'the selected ticker is already in the list',
        tickerList: tickerListUpdate,
      });
      return;
    }

    tickerList.push(
      {
        ticker: selectedOption ? selectedOption.value : '',
        companyName: selectedOption ? selectedOption.label : ''
      }
    );
    this.setState({
      tickerList,
      message: '',
    })
  }

  mapStockDetail = (data, prop) => {
    return data.reduce((acc, item) => {
      let key = item[prop];
      if(!acc[key]){
        acc[key] = [];
      }
      acc[key].push(item);
      return acc;
    }, {}) 
  }

  getDetails = () => {
    const{tickers} = this.state;
    fetch('stocks/timeseries',
    {
      method: "POST",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(tickers)
    })
    .then(res => {    /* IF statement checks server response: .catch() does not do this! */ 
        if (res.ok) { console.log("HTTP request successful") }
        else { console.log("HTTP request unsuccessful") }
        return res
    })
    .then(res => res.json())
    .then(data => {
      const formattedData = this.mapStockDetail(data, 'ticker');
      const tableData = Object.entries(formattedData);
      this.setState({stockDetail: tableData})
    }) // the data
    .catch(error => console.log(error))
  }

  handleOptionChange = (selectedOption) => {
    this.setState({
      selectedOption
    });
  }

  handleCheckboxChange = (e) => {
    const {id, checked} = e.target;
    const company = id.split("-");
    const {tickers} = this.state;
    let checkedTickers = [...tickers];
    if (checked) {
      checkedTickers.push({ticker: company[0], companyName: company[1]});
    } else {
      checkedTickers = tickers.filter(ticker => ticker.ticker !== company[0]);
    }

    this.setState({
      tickers: checkedTickers,
    });
  }


  removeTicker = () => {
    const{tickers, tickerList} = this.state;
    fetch('stocks/timeseries',
    {
      method: "DELETE",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(tickers)
    })
    .then(res => {    /* IF statement checks server response: .catch() does not do this! */ 
        if (res.ok) { console.log("HTTP request successful") }
        else { console.log("HTTP request unsuccessful") }
        return res
    })
    .then(res => res.json())
    .then(data => this.setState({
      stockDetail: [],
      
      tickerList: this.extractSelection(tickerList, tickers),
      tickers: [],
      
    })) // the data
    .catch(error => console.log(error))
  }
  extractSelection = (completeArray, arrayToRemove) => {
    if (completeArray.length === 0 || arrayToRemove.length === 0) {
      return completeArray;
    }
    return completeArray.filter(
      arrayItem => !arrayToRemove.some(removeItem => removeItem.ticker === arrayItem.ticker),
    );
  };

  render() {
    const {title} = this.props;
    const {ticker, tickers, stockDetail, message, selectedOption, availableTickers, tickerList} = this.state;
    return (
      <div className= "flex-container">

          <div className="flex-left">
            <h2>select a company</h2>
            <Select 
              onChange = {this.handleOptionChange} 
              value = {selectedOption} 
              //components = {{singleValue}} 
              options = {availableTickers} 
            />
            {/* <input type = 'text' onChange = {this.handleTickerInput} value = {ticker} placeholder = "add ticker here"/> */}
            <div >
               <button className="button button-shadow"  onClick={this.handleAddTicker}> Add to Stock List </button>
            </div>
            <h2>My Stock List</h2>
            <div className="inbox">
              {tickerList.map(
                ticker=>
                  <div className="list-item">
                    <input type = 'checkbox' id ={ticker.ticker + '-' + ticker.companyName} onClick={this.handleCheckboxChange}/>
                    <p>{ticker.ticker + ' (' + ticker.companyName + ')'}</p>
                  </div>
              )}
            </div>
            
            <div>
              <button  className="button button-shadow" onClick={this.removeTicker}> Remove from List</button>
              <button  className="button button-shadow" onClick={this.getDetails}> Get Details </button>
            </div>
            
          </div>
        <div className="flex-right">
          <h1>{title}</h1>
          
            {stockDetail && stockDetail.length > 0 &&
              stockDetail.map(([key, value]) =>
                <div className="table-container">
                  <h2>{(value && value.length > 0 ? value[0].companyName : '') + ' - ' +  key}</h2>
                  <table class='tsTableElements' id="tsTable">
                    <thead>
                      <th class='tsTableElements'>Date/Time</th>
                      <th class='tsTableElements'>Open Price</th>
                      <th class='tsTableElements'>Close Price</th>
                      <th class='tsTableElements'>Highest Price</th>
                      <th class='tsTableElements'>Lowest Price</th>
                    </thead>
                    <tbody>
                      {value && value.length > 0 && value.map(stock =>
                      <tr>
                        <td class='tsTableElements'>{stock.date}</td>
                        <td class='tsTableElements'>${stock.open}</td>
                        <td class='tsTableElements'>${stock.close}</td>
                        <td class='tsTableElements'>${stock.high}</td>
                        <td class='tsTableElements'>${stock.low}</td>
                      </tr>)}
                    </tbody>
                    
                  </table>
                </div>
                
              )
            }
          
        </div>
          
        
    
      </div>

    );
  }
}