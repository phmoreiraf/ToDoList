FROM maven:3.8.1-openjdk-17-slim AS build

WORKDIR /app

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY Codigo/roteiro01/pom.xml .
COPY Codigo/roteiro01/roteiro01/src/ ./src
#COPY Codigo/roteiro01/roteiro01/src/main/java .

RUN mvn clean package

#RUN apt-get install maven -y
#RUN mvn clean install

FROM openjdk:17-jre-slim

EXPOSE 8080

WORKDIR /app

COPY --from=BUILD /target/roteiro01-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]