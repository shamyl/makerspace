# Use Maven with Java 17 from Eclipse Temurin
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Verify Java version (this step will help ensure JAVA_HOME is set correctly)
RUN java -version

# Create and set the working directory
WORKDIR /app

# Copy the pom.xml to the working directory
COPY pom.xml ./

# Download project dependencies in offline mode
RUN mvn dependency:go-offline

# Copy the remaining project files into the container
COPY . .

# Build the project (for example, a jar file)
RUN mvn clean install -DskipTests

# Expose the port your application will use
EXPOSE 7100

# Command to run the backend server (change this according to your setup)
# CMD ["java", "-jar", "target/Makerspace-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "target/Makerspace-0.0.1-SNAPSHOT.jar", "--server.address=0.0.0.0", "--server.port=7100"]
