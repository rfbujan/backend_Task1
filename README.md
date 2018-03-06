# Back-end Technical Assessment

Introduction
============

This is the solution provided as response of the Addison Global Backend Technical Assessment. 

As requested, this document contains relevant information related to technical assumptions/decisions made, as well as instructions for running the solution and its tests in a Linux environment.

The document is divided in four main sections: one for general considerations applicable to all projects; and other three sections, each on dedicated to each of the tasks to be implemented. 

All sections have the same structure:
- 1 **Assumptions and Decisions made** --> Technical overview of the solution
- 2 **Technical choices** --> third-party libraries used
- 3 **Room for improvement** --> points where the solution can be improved
- 4 **Instructions** --> instructions on how to run the solution and its tests

**IMPORTANT:** for downloding, compile and run the application the following tools are needed:
- GIT
- maven 3
- Java 8

General Considerations
======================

The programming language chosen for implementing this exercise is Java 8.  All modules have been implemented following the SOLID design principles (promoted by Robert C. Martin) in order to provide a more understandable, flexible and maintainable solution. 

Unit tests are provided for all classes that implement some functionality, leaving out datamodel objects (such as User, UserToken and Credentials) and interfaces. These tests have been implemented following the F.I.R.S.T principles where unit tests must be Fast, Independent, Repeatable and Self-Validating.

All provided code has been properly commented. Comments have been kept minimal in order to avoiding redundant and noisy comments that add no value to the solution but rather clutter. All public interfaces have been documented with Javadocs. And the JCIP annotation has been used for document concurrent programming matters (as recommended in Java Concurrency In Practice book.). 

As Robert C. Martin said: 
> "The proper use of comments is to compensate for our failure to express in code. Note that I used the word failure. I meant it. Comments are always failures.". 

In order to achieve code that is easy to read and go through, all functionality has been implemented in methods which are short, well named, and nicely organized. For private methods by default comments have not been added since none of them implement a too complicated functionality that cannot be clearly express in code. In addition, a format template (*format_backend_test_template.xml*) has been created in order to get easily a nice format of the code which is applied all across the code solution.

Assumptions and Decisions made
------------------

The three tasks have been implemented under the same repository but in different Java projects. The relation of projects with the tasks that they implement is as follows

  1. task1 (service interface)
  	* task1_token_service
  2. task2 (Service Implementation)
	* task2_common
	* task2_authenticator
	* task2_token_creator
	* task2_token_provider
  3. task3 (REST API)
  	* task3_token_rest_service

The dependencies and build life-cycle for all three solutions (5 projects) are governed by Maven. There is a parent maven pom file that defines the different implemented modules. Therefore the build life-cycle of all 5 projects can be managed from the main repository folder using the different maven goals (e.g. `mvn clean install` will build and install all artifacts used by the projects into the local repository ). 

In addition, the parent maven pom defines three profiles (task1, task2 and task3), which allows to govern the different solutions for each task independently. (e.g. `mvn -Ptask2 clean install` ) will only affect to the projects that forms the solution for the task 2.

Technical choices
-----------------

- **Eclipse Oxygen** as IDE. 
- **Maven 3** as dependency and build life cycle management tool. 
- **Junit 4** as unit test framework.
- **Mockito** as mocking framework.
- **JCIP** for concurrent annotation.
- **GIT** as control version tool. 
 
Room for improvement
--------------------

There is room for improvement in different parts of the solution. Here are only listed some of the improvements that applies to all three solutions. Additional improvements will be commented for a particular task in its corresponding section.

- **Logging mechanism:** Logging framework (such as log4j) that records different levels of relevant events that takes place in a operating system. 
- **Code coverage tool:** Coverage tools (such as sonarQ) report gaps in your testing strategy. They make it easy to find modules, classes, and functions that are insufficiently tested. 

Instructions 
------------
For downloading the whole exercise just clone the repository with the following command:
`git clone https://github.com/rfbujan/backend_technical_test.git`

This git command will create a folder called *backend_technical_test* which contains the whole repository and therefore the five Java projects.

The dependencies as build life-cycle for all three solutions (5 projects) are governed by Maven. There is a parent maven pom file that defines the different implemented modules. Therefore the build life-cycle of all 5 projects can be managed from the main repository folder using the different maven goals (e.g. `mvn clean install` will build and install all artifacts used by the projects into the local repository ). 

For running tests, `mvn test` will run all unit tests and `mvn failsafe:integration-test` will run the integration tests.   


Service Trait / Interface
=========================

The solution for this tasks is implemented by a single Java project called *task1_token_service*. this project is wrapped in a maven artifact (*token_service_interface*) and it is declared under the profile *task1* in the main maven pom file. 

