FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:resolve

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

COPY --from=build /app/target/your-app-name.jar /app/your-app-name.jar


ENTRYPOINT ["java", "-jar", "/app/your-app-name.jar"]
