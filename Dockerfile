FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
WORKDIR /app
COPY target/mmorpg-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
