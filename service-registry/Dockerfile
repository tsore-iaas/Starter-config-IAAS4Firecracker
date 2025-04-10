# Build stage
FROM maven:3.8.4-openjdk-17 AS build
LABEL authors="zaz"
WORKDIR /app

# Copier les fichiers du projet
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17
LABEL authors="zaz"
VOLUME /tmp
COPY --from=build /app/target/service-registry.jar service-registry.jar

ENTRYPOINT ["java", "-jar", "/service-registry.jar"]