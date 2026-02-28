🤖 Auto Heal Innovators

📌 Problem Statement:

In traditional Selenium automation frameworks:

    1.Test cases fail when locators change

    2.Even small DOM updates break automation

    3.Maintenance effort is very high

    4.QA teams spend significant time fixing broken locators

    5.In fast-paced Agile environments, UI changes happen frequently, causing automation instability and increasing regression maintenance cost.

💡 Proposed Solution:

This project introduces an AI-powered Self-Healing Locator Framework that:

    1.Automatically detects broken locators

    2.Scans the live DOM dynamically

    3.Applies intelligent similarity matching

    4.Calculates confidence score using weighted logic

    5.Automatically generates new locator

    6.Updates JSON locator repository

    7.Continues test execution without failure

The system transforms traditional Selenium into a self-adaptive automation engine.

🏗️ Architecture Overview:

The framework consists of the following layers:

1️⃣ Test Layer

    1.TestNG Test Classes

    2.Page Object Model

    3.Java Test Scripts

    4.Responsible for executing test cases.

2️⃣ SmartDriver Layer

    1.Custom wrapper over Selenium WebDriver

    2.Central point for locator execution

    3.Intercepts failures and triggers healing

    4.This layer prevents direct Selenium calls.

3️⃣ Healing Engine Layer

    Core intelligence module:

    1.DOM Capture (driver.getPageSource())

    2.DOM Parsing

    3.Attribute Extraction (id, name, class, text, tag)

    4.Fuzzy String Matching

    5.Weighted Scoring Algorithm

    6.Confidence Threshold Validation

    7.Best Match Selection

4️⃣ Data Storage Layer

    1.JSON (LocatorStorage)

    2.Stores original and healed locators

    3.Automatically updated after successful healing

5️⃣ Reporting & Evidence Layer

    1.TXT Logs

    2.HTML Reports

    3.Screenshot Capture (TakesScreenshot)

🔁 Application Flow
Step 1: Normal Execution

    Test starts

    Page Object calls SmartDriver

    SmartDriver reads locator from JSON

    Selenium tries original locator

    If element is found →
    Test continues normally.

Step 2: Locator Failure

    If Selenium throws NoSuchElementException:

    Healing Engine activates

Step 3: Healing Process

    Capture current DOM

    Parse DOM elements

    Extract attributes (id, name, class, text, tag)

    Apply fuzzy matching (string similarity)

    Calculate weighted score

    Select element with highest score

    Compare with confidence threshold

    If score ≥ threshold:

    Generate new locator

    Retry action

    Update JSON

    Capture screenshot

    Log healing success

    Continue test

    If score < threshold:

    Log healing failure

    Mark test as failed

📂 Project Structure
Smart-Self-Healing-Framework/
│
├── README.md
├── pom.xml
│
├── reports/
│   ├── healing-report.html       
│
├── storage/
│   └── locator-storage.json
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── driver/
│   │   │   │   └── SmartDriver.java
│   │   │   │
│   │   │   ├── model/
│   │   │   │   └── ElementMetadata.java
│   │   │   │
│   │   │   ├── storage/
│   │   │   │   └── LocatorStorage.java
│   │   │   │
│   │   │   ├── utils/
│   │   │       ├── StringSimilarity.java
│   │   │       ├── ScreenshotUtil.java
│   │   │       └── HealingReport.java
│   │   │   
│   │
│   └── test/
│       └── java/
│           └── base/
│               └── BaseTest.java
|

⚙️ Setup Instructions
1️⃣ Clone Repository
git clone <your-public-repo-url>
cd Auto-Healing-Framework
2️⃣ Install Dependencies

Ensure Maven is installed:

mvn clean install
3️⃣ Run Tests
mvn test

Or run using TestNG from IDE.

🧠 Technical Highlights:

    1.Custom SmartDriver wrapper

    2.AI-inspired fuzzy similarity matching

    3.Weighted attribute scoring logic

    4.Dynamic locator regeneration

    5.Automatic JSON updates

    6.Screenshot evidence on healing

    7.Clean layered architecture

    8.Reduced test maintenance effort

🧮 Healing Algorithm Logic:
1.Fuzzy Matching

Instead of exact comparison:

loginBtn != loginButtonNew

We calculate similarity:

Similarity = 0.82
Weighted Scoring

Each attribute has importance weight:

ID → 40%

Class → 20%

Text → 20%

Tag → 20%

Final Score = Weighted sum

Confidence Threshold
If score ≥ 0.75 → Accept
Else → Reject

This ensures accuracy and prevents wrong element selection.

🔐 Security & Best Practices:

    1.No hardcoded credentials

    2.Clean separation of concerns

    3.Reusable healing engine

    4.JSON-based dynamic locator storage

🚀 Future Enhancements: 

    1.Machine Learning based locator prediction

    2.CI/CD Integration

    3.Jira integration for automatic bug logging

    4.Dashboard analytics for healing metrics

    5.Cross-browser healing stability analysis

🎯 Hackathon Impact:

This framework reduces:

    1.Locator maintenance effort

    2.Regression instability

    3.Manual intervention

    4.It converts traditional Selenium into an intelligent self-healing automation system.