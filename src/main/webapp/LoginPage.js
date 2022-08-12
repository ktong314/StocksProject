import React, {Component} from 'react';
import StockDetailManager from './StockDetailManager';
import './LoginComponent.css';

export default class LoginPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            showSuccessMessage: false,
            hasLoginFailed: false
        }
        this.handleChange = this.handleChange.bind(this);
        this.loginClick = this.loginClick.bind(this);
    }

    handleChange(event) {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    loginClick() {

       const {username, password } = this.state;

       const payload = {username, password};

       this.setState({
        hasLoginFailed: false,
        showSuccessMessage: true,
        message: "Success",
       });
    //    fetch('/stocks/login', 
    //    {
    //      method: "POST", 
    //      headers: {
    //          'Content-type': 'application/json'
    //      },
    //      body:  JSON.stringify(payload)
       
    //      })
    //      .then(res => {
    //        if (res.ok) { console.log("HTTP request successful") }
    //        else { console.log("HTTP request unsuccessful") }
    //        return res
    //      })
    //      .then(res => res.json())
    //      .then( data => this.setState({
    //         hasLoginFailed: false,
    //         showSuccessMessage: true,
    //         message: data,
    //      })) // the data
    //      .catch(error =>    this.setState({
    //         hasLoginFailed: true,
    //         showSuccessMessage: false,
    //         message: error,
    //     }));

    }
  

    render() {
    
        return (
            <>
                {!this.state.showSuccessMessage && (<div className="login-form">

                    {this.state.hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}

                    <form action='#'>
                        <h2 className="text-center">Log in</h2>
                        <div className="form-group">

                            <input type="text" name="username" className="form-control" value={this.state.username}
                                onChange={this.handleChange}></input>
                        </div>
                        <div className="form-group">
                            <input type="password" name="password" className="form-control" value={this.state.password}
                                onChange={this.handleChange}></input>
                        </div>

                    </form>

                    <div className="form-group">
                        <button className="btn btn-primary btn-block" onClick={this.loginClick}>Login</button>
                    </div>

                </div>)}
                {this.state.showSuccessMessage &&  <StockDetailManager title="Stocks Manager"/>}
            </>
        );
    }
    
}