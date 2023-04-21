package ru.bogachenko.exceptions;

public class OwnerNotFoundException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public OwnerNotFoundException(Long id) {
        super(String.format("Owner with Id %d not found", id));
    }
}