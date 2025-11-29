# ЭТАП 1: Сборка (Build)
# МЕНЯЕМ 17 на 21
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew

COPY src src

# Собираем приложение
RUN ./gradlew bootJar -x test --no-daemon

# ЭТАП 2: Запуск (Run)
# МЕНЯЕМ 17 на 21
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Копируем jar. Обратите внимание, что команда копирует первый попавшийся jar.
# Чтобы избежать ошибки с двумя файлами (plain и boot), лучше уточнить имя или отключить plain в gradle.
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]