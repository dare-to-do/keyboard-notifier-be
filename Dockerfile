FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

COPY src/main/resources/application.yml ./application.yml

COPY build/libs/*.jar app.jar

CMD ["./gradlew", "clean", "build"]

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:///app/application.yml"]
