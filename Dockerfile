FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar file
COPY --from=build /app/target/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run with proper Java options
ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]

#FROM maven:3.9.9-eclipse-temurin-21 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## Runtime stage
#FROM openjdk:21-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/ExpenseTracker-0.0.1-SNAPSHOT.jar ExpenseTracker.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","ExpenseTracker.jar"]


