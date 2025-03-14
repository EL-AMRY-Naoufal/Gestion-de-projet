FROM eclipse-temurin:17-jdk-focal AS base

WORKDIR /app
EXPOSE 8080

FROM base AS dev

# Commande pour exécuter l'application Spring Boot
CMD sed -i 's/\r$//' ./mvnw && ./mvnw spring-boot:run

FROM base AS build

WORKDIR /app

# Copie des sources et compilation du JAR
COPY . .
RUN chmod +x ./mvnw && \
    sed -i 's/\r$//' ./mvnw && \
    ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-focal AS prod

WORKDIR /app
# Copie uniquement le JAR compilé depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Lancement du JAR au démarrage du conteneur
CMD ["java", "-jar", "app.jar"]