package ru.bogachenko.banks.exceptions;

public class AttackerException extends ClientExceptions {
    public AttackerException(String num) {
        super("Not attacker " + num);
    }
}
