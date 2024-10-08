FROM openjdk:17.0.1-jdk-slim

CMD ["./gradlew", "clean", "build"]

WORKDIR /app

COPY src/main/resources/application.yml ./application.yml

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:///app/application.yml"]
