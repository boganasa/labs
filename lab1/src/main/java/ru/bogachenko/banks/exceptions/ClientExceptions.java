package ru.bogachenko.banks.exceptions;

public abstract class ClientExceptions extends Exception {
    protected ClientExceptions(String message) {
        super(message);
    }
}