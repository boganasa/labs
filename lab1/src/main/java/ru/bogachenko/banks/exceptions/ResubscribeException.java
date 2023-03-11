package ru.bogachenko.banks.exceptions;

public class ResubscribeException extends BankExceptions {
    public ResubscribeException() {
        super("Client already subscribe");
    }
}
