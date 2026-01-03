package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.CheckingAccount;
import com.example.bank.model.SavingsAccount;
import com.example.bank.model.Transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account createSavingsAccount(String ownerName, BigDecimal initialDeposit, BigDecimal interestRate) {
        SavingsAccount acc = new SavingsAccount(ownerName, initialDeposit, interestRate);
        accounts.put(acc.getAccountNumber(), acc);
        return acc;
    }

    public Account createCheckingAccount(String ownerName, BigDecimal initialDeposit, BigDecimal overdraftLimit) {
        CheckingAccount acc = new CheckingAccount(ownerName, initialDeposit, overdraftLimit);
        accounts.put(acc.getAccountNumber(), acc);
        return acc;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Collection<Account> listAccounts() {
        return accounts.values();
    }

    public void transfer(String fromAccNum, String toAccNum, BigDecimal amount) {
        if (fromAccNum.equals(toAccNum)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        Account from = accounts.get(fromAccNum);
        Account to = accounts.get(toAccNum);
        if (from == null || to == null) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }

        Account first = fromAccNum.compareTo(toAccNum) < 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first) {
            synchronized (second) {
            	
                from.withdraw(amount);
                
                to.deposit(amount);

                from.addTransaction(new Transaction(Transaction.Type.TRANSFER_OUT, amount,
                        "Transfer to " + to.getAccountNumber(), from.getBalance()));
                to.addTransaction(new Transaction(Transaction.Type.TRANSFER_IN, amount,
                        "Transfer from " + from.getAccountNumber(), to.getBalance()));
            }
        }
    }
}