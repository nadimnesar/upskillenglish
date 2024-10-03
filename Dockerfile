FROM gradle:8.8-jdk17 as build
LABEL authors="nadimnesar"

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim

ENV GEMINI_KEY=your_gemini_api_key \
    HUGGINGFACE_KEY=your_huggingface_key \
    POSTGRES_DBNAME=your_db_name \
    POSTGRES_USERNAME=your_db_username \
    POSTGRES_PASSWORD=your_db_password \
    BASE64_CODE=your_jwt_secret_key

WORKDIR /app

COPY --from=build /app/build/libs/upskillenglish-0.0.1-SNAPSHOT.jar /app/upskillenglish.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/upskillenglish.jar"]