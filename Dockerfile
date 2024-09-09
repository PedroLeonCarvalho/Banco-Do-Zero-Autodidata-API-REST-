FROM openjdk:17-jdk-alpine
LABEL maintainer="pedrolbbcarvalho@gmail.com"
EXPOSE 8080
ARG JAR_FILE=target/banking_api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} banking_api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/banking_api.jar"]
