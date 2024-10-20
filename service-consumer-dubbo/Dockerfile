FROM openjdk:17-jdk-slim
COPY build/libs/service-consumer-dubbo-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]