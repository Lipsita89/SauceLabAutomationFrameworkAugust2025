## Selenium-Cucumber-Java

This repository contains a collection of sample projects and libraries that demonstrate how to use `selenium-cucumber-java`, a BDD (Behavior-Driven Development) framework with Cucumber and Java. The projects showcase automation script development and HTML report and JSON. Additionally, it offers the ability to capture screenshots for tests and generate error shots for failed test cases.

### Installation & Prerequisites

1. JDK/openJDK (Ensure that the Java class path is properly set)
2. Maven (Ensure that the .m2 class path is properly set)
3. Eclipse IDE / Intellij
4. Required Eclipse Plugins:
   - Maven
   - Cucumber
     
## Framework Setup

To set up the framework, you can either fork or clone the repository from [here](https://github.com/Harisha-Muddegowda/AutomationFramework.git), or download the ZIP file and set it up in your local workspace.

## Running Sample Tests

Access the CLI of your operating system (e.g., iTerm for macOS or PowerShell for Windows) and navigate to the project directory. Then, run the following command to execute the features: `mvn verify`. 
- To run features on a specific environment, create the config property file for the environmrnt similar to src/test/resources/local_config.properties and use the command: `mvn verify -Denv=LOCAL`
- To run the features on the specific browser, update browser on config properties file, by default features will be executed on chrome browser.

## Running Tests Browserstack

- To run the features on browserstack, update browserstackuserName and browserstackAccessKey on config properties and use the command: `mvn verify -Denv=LOCAL -DUSE_BROWSERSTACK=true'

## Report

This framework configured to generate Cucumber JVM surefire html report.
- On features execution completion the report will be generated and will available at target/cucumber-html-reports folder
# NewTest3
