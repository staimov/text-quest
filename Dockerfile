# First stage: build environment
FROM maven:3.9.1-eclipse-temurin-17-alpine AS builder
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean package -P-bootstrap-compile

# Second stage: runtime environment
FROM openjdk:17-jdk-alpine
LABEL maintainer="staimov@mail.ru"
# copy jar from the first stage
COPY --from=builder /usr/app/target/text-quest-1.0.0.jar text-quest-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/text-quest-1.0.0.jar"]
