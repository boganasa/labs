package ru.bogachenko.banks.exceptions;

public abstract class BankExceptions extends Exception {
    protected BankExceptions(String message) {
        super(message);
    }
}

