# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]
## Added
- precision command to set the precision of Real number calculations globally (for the CLI)
- precision field to set the precision of Real number calculations globally (for the GUI)
- DockerFile to run backend
- DockerFile to run frontend
- DockerCompose to run the backend and frontend and expose the application
- Random function: 
  - randreal: generates a random number between 0 and 1.
  - randint: generates a random integer between 0 and a maximum.
  - randrat: generates a random rational number with a numerator and denominator between 0 and a maximum. (denominator cannot be zero)
  - randcomplex: generates a random complex number with real and imaginary parts between 0 and a maximum.
- Random generator: generates a random number between a specified minimum and maximum.

### Changed
- README: added information about the way to start the web-app with docker compose
- POM file: The main class is now `calculator.Main` instead of `calculator.CalculatorApplication` to launch the CLI

### Fixed
- In the CLI, the logger no longer displays the date, hour when this is a message from the calculator.

## [v2.1.0]

### Fixed
- Difference between division and rational numbers (/ is for rationnals and // for the real division)

## Changed
- POM file: The main class is now `calculator.Main` instead of `calculator.CalculatorApplication` to launch the CLI.

## [v2.1.0]

### Fixed
- Difference between division and rational numbers (/ is for rationnals and // for the real division)

## [v2.0.0]

### Added
- REST API and GUI 

## [v1.2.0]

### Added
- Command-line interface (CLI): commmand help, exit and eval.

## [v1.0.1]

### Fixed
- Log precision error with Reals

## [v1.0.0]

### Added
- Handling of different number types (Real, Complex, Rational, Integer)
- power operations (**)
- Grammar ANTLR for the calculator, supporting infix, prefix and postfix notations.
- Parser and Visitor for the grammar, allowing to create an Expression tree from a string input.
- Support for unary and binary functions
- Trigonometric functions: sine, cosine, tangent with their inverses and hyperbolic variants.
- Others functions: logarithm, square root.
- Power operator

### Changed
- update dependencies (thanks to dependabot)

### Fixed
- In case of a usage of several notations inside an expression (infix, prefix and postfix), the expression is considered as the type of the top-level.
- Removal of the notation field in the class Operation. Now, the notation is only used when we use the Printer Visitor.



## [v0.0.1]

### Added
- Initial project structure based on `calculator-cucumber-2026` template.
- Basic arithmetic operations: Addition, Subtraction, Multiplication, Division.
- Support for Prefix, Infix, and Postfix notations.
- JUnit 5 tests and Cucumber scenarios.
- CI/CD workflows for Maven build and dependency review. Build on three OS (Linux, Max and Windows).
- CI/CD workflows for security analysis.
- Documentation updates: README badges, .gitignore configuration.
- Added division by zero handling
- CI/CD code analysis and testing report.
- Web-based GUI: Built with Vue 3
- API Integration: Implemented the POST request structure for the /api/evaluate endpoint.

### Changed
- Updated `.gitignore` to ignore the `target/` directory and others files.
- Updated dependencies of Junit and Cucumber to the latest version (6.0.2 and 7.34.2 respectively).
