FROM apache/beam_java17_sdk:latest
WORKDIR /app
COPY build/libs/analytics-manager-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "analytics-manager-0.0.1-SNAPSHOT.jar"]