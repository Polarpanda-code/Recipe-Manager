# Recipe-Manager
A standalone java application which allows users to manage their favourite recipes
# Setup guide
Minimum Requirements
Java 11
Maven 3.x

Install the application
Make sure you have Java and Maven installed

Open the command line in the source code folder

Build project

$ mvn package
Run the tests

$ mvn test
Run the project

$ java -jar recipe-0.0.1-SNAPSHOT.jar

Open the swagger-ui with the link below
http://localhost:8080/swagger-ui.html#/


1. Here the relationship is One to many between Recipe and Ingredients.But it can be other relationship.
2. Test cases covered 
3. Exceptions Handled using standard @controllerAdvice
4.  Used gradle to build faster than maven.
5.  Swagger implementation for api .

# What More can be done : 

1. we can create Consumer driven contracts for thsi api using PACT.

2 Logs can be handled  using logback  framework for pushing to logstash or kibana or instana or grafana.

3. DockerFile can be created .
   
4. More custom annotation processor can be made replacing many validation.


