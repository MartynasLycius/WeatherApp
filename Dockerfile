FROM maven:3.8.3-openjdk-17 as builder

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
COPY ./frontend ./frontend
RUN mvn clean package -Pproduction


FROM eclipse-temurin:17-jre

WORKDIR /weather-app

COPY --from=builder target/*.jar app.jar

CMD ["java", "-jar", "./app.jar"]