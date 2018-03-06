# Back-end Technical Assessment

## Introduction
This is the solution provided as response of the Addison Global Backend Technical Assessment. 

As requested, this document contains relevant information related to technical assumptions/decisions made, as well as instructions for running a solution (when applicable) and tests in a Linux environment.

The document is divided in four sections where the first section provides common information for the whole solution. And the following three sections provide more specific details for each of the implemented tasks.

## General Considerations

The programming language chosen for implementing this exercise is Java 8.  All modules have been implemented following the SOLID design principles (promoted by Robert C. Martin) in order to provide a more understandable, flexible and maintainable solution. 

Unit tests are provided for all classes that implement some functionality, leaving out data model objects (such as User, UserToken and Credentials) and interfaces. These tests have been implemented following the F.I.R.S.T principles where unit tests must be Fast, Independent, Repeatable and Self-Validating.

All provided code has been properly commented. Comments have been kept to minimal use, avoiding redundant and noise comments that add no value to the solution but rather clutter. All public interfaces have been documented with Javadocs. And the JCIP annotation has been used for document concurrent programming matters (as recommended in Java Concurrency In Practice book.). 

As Robert C. Martin said: 
> "The proper use of comments is to compensate for our failure to express in code. Note that I used the word failure. I meant it. Comments are always failures.". 

In order to achieve code that is easy to read and follow, all functionality has been implemented in methods which are short, well named, and nicely organized. For private methods where the code should be expressive and clear enough, comments have not been added. Also a Eclipse format template has been created in order to get easily a nice format of the code which is applied all across the code solution.

### Solution structure

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
	
### Technical choices
- **Eclipse Oxygen** as IDE. 
- **Maven** as dependency and build life cycle management tool. 
- **Junit** 4 as unit test framework.
- **Mockito** as mocking framework.
- **JCIP** for concurrent annotation.
- **GIT** as control version tool. 
 
### Room for improvement

There is room for improvement in different parts of the solution. Here are only listed some of the improvements that applies to all three solutions. Additional improvements will be commented for a particular task in its corresponding section.

- **Logging mechanism:** Logging framework (such as log4j) that records different levels of relevant events that takes place in a operating system. 
- **Code coverage tool:** Coverage tools (such as sonarQ) report gaps in your testing strategy. They make it easy to find modules, classes, and functions that are insufficiently tested. 

### Instructions 
All three solutions (5 projects) are governed by Maven. There is a parent maven pom file that defines the different implemented modules. Therefore the build life-cycle of all 5 projects can be managed from the main repository folder using the different maven goals (e.g. `mvn clean install` will build and install all artifacts used by the projects into the local repository ). 

For running tests `mvn test` will run all unit tests and `mvn failsafe:integration-test` will run the integration tests. 

In addition, the parent maven pom defines three profiles (task1, task2 and task3), which allows to govern the different solutions for each task independently. (e.g. `mvn -Ptask2 clean install` ) will only affect to the projects that forms the solution for the task 2.   


## Service Trait / Interface

The solution for this tasks is implemented by a single Java project called *task1_token_service*. this project is wrapped in a maven artifact (*token_service_interface*) and it is declared under the profile *task1* in the main maven pom file. 

As requested in the assessment, this project implements two versions of the `requestToken(Credentials credentials)` method. One implementation is synchronous and another one is asynchronous.
 
### Assumptions and Decisions made

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
return CompletableFuture.supplyAsync(() ->
	{
	    return requestToken(credentials);
	}).exceptionally(ex -> createInvalidUserToken());
``` 
All three objects that formed the data model of the project (i.e. User, Credentials and UserToken) are immutable objects for which its state cannot be updated. For this reason the objects have been annotated as Thread-safe. All three objects also override the methods *equals* and *hashCode* (this methods have been automatically generated by the eclipse IDE).


### Technical choices
*No additional technical choices besides the ones already mentioned on the General Considerations section* 
### Room for improvement
**Custom *Executor*:** A custom *Executor* can be passed as a second argument to the overloaded version of the *supplyAsync* factory method. The performance can be improved by creating a new *Executor* that fits the characteristics of the application maximizing the usage of available resources.  
### Instructions 
As mentioned in previous sections, maven is the tool chosen for the build life-cycle management and a profile called task1 has been created for this particular solution. As this solution, is not a complete product, it cannot be deployed or run as a normal application. The evaluation of this task has to be performed by inspecting the source code. Unit tests are provided for the implementation of the *TokenProvider* interface. These tests through maven with the command `mvn -Ptask1 clean install` or simply by calling `mvn -Ptask1 test`.


## Service Implementation
### Assumptions and Decisions made
### Technical choices
### Room for improvement
### Instructions 

## REST API
### Assumptions and Decisions made
### Technical choices
### Room for improvement
### Instructions 
