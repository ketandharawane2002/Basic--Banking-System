package com.example.bank.model;

import com.example.bank.exception.InvalidAmountException;
import com.example.bank.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingsAccount extends Account {
    private final BigDecimal interestRate; // e.g., 0.02 = 2%

    public SavingsAccount(String ownerName, BigDecimal initialDeposit, BigDecimal interestRate) {
        super(ownerName, initialDeposit);
        if (interestRate == null) {
            this.interestRate = BigDecimal.ZERO;
        } else {
            this.interestRate = interestRate.setScale(4, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in savings account.");
        }
        balance = balance.subtract(amount);
        addTransaction(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Withdrawal", balance));
    }

    public synchronized void applyInterest() {
        BigDecimal interest = balance.multiply(interestRate).setScale(2, RoundingMode.HALF_UP);
        if (interest.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(interest);
            addTransaction(new Transaction(Transaction.Type.DEPOSIT, interest, "Interest applied", balance));
        }
    }
}