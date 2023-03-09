package ru.bogachenko.banks.exceptions;

public class CantDoException extends DepositExceptions {
    public CantDoException(String doing) {
        super("You can't " + doing + " money from deposit now");
    }
}
