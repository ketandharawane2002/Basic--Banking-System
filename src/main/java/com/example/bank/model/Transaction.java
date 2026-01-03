package com.example.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT }

    private final Type type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime timestamp;
    private final BigDecimal balanceAfter;

    public Transaction(Type type, BigDecimal amount, String description, BigDecimal balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.balanceAfter = balanceAfter;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        String time = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("[%s] %s: %s — %s (balance: %s)", time, type, amount, description, balanceAfter);
    }
}