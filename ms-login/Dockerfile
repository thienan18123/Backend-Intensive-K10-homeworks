FROM gradle:8.14-jdk21 AS build
WORKDIR /build
COPY . ./ms-login
RUN gradle -p ms-login clean build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/ms-login/build/libs/*-boot.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]