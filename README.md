# QuickChatApp — Part 2: Message Management 💬

This repository contains **Part 2** of the QuickChatApp development suite, focusing on the core message creation engine, automated data processing utilities, and strict data validation testing guidelines. 

 🛠️ Features & Architecture

The system utilizes a dedicated domain model and utility framework to process outgoing transmissions safely before they are written to data stores:

 1. The Message Class (`Message.java`)
Manages the structured state variables required for every communication log:
* Stores automated message identification tracking keys.
* Pairs recipient addresses with unique structural hash values.
* Tracks operational user action routing codes (Send, Disregard, or Store).

2. The Message Utility Framework (`MessageUtil.java`)
Handles programmatic validation and back-end background string compilation:
* **Character Limit Guard:** Validates that raw text packets do not exceed 250 characters.
* **Automated Message ID:** Generates unique, 10-digit system identification keys for every entry.
* **Data Verification Hashing:** Compiles a custom uppercase signature signature (e.g., `00:0:HITONIGHT`) to track transactional records.

 Unit Testing Scenarios (JUnit 5)

Our automated unit testing suite ensures data validation checks work flawlessly across both positive and negative execution paths:

| Component Under Test | Success Condition Test | Failure Condition Test |
| **Message Length** | Verifies text layout under 250 characters returns `"Message ready to send."` | Verifies text exceeding the 250-character limit calculates the exact overflow count. |
| **Recipient Cell** | Validates international `+27` prefix codes capture cleanly. | Rejects strings missing the explicit country formatting layout. |
| **Message Hash** | Assures string formatting logic generates the exact character placement signature. | N/A |
| **Message ID** | Confirms generated tracking sequence contains exactly 10 digits. | N/A |
| **Action Code** | Confirms proper console response text routing layouts for choices 1, 2, and 3. | N/A |

## 💻 Running the Part 2 Test Suite

1. Open the project workspace inside **NetBeans**.
2. Locate the test directory folder.
3. Right-click **`MessageUtilTest.java`** or **`MessageTest.java`** and click **Test File** (or press `Ctrl + F6`) to execute the verification checks.

## 📚 Academic References & Collaboration

The structural design parameters, validation bounds, and reporting models implemented throughout this project follow strict academic honesty guidelines.

### Harvard Institutional Citation:
THE IIE. 2026. *Software Engineering (SEST6221/W/P) Portfolio of Evidence (POE) Part 2 Task Brief*. Independent Institute of Education: Unpublished.

### Peer Collaboration Acknowledgment:
The development, loop construction, and logic validation structures for the Part 2 Message modules were built in collaborative peer-to-peer programming sessions.

* **Co-Developer / Contributor:** [HLOMLA DLEKEDLA]

### Harvard Citation for Personal Collaboration:
NGCOBO, P. & [DLEKEDLA,H]. 2026. *QuickChatApp: Part 2 Message Validation and Utility Framework*. (Personal collaboration/Co-authored software code). Unpublished Java Application.