As requested in the assessment, this project implements two versions of the `requestToken(Credentials credentials)` method. One implementation is synchronous and another one is asynchronous.
 
Assumptions and Decisions made
------------------------------

The original interface suggested by the assessment has been splitted in three interfaces:  
 - `TokenAuthenticator` : provides the means needed for generating a user Token for a given user. `User authenticate(Credentials credentials)` method
 - `TokenCreator`: provides the means for validating given credentials and generate a user from them. `UserToken issueToken(User user)`
 - `TokenProvider`: provides the means for requesting a user token based on given credentials. This interface provides two different implementations for requesting a token:
 	- `<UserToken requestToken(Credentials credentials)>`: synchronous implementation where clients need to wait for the token to be returned
 	- `<CompletableFuture<UserToken> requestToken(Credentials credentials)>`: Asynchronous implementation where clients don't need to wait for the token to be returned.
 	
The original interface, suggested in the assessment, has been splitted in three interface in order to follow the Single Responsibility Principle (SRP) which states that a class or module should have one, and only one, reason to change. Class should have one responsibility. 

As suggested, both implementations of *requestToken* have been designed in terms of *authentificate* and *issueToken*. This way, who ever implements the services will only need to implement the interfaces `TokenAuthenticator` and `TokenCreator` which contain the methods `User authenticate(Credentials credentials)` and `UserToken issueToken(User user) ` respectively. 

The non-blocking nature of the `CompletableFuture<UserToken> requestToken(Credentials credentials)` is provided by the *CompletableFeature* added in Java 8 (as suggested in the assessment). For doing that, the asynchronous *requestToken* method makes use of the *supplyAsync* factory method which accepts a *Supplier* as argument and returns a *CompletableFuture* that will be asynchronously completed with the value obtained by invoking that *Supplier*. This *Supplier* will be run by one of the *Executors* in the *ForkJoinPool*.   

This *Supplier* used is as follows:

```java
    public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials)
    {

	return CompletableFuture.supplyAsync(() ->
	{
	    return requestToken(credentials);
	});
    }
``` 
All three objects that formed the data model of the project (i.e. User, Credentials and UserToken) are immutable objects for which its state cannot be updated. For this reason the objects have been annotated as Thread-safe. All three objects also override the methods *equals* and *hashCode* (these methods have been automatically generated by the eclipse IDE).

In addition, two additional obejcts have been created: *TokenCreationException* and *TokenAuthentificationException*. These two objects represent checked Exceptions that can be thrown by the methods `User authenticate(Credentials credentials)` and `UserToken issueToken(User user) `. This exceptions are handled by the implementation of the `UserToken requestToken(Credentials credentials)` method which is the one that makes use of these aforementioned methods. As can be seen in the code below, if any of this exceptions take place then an *invalid* user token is returned. 
```java
public UserToken requestToken(Credentials credentials)
    {
	UserToken token;
	try
	{
	    token = authenticateAndIssueToken(credentials);

	} catch (TokenCreationException | TokenAuthentificationException e)
	{
	    token = createInvalidUserToken();
	}
	return token;
    }
```
In this task, it is assummed that a user token is considered *invalid* when its token contains the keyword *INVALID*

The unit tests implemented checks:
- Nominal scenraio
- TokenAuthentificationException occurs
- TokenCreationException occurs

For implementing the different scenraios the implementations for the interfaces `TokenAuthenticator` and `TokenCreator` are mocked.

Technical choices
-----------------

*No additional technical choices besides the ones already mentioned on the General Considerations section* 

Room for improvement
--------------------

**Custom *Executor*:** A custom *Executor* can be passed as a second argument to the overloaded version of the *supplyAsync* factory method. The performance can be improved by creating a new *Executor* that fits the characteristics of the application maximizing the usage of available resources.  

Instructions 
------------

As mentioned in previous sections, maven is the tool chosen for the build life-cycle management and a profile called task1 has been created for this particular solution. As this solution, is not a complete product, it cannot be deployed or run as a normal application. The evaluation of this task has to be performed by inspecting the source code. Unit tests are provided for the implementation of the *TokenProvider* interface. These tests through maven with the command `mvn -Ptask1 clean install` or simply by calling `mvn -Ptask1 test`.

Service Implementation
======================

The solution for this tasks is implemented by a four Java project called *task2_common*, *task2_authenticator*, *task2_token_creator*, *task2_token_provider*. Each project is wrapped in a maven artifact (with id *common*,*athenticator*,*token-creator*,*token_service_interface* respectively) and they are declared under the profile *task2* in the main maven pom file. 

