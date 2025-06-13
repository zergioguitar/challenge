# Stage 1: Build with Gradle Wrapper
FROM gradle:8.14.2-jdk21-alpine AS builder

WORKDIR /app

# Copy only Gradle wrapper and config files first (for caching)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies (non-failing for cache warmup)
RUN ./gradlew dependencies --no-daemon || true

# Copy full project
COPY . .

# Build the Spring Boot jar
RUN ./gradlew bootJar --no-daemon

# Stage 2: Run the jar in a lightweight JRE container
FROM eclipse-temurin:21-jre-alpine

VOLUME /tmp
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
