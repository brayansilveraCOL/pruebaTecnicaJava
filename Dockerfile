FROM gradle:8.9-jdk21-alpine AS build
WORKDIR /workspace
COPY --chown=gradle:gradle . .
RUN --mount=type=cache,target=/home/gradle/.gradle \
    gradle --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
