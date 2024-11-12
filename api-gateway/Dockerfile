# Use Amazon Corretto as the base image
FROM amazoncorretto:17.0.13

# Set the working directory and copy files in one step
WORKDIR /app
COPY wait-for-it.sh ./
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will listen on
EXPOSE 8080

# Ensure the wait-for-it.sh script is executable and run the application with a single ENTRYPOINT
RUN chmod +x /app/wait-for-it.sh
ENTRYPOINT ["/app/wait-for-it.sh", "keycloak:8080", "--", "java", "-jar", "/app/app.jar"]
