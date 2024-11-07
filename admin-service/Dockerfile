# Use Amazon Corretto as the base image
FROM amazoncorretto:17.0.13

# Set the working directory in the container
WORKDIR /app


#Add opentelemetry JavaAgent
#ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.32.0/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar
#ADD build/agent/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

# Set environment variables for OpenTelemetry
#ENV JAVA_TOOL_OPTIONS="-javaagent:/otel/opentelemetry-javaagent.jar"
#ENV OTEL_SERVICE_NAME=admin-service
#ENV OTEL_LOGS_EXPORTER=otlp
#ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://collector:4318
#ENV OTEL_EXPORTER_OTLP_PROTOCOL="http/protobuf"
#ENV OTEL_RESOURCE_ATTRIBUTES="service=admin-service"
#ENV OTEL_INSTRUMENTATION_EUREKA_ENABLED=false
#ENV OTEL_INSTRUMENTATION_OKHTTP_ENABLED=false
#ENV OTEL_INSTRUMENTATION_SPRING_REST_TEMPLATE_ENABLED=false



# Copy the Maven build artifact (JAR file) into the container
COPY target/admin-service-0.0.2-SNAPSHOT.jar app.jar

# Expose a random port (replace <port> with the actual application port)
#EXPOSE 9093


# Run the application
#ENTRYPOINT java -javaagent:/opentelemetry-javaagent.jar -jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
