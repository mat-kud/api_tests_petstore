# API Tests

Service: ***petstore.swagger.io***

Used technologies: **Java**, **REST Assured**, **TestNG**, **Allure**, **Log4j**, **Lombok**.

## Log files rollover

I encountered an issue regarding rollover policy when using routing appender in the log4j2 configuration file. For that reason I created a feature for deleting obsolete logs based on three parameters:

``ROLL_ON (default: true)`` - a boolean value indicating whether rollover feature should be enabled

``ROLL_SIZE (default: 100)`` - described in MB, the maximum occupied space by log files beyond which a function for their removal may be triggered

``ROLL_FREQ (default: 7)`` - described in days, the time interval, measured from the previous deletion of the 'logs' folder, after which the occupied space by log files will be checked

To change default values of parameters use commands: ``-DROLL_ON``, ``-DROLL_SIZE`` and ``-DROLL_FREQ``, for example:  
``mvn clean test -DROLL_SIZE=50 -DROLL_FREQ=3``

Default values can be also changed by altering related properties in pom file:
```
    <roll.on>true</roll.on>
    <roll.size>100</roll.size>
    <roll.freq>7</roll.freq>
```

## Running tests and generating Allure report

Log file containing all logs for specific test case is attached to the specific test case summary of the Allure report

To run all tests, use maven command: ```mvn clean test```  
To run specific group of tests: ```mvn clean test -Dgroups=groupName```  
To run specific class of tests: ``mvn clean test -Dtest=className``

### Accessing the report manually

1. Generate the report by running: ```mvn allure:report```
2. You can access the report by opening ```target/site/allure-maven-plugin/index.html``` in a browser

### Accessing the report automatically

To generate and open the report in your default browser run: ```mvn allure:serve```











