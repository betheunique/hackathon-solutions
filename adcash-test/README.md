```` Mini Stock Market Application ````

Requirements :
- JDK 1.8
- MySQL
- Apache maven


SET UP :
- Create Database ad-cash
- Import sql from adcash-test/sql/db.sql
- Change database configuration in application.properties

    spring.datasource.url=jdbc:{database-url-with-database-name}
    spring.datasource.username={username}
    spring.datasource.password={password}

BUILD :

mvn clean package

RUN :

java -jar adcash-test/target/ adcash-test.0.0.1-SNAPSHOT.jar


FUNCTIONALITY :

END POINT :
localhost:8080/?countryCode="IN"&category="IT"&baseBid=10

Expected Result :

{
    "message": either countryCode or specific messages
}

DB SNAPSHOT:

localhost:8080/databaseSnapshot

Expected Result :

Tabular Structure similar to db using thymeleaf template engine.

LOG SNAPSHOT:

application.log