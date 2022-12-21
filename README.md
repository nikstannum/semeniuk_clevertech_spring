# Test task from the company Clever Technology
## A simple application for receiving a sales receipt
##### Author: Nikita Semeniuk

## Description

The idea is to provide the application with a set of parameters (product id and, if available, discount card id) passed as GET request parameters, and the application will generate a sales receipt in the form of a JSON object.

GET request format: http://localhost:8080/check?itemId=1&itemId=1&itemId=2&card=1.

This request means that the receipt must contain two products with id=1, one product with id=2, and a discount for a discount card with id=1 must also be applied.
Products and discount cards indicating the amount of discount for a particular card are stored in a relational database.

Stack used:
Java 17, Spring Framework (Core container, Spring MVC, Spring Data (JDBC), tool Spring Boot), buil tool Gradle 7.5, DB PostgreSQL, Lombok plugin, log4j2

## Startup instructions:

1) run the application in the TOMCAT servlet container (this application was developed using the version 9.0.62)
2) open browser (Google Browser should be preferred)
3) open developer tools (if you are not using browser plugins like JSON viewer)
4) enter a request in the address bar in the format http://localhost:portNumber/check?params (for example: http://localhost:8080/check?itemId=1&itemId=1&itemId=2&card=1)

## Endpoints description:

This application has one API endpoint, which is mapped by a GET-request to the address http://localhost:8080/check
