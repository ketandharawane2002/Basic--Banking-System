package com.example.bank.exception;

@SuppressWarnings("serial")
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}