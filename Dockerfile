# FROM openjdk:21



# ARG JAR_FILE=target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar
# COPY ${JAR_FILE} app.jar

# ENTRYPOINT ["java", "-jar", "app.jar"]

# Build stage using Maven
# FROM maven:3.8.4-openjdk-11 AS builder

# # Set the working directory inside the container
# WORKDIR /home/noureddinedriouech/Downloads/HamNews

# # Copy the pom.xml and source files to the container
# COPY . /home/noureddinedriouech/Downloads/HamNews

# # Build the application with Maven
# RUN mvn clean package -DskipTests

# # Final image with a smaller base (openjdk)
# FROM openjdk:11-jre-slim

# # Set the working directory inside the container
# WORKDIR /HamNews

# # Copy the JAR file from the builder stage
# COPY --from=builder /home/noureddinedriouech/Downloads/HamNews/target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar /HamNews/app.jar

# # Install any required packages (if needed, e.g., for graphics)
# RUN apt-get update && apt-get install --no-install-recommends -y xorg libgl1-mesa-glx && rm -rf /var/lib/apt/lists/*

# # Expose the port if necessary (if your app runs on a specific port)
# EXPOSE 8080

# # Command to run the application
# CMD ["java", "-jar", "app.jar"]
# Use OpenJDK 21 base image instead of OpenJDK 17
FROM openjdk:21-slim

# Install dependencies for JavaFX and Xvfb (virtual display)
RUN apt-get update && apt-get install -y \
    libgtk-3-0 \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    xvfb \
    wget \
    unzip \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the JAR file from the target folder
COPY target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# Install JavaFX SDK (needed for JavaFX apps)
RUN wget https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_linux-x64_bin-sdk.zip \
    && unzip openjfx-17.0.2_linux-x64_bin-sdk.zip -d /opt/javafx-sdk \
    && rm openjfx-17.0.2_linux-x64_bin-sdk.zip

# Set the environment variable for JavaFX
ENV JAVAFX_HOME=/opt/javafx-sdk/javafx-sdk-17.0.2
ENV PATH="${JAVAFX_HOME}/bin:${PATH}"

# Ensure Java is headless (useful for Docker)
ENV _JAVA_OPTIONS="-Djava.awt.headless=true"

# Copy the start.sh script into the container
COPY start.sh /start.sh

# Make sure the script is executable
RUN chmod +x /start.sh

# Set the entry point
ENTRYPOINT ["/start.sh"]
