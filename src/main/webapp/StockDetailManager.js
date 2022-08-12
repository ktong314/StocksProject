import React, {Component} from 'react';
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
    fetch("/timeseries/alltimeseries")
    .then(response => response.json())
    .then(
      (data) => {
        this.setState({
          stockDetail: data
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
      tickerListUpdate[index].displayText = selectedOption ? selectedOption.value + " ("+selectedOption.label +")" : '';
      this.setState({
        message: 'the selected ticker is already in the list',
        tickerList: tickerListUpdate,
      });
      return;
    }

    tickerList.push(
      {
        ticker: selectedOption ? selectedOption.value : '',
        displayText: selectedOption ? selectedOption.value + " (" + selectedOption.label + ")" : ''
      }
    );
    this.setState({
      tickerList,
      message: '',
    })
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
    .then(data => this.setState({
      stockDetail: data,
    })) // the data
    .catch(error => console.log(error))
  }

  handleOptionChange = (selectedOption) => {
    this.setState({
      selectedOption
    });
  }

  handleCheckboxChange = (e) => {
    const {id, checked} = e.target;
    const {tickers} = this.state;
    if (checked) {
      tickers.push(id);
    } else {
      tickers = tickers.filter(ticker => ticker !== id);
    }
    this.setState({
      tickers,
    })

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
      stockDetail: data,
      
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
      arrayItem => !arrayToRemove.some(removeItem => removeItem === arrayItem.ticker),
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
            <button type = 'button' onClick={this.handleAddTicker}> Add to Stock List </button>
            <button type = 'button' onClick={this.getDetails}> Get Details </button>
            <h2>My Stock List</h2>
            <div className="inbox">
              {tickerList.map(
                ticker=>
                  <div className="list-item">
                    <input type = 'checkbox' id ={ticker.ticker} onClick={this.handleCheckboxChange}/>
                    <p>{ticker.displayText}</p>
                  </div>
              )}
            </div>
            <button type = 'button' onClick={this.removeTicker}> Remove from List</button>
          </div>
        <div className="flex-right">
          <h1>{title}</h1>
          <table class='tsTableElements' id="tsTable">
            <thead>
              <th class='tsTableElements'>Ticker</th>
              <th class='tsTableElements'>Date/Time</th>
              <th class='tsTableElements'>Open Price</th>
              <th class='tsTableElements'>Close Price</th>
              <th class='tsTableElements'>Highest Price</th>
              <th class='tsTableElements'>Lowest Price</th>
            </thead>
            {stockDetail && stockDetail.length > 0 &&(<tbody>
              {
                stockDetail.map(
                  stock =>
                  <tr key = {stock.id}>
                    <td class='tsTableElements'>{stock.ticker}</td>
                    <td class='tsTableElements'>{stock.date}</td>
                    <td class='tsTableElements'>${stock.open}</td>
                    <td class='tsTableElements'>${stock.close}</td>
                    <td class='tsTableElements'>${stock.high}</td>
                    <td class='tsTableElements'>${stock.low}</td>
                  </tr>
                )
              }
            </tbody>)}
          </table>
          <p id='message'>{message}</p>
        </div>
          
        
    
      </div>

    );
  }
}