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
./mvnw -Dheadless=true -DsuiteXmlFile=testng.xml clean test
```

## CI

A GitHub Actions workflow is configured at `.github/workflows/ci.yml` to run smoke and quality checks on PRs and pushes.

## Security

- We run OWASP Dependency-Check in CI and periodically scan for CVEs.
- Dependabot/Renovate is recommended to auto-raise dependency updates.

## Recommended next steps

- Add Maven Wrapper: `mvn -N io.takari:maven:wrapper`
- Enable Dependabot in repo settings or add a `dependabot.yml` to keep dependencies up-to-date.
- Add SonarCloud or local SonarQube for deeper code quality insights.
