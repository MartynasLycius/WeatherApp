# our base build image
FROM maven:3.8.3-openjdk-17 as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src
COPY ./frontend ./frontend

# build for release
RUN mvn clean package -Pproduction

# our final base image
FROM eclipse-temurin:17-jre

# set deployment directory
WORKDIR /weather-app

# copy over the built artifact from the maven image
COPY --from=maven target/*.jar app.jar

# set the startup command to run the binary
CMD ["java", "-jar", "./app.jar"]
