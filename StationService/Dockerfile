FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/firebase-service-account.json /app/src/main/resources/firebase-service-account.json
ENTRYPOINT ["java","-jar","/app.jar"]
