package ru.bogachenko.banks.exceptions;

public class CancelException extends BankExceptions {
    public CancelException() {
        super("cant cansel transaction");
    }
}
