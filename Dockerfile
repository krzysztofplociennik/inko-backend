FROM gradle:9.0.0-jdk24-alpine as builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY . ./
RUN chmod +x gradlew
RUN ./gradlew :web:build -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/web/build/libs/web-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]