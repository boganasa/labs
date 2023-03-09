package ru.bogachenko.banks.exceptions;

public abstract class DebitExceptions extends Exception {
    protected DebitExceptions(String message) {
        super(message);
    }
}

