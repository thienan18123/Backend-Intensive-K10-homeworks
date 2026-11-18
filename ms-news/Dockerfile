FROM gradle:8.14-jdk21 AS build
WORKDIR /build

COPY ms-login ./ms-login
RUN gradle -p ms-login publishToMavenLocal -x test --no-daemon

COPY ms-news ./ms-news
RUN gradle -p ms-news bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/ms-news/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]