# Subscription Service

Сервис учёта пользовательских подписок. Позволяет создавать подписки, управлять их статусами и получать статистику.

## Стек

- Kotlin + Spring Boot
- PostgreSQL + Flyway
- Spring Data JPA
- OpenAPI / Swagger
- Docker + Docker Compose

## Жизненный цикл подписки

ACTIVE → PAUSED → ACTIVE → CANCELLED → EXPIRED

PAUSED → CANCELLED EXPIRED → ACTIVE (только через продление /renew)

Истёкшую подписку нельзя возобновить без продления — только через `PATCH /{id}/renew`.

## Scheduler

Каждую ночь в полночь автоматически переводит подписки с истёкшей датой из `ACTIVE` в `EXPIRED`.

```kotlin
cron: "0 0 0 * * *"
```

## Swagger UI

```
http://localhost:8080/v1/api/swagger-ui/index.html
```

## Запуск

### Требования

- Docker + Docker Compose

### Через Docker Compose

```bash
docker-compose up --build
```

Приложение будет доступно на `http://localhost:8080/v1/api/`

### Локально

```bash
# Запустить PostgreSQL
docker-compose up postgres

# Запустить приложение
./gradlew bootRun
```

## Валидация

- Дата окончания должна быть позже даты начала
- Стоимость не может быть отрицательной
- Нельзя возобновить истёкшую подписку без продления
- Нельзя совершить недопустимый переход статуса
