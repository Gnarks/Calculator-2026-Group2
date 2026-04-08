Release:[![](https://img.shields.io/github/v/release/Gnarks/Calculator-2026-Group2?label=Latest%20Release)](https://github.com/Gnarks/Calculator-2026-Group2/releases/latest)


Build: ![Maven Build Status](https://github.com/Gnarks/Calculator-2026-Group2/actions/workflows/Pipeline.yml/badge.svg)

Code quality:
![CodeQL Analysis](https://github.com/Gnarks/Calculator-2026-Group2/actions/workflows/codeql.yml/badge.svg)
![PMD Code Analysis](https://github.com/Gnarks/Calculator-2026-Group2/actions/workflows/pmd.yml/badge.svg)
![Scorecard](https://github.com/Gnarks/Calculator-2026-Group2/actions/workflows/scorecard.yml/badge.svg)

Test coverage: 
![JaCoCo Coverage](.github/badges/jacoco.svg)
![Branches Coverage](.github/badges/branches.svg)

# Calculating arithmetic expressions

## Site
A report of the code quality, test coverage, BDD tests, and units test can be found at :
[This repository GitHub Pages](https://gnarks.github.io/Calculator-2026-Group2/)

The production site can be found at :  
[This link](https://calculator.barathaba.space/)


## About

This repository contains Java code for computing arithmetic expressions. It acts as a basis for extensions, specifically for the Software Evolution course at the University of Mons.

The project currently supports:
* **Arithmetic Operations**: Basic integer arithmetic including addition, subtraction, multiplication, and division.
* **Notations**: Expressions can be represented and printed in Infix, Prefix, and Postfix notations.
* **Design Patterns**: The code implements the **Composite** pattern for expression structures and the **Visitor** pattern for operations like evaluation and printing.
* **Testing**: Includes a comprehensive test suite using **JUnit 5** for unit tests and **Cucumber** for Behavior-Driven Development (BDD).

**For more information about the instructions for this project, please read the [Wiki pages](https://github.com/University-of-Mons/calculator-cucumber-2026/wiki).**

### Unit testing and BDD

*  All tests can be found in the src\test directory. They serve as executable documentation of the source code.
*  The source code is accompanied by a set of JUnit 5 unit tests. 
*  The source code is accompanied by a set of Cucumber BDD scenarios, also running in JUnit.
The BDD scenarios are specified as .feature files in the src\test\resources directory. Some classes defined in src\test take care of converting these scenarios to executable JUnit tests.

### Prerequisites

*  You will need to have a running version of Java 25 on your machine in order to be able to compile and execute this code, although it is also backward compatible with earlier versions of Java.
*  You will need to have a running version of Maven, since this project is accompanied by a pom.xml file so that it can be installed, compiled, tested and run using Maven.
* You will need to have docker and docker-compose installed for the Server + API
* Also for the Server + API to work you would need a dockerized reverse proxy on network "proxy" and a configuration using ssl and mapping /api to "http://backend:1523"

### Installation and testing instructions

*  Upon first use of the code in this repository, you will need to run "mvn clean install" to ensure that all required project dependencies (e.g. for Java, JUnit, Cucumber, and Maven) will be downloaded and installed locally.
*  Assuming you have a sufficiently recent version of Maven installed (the required versions are specified as properties in the POM file), you can compile the source code using "mvn compile"
*  Once the code is compiled, you can execute the main class of the Java code using "mvn exec:java" 
*  The tests and BDD scenarios are executable with Maven using "mvn test"
*  Note that the tests are also executed when you do a "mvn install". It is possible to skip those tests by providing an extra parameter. For details of more advanced uses of Maven, please refer to its official documentation https://maven.apache.org/guides/.

### Running the server with its API in production

* After setting up the reverse proxy defines in Prerequisites
* Simply enter `docker compose up` (with `-d` to detach from the logs) to run the application on http://yourpath.yourdomain

### Running the server with its API in dev

* run the backend by going to the backend directory and entering `mvn spring-boot:run`
* run the frontend by goind to the frontend directory and entering `npm run dev`

### Test coverage and JavaDoc reporting

*  In addition to testing the code, "mvn test" will also generate a test coverage report (in HTML format) using JaCoCo. This test coverage is generated in target/site/jacoco.
*  When packaging the code using "mvn package" the JavaDoc code documentation will be generated and stored in target/site/apidocs.

## Built With

*  [Maven](https://maven.apache.org/) - an open source build automation and dependency management tool
*  [JUnit5](https://junit.org/junit5/) - a unit testing framework for Java
*  [Cucumber](https://cucumber.io/docs/cucumber/) - a tool for Behaviour-Driven Development
*  [JaCoCo](https://www.jacoco.org) - a code coverage library for Java
*  [JavaDoc](https://docs.oracle.com/en/java/javase/21/javadoc/javadoc.html) - a code documentation tool for Java


## Frontend web interface 
To launch the web interface, navigate to the frontend directory with `cd frontend` and run the command `npm run dev` to start the development server.

## Versions

We use [SemVer](http://semver.org/) for semantic versioning. For the versions available, see the [tags on this repository](https://github.com/Gnarks/Calculator-2026-Group2/tags). 

## Contributors

* Tom Mens
* Gauvain Devillez @GauvainD
* Bartha Maxime
* Delobbe Antoine
* Verscheure Nicolas
* Godard Alexis

## Licence


[This code is available under the GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/) (GPLv3)

## Acknowledgments

* Software Engineering Lab, Faculty of Sciences, University of Mons, Belgium.
