# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copier les fichiers du projet
COPY pom.xml .
COPY src ./src

# Construire l'application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17
VOLUME /tmp
COPY --from=build /app/target/service-config.jar service-config.jar

ENTRYPOINT ["java", "-jar", "/service-config.jar"]