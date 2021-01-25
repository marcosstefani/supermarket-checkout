FROM openjdk:11.0.8-slim-buster

COPY . /app
WORKDIR /app
RUN ./gradlew build
RUN rm -rfv ./gradlew
RUN find . -type f -name "*.jar"
RUN mv ./build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Duser.timezone=Europe/London"]