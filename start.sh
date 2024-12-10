#!/bin/bash

# Start Xvfb (virtual display) in the background
Xvfb :99 -screen 0 1024x768x16 &

# Set DISPLAY environment variable for JavaFX
export DISPLAY=:99

# Run your JavaFX application
java -jar /app/app.jar
