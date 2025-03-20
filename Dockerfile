FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY . ./

RUN chmod +x gradlew

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /web/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]