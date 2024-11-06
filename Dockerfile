# Use Maven with Eclipse Temurin JDK 17 as the base image
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set JAVA_HOME environment variable for Java 17
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

# Set the working directory
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml ./

# Run Maven to fetch dependencies and cache them
RUN mvn dependency:go-offline

# Copy the entire source code into the container
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built application JAR file from the build stage
COPY --from=build /app/target/makerspace-backend.jar /app/makerspace-backend.jar

# Expose the backend application's port (adjust if necessary)
EXPOSE 7100

# Run the backend application
CMD ["java", "-jar", "makerspace-backend.jar"]
