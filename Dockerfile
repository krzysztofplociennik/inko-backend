# Use a Gradle image with JDK 21
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and source code
COPY gradlew .
COPY gradle ./gradle
COPY . ./

# Grant execute permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application using Gradle
RUN ./gradlew build -x test

# Use a minimal JRE 21 image for runtime
FROM openjdk:21-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your application listens on (adjust as needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]