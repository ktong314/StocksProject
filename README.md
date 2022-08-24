# Stock performance tracker App
Web App for stock ticker tracker, Building a personal Portfolio
### Built With
* Java: <br />
* spring boot: Makes it easy to create stand-alone, RESTFul APIs web application <br />
* Nodejs: a runtime environment used to execute JavaScript for server-side scripting. <br />
* JavaScript(ES6): the project's front programming language with ES6 Syntax <br />
* React,  a JavaScript library used to assist with efficient management of rapidly changing data and maintaining a single-page web application structure. <br />
* HTML and CSS3 : used to manage the presentation and styling of the project. <br />
* Apache Derby: A Relational Database Management System which is fully based on (written/implemented in) Java programming language. It is an open source database developed by Apache Software Foundation. <br />
* Webpack:  is a module bundler. Its main purpose is to bundle JavaScript files for usage in a browser. Create a file named webpack.config.js to describe the configurations required for webpack. <br />
* Gradle: Use to build Java project <br />
### Tools
The following items should be installed in your system: <br />
* Eclipse - Spring Tool Suite or Mars <br />
* Server - Apache tomcat 7 <br />
* Visual Studio for react js <br />
* Install Node JS (https://nodejs.org) <br />
* Install gradle 6 <br />
* Postman <br />
### Purpose
This is my first big project where I develop a full stack app on my own. It is a web application where users can: <br />
* get real-time stock data using live retrieval from  https://twelvedata.com/ <br />
* create personal stock portfolios <br />
* monitor stocks performance <br />
* analyze the stock market <br />
### Future to do list 
* Add charts for visualization (research which chart library to use) <br />
* Implement feature to analyze stocks performance

### Steps
1. API key is required from https://twelvedata.com/
2. Use Spring Initializer: Just go to https://start.spring.io/ and generate a new spring boot gradle project. <br />
	select : <br />
	* gradle project
	* Spring boot 2.612(snapshot)
	* Add dependencies: Jersey, Apache Derby database
	* Java 17
	* Provide project name: exp: demo
	* Click generate
3. Create gradle project from eclipse with newly generated project: demo
4. Add the following extra dependencies to build.gradle
```
dependencies {
	implementation group: 'com.opencsv', name: 'opencsv', version: '5.6'
	implementation 'com.googlecode.json-simple:json-simple:1.1.1'
	implementation 'org.springframework.boot:spring-boot-starter-jersey'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.apache.derby:derby'
	implementation group: 'org.slf4j', name: 'slf4j-api', version: '1.7.25'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testImplementation 'org.junit.jupiter:junit-jupiter-engine:5.8.1'    

}
``` 
5. Understand spring boot design pattern and workflow: <br />
	Java packages <br />
	* Controller: Rest layer to accept the request from UI and send response to UI
	* Service: Business logic layer to implement features  that facilitates communication between the controller and the persistence layer. Additionally, It includes validation logic in particular.
	* Domain:  Data access object class to define database table entity and relationship between tables (one to many, many to one, many to many) <br />
	* Repository: Data access layer, using Spring data JPA repositories to provides JPA related methods such as flushing the persistence context and delete records in a batch https://www.baeldung.com/spring-data-repositories

6. Configure application.properties:
```
spring.datasource.url=jdbc:derby:stocklocal; create=true
spring.datasource.username=derbyuser
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.DerbyTenSevenDialect
```

7. Create a new single-page app through cmd
```
Install node.js 
npx create-react-app my-app
cd my-app
npm start
```
8. Understand React state and lifecycle: https://reactjs.org/docs/state-and-lifecycle.html
9. Add UI dependencies to package.json
10. Integrating UI with spring boot backend
* Create webapp folder under src/main
* Create index.html file under src/main/resources/static/dist and make sure the html body has following
```
<body>
	<div id="root"></div>
 	<script src="dist/react-app.js"></script>
</body>
```
* Modify index.js to have this statement:
```
const root = ReactDOM.createRoot(document.getElementById('root'));
//'root' should be same as id of div in the index.html 
```
11. Configure gradle with Node
* Build react app with gradle, add following in build.gradle
```
plugins {
    id 'org.springframework.boot' version '2.7.1'
    id 'java'
    id "com.github.node-gradle.node" version "3.0.1"
}
node {
    version = '14.15.5'
}
```
* Add following in package.json
```
{
  "name": "spring-react-app",
  "description": "Stock market data tacker application using React with Spring Boot",
  "dependencies": {
	Add dependencies here
  }
}
```
12. Bundle react js with webpack
* create configuration for Webpack, webpack.config.js, in the root directory.
	
```          
         module.exports = {
    devtool: 'source-map',
    output: {
        filename: 'react-app.js'
    },
    module: {
        rules: [{
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            loader: "babel-loader",
            options: {
                presets: ['@babel/preset-env', '@babel/preset-react']
            }
        }]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    }
};
```

13. Use gradle to build reactjs, add following to build.gradle
```
task buildReactApp(type: NodeTask, dependsOn: 'npmInstall') {
    script = project.file('node_modules/webpack/bin/webpack.js')
    args = [
            '--mode', 'development',
            '--entry', './src/main/webapp/stocks/index.js',
            '-o', './src/main/resources/static/dist'
    ]
}
 
processResources.dependsOn 'buildReactApp'
clean.delete << file('node_modules')
clean.delete << file('src/main/resources/static/dist')
```

14. Build the application: gradle build and run the app  gradlew bootrun

15: open http://localhost:8080/ in the browser


### How to Run
* Practice the knowledge of the Java language
* hands on experience with basic CRUD operations using the Spring framework, JPA, Apache derby
* Create the REST API service for the backend application using spring boot framework, JPA, Apache derby
* Developing and running Spring Boot applications with one of the following IDE's: Eclipse, Visual Studio
* Practice the knowledge of building process with gradle
* Leaning building UI with  HTML, CSS, and JavaScript (ES6 Syntax is important), and React lifecycle
* Integrate NodeJS with React
  
