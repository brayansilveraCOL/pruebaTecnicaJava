# syntax=docker/dockerfile:1.7

ARG JDK_VERSION=21
ARG GRADLE_VERSION=8.9

# ---- Stage 1: build con Gradle (caché de dependencias) ----
FROM gradle:${GRADLE_VERSION}-jdk${JDK_VERSION}-alpine AS build
WORKDIR /workspace

# Copiamos solo archivos de configuración primero para aprovechar caché
COPY build.gradle* settings.gradle* gradlew gradle/ ./
RUN ./gradlew --version

# Ahora el resto del código
COPY . .

# (Opcional) usa BuildKit para cachear .gradle:
# RUN --mount=type=cache,target=/home/gradle/.gradle ./gradlew --no-daemon clean bootJar
RUN ./gradlew --no-daemon clean bootJar

# ---- Stage 2: runtime liviano ----
FROM eclipse-temurin:${JDK_VERSION}-jre-alpine AS runtime
WORKDIR /app

# Opciones JVM razonables para contenedores
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=25 -XX:+UseContainerSupport"

# Copia el jar construido
COPY --from=build /workspace/build/libs/*.jar app.jar

# (Opcional) Healthcheck si tienes actuator
# HEALTHCHECK --interval=30s --timeout=3s --start-period=30s \
#   CMD wget -qO- http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
