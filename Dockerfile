# Используем официальный образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR файл приложения в контейнер
COPY target/electricity-prices-1.0-SNAPSHOT.jar app.jar

# Открываем порт, на котором работает Spring Boot (8074)
EXPOSE 8074

# Устанавливаем активный профиль для Docker
ENV SPRING_PROFILES_ACTIVE=docker

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]
