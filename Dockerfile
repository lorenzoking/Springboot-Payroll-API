# Use a lightweight Alpine image
FROM openjdk:8-jdk-alpine

# Set environment variables
ENV VERSION=1.0.0
ENV SPRINGBOOT_VERSION=2.5.5
ENV STABLE_VERSION=1.0.0
ENV DOCKER_REGISTRY=springboot
ENV DOCKER_IMAGE=payroll-api

# Set the working directory to /app
WORKDIR /root

# Copy the current directory contents into the container at /app
COPY . /root

# Build the application with Maven

RUN ./mvnw package

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application when the container starts
CMD ["java", "-jar", "./target/spring-boot-payroll-api-0.0.1-SNAPSHOT.jar"]
