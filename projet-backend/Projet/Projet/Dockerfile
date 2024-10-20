FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY ./src ./src

ENV APP_MODE=dev
#CMD ["./mvnw", "spring-boot:run"]
#CMD if [ "$APP_MODE" = "test" ]; then ./mvnw test; else ["./mvnw", "spring-boot:run"] fi
CMD /bin/sh -c 'if [ "$APP_MODE" = "test" ]; then ./mvnw test; else ./mvnw spring-boot:run; fi'
