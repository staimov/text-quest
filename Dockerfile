FROM openjdk:17-jdk-alpine
LABEL maintainer="ashimmu@gmail.com"
COPY target/text-quest-1.0.0.jar text-quest-1.0.0.jar
ENTRYPOINT ["java","-jar","/text-quest-1.0.0.jar"]
