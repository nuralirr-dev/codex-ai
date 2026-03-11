# API Docs UI (Swagger)

Отдельный Spring Boot проект для отображения OpenAPI/Swagger UI по схеме:
- UI поднимается локально (`http://localhost:8085/`)
- OpenAPI JSON подтягивается из другого сервиса (например, `http://localhost:8080/api/v3/api-docs/01.all-api`)

## Требования

- Java 21
- Spring Boot 4.0.3

## Запуск

```bash
mvn spring-boot:run
```

По умолчанию UI защищен Basic-авторизацией:
- username: `swagger`
- password: `swagger`

## Конфигурация

Смотрите `src/main/resources/application.yml`:

- `external.api-docs.base-url` — базовый URL внешнего OpenAPI endpoint, без группы в конце.
  - Пример: `http://localhost:8080/api/v3/api-docs`
- `springdoc.swagger-ui.url` — локальный proxy endpoint группы.
  - По умолчанию: `/api-docs/01.all-api`

### Авторизация при чтении внешнего api-docs

Если внешний сервис с `api-docs` требует авторизацию, можно указать одно из:

- Basic:
  - `external.api-docs.username`
  - `external.api-docs.password`
- Bearer:
  - `external.api-docs.bearer-token`

## Как это работает

1. Swagger UI открывается в этом проекте.
2. UI запрашивает `GET /api-docs/{group}` у этого же проекта.
3. Контроллер проксирует запрос на внешний сервис: `{external.api-docs.base-url}/{group}`.
4. При необходимости добавляется Basic или Bearer авторизация к запросу внешнего `api-docs`.

> Для кнопки `Authorize` в Swagger UI убедитесь, что во внешнем OpenAPI JSON описаны security schemes.
