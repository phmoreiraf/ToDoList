FROM ubuntu:latest AS BUILD

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY Codigo/roteiro01/roteiro01/src/main/java .
COPY Codigo/roteiro01/pom.xml .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=BUILD /target/roteiro01-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]