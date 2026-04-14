# 1. Use Maven to build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Use a lightweight image to run the jar
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 3. Set the port for Render
ENV PORT=8080
EXPOSE 8080

# 4. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
