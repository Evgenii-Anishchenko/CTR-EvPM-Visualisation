ARG JAVA_BASE_IMAGE=eclipse-temurin:17

FROM ${JAVA_BASE_IMAGE} AS build
WORKDIR /app
COPY gradlew gradle.properties build.gradle settings.gradle ./
COPY gradle gradle/
COPY src src/
COPY src/main/resources /app/src/main/resources

RUN chmod +x ./gradlew && ./gradlew bootJar --no-daemon

FROM ${JAVA_BASE_IMAGE}
WORKDIR /app
COPY --from=build /app/build/libs/*.jar analytics-manager-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","analytics-manager-0.0.1-SNAPSHOT.jar"]