# Basic Banking System (Java)

A lightweight Java console application demonstrating a basic banking system:
- Account classes with inheritance (SavingsAccount, CheckingAccount)
- Deposit, withdraw, transfer operations
- Transaction history per account
- Custom exceptions (InsufficientFundsException, InvalidAmountException)
- Simple Bank service for managing accounts
- CLI (App) for interactive use

Requirements
- Java 11+ (configured in pom.xml)
- Maven

Build and run
1. Build: mvn clean package
2. Run from Maven:
   mvn exec:java -Dexec.mainClass="com.example.bank.App"

Or run the jar:
1. mvn package
2. java -cp target/basic-banking-system-1.0-SNAPSHOT.jar com.example.bank.App

Project layout
- src/main/java/com/example/bank
  - App.java (CLI)
  - model: Account, SavingsAccount, CheckingAccount, Transaction
  - service: Bank
  - exception: InsufficientFundsException, InvalidAmountException

Notes
- Persistence is in-memory only (no database). You can add serialization or DB support later.
- Monetary values use BigDecimal to avoid floating-point issues.
- Account numbers are generated sequentially for demo purposes.

Sample usage
- Create accounts, deposit, withdraw, transfer, view transaction history and balances.
- Input is validated and exceptions are handled with user-friendly messages.

Feel free to ask for:
- A GUI (Swing/JavaFX) version
- Persistence using files or an embedded DB (H2)
- Unit tests (JUnit)