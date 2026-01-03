package com.example.bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.example.bank.exception.InsufficientFundsException;
import com.example.bank.exception.InvalidAmountException;
import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.service.Bank;

public class App {
    private static final Bank bank = new Bank();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Basic Banking System ===");
        boolean running = true;
        while (running) {
            showMenu();
            String choice = prompt("Choose an option");
            try {
                switch (choice) {
                    case "1":
                        createSavings();
                        break;
                    case "2":
                        createChecking();
                        break;
                    case "3":
                        deposit();
                        break;
                    case "4":
                        withdraw();
                        break;
                    case "5":
                        transfer();
                        break;
                    case "6":
                        showBalance();
                        break;
                    case "7":
                        showHistory();
                        break;
                    case "8":
                        listAccounts();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InvalidAmountException | InsufficientFundsException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
                ex.printStackTrace(System.out);
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n1) Create Savings Account");
        System.out.println("2) Create Checking Account");
        System.out.println("3) Deposit");
        System.out.println("4) Withdraw");
        System.out.println("5) Transfer");
        System.out.println("6) Show Balance");
        System.out.println("7) Show Transaction History");
        System.out.println("8) List Accounts");
        System.out.println("0) Exit");
    }

    private static void createSavings() {
        String name = prompt("Owner name");
        BigDecimal deposit = readAmount("Initial deposit (or empty for 0)");
        BigDecimal interest = readAmount("Interest rate (e.g., 0.02 for 2%) (or empty for 0)");
        Account acc = bank.createSavingsAccount(name, deposit, interest);
        System.out.println("Created Savings Account: " + acc.getAccountNumber());
    }

    private static void createChecking() {
        String name = prompt("Owner name");
        BigDecimal deposit = readAmount("Initial deposit (or empty for 0)");
        BigDecimal overdraft = readAmount("Overdraft limit (or empty for 0)");
        Account acc = bank.createCheckingAccount(name, deposit, overdraft);
        System.out.println("Created Checking Account: " + acc.getAccountNumber());
    }

    private static void deposit() {
        String accNum = prompt("Account number");
        Account acc = bank.getAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        BigDecimal amount = readAmount("Amount to deposit");
        acc.deposit(amount);
        System.out.println("Deposited " + amount + ". New balance: " + acc.getBalance());
    }

    private static void withdraw() {
        String accNum = prompt("Account number");
        Account acc = bank.getAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        BigDecimal amount = readAmount("Amount to withdraw");
        acc.withdraw(amount);
        System.out.println("Withdrew " + amount + ". New balance: " + acc.getBalance());
    }

    private static void transfer() {
        String from = prompt("From account number");
        String to = prompt("To account number");
        BigDecimal amount = readAmount("Amount to transfer");
        bank.transfer(from, to, amount);
        System.out.println("Transferred " + amount + " from " + from + " to " + to);
    }

    private static void showBalance() {
        String accNum = prompt("Account number");
        Account acc = bank.getAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Account " + acc.getAccountNumber() + " balance: " + acc.getBalance());
    }

    private static void showHistory() {
        String accNum = prompt("Account number");
        Account acc = bank.getAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        List<Transaction> history = acc.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        System.out.println("Transaction history for " + acc.getAccountNumber() + ":");
        history.forEach(t -> System.out.println(t));
    }

    private static void listAccounts() {
        System.out.println("Accounts:");
        bank.listAccounts().forEach(a -> System.out.printf("%s — %s — balance: %s%n",
                a.getAccountNumber(), a.getOwnerName(), a.getBalance()));
    }

    private static String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    @SuppressWarnings("deprecation")
	private static BigDecimal readAmount(String label) {
        System.out.print(label + ": ");
        String s = scanner.nextLine().trim();
        if (s.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid numeric amount.");
        }
    }
}