As requested in the assessment, this project implements three modules for the three services:
- **Authenticator:** Validates the Credentials and return an instance of a User.
- **Token Creator:** Returns a UserToken for a given User.
- **Token Provider:** Makes use of the previously defined services for authenticating users and granting tokens.

Assumptions and Decisions made
------------------------------

The solution provided for this task can be understood as an evolution of *task1*. In this case the implementation of the `TokenAuthenticator` and `TokenCreator`interfaces is also provided. Each module is implemented in a different Java projects which are independent one from each other (each module is not aware of the existance of the other module).  

As per task1, the implementation of *requestToken* have been designed in terms of *authentificate* and *issueToken*.This dependency of the *requestToken* with the `TokenAuthenticator` and `TokenCreator` interfaces has been implemented following the Dependency Inversion Principle (DIP) which states that high level modules should not depend on low level modules; both should depend on abstractions. Abstractions should not depend on details. Details should depend upon abstraction.

Nevertheless, this solution is not as modular and dynamic as it is desire. In the "Room for improvement" section it is suggested how this can be improved. 

As it is commented in the introduction of this task, there is a fourth module called *common*. This module contains the common data model and common utilities that are share by the three other modules. Again in the "Room for improvement" section it is documented how this can be achieve in another way and lists the pros and cons of having a common data model. 

The data model is quite similar to the one implemented for task1. It contains the three main objects: *User*, *UserToken*, *Credentials*. Again in this solution, the all three objects are inmutable which, in a multi-threaded environment, allows to read freely the objects without worrying about its state changing by other thread. It have a performance cost, but in this particular problem this should not be a concern (Objects are simple strings). A minor updated added in this solution, is that *User*, and *UserToken* includes the logic for creating an *invalid* user or *invalid* user token. Both objects contains a factory method with this purpose. 

```java
 public static final UserToken invalidUserToken()
    {
	return new UserToken(INVALID);
    }
```
```java
 public static final User invalidUser()
    {
	return new User(INVALID);
    }
```

Also in this module, the interfaces that the different modules implement are included with a similar definition as the one provided in task1. The main difference is that no specific Exceptions have been declared. And also, the `TokenAuthenticator` and `TokenCreator`interfaces have been defined as non-blocking APIs where the methods *authentificate* and *issueToken* return also a *CompletableFuture* object.

In addition to the data model, the *common* project provides a utily class that gather commonn functionality (e.i. *randomDelay* used by the *Authenticator* service and *Creator* service).

As per the implementation of the three interfaces, as already noted, each implementation is provided in a different module. They all follow the same principles as per the implementation of the task1 (the code is written as clear and easy to follow as possible). All methods follow a functional-style programming where there is no side-effects and the methods are characterized only by their input arguments and their output result. The methods implemented by all modules always returns the same result value when called with the same argument value (referenctial transparency). Although. it is important to noticed that for the *SimpleAsyncTokenService* implementation is not fully guaranteed since it relays on the implementation of the two other interfaces. As per task1, The SimpleAsyncTokenService gets the *TokenCreator* and *TokenAuthenticator* by composition. Eventhough this objects are declared as *final*, its immutability/side-effect-free/referencial transparency depends on how theses two interfaces are implemented. 

In the same way, as can be seen below, the asynchrounous aspect of the *requestTokenAsync* method relays on the *authenticateAsync* implementation. 

```java
public CompletableFuture<UserToken> requestTokenAsync(Credentials credentials)
    {
	return tokenAuthenticator.authenticateAsync(credentials)
		.thenCompose((user) -> tokenCreator.issueTokenAsync(user)).exceptionally(ex -> handleExceptions());
    }
```
Also, it is worth notice, that exceptions are handled differently in this solution. In this case, it is used the *exceptionally* method of the *CompletableFuture* object, which accepts a *Function* as argument and returns a new *CompletableFuture* that is completed when this CompletableFuture completes, with the UserToken generated by *issueTokenAsync* method if it completes normally; or with a *invalid* user token (returned by the *handleExceptions* function) if the *CompletableFuture* completes exceptionally.

Unit tests are provided for all three modules. the *common* module does not provide unit test since has no (or very trivial) functionality. The approach follow for unt testing is the same as per task1.

Technical choices
-----------------

*No additional technical choices besides the ones already mentioned on the General Considerations section* 

Room for improvement
--------------------

