FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/auth-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} auth-server.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "user.jar"]