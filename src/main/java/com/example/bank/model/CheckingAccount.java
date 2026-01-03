package com.example.bank.model;

import com.example.bank.exception.InvalidAmountException;
import com.example.bank.exception.InsufficientFundsException;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    private final BigDecimal overdraftLimit; // positive number representing allowed negative balance

    public CheckingAccount(String ownerName, BigDecimal initialDeposit, BigDecimal overdraftLimit) {
        super(ownerName, initialDeposit);
        if (overdraftLimit == null) {
            this.overdraftLimit = BigDecimal.ZERO;
        } else {
            this.overdraftLimit = overdraftLimit.abs();
        }
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        BigDecimal potential = getBalance().subtract(amount);
        if (potential.compareTo(overdraftLimit.negate()) < 0) {
            throw new InsufficientFundsException("Withdrawal would exceed overdraft limit.");
        }
        balance = balance.subtract(amount);
        addTransaction(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Withdrawal", balance));
    }
}