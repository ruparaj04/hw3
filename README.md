# hw3_starter

# CS520 Spring 2026 - Homework 3

## Overview and Goal
In this assignment, you will work with an existing implementation of an Expense Tracker application. This application applies the MVC architecture pattern.
Rather than building brand-new user features, your task is to understand, analyze, and restructure the system using sound software engineering principles.

Treat this as a realistic on-boarding task: you are joining a team with shipped software, and your responsibility is to improve understandability, modularity, extensibility readiness, and testability while preserving current behavior. 

## Getting Started
1. Clone the repository: `git clone https://github.com/CS520-Spring2026/hw3_starter`
2. Read this `README.md` file.
3. Build, test, and run the application using the commands below.
4. Explore source code in `src/` and tests in `test/`.

We'll use the ant build tool (`https://ant.apache.org/manual/installlist.html`) to build and run the application.

## Optional Working Files
You may draft your answers in local markdown files (for example under `docs/`) while working, but these files are optional and are not required for grading.
All written graded content must appear in `HW3_answers.pdf`.

## Build and Run

The Expense Tracker application has the following structure:
- `bin/`: Contains the generated class files
- `jdoc/`: Contains the generated javadoc folders/files
- `lib/`: Contains the third party software jar files
- `src/`: Contains the Java folders and source files
- `test/`: Contains the JUnit test suite source files
- `build.xml`: Is the ant build tool input file
- `build/`: Contains the ant build tool output files
- `coverage_report/`: Contains the test coverage report

The build requirements are:
- JDK 21+: Generate API doc (javadoc), compile (javac), run (java)
- Ant 1.10.15+: Build and run the application and test suite(s)

From the root directory (containing the build.xml file):

1. Build app: `ant compile`

<!-- 2. Run the app: `java -cp bin ExpenseTrackerApp` -->
2. Run the app:
   - On Windows: `java -cp "bin;lib\xchart-3.8.8.jar;lib\tinylog-api-2.7.0.jar;lib\tinylog-impl-2.7.0.jar" ExpenseTrackerApp`
   - On Linux/macOS: `java -cp "bin:lib/xchart-3.8.8.jar:lib/tinylog-api-2.7.0.jar:lib/tinylog-impl-2.7.0.jar" ExpenseTrackerApp`

3. Build and run tests: `ant test` (See the build/TEST-*.txt files for more details.)

4. Generate the test coverage report: `ant coverage.report` (See `coverage_report/index.html` for more details.)

5. Generate Javadoc: `ant document`

6. Perform linting `ant lint`

7. Clean generated artifacts (e.g., class files, javadoc files): `ant clean`

# Architecture

The Expense Tracker application applies the MVC architecture pattern as follows:
* model package: Contains the data model represented as a list of transactions
* view package: Contains the visualizations of the model and supports user interactions
* controller package: Contains the application logic to process the user interactions

# Features

* **Add Transaction:**
  Enter a valid amount and category, then click **Add Transaction**.
  The valid transaction appears in the list, and the total cost updates automatically.

* **Delete Transaction:**
  Select a valid transaction from the list.
  In the 'Edit' menu, select the 'Delete' menu item.
  The valid transaction disappears from the list, and the total cost updates automatically.

* **Save the Transaction List:**
  In the 'File' menu, select the 'Save As...' menu item
  In the Save dialog, first select a valid output file and then click the 'Save' button

* **Open a Transaction List:**
  In the 'File' menu, select the "Open File..." menu item
  In the Open dialog, first select a valid input file and then click the 'Open' button

* **Analyze:**
  First select the 'Analysis' tab.
  In that tab, select the analysis time window (e.g., 'Last week')
  In the tab, click the 'Analyze' button
  If there are transactions in that time window, displays the analysis results. If not, displays an error message.

# Accessibility

The application supports assistive technologies (e.g., screen readers) via the Java Accessibility API.

## Screen Reader Support

Two improvements were implemented to improve the experience for screen reader users:

### Improvement 1: Label-to-Field Association (`JLabel.setLabelFor`)

In `DataPanelView` and `AnalysisPanelView`, each visible text label is associated with its corresponding interactive component using `JLabel.setLabelFor(component)`. When a screen reader focuses a text field or combo box, it reads the associated label text aloud, telling the user what to enter.

- The **Amount** label is associated with the amount text field.
- The **Category** label is associated with the category text field.
- The **Analysis Time Window** label is associated with the time-window combo box.

### Improvement 2: Accessible Names, Descriptions, and Tooltips on All Interactive Components

Every interactive UI component now has an accessible name (read aloud when focused) and an accessible description (read aloud as additional context), set via `component.getAccessibleContext().setAccessibleName(...)` and `.setAccessibleDescription(...)`. Tooltip text is also set on each component so the descriptions are available via the mouse as well.

Components updated:
- **Amount field** — announces expected input range
- **Category field** — announces valid category values
- **Add Transaction button** — describes its action
- **Transactions table** — describes columns and how to delete a row
- **Time Window combo box** — lists available time window options
- **Analyze button** — describes what it does
- **Analysis status label** — identified as a status/error region
- **File > Open File...** menu item — describes its action
- **File > Save As...** menu item — describes its action
- **Edit > Delete** menu item — describes its action
- **Tabbed pane** — describes the two tabs and their purpose


# Logging

The application uses the [tinylog 2.7.0](https://www.tinylog.org/) library for structured runtime logging.

## Configuration

Logging is configured in `tinylog.properties` at the project root. Log output is written to:
- **Console** — all log levels are printed to standard output while the app is running.
- **File** — `logs/expense-tracker.log` — debug-level and above; the file is overwritten on each run (`append = false`).

Log entries follow the format:
```
{date: yyyy-MM-dd HH:mm:ss} [{level}] {class}.{method}() - {message}
```

## Logging Strategy

Logging statements were added to `ExpenseTrackerController` and `AnalysisPanelView` using three levels:

| Level | What is logged |
|-------|----------------|
| `INFO` | Major user actions: transaction added/deleted, analysis started/completed |
| `DEBUG` | Internal state details: input values (amount, category), selected row ID |
| `WARN` | Invalid or failed operations: unparseable amount, invalid category, no transactions to analyze |

## Log Location

The runtime log is written to `logs/expense-tracker.log`. A sample log covering the standard usage scenario (add three transactions, delete one, analyze with Last week window) is committed in that file.
