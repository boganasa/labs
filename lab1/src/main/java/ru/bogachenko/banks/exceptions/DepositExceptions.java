package ru.bogachenko.banks.exceptions;

public abstract class DepositExceptions extends Exception {
    protected DepositExceptions(String message) {
        super(message);
    }
}

