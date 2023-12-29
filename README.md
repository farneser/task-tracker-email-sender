# task-tracker-email-sender

[![Maven build](https://github.com/farneser/task-tracker-email-sender/actions/workflows/maven.yml/badge.svg)](https://github.com/farneser/task-tracker-email-sender/actions/workflows/maven.yml)
[![Docker Image build](https://github.com/farneser/task-tracker-email-sender/actions/workflows/docker.yml/badge.svg)](https://github.com/farneser/task-tracker-email-sender/actions/workflows/docker.yml)

The Task Tracker Email Sender is a specialized microservice designed to seamlessly integrate into a larger stack,
working in tandem with API services and schedulers. Developed with the purpose of enhancing task tracking
functionalities, this microservice excels at efficiently processing messages from RabbitMQ and dispatching them via
email

## Build

### Install dependencies

```bash
./mvnw clean install
```

### Run tests

Docker daemon must be running for integration tests

```bash
./mvnw clean test
```

### Run application

```bash
./mvnw spring-boot:run
```

### Run jar

```bash
java -jar target/task-tracker-email-sender-0.0.1-SNAPSHOT.jar
```

## Environment

### Application

| Parameter               | Default value                          | Description                              |
|-------------------------|----------------------------------------|------------------------------------------|
| EMAIL_SENDER_LOG_LEVEL  | `INFO`                                 | Spring application logging level         |
| CLIENT_CONFIRMATION_URL | `http://localhost:3000/confirm?token=` | URL for client confirmation (with query) |

### SMTP

| Parameter     | Default value    | Description          |
|---------------|------------------|----------------------|
| MAIL_HOST     | `smtp.gmail.com` | Mail server host     |
| MAIL_PORT     | `587`            | Mail server port     |
| MAIL_USERNAME |                  | Mail server username |
| MAIL_PASSWORD |                  | Mail server password |

### RabbitMQ

| Parameter         | Default value | Description                         |
|-------------------|---------------|-------------------------------------|
| RABBITMQ_HOST     | `localhost`   | RabbitMQ server host                |
| RABBITMQ_PORT     | `5672`        | RabbitMQ server port                |
| RABBITMQ_USERNAME | `rabbitmq`    | Username for connecting to RabbitMQ |
| RABBITMQ_PASSWORD | `rabbitmq`    | Password for connecting to RabbitMQ |
