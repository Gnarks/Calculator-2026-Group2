# Instructions for AI Agents

This file contains context, guidelines, and conventions for Artificial Intelligence agents (LLMs, coding assistants) working on the **Calculator-2026-Group2** repository.

## Project Context

*   **Project Name**: Calculator-2026-Group2
*   **Description**: A Java-based arithmetic calculator project for the Software Evolution course at the University of Mons (2026).
*   **Purpose**: Educational project to demonstrate software evolution, design patterns (Composite, Visitor), and testing (JUnit, Cucumber).
*   **Key Dependencies**: Java 25, Maven, JUnit 5, Cucumber, JaCoCo.

## Coding Conventions

When generating code, please adhere to the following:

1.  **Language**: Java (Version 25 compatibility).
2.  **Style**:
    *   Use standard Java Naming Conventions.
    *   Classes: `PascalCase`
    *   Methods/Variables: `camelCase`
    *   Constants: `UPPER_SNAKE_CASE`
3.  **Documentation**:
    *   Add Javadoc for all public classes and methods.
    *   Include `@param`, `@return`, and `@throws` tags where applicable.
4.  **Error Handling**:
    *   Use specific exceptions rather than generic `Exception`.
    *   Include meaningful error messages.

## Testing Guidelines

*   **Frameworks**:
    *   Unit Tests: **JUnit 5** (`org.junit.jupiter.api`)
    *   BDD: **Cucumber** (`io.cucumber.java`)
*   **Location**:
    *   Code: `src/main/java/calculator`
    *   Tests: `src/test/java/calculator`
    *   Features: `src/test/resources/calculator/*.feature`
*   **Requirement**:
    *   All new logic must be covered by a corresponding test case.
    *   Prefer writing a Cucumber Scenario for functional requirements.

## Build & Run

*   **Build**: `mvn clean install`
*   **Run Tests**: `mvn test`
*   **Run App**: Use `src/main/java/calculator/Main.java`.

## Git & Version Control

*   **Commit Messages**: Simple, imperative mood (e.g., "Add feature X", "Fix bug Y").
*   **Files to Ignore**: Respect `.gitignore`. Do not suggest committing `target/`, `.idea/`, or `*.class` files.

## Documentation Maintenance

*   Update `CHANGELOG.md` under `[Unreleased]` when adding significant features.
*   Update `README.md` if architectural changes or new setup steps are introduced.
