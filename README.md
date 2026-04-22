# Selenium Learning Framework

This repository contains UI tests using Selenium WebDriver and TestNG.

## Quickstart

Prerequisites:
- Java 21
- Maven 3.8+

Run tests locally:

```powershell
# Preferred: use the Maven wrapper (bootstrap once with Maven):
mvn -N io.takari:maven:wrapper
./mvnw -Dheadless=true -DsuiteXmlFile=testng-ui.xml clean test
```

## UI Testing (Selenium)

Run UI suites:

```powershell
# UI regression
./mvnw "-DsuiteXmlFile=testng-ui.xml" test

# UI smoke
./mvnw "-DsuiteXmlFile=testng-ui-smoke.xml" test

# UI parallel regression
./mvnw "-DsuiteXmlFile=testng-ui-parallel.xml" test
```

## API Testing (Rest-Assured)

Default API base URL comes from `src/test/resources/framework.properties`:
- `api.base.url=https://jsonplaceholder.typicode.com`
- `api.users.path=/users`
- `api.orders.path=/posts` (demo default for jsonplaceholder)

Run API suites:

```powershell
# All API tests
./mvnw "-DsuiteXmlFile=testng-api.xml" test

# API smoke tests
./mvnw "-DsuiteXmlFile=testng-api-smoke.xml" test

# API regression tests
./mvnw "-DsuiteXmlFile=testng-api-regression.xml" test
```

Maven profiles for test separation:

```powershell
# UI
./mvnw -Pui test
./mvnw -Pui-smoke test
./mvnw -Pui-parallel test

# API
./mvnw -Papi test
./mvnw -Papi-smoke test
./mvnw -Papi-regression test
```

Authentication options:
- `api.auth.type=none|bearer|api_key|login`
- `api.auth.token` for bearer token auth
- `api.auth.key.name`, `api.auth.key.value`, `api.auth.key.in=header|query` for API key auth
- `api.auth.login.path`, `api.auth.login.username`, `api.auth.login.password`, `api.auth.login.token.json.path` for login-based token auth
- `api.auth.header.name`, `api.auth.scheme` to control Authorization header format
- Environment variable fallbacks are supported:
  `API_BASE_URL`, `API_AUTH_TYPE`, `API_AUTH_TOKEN`, `API_AUTH_KEY_NAME`, `API_AUTH_KEY_VALUE`, `API_AUTH_KEY_IN`,
  `API_AUTH_LOGIN_PATH`, `API_AUTH_LOGIN_USERNAME`, `API_AUTH_LOGIN_PASSWORD`, `API_AUTH_LOGIN_TOKEN_JSON_PATH`

Endpoint path overrides (optional):
- `api.users.path` / `API_USERS_PATH`
- `api.orders.path` / `API_ORDERS_PATH`

Example with runtime overrides:

```powershell
./mvnw "-DsuiteXmlFile=testng-api.xml" `
  "-Dapi.base.url=https://your-api-host" `
  "-Dapi.auth.type=bearer" `
  "-Dapi.auth.token=YOUR_TOKEN" test
```

PowerShell example with environment variables (recommended for secrets):

```powershell
$env:API_BASE_URL = "https://your-api-host"
$env:API_AUTH_TYPE = "bearer"
$env:API_AUTH_TOKEN = "YOUR_TOKEN"
$env:API_USERS_PATH = "/users"
$env:API_ORDERS_PATH = "/orders"
./mvnw "-DsuiteXmlFile=testng-api-smoke.xml" test
```

PowerShell example with automatic `/auth/login` token flow:

```powershell
$env:API_BASE_URL = "https://your-api-host"
$env:API_AUTH_TYPE = "login"
$env:API_AUTH_LOGIN_PATH = "/auth/login"
$env:API_AUTH_LOGIN_USERNAME = "api-user"
$env:API_AUTH_LOGIN_PASSWORD = "super-secret"
$env:API_AUTH_LOGIN_TOKEN_JSON_PATH = "data.accessToken"
$env:API_AUTH_SCHEME = "Bearer"
$env:API_USERS_PATH = "/users"
$env:API_ORDERS_PATH = "/orders"
./mvnw "-DsuiteXmlFile=testng-api-regression.xml" test
```

Contract-driven payload/schema setup:
- Request payloads are loaded from `src/test/resources/payloads/**`.
- Response schemas are loaded from `src/test/resources/schemas/**`.
- You can point tests to real backend contracts with:
  `api.users.create.payload.path`, `api.users.update.payload.path`,
  `api.orders.create.payload.path`, `api.orders.update.payload.path`,
  `api.users.response.schema.path`, `api.orders.response.schema.path`,
  `api.delete.response.schema.path`.

OpenAPI template:
- A runnable sample contract for current default API tests is available at
  `openapi/openapi-jsonplaceholder.yaml`.
- A generic starter template is available at `openapi/openapi-template.yaml`.
- First customization targets:
  `servers[0].url`, `/auth/login`, `/users`, `/orders` and related schemas.
- Keep endpoint names and schema fields aligned with
  `src/test/resources/framework.properties` and payload/schema files.

OpenAPI contract validation in tests:
- Integration is available via Rest-Assured filter and is disabled by default.
- Enable with:
  `-Dapi.contract.validation.enabled=true`
- Optionally set spec path with:
  `-Dapi.contract.openapi.spec.path=openapi/openapi-jsonplaceholder.yaml`

Example:

```powershell
./mvnw "-DsuiteXmlFile=testng-api-smoke.xml" `
  "-Dapi.contract.validation.enabled=true" `
  "-Dapi.contract.openapi.spec.path=openapi/openapi-jsonplaceholder.yaml" test
```

Schema validation:
- API responses are validated with JSON Schema files under `src/test/resources/schemas`.
- `post-response.schema.json` is used for `GET/POST/PUT`.
- `delete-response.schema.json` is used for non-204 delete responses.
- `user-response.schema.json` is used for user endpoints.
- `order-response.schema.json` is used for order endpoints.

## CI

A GitHub Actions workflow is configured at `.github/workflows/ci.yml` to run smoke and quality checks on PRs and pushes.

## Security

- We run OWASP Dependency-Check in CI and periodically scan for CVEs.
- Dependabot/Renovate is recommended to auto-raise dependency updates.

## Recommended next steps

- Add Maven Wrapper: `mvn -N io.takari:maven:wrapper`
- Enable Dependabot in repo settings or add a `dependabot.yml` to keep dependencies up-to-date.
- Add SonarCloud or local SonarQube for deeper code quality insights.
