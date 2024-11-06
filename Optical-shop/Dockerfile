# Step 1: Use Maven image to build the application
FROM maven:3.8.6-openjdk-17-slim AS build

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
