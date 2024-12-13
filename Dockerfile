
# FROM openjdk:21-slim

# # Install dependencies for X11 and JavaFX
# RUN apt-get update && apt-get install -y \
#     libgtk-3-0 \
#     libx11-6 \
#     libxext6 \
#     libxrender1 \
#     libxtst6 \
#     libxi6 \
#     libgl1-mesa-glx \
#     x11-apps \
#     xorg \
#     maven \
#     && rm -rf /var/lib/apt/lists/*

# # Set the working directory
# WORKDIR /app

# # Copy the JAR file from the target folder
# COPY target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# # Install JavaFX SDK
# RUN wget https://download2.gluonhq.com/openjfx/21.0.5/openjfx-21.0.5_linux-x64_bin-sdk.zip -d /opt/javafx-sdk \
#     && rm openjfx-21.0.5_linux-x64_bin-sdk.zip

# # Set the environment variable for JavaFX
# ENV JAVAFX_HOME=/opt/javafx-sdk/javafx-sdk-21.0.5
# ENV PATH="${JAVAFX_HOME}/bin:${PATH}"

# # Remove headless mode restriction
# ENV _JAVA_OPTIONS=""

# # Copy the start script
# COPY start.sh /start.sh

# # Make the start script executable
# RUN chmod +x /start.sh

# # Set the entry point
# ENTRYPOINT ["/start.sh"]




FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y \
    maven \
    libgl1-mesa-glx \
    libgtk-3-0 \
    wget \
    unzip \
    xvfb \
    x11vnc \
    fluxbox



WORKDIR /app

COPY pom.xml .

RUN mvn package

COPY resources /app/resources
COPY src /app/src

RUN wget https://download2.gluonhq.com/openjfx/23.0.1/openjfx-23.0.1_linux-x64_bin-sdk.zip && \
    unzip openjfx-23.0.1_linux-x64_bin-sdk.zip -d /opt/javafx && \
    rm openjfx-23.0.1_linux-x64_bin-sdk.zip
COPY target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

ENV DISPLAY=:99

EXPOSE 5900

ENV JAVAFX_HOME=/opt/javafx-sdk
ENV PRISM_VERBOSE=true
ENV JAVAFX_DISABLE_NATIVE_FULLSCREEN=true
ENV DISPLAY=:0


CMD ["sh", "-c", "Xvfb :99 -screen 0 1024x768x24 & sleep 2 && fluxbox & x11vnc -display :99 -nopw -forever & java --module-path /opt/javafx/javafx-sdk-23.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar app.jar"]

