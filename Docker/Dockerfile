# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-22-alpine AS build
  ##3.9.6-eclipse-temurin-22-alpine

  # Set the working directory inside the container
WORKDIR /app

  # Copy the Maven project files to the container
COPY .. .

  # Build the project and create an executable JAR file
RUN mvn clean package -DskipTests

  # Stage 2: Run the application
FROM openjdk:22-jdk

  # Set the working directory inside the container
WORKDIR /app

  # Copy the JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

  # Expose the application port (adjust if necessary)
EXPOSE 80

  # Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]


##Instructions:
## From project root:
## docker build -f Docker/Dockerfile -t 51-burguer-app-aws .
## docker tag 51-burguer-app-aws appburguer51/51-burguer-app-aws:1.0

## Pushing image to docker hub
## docker login registry-1.docker.io -u appburguer51
## docker push appburguer51/51-burguer-app-aws:1.0

## docker-compose -f Docker/docker-compose.yml up

##caramassada/51-burguer-app-aws:latest