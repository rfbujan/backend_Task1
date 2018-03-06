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

**Maven** as dependency and build life cycle management tool. 
**Junit** 4 as unit test framework
**Mockito** as mocking framework
**JCIP** for concurrent annotation.
 
### Room for improvement

There is room for improvement in different parts of the solution. Here are only listed some of the improvements that applies to all three solutions. Additional improvements will be commented for a particular task in its corresponding section.

	* Logging mechanism: Logging framework (such as log4j) that records different levels of relevant events that takes place in a operating system. 
	* Code coverage tool: Coverage tools (such as sonarQ) report gaps in your testing strategy. They make it easy to find modules, classes, and functions that are insufficiently tested. 

### Instructions 
All three solutions (5 projects) are governed by Maven. There is a parent maven pom file that defines the different implemented modules. Therefore the build life-cycle of all 5 projects can be managed from the main repository folder using the different maven goals (e.g. `<mvn clean install>` will build and install all artifacts used by the projects into the local repository ). 

For running tests `<mvn test>` will run all unit tests and `<mvn failsafe:integration-test>` will run the integration tests. 

In addition, the parent maven pom defines three profiles (task1, task2 and task3), which allows to govern the different solutions for each task independently. (e.g. `<mvn -Ptask2 clean install>` ) will only affect to the projects that forms the solution for the task 2.   


## Service Trait / Interface
### Assumptions and Decisions made
### Technical choices
### Room for improvement
### Instructions 

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
