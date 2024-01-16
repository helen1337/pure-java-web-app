## introduction

A blog site based in JSP/Servlet with the ability to log in and check the weather.

A Java web application site that strives to implement the MVC pattern as much as possible without the use of frameworks.

A single-threaded socket server is implemented inside the application to communicate with an external API to receive a weather forecast.

The features: such as homepage preview, article publishing (with and without authorization), search articles by topic and author,view the weather in the city.

The site takes into account whether you are an authorized user to protect your data, but does not restrict the ability of unauthorized visitors.

## key knowledge:

* Servlet
* JSP
* MySQL
* Bootstrap
* HTML/CSS/JS
* JSON
* Maven

## quick run:
### pre-requisites
1. install and use JDK 19+
2. install and use Apache Maven 3.8.1+
3. install and use your favorite servlet engine
### run
3. implement the file `config.properties` in `src/main/resources` using `config_template.properties` as a template:

`urlDB`, `driverDB`, `userDB`, `passwordDB` - data for connecting to your database;

`apiKey` - token for using the weather API, get on https://openweathermap.org/

4. run the table creation SQL script `src/blog.sql` in your database
5. build project
```
mvn package
```
6. deploy `my-blog.war` to your favorite servlet engine
7. visit localhost:xxx/my-blog/ to access the site


## directory overview

### java
* api - package with server and client parts for communication over sockets and requests for weather information
* dao - database interface classes
* daoImpl - database interface implementation classes
* models - bean package
* parsers - information parser
* service - service layer for web
* servlets - controllers servlet
* utils - utility package

### webapp
* list of main web pages
* blocks - regular page patterns
* css - extra styles
* js - extra scripts

### resources
* config.properties - basic properties for launching the application