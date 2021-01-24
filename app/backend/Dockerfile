FROM openjdk:11.0.8-slim-buster

COPY build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Duser.timezone=Europe/London"]