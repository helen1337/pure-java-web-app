## introduction

A blog site based in JSP/Servlet with the ability to log in and check the weather.

A Java web application site that strives to implement the MVC pattern as much as possible without the use of frameworks.

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

1. Run the table creation SQL script src/blog.sql in your database.
2. Set up a server environment, such as Tomcat, and import the entire demo.
3. Use Maven to build the project.
4. Visit localhost:xxx/my-blog/ to access the site.


## directory overview

### java
* dao - database interface classes;
* daoImple - database interface implementation classes;
* models - bean package;
* parsers - information parser;
* requester - class for requesting external API;
* service - service layer for web;
* servlets - controllers servlet
* utils - utility package

### webapp
* list of main web pages
* blocks - regular page patterns
* css - extra styles
* js - extra scripts

