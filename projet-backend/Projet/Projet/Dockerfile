FROM eclipse-temurin:17-jdk-focal AS base

WORKDIR /app
EXPOSE 8080

FROM base AS dev

# Commande pour exécuter l'application Spring Boot
CMD sed -i 's/\r$//' ./mvnw && ./mvnw spring-boot:run

FROM base AS prod

# TODO: Compiler le jar et le mettre dans l'image au lieu de re compiler à chaque fois
COPY . .
CMD sed -i 's/\r$//' ./mvnw && ./mvnw spring-boot:run