As mentioned earlier thereare several points where this solution can be improved:
- **Dynamic Modularity:** Eventhough, each of the services are implemented in different modules where each module is not aware of the other, still the integration of all three modules (as we will see in task3) ties the modules in runtime. It is not possible to upgrade pr replace a modules without stopping the application and re-compiling the code. This can be improved following a more modular approach as the one provided by the OSGi standard where services are registered and can be installed, resolved, started, stopped and un-installed at runtime. The drawback of this solution is that implementing OSGi service is not a simple task and adds complexity to the solution. Other approach is to implement each module as a ReST service (similar as it is done in task3). Other solution would be to use an Actors framework as suggested in the assessment. 
- **Common datamodel:** the common data model approach has been adapted because reduce duplicities in the code and avoids having to translate objects between the different modules. The problem of this approach is that each change on the datamodel, even if it is for a particular service, affects all the other modules. Other problem is that the common data model implement funtionality that might be not of interest for all modules (e.g. the *randomDelay* method is not used by the SimpleAsyncTokenService).
- **Custom Executor:** It is a sensible choice to create an *Executor* with a number of threads in its pool that takes into account the actual workload of the application. The problem of this feature is that it is not an easy task sizing thread pools. In the book *Java Concurrency in Practice* give some advice to find the optimal size for a thread pool. This is important because if the number of threads in the pool is too big, they will end up competing for scarce CPI and memory resources. Conversely, if this number is too small, some of the cores of the CPU will remain underutilized.
- **Scalability:** this solution does not scale horizontally. It can only scale-up by upgrading the resources on the server where the application is running. The Rest service approach, or the Actors model approach suggested for the *Dynamic Modularity* issue would be valid for solving this problem. 

Instructions
============

As per task1 there is not an actual application to be run. This will be performed as part of task3. The evaluation of this task has to be done also by inspecting the code. 
Unit tests can be run through maven with the command `mvn -Ptask2 clean install` or simply by calling `mvn -Ptask2 test`.

REST API
========

The solution for this task is implemented by a single Java Project called *task3_token_rest_service* which re-uses the modules implemented in the task2. this project is wrapped in a maven arifact (*token-rest-service*) and it declared under the profile *task3* in the main pom file, which also includes the modules from task2.

As requested in the assesment, this project defines a simple REST API to offer the functionality of the **SimpleAsyncTokenService** implemented in the previous task.

Assumptions and Decisions made
------------------------------

In this task the REST API makes use of the GET http method for requesting a new token from credentials provided (i.e. username and password). Username and password are provided as string parameters in the http request. 
```
http://localhost:8080/token?username=perro&password=PERRO
```
If the credentials authorization is performed successfully then a JSON file is return with the token value (created by the *token-creator* module) and a boolean flag indicating that the token is valid. 
```
{"token":"perro_2018-03-06T14:02:54.856Z","valid":true}
```
If the credentials authorization does not succeed then the service return a JSON file with a token value that starts with the keyword *invalid* and the valid flag set to `false`.
```
{"token":"invalid_2018-03-06T14:07:34.704Z","valid":false}
```
The project is quite simple. The application uses Spring Boot and it is formed by two main classses: `TokenServiceApplication`and `TokenServiceController`. The former is the main entry-point of the application. the latter exposes the annotated bean's method as HTTP endpoints.  `TokenServiceController` contains a single method *requestToken* that wraps the *resquestToken* method implemented for task2. 
Spring Boot framework takes care of pretty much everything and for this reason no unit tests are provided in this solution. This project contains an integration test that makes sure that the whole solution work as expected together (including task2 solution).

Technical choices
-----------------

- **Spring Boot** from creating REST Web service
### Room for improvement
- **Creation of Service Objects:** the *SimpleAsyncTokenService* is created in situ with the other two services (as can be seen below). This could be improve with a Factory for each of the services. This will encampsulate the creaton of this objects and will help to maintain the system in case of (in hypothetical future) new families of of the three services are created. 
```java
private final TokenProvider simpleAsyncTokenProvider = new SimpleAsyncTokenService(new TokenCreatorWithRandomDelay(), new TokenAuthenticatorRandomDelay());
```
Instructions
------------

For this task there is an actual application to be run!! This application can be run by using the maven command ` mvn spring-boot:run'. Once the application is running then it can be tested using localhost (as IP) and the 8080 port (used by default in Tomcat). In the command shell should be something like the image *SpringBootConsole* stored in this repository. Also the port on which Tomcat is stated can be checked. 

As explained before, this task does not provide unit tests, but still the unit test (on which this solution depends) can be run through maven with the command `mvn -Ptask3 clean install` or simply by calling `mvn -Ptask3 test`.

Integration tests can be run with the command `mvn -Ptask3 failsafe:integration-test`. In this case, the profile is not really necessary since there are no other integration tests in the solution. 
