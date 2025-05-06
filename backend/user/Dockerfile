FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/user-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} user.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "user.jar"]