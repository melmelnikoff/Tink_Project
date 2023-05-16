FROM openjdk:17-slim

ARG JAR_FILE=scrapper/target/*.jar
WORKDIR /app/scrapper
COPY ${JAR_FILE} scrapper.jar

ENTRYPOINT exec java -jar scrapper.jar