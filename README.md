# Lendico application - Ievgen Kliuchkevych

### How to run 
Preconditions: 
 - Java is installed, JAVA_HOME is configured
 - Maven is installed and added to the path
 
 ####To run application:
 - download the code
 - navigate to the plangenerator folder:
 - run command prompt and type
 - mvn install
 - java -ea -jar target/plangenerator-0.0.1-SNAPSHOT.jar
 
 #### How to test
 Send POST localhost:8080/repayment-plan request with body (application type = json)
 ```
 {
 "loanAmount": "5000",
 "nominalRate": "5.0",
 "duration": 24,
 "startDate": "2018-01-01T00:00:01Z"
 }
 ```
 
### Some decisions clarification

#### Comments
I've added javadocs to public methods. 
'Comment:' remarks to for production code, but to explain some my thoughts.
I've added inline comments for RepaymentPlanService. I prefer to avoid them but added because of assignments' requirement.

#### BigDecimal
 I've choosen BigDecimal type to store data and calculation to save big precision.
 I think it's important during for financial operations. It provides wide opportunity to work with numbers. And it's pretty easy and flexible formatting options.
 
#### Validation
 I've introduced separate Api entities to read data from user and provide data to user in controlled maner.
 During reading input data I've added simple validation of fields filling on the API layer.
 Mappers of the resulting data give us the opportunity to format data to end user in what way we want
 without touching of internal implementation.


#### Exceptions
I've introduced own RepaymentCalculationException to catch some of the predictable exceptions and show to the user
in a friendly manner. 
Error handlers provide the ability to show user-friendly messages instead of Internal Server Errors.

#### Builders
Some entities have own builder. It's not mandatory options but helps to build object simpler.

#### Tests
I've added several unit tests. They cover main scenarios, but not all exceptional cases. 
It's nice to add, but leave as is because of lack of time.
I've added one test for endpoint level. It is more integration test than a unit test because it starts application context and runs real call.
Also should be extended with additional cases and checks. 

### Code improvements
- use Lombok to get rid of getters, setters and other stuff
- add logging to save what happened with application
- write more tests
- extend validation to not allow user send incorrect data

### Application improvements
- As far as this application is stateless it's suitable to convert it to the AWS lambda function. It allows to run it on demand and sleep after.
- It is possible to add caching to the application layer to not start calculation for the same requests
- We can store result of calcualtion to the DB for statistic and history
 

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

