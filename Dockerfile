
FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test
