FROM openjdk:23-slim-bullseye

RUN useradd -s /bin/bash serveruser
USER serveruser

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
