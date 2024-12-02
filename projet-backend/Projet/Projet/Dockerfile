FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

# Commande pour exécuter l'application Spring Boot
CMD sed -i 's/\r$//' ./mvnw && ./mvnw spring-boot:run