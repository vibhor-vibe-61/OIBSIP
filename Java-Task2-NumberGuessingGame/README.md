# Task 2: Number Guessing Game — Oasis Infobyte Java Internship

## 📌 Project Overview
A features-packed, object-oriented console application developed as part of the **Oasis Infobyte Java Development Internship**. The program generates a secret pseudo-random number within a dynamic range based on user-selected difficulty levels (`Easy`, `Medium`, `Hard`). Players have a capped budget of attempts to deduce the secret number with immediate `"Too High!"` or `"Too Low!"` feedback.

---

## 🔗 Presentation & Social Links
- 🌐 **LinkedIn Post:** [View LinkedIn Post](https://lnkd.in/p/ddcc7C-J)
- 📁 **GitHub Repository:** [vibhor-vibe-61/OIBSIP](https://github.com/vibhor-vibe-61/OIBSIP)

---

## ⭐ Core Features

- 🎯 **Random Number Generation:** Dynamic range assignment per difficulty level (`1-50`, `1-100`, `1-200`).
- 🎮 **Difficulty Levels (Bonus Feature):**
  - **Easy:** Range `1–50`, `10` attempts, Base Score: `100` points.
  - **Medium:** Range `1–100`, `7` attempts, Base Score: `150` points.
  - **Hard:** Range `1–200`, `5` attempts, Base Score: `200` points.
- 💡 **Interactive Hints:** Clear `"Too High!"` and `"Too Low!"` feedback.
- 🔢 **Attempt Counter & Bound Limit:** Dynamic remaining attempt updates.
- 📊 **Scoring System:** Dynamic attempt penalty formula:
  $$\text{Score} = \text{BaseScore} - (\text{AttemptsUsed} - 1) \times \text{PenaltyPerAttempt}$$
- 📜 **Previous Guess History & Duplicate Warnings:** Displays previously attempted numbers in active round and warns on duplicates.
- 🛡️ **Robust Input Validation:** Rejects non-numeric characters, empty inputs, and out-of-bounds numbers without crashing.
- 🔄 **Multi-Round Support:** Play multiple rounds continuously while accumulating total cumulative points.

---

## 🏗️ Project Architecture & File Structure

```text
OIBSIP/
└── Java-Task2-NumberGuessingGame/
    ├── src/
    │   └── NumberGuessingGame.java     <-- Source code file
    ├── screenshots/                     <-- Demo execution screenshots
    │   ├── Screenshot 2026-09-19 151347.png
    │   ├── Screenshot 2026-09-19 151402.png
    │   └── Screenshot 2026-09-19 151445.png
    └── README.md                       <-- Project documentation
```

---

## 📷 Screenshots

| Game Execution & Gameplay | Victory & Score Breakdown | Final Game Exit Summary |
| :---: | :---: | :---: |
| ![Gameplay](./screenshots/Screenshot%202026-09-19%20151347.png) | ![Score Breakdown](./screenshots/Screenshot%202026-09-19%20151402.png) | ![Final Exit](./screenshots/Screenshot%202026-09-19%20151445.png) |

---

## 🚀 How to Build & Run

### Prerequisites
- Java Development Kit (JDK 11 or higher)
- Terminal / PowerShell / Command Prompt

### Build Instructions

```bash
# Navigate to project directory
cd Java-Task2-NumberGuessingGame

# Compile source code into 'bin' output directory
javac -d bin src/NumberGuessingGame.java

# Run application
java -cp bin NumberGuessingGame
```

---

## 🧪 Testing Matrix & Verification Checklist

| Test Scenario | Input | Expected Output | Status |
| :--- | :--- | :--- | :--- |
| Difficulty Selection | `1` | Range `1-50`, `10` attempts set | PASS |
| Guess Too High | Guess `>` Secret | `[HINT] Too High! Try a lower number.` | PASS |
| Guess Too Low | Guess `<` Secret | `[HINT] Too Low! Try a higher number.` | PASS |
| Correct Guess | Guess `==` Secret | `[SUCCESS] CONGRATULATIONS!` + Points calculation | PASS |
| Out of Bounds | `999` (in Easy mode) | `[!] Out of bounds! Please enter a number between 1 and 50.` | PASS |
| Non-Numeric Input | `"abc"` | `[!] Invalid input format. Please enter a valid integer.` | PASS |
| Duplicate Guess | Repeat same guess | `[!] You already guessed X!` | PASS |
| Replay Game | `Y` | Starts Round 2 with total score retained | PASS |

---

## 📄 License & Attribution
Developed by **Tekriwal Vibhor Vijay** for the **Oasis Infobyte Java Development Internship** (September 2026 – October 2026).
