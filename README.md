Here's a comprehensive README.md and required setup files for your reward-service application:

**README.md**

```markdown
# Reward Service

A reactive Spring Boot service for reward management with Axon Framework, Kafka, and PostgreSQL.

## Prerequisites

- Java 17+
- sbt 1.8+
- PostgreSQL 14+
- Kafka 3.3+
- Zookeeper 3.7+
- IntelliJ IDEA (recommended)

## Setup Instructions

### 1. Database Setup

1. Create database and user:
```bash
psql -U postgres -a -f db-setup.sql
```

2. Apply Liquibase migrations will be handled automatically on application startup

### 2. Environment Setup

Create `environment.env` file in project root:
```bash
# Application
SERVER_PORT=8083
AXON_SERVER=localhost:8124

# Kafka
KAFKA_BROKER=127.0.0.1:9092
REWARD_SERVICE_KAFKA_TOPIC_NAME=reward-service-topic
REWARD_SERVICE_KAFKA_CLIENT_ID=reward-service
REWARD_SERVICE_KAFKA_TX_ID_PREFIX=reward-service
REWARD_SERVICE_KAFKA_GROUP_ID=RewardEventProcessor

# Database
REWARD_DB_URL=jdbc:postgresql://localhost:5432/reward_db
REWARD_DB_USER_NAME=localdev
REWARD_DB_PASSCODE=localdev
REWARD_DB_DRIVER=org.postgresql.Driver

# Eureka
EUREKA_SERVER_URL=http://localhost:8761/eureka

# Redis
CACHE_TYPE=redis
REDIS_SERVER_URL=localhost
REDIS_PORT=6379
```

Source the environment variables:
```bash
source environment.env
```

### 3. IntelliJ Configuration

1. Import project as sbt project
2. Add environment variables to Run Configuration:
   - Go to `Run > Edit Configurations`
   - Add new sbt Task configuration
   - Tasks: `runAll`
   - Environment variables: `From file` -> `environment.env`

### 4. Start Dependencies

1. Start Zookeeper:
```bash
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
```

2. Start Kafka:
```bash
kafka-server-start /usr/local/etc/kafka/server.properties
```

### 5. Run Application
```bash
sbt runAll
```

## API Documentation
Swagger UI available at: http://localhost:8083/swagger-ui.html

## Architecture
- Uses CQRS pattern with separate command and query models
- Event sourcing with Axon Framework
- Kafka as event bus
- PostgreSQL for event store and materialized views
- Redis for caching
- Eureka for service discovery

## Important Configuration
- Check `application.properties` for detailed configuration
- Axon Framework configured with Kafka as message broker
- Liquibase for database migrations
- HikariCP connection pooling
```

**db-setup.sql**
```sql
-- Create database
CREATE DATABASE reward_db;

-- Create user
CREATE USER localdev WITH PASSWORD 'localdev';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE reward_db TO localdev;

-- Connect to database
\c reward_db

-- Grant schema privileges
GRANT ALL ON SCHEMA public TO localdev;
```

**environment.env** (same as in README, but as separate file)

**Additional Notes:**

1. Kafka Topic Creation:
```bash
# Create Axon events topic
kafka-topics --create --topic axon-events \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1

# Create service-specific topic
kafka-topics --create --topic reward-service-topic \
--bootstrap-server localhost:9092 \
--partitions 1 \
--replication-factor 1
```

2. Verify Database Connection:
```bash
psql -U localdev -d reward_db -h 127.0.0.1
```

3. Recommended Tools:
- pgAdmin for PostgreSQL management
- Kafka Tool for Kafka monitoring
- RedisInsight for Redis monitoring

4. Health Endpoints:
- Actuator health: http://localhost:8083/actuator/health
- Prometheus metrics: http://localhost:8083/actuator/prometheus

This setup provides a complete local development environment with proper configuration for Axon Framework, Kafka, and PostgreSQL. Make sure to adjust the paths for Kafka and Zookeeper properties files according to your local installation.
