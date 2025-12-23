<center>

# AlgaSensors

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-21.0.0-DD0031?logo=angular&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Latest-2496ED?logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.0.0-FF6600?logo=rabbitmq&logoColor=white)

</center>

A distributed temperature monitoring system with multiple microservices for device management, data processing, and real-time monitoring.

## Overview

AlgaSensors consists of the following services:

- **Manager** ([manager/README.md](./manager/README.md)) - Device and sensor management API
- **Monitor** ([monitor/README.md](./monitor/README.md)) - Temperature monitoring and alerting service
- **Processor** ([processor/README.md](./processor/README.md)) - Data processing worker
- **Client** ([client/README.md](./client/README.md)) - Angular web dashboard

Services communicate via RabbitMQ message queue.

## Deployment

### Production/QA/Staging

Deploy all services using Docker Compose:

```bash
docker-compose up -d
```

This starts all services (RabbitMQ, Processor, Monitor, Manager, Client) with proper dependencies and networking.

### Development

For development, it's recommended to run each service separately:

1. Start RabbitMQ (via docker-compose):
   ```bash
   docker-compose up algasensors-rabbitmq -d
   ```

2. Load environment variables for each service:
   ```bash
   set -a; source .env.dev; set +a
   ```
   (Works in bash/zsh)

3. Run each service individually using its respective local start command (see service-specific READMEs).
