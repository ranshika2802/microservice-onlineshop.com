# Use Amazon Corretto as the base image
FROM amazoncorretto:17.0.13

# Set the working directory in the container
WORKDIR /app

# Copy the Maven/Gradle build artifact (JAR file) into the container
COPY target/price-service-0.0.1-SNAPSHOT.jar app.jar

# Expose a random port (replace <port> with the actual application port)
#EXPOSE 9093

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
