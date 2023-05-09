FROM openjdk:17-slim

ARG JAR_FILE=bot/target/*.jar
WORKDIR /app/bot
COPY ${JAR_FILE} bot.jar

ENTRYPOINT exec java -jar bot.jar