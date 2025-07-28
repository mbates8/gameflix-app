FROM openjdk:17-jdk-slim

VOLUME /tmp

# Copy the built jar (adjust the filename if yours differs)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]