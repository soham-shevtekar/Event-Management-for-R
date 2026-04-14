# Build stage using OpenJDK 17
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copy all project files
COPY . .

# Fix line endings to prevent 'bad interpreter' errors when built from Windows
RUN sed -i 's/\r$//' ./mvnw

# Give execute permission to Maven wrapper
RUN chmod +x ./mvnw

# Build the application using Maven wrapper, skipping tests
RUN ./mvnw clean package -DskipTests

# Run stage using lightweight JRE 17
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the generated JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for Render
ENV PORT=8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
