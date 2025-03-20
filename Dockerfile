FROM gradle:8.5.0-jdk17-alpine as builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY . ./
RUN chmod +x gradlew
RUN ./gradlew :web:build -x test
RUN ls -l /app/inko-backend/web/build/libs/

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/inko-backend/web/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]