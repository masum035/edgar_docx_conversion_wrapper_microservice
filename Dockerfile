FROM maven:latest as builder
LABEL version="0.0.1"
LABEL description="This is my spring boot image for docx conversion"
LABEL author="Md. Abdullah Al Masum"
LABEL maintainer="edgar team of wsd"

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:22-jdk-alpine as prod
RUN mkdir /app
COPY --from=builder /app/target/edgardocs-wrapper-0.0.1-SNAPSHOT.jar /app/edgardocs-wrapper-0.0.1-SNAPSHOT.jar
WORKDIR /app
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/edgardocs-wrapper-0.0.1-SNAPSHOT.jar"]