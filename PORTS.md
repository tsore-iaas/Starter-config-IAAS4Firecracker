# Configuration des Ports

Ce document liste tous les ports utilisés par les différents services de l'application.

## Services Principaux

| Service | Port Externe | Port Interne | Description |
|---------|--------------|--------------|-------------|
| service-config | 8080 | 8080 | Serveur de configuration Spring Cloud Config |
| service-registry | 8081 | 8761 | Serveur Eureka pour la découverte de services |
| service-proxy | 8082 | 8079 | API Gateway pour le routage des requêtes |

## Services Infrastructure

| Service | Port(s) | Description |
|---------|---------|-------------|
| RabbitMQ | 5672 | Port AMQP principal |
| RabbitMQ Management | 15672 | Interface d'administration RabbitMQ |

## Accès aux Services

- **Service Config**: http://localhost:8080
  - Point d'accès pour la configuration centralisée

- **Service Registry (Eureka)**: http://localhost:8081
  - Dashboard Eureka: http://localhost:8081

- **API Gateway**: http://localhost:8082
  - Point d'entrée principal pour les requêtes API

- **RabbitMQ Management**: http://localhost:15672
  - Interface d'administration
  - Identifiants par défaut:
    - Username: guest
    - Password: guest

## Notes

- Les ports externes sont ceux exposés sur votre machine
- Les ports internes sont ceux utilisés à l'intérieur des conteneurs Docker
- Tous les services sont connectés via le réseau Docker "microservices-network"
