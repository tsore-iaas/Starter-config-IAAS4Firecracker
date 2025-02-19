# FireCracker-Prod - Guide de Démarrage

Ce projet est une architecture microservices composée de plusieurs services interconnectés. Voici la description et les instructions pour démarrer chaque composant.

## Architecture du Projet

Le projet est composé des services suivants :
- **service-config**: Serveur de configuration centralisée
- **service-registry**: Service de découverte (Eureka Server)
- **service-proxy**: Service de routage (API Gateway)
- **RabbitMQ**: Broker de messages
- **MySQL**: Base de données

## Prérequis

- Docker et Docker Compose
- Java 17 ou supérieur
- Maven

## Comment démarrer les services

1. **Cloner le projet**
   ```bash
   git clone [URL_DU_REPO]
   cd FireCracker-Prod
   ```

2. **Construire et démarrer les services**
   ```bash
   docker-compose up --build
   ```

   Cette commande va :
   - Construire toutes les images Docker
   - Démarrer tous les services dans le bon ordre
   - Configurer le réseau entre les services

3. **Vérifier que tout fonctionne**
   - Service Config: http://localhost:8080
   - Eureka Dashboard: http://localhost:8081
   - API Gateway: http://localhost:8082
   - RabbitMQ Management: http://localhost:15672 (user: guest, password: guest)

## Ordre de démarrage des services

Les services démarrent dans l'ordre suivant grâce aux dépendances configurées :
1. Service Config (doit démarrer en premier)
2. Service Registry (dépend du Service Config)
3. Service Proxy (dépend du Service Registry)

## Configuration

- La configuration des services est centralisée dans le Service Config
- Les services se connectent automatiquement au Service Registry pour la découverte
- Le Service Proxy route les requêtes vers les services appropriés

## Développement

Pour développer localement :
1. Cloner le repository
2. Importer les projets dans votre IDE
3. Chaque service peut être lancé individuellement avec Maven :
   ```bash
   cd [nom-du-service]
   mvn spring-boot:run
   ```
