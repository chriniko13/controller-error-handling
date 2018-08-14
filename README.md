# controller-error-handling

About:
- How to handle exceptions/errors correctly on resource(controller) layer.

Technologies Used:
- Spring boot starter web
- Spring boot starter test
- Lombok
- Typeof (https://github.com/nurkiewicz/typeof)
- Vavr

Build Tool:
- Maven

Build Plugins Used:
- Maven surefire (for unit tests)
- Maven failsafe (for integration tests)
- Jacoco (for line and branch coverage on unit tests)
- Pitest (for mutation testing on unit tests)

Unit Tests:
- mvn test
- To see jacoco output go to: target/jacoco-ut/index.html

Integration Tests:
- mvn integration-test

Mutation Testing:
- mvn test org.pitest:pitest-maven:mutationCoverage
- To see pitest output go to: target/pit-reports/index.html

Apply Coverage Rules (for Jacoco & Pitest)
- mvn verify
