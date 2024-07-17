# Variables
WAR_FILE=weather-app-1.0-SNAPSHOT.war
DOCKER_COMPOSE_FILE=docker-compose.yml

# Default target
.PHONY: all
all: build deploy

# Build the WAR file using Maven
.PHONY: build
build:
	@echo "Building the project..."
	mvn clean package

# Deploy the WAR file to Docker containers
.PHONY: deploy
deploy: build
	@echo "Deploying to Docker..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

# Stop and remove Docker containers
.PHONY: stop
stop:
	@echo "Stopping Docker containers..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) down

# Remove the WAR file and other build artifacts
.PHONY: clean
clean:
	@echo "Cleaning up build artifacts..."
	mvn clean

# Clean and rebuild the project, then deploy
.PHONY: rebuild
rebuild: clean build deploy

# Show the status of the Docker containers
.PHONY: status
status:
	@echo "Showing Docker container status..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) ps

# Display logs from Docker containers
.PHONY: logs
logs:
	@echo "Showing Docker container logs..."
	docker-compose -f $(DOCKER_COMPOSE_FILE) logs -f
