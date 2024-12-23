FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the application's JAR file to the container
COPY target/conferenceManagement-0.0.1-SNAPSHOT.jar app.jar
# Expose the port your Spring Boot app runs on
EXPOSE 8080
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


# docker build -t conference-management .
# docker run -p 8080:8080 conference-management
# docker-compose up
