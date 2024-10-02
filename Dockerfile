# First stage: Maven build
FROM maven:latest AS builder
LABEL version="0.0.1-SNAPSHOT"
LABEL description="This is my spring boot image for docx conversion"
LABEL author="Md. Abdullah Al Masum"
LABEL maintainer="edgar team of wsd"
# Use a volume to cache Maven dependencies
VOLUME /root/.m2

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean install package -DskipTests=true

FROM openjdk:17-jdk-slim as prod
WORKDIR /app
COPY --from=builder /app/target/EDGAR-2-Point-O-Trailer-1-0.0.1-SNAPSHOT.jar /app/docx_conversion_wrapper.jar
EXPOSE ${APPLICATION_PORT}
ENTRYPOINT ["java","-jar","/app/docx_conversion_wrapper.jar"]