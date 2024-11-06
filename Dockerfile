# Use Maven with Eclipse Temurin JDK 17 as the base image
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory for the application
WORKDIR /app

# Copy the pom.xml file into the container
COPY pom.xml ./

# Run Maven to fetch dependencies
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the application (without running tests)
RUN mvn clean package -DskipTests

# Use a smaller runtime image for the final application
FROM openjdk:17-jdk-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/makerspace-backend.jar /app/makerspace-backend.jar

# Expose the application port
EXPOSE 7100

# Command to run the backend application
CMD ["java", "-jar", "makerspace-backend.jar"]
