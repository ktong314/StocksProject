Stock performance tracker App
Web App for stock ticker tracker, Building a personal Portfolio
Built With
Java: 
 spring boot: Makes it easy to create stand-alone, RESTFul APIs web application 
 Nodejs: a runtime environment used to execute JavaScript for server-side scripting.
JavaScript(ES6): the project's front programming language with ES6 Syntax 
 React,  a JavaScript library used to assist with efficient management of rapidly changing data and maintaining a single-page web application structure.
HTML and CSS3 : used to manage the presentation and styling of the project.
 Apache Derby: A Relational Database Management System which is fully based on (written/implemented in) Java programming language. It is an open source database developed by Apache Software Foundation.
Webpack:  is a module bundler. Its main purpose is to bundle JavaScript files for usage in a browser. Create a file named webpack.config.js to describe the configurations required for webpack.
 Gradle: Use to build Java project
 Tools
The following items should be installed in your system:
Eclipse - Spring Tool Suite or Mars
Server - Apache tomcat 7
Visual Studio for react js
Install Node JS (https://nodejs.org)
Install gradle 6
Postman
Purpose
This is my first big project to develop a full stack app on my own.
It is a web application that acquires stock market data and. Users can get real-time stock data using
live retrieval of stock data  https://twelvedata.com/
Creation of personal stock portfolios, 
Monitor stocks performance 
Analysis the stock market
Future to do list 
Adding charts visualization (research) 
Implementing feature to analyze stocks performance
Demo 
	Use screen to gif to record the UI workflow
Steps
API key is required from https://twelvedata.com/
Use Spring Initializer: Just go to https://start.spring.io/ and generate a new spring boot gradle project.
select : 
gradle project
Spring boot 2.612(snapshot)
Add dependencies: 
 Jersey, 
 Apache Derby database
Java 17
Provide project name: exp: demo
Click generate
     3. Create gradle project from eclipse with newly generated project: demo
     4. Add extra dependencies to build.gradle like following
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

     4. Understand spring boot design pattern and workflow



Java packages
Controller: Rest layer to accept the request from UI and send response to UI

Servie: Business logic layer to implement features  that facilitates communication between the controller and the persistence layer. Additionally, It includes validation logic in particular.
Domain:  Data access object class to define database table entity and relationship between tables (one to many, many to one, many to many)

Repository: Data access layer, using Spring data JPA repositories to provides JPA related methods such as flushing the persistence context and delete records in a batch
https://www.baeldung.com/spring-data-repositories


	5. Config application.properties
		spring.datasource.url=jdbc:derby:stocklocal; create=true
spring.datasource.username=derbyuser
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.DerbyTenSevenDialect

          6. Creating a new single-page app

Install node.js 
npx create-react-app my-app
cd my-app
npm start
     7. Understand React state and lifecycle: https://reactjs.org/docs/state-and-lifecycle.html
         8. Add UI dependencies to package.json
         9. Integrating UI with spring boot backend
Create webapp folder under src/main
Create index.html file under src/main/resources/static/dist and make sure the html body has following

  <body>
        <div id="root"></div>
 
        <script src="dist/react-app.js"></script>
   </body>

Modify index.js to have this statement :
const root = ReactDOM.createRoot(document.getElementById('root'));
    'root' should be same as id of div in the index.html 
   10. Gradle with Node
Build react app with gradle  , add following in build.gradle
plugins {
    id 'org.springframework.boot' version '2.7.1'
    id 'java'
    id "com.github.node-gradle.node" version "3.0.1"
}
node {
    version = '14.15.5'
}
Add following in package.json
{
  "name": "spring-react-app",
  "description": "Stock market data tacker application using React with Spring Boot",
  "dependencies": {
	Add dependencies here
  }
}

11. Bundle react js with webpack
  
create configuration for Webpack, webpack.config.js, in the root directory.
	
                 
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

12. Use gradle to build reactjs, add following to build.gradle
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
13. Build the application: gradle build and run the app  gradlew bootrun

14: open http://localhost:8080/ in the browser


How to Run
 
 
Practice the knowledge of the Java language
 hands on experience with basic CRUD operations using the Spring framework, JPA, Apache derby
Create the REST API service for the backend application using spring boot framework, JPA, Apache derby
Developing and running Spring Boot applications with one of the following IDE's: Eclipse, Visual Studio
Practice the knowledge of building process with gradle
Leaning building UI with  HTML, CSS, and JavaScript (ES6 Syntax is important), and React lifecycle
Integrate NodeJS with React
  
