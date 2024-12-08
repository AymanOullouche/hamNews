## Use an OpenJDK base image with Java 17 or higher
#FROM openjdk:21-jdk-slim
#
#RUN apt-get update && apt-get install -y \
#    libx11-6 \
#    libglu1-mesa \
#    libgdk-pixbuf2.0-0 \
#    libgtk-3-0 \
#    libasound2 \
#    libxi6 \
#    libxtst6 \
#    libxss1 \
#    libxxf86vm1 \
#    libgl1-mesa-glx \
#    && rm -rf /var/lib/apt/lists/*
#
## Copy your jar file into the container
#COPY target/hamNews.jar news.jar
#
## Set the default command to run your application
#ENTRYPOINT ["java", "-jar", "news.jar"]

# Use a multi-stage build to compile the JavaFX application
FROM maven:3.8.5-openjdk-11 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the Docker image
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a lightweight base image for running the application
FROM openjdk:11-jre-slim

# Install required packages for running JavaFX applications
RUN apt-get update && apt-get install --no-install-recommends -y \
    openjfx \
    && rm -rf /var/lib/apt/lists/*

# Copy the built application from the builder stage
COPY --from=builder /app/target/*.jar /app/app.jar

# Set the working directory to where the app is located
WORKDIR /app

# Command to run the JavaFX application
CMD ["java", "-jar", "app.jar"]
