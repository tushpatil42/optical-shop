# Step 1: Use Maven image to build the application
#FROM maven:3.8.6-openjdk-17-slim AS build
FROM ubuntu:20.04

RUN apt-get update && apt-get install -y \
    apt-transport-https \
    ca-certificates \
    gnupg \
    curl \
    lsb-release \
    && rm -rf /var/lib/apt/lists/*

# Add the Google Cloud SDK distribution URI as a package source
RUN echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] https://packages.cloud.google.com/apt cloud-sdk main" | tee -a /etc/apt/sources.list.d/google-cloud-sdk.list

# Import the Google Cloud public key
RUN curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | tee /usr/share/keyrings/cloud.google.gpg

# Update package list and install the Google Cloud SDK
RUN apt-get update && apt-get install -y google-cloud-sdk

# Optional: Install additional components if needed (e.g., kubectl)
# RUN apt-get install -y google-cloud-sdk-kubectl

RUN gcloud auth configure-docker

RUN docker pull openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY ./Optical-shop/pom.xml .
COPY ./Optical-shop/src ./src

# Step 2: Build the application using Maven (clean and package)
RUN mvn clean package -DskipTests

# Step 3: Use a smaller image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Step 4: Copy the built jar from the build image
COPY --from=build ./Optical-shop/app/target/*.jar app.jar

# Step 5: Expose the port the application will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Step 6: Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
