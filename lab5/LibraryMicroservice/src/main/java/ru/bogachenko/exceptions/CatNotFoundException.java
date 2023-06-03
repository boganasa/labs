package ru.bogachenko.exceptions;

public class CatNotFoundException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public CatNotFoundException(Long id) {
        super(String.format("Cat with Id %d not found", id));
    }
}