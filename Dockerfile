FROM maven:3.9.4 AS build

LABEL authors="farneser"

WORKDIR /app

COPY pom.xml /app

RUN mvn dependency:resolve

COPY src /app/src

ENV RABBITMQ_HOST ${RABBITMQ_HOST}
ENV RABBITMQ_PORT ${RABBITMQ_PORT}
ENV RABBITMQ_USERNAME ${RABBITMQ_USERNAME}
ENV RABBITMQ_PASSWORD ${RABBITMQ_PASSWORD}

RUN mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:17-jdk-slim as production

COPY --from=build /app/target/task-tracker-email-sender*.jar /usr/local/lib/task-tracker-email-sender.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/usr/local/lib/task-tracker-email-sender.jar"]