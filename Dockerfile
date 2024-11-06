# Stage 1: Build the application using Maven with JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the entire project and build it
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

# Stage 2: Run the application using JDK 17
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/Makerspace-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 7100

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
