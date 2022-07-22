import React, {Component} from 'react';
import './App.css';


export default class StockDetailManager extends Component{
  constructor(props){

    super(props);
    this.state = {
      stockDetail: [],
      ticker: '',
      tickers: [],
      message: '',
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
    const {ticker, tickers} = this.state;
    tickers.push(ticker);
    this.setState({
      tickers,
    })
  }

  saveTicker = () => { 
    const {tickers} = this.state;
    fetch('stocks/newstocks', {
      method: "POST",
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(tickers)
    })
    .then(res => {
      if (res.ok) { console.log("HTTP request successful") }
      else { console.log("HTTP request unsuccessful") }
      return res
    })
    .then(res => res.json())
    .then(data => this.setState({
      message: "success"
    }))
    .catch(error =>
      this.setState({
        message: error
      })
    )
  }

  getDetails = () => {
    const{tickers} = this.state;
    fetch('timeseries/alltimeseries')
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

  render() {
    const {title} = this.props;
    const {ticker, tickers, stockDetail, message} = this.state;
    return (
      <div className="App">
        <header className="App-header">
          <p>
            {title}
          </p>
          <div>
            <input type = 'text' onChange = {this.handleTickerInput} value = {ticker} placeholder = "add ticker here"/>
            <button type = 'button' onClick={this.handleAddTicker}> Add Ticker </button>
          </div>
          <table>
            <thead>
              <th>Ticker</th>
            </thead>
            {tickers && tickers.length > 0 &&(<tbody>
              {
                tickers.map(
                  ticker =>
                  <tr>
                    <td>{ticker}</td>
                  </tr>
                )
              }
            </tbody>)}
          </table>
          <div>
            <button type = 'button' onClick={this.saveTicker}> Save </button>
            <button type = 'button' onClick={this.getDetails}> Get Details </button>
          </div>
          <table>
            <thead>
              <th>Ticker</th>
              <th>Date/Time</th>
              <th>Open Price</th>
              <th>Close Price</th>
              <th>Highest Price</th>
              <th>Lowest Price</th>
            </thead>
            {stockDetail && stockDetail.length > 0 &&(<tbody>
              {
                stockDetail.map(
                  stock =>
                  <tr key = {stock.id}>
                    <td>{stock.ticker}</td>
                    <td>{stock.date}</td>
                    <td>{stock.open}</td>
                    <td>{stock.close}</td>
                    <td>{stock.high}</td>
                    <td>{stock.low}</td>
                  </tr>
                )
              }
            </tbody>)}
          </table>
          <p>{message}</p>
        </header>
      </div>
    );
  }
}
// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

// export default App;
