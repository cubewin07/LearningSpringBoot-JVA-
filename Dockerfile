
FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /app

COPY . .

RUN ./gradlew build
