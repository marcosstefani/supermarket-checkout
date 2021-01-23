FROM openjdk:11.0.8-slim-buster

COPY wiremock-standalone-2.14.0.jar /app/wiremock-standalone-2.14.0.jar
COPY start.sh /app/start.sh
COPY mappings/ /app/mappings/
WORKDIR /app

EXPOSE 8081

ENTRYPOINT ["bash", "./start.sh"]