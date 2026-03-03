FROM maven:3.9.12-eclipse-temurin-25 AS Build
LABEL authors="Organisationen"

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25.0.2_10-jre
WORKDIR /app
COPY --from=Build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
