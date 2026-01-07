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
- **Generator** ([generator/README.md](./generator/README.md)) - Mock temperature data generator
- **Client** ([client/README.md](./client/README.md)) - Angular web dashboard

Services communicate via RabbitMQ message queue.

## Features

### Dashboard
- **Sensor Overview Cards**: Each sensor card displays the mean temperature of the last 10 days in an interactive bar chart visualization, providing quick insights into temperature trends.
- **Statistics Dashboard**: Real-time overview showing total sensors, online sensors, average temperature, and the most recent alert.

### Sensor Detail Page
- **Interactive Temperature History Graph**: Visualize temperature data over time with an interactive line chart. Select from multiple time periods:
  - 24 hours (aggregated by 5-minute intervals)
  - 7 days (aggregated by 1-hour intervals)
  - 30 days (aggregated by 6-hour intervals)
- **Recent Activity Card**: View the latest temperature readings and alert events in a detailed table format, showing timestamps, event types, values, and status indicators.
- **Alert Configuration**: Configure minimum and maximum temperature thresholds for each sensor. The system automatically triggers alerts when temperatures exceed these limits.

## Observability

All backend services are configured with comprehensive logging to log files:

- **Manager**: `./manager/logs/manager.log`
- **Monitor**: `./monitor/logs/monitor.log`
- **Processor**: `./processor/logs/processor.log`
- **Generator**: `./generator/logs/generator.log`

Log files are configured with:
- Rolling policy: Maximum file size of 10MB
- Retention: Keeps up to 10 historical log files
- Structured format: Includes timestamp, thread, log level, and logger information

When running with Docker Compose, log files are mounted as volumes and accessible in the respective service directories.

## Deployment

### Production/QA/Staging

Deploy all services using Docker Compose:

```bash
docker-compose up -d
```

This starts all services (RabbitMQ, Processor, Monitor, Manager, Generator, Client) with proper dependencies and networking.

### Development

For development, it's recommended to run each service separately:

1. Start RabbitMQ (via docker-compose):

   ```bash
   docker-compose up rabbitmq -d
   ```

2. Load environment variables for each service:

   ```bash
   set -a; source .env.dev; set +a
   ```

   (Works in bash/zsh)

3. Run each service individually using its respective local start command (see service-specific READMEs).
