# 🥒 Enterprise Scalable Test Automation Framework

A robust, thread-safe BDD testing framework built from scratch to demonstrate modern SDET architecture. Designed for high scalability, this framework leverages **Java**, **Selenium 4**, and **Cucumber** with **TestNG** for parallel execution.

![CI/CD Status](https://github.com/SDETBMan/cucumber-framework-portfolio/actions/workflows/maven.yml/badge.svg)

## 🚀 Key Features

* **Hybrid BDD Architecture:** Clear separation of concerns using Feature files, Step Definitions, and Page Object Model (POM).
* **Parallel Execution:** Implemented `ThreadLocal<WebDriver>` to ensure thread safety, allowing multiple tests to run simultaneously (10x execution speedup).
* **Rich Reporting:** Integrated **Extent Reports 5** (Spark Reporter) for dashboard-style analytics and failure screenshots.
* **CI/CD Integration:** Fully automated pipeline via **GitHub Actions** running tests in Headless Chrome on Ubuntu containers.
* **Singleton Design Pattern:** Efficient `DriverManager` to handle browser instances and prevent resource leaks.

## 🛠️ Tech Stack

* **Language:** Java 11
* **Core Library:** Selenium WebDriver 4
* **Runner:** TestNG (configured for parallel scenarios)
* **BDD:** Cucumber 7
* **Build Tool:** Maven
* **CI/CD:** GitHub Actions

## 📊 Reporting

The framework generates a "Manager-Friendly" interactive dashboard at:
`target/spark-reports/Spark.html`

It provides:
* Pie charts of Pass/Fail status.
* Step-by-step logs for every scenario.
* Timestamps and performance metrics.

## ⚡ How to Run

### Local Execution (GUI Mode)
To run the tests on your local machine and see the browser:
```bash
mvn clean test
```

CI/CD / Headless Mode
The framework automatically detects if it's running in a CI environment (like Jenkins or GitHub Actions) and switches to Headless Chrome to run without a GUI.

📂 Project Structure
```
src/test/java
├── com.saucedemo.pages           # Page Object Classes (Locators & Methods)
├── com.saucedemo.stepDefinitions # Glue Code linking Gherkin to Java
├── com.saucedemo.runners         # TestNG Runner with Parallel settings
├── com.saucedemo.utils           # Driver Factory & Utilities
src/test/resources
├── features                      # Gherkin Feature Files (.feature)
├── extent.properties             # Reporting Configuration



