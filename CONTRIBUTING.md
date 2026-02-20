# Contributing to Calculator-2026-Group2

## Table of Contents

1. [Getting Started](#getting-started)
2. [How to Contribute](#how-to-contribute)
3. [Coding Conventions](#coding-conventions)
4. [Testing](#testing)
5. [Reporting Bugs](#reporting-bugs)

## Getting Started

To work on this project, you will need:

*   **Java 25**: Ensure you have a compatible JDK installed.
*   **Maven**: Used for dependency management and building the project.

Clone the repository:

```bash
git clone https://github.com/Gnarks/Calculator-2026-Group2.git
cd Calculator-2026-Group2
mvn clean install
```

## How to Contribute

1.  **Fork** the repository on GitHub.
2.  **Clone** your fork locally.
3.  Create a **new branch** for your feature or bugfix:
    ```bash
    git checkout -b feature/my-new-feature
    ```
4.  **Commit** your changes with clear, descriptive messages.
5.  **Push** to your fork:
    ```bash
    git push origin feature/my-new-feature
    ```
6.  Submit a **Pull Request** (PR) to the `main` branch of the original repository.

## Coding Conventions

*   **Language**: Java.
*   **Style**: Please follow standard Java naming conventions (CamelCase for classes, camelCase for methods/variables).
*   **Documentation**: Add Javadoc for public methods and classes.
*   **Commits**: Use imperative mood in commit messages (e.g., "Add subtraction feature" not "Added subtraction feature").

## Testing

We use **JUnit 5** for unit testing and **Cucumber** for BDD.

*   Every new feature **must** be accompanied by tests.
*   Run tests locally before submitting a PR:
    ```bash
    mvn test
    ```
*   Ensure that existing tests do not fail.

## Reporting Bugs

If you find a bug, please create an issue on GitHub describing:
1.  The version you are using.
2.  Steps to reproduce the error.
3.  Expected vs. actual behavior.
