# Task 3: ATM Interface

## 📌 Project Overview
An interactive console-based ATM system built in Java adhering to Object-Oriented Programming (OOP) principles.

## 🏗️ Architecture & Classes
- `Account`: Stores account details, balance, PIN validation, deposit, withdraw, and transaction history logging.
- `Transaction`: Represents an individual transaction (DEPOSIT, WITHDRAW, TRANSFER) with timestamp and amount.
- `Bank`: Manages accounts list (`ArrayList<Account>`), authentication, and account search.
- `ATM`: Handles UI menu rendering, user interaction, input validation, and delegation to `Bank`.
- `Main`: Application entry point, sample data initialization, and main runtime loop.

## 🚀 How to Run
```bash
# Compile
javac -d bin src/com/oasis/atm/*.java

# Run
java -cp bin com.oasis.atm.Main
```
