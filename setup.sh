#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Define variables
WAR_FILE="weather-app-1.0-SNAPSHOT.war"
DOCKER_COMPOSE_FILE="docker-compose.yml"

# Print a message
echo "Starting the setup process..."

# Clean Maven build artifacts
echo "Cleaning Maven build artifacts..."
mvn clean

# Build the project
echo "Building the project..."
mvn package

# Stop and remove any existing Docker containers
echo "Stopping and removing existing Docker containers..."
docker-compose -f $DOCKER_COMPOSE_FILE down

# Start Docker containers and deploy the WAR file
echo "Starting Docker containers and deploying the WAR file..."
docker-compose -f $DOCKER_COMPOSE_FILE up -d

# Print a message indicating the end of the setup process
echo "Setup complete. Your application is now running."

# Optional: Display logs from Docker containers (uncomment if needed)
# echo "Displaying Docker container logs..."
# docker-compose -f $DOCKER_COMPOSE_FILE logs -f
