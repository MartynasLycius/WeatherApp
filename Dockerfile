# Use a Maven base image for building
FROM maven:3.8.3-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files for dependency resolution
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src
COPY frontend ./frontend

# Build the application
RUN mvn clean package -Pproduction

# Use a Tomcat base image for deployment
FROM tomcat:10.0

# Remove the default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the JAR file (compiled application) into the container
COPY --from=build /app/target/weatherapp-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port your application will run on
EXPOSE 8080

# Command to run the application
CMD ["catalina.sh", "run"]


