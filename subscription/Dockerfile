FROM eclipse-temurin:24-jdk-alpine AS builder
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY src ./src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]