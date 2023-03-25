package ru.bogachenko.models;
public enum Color {
        ORANGE,
        RED,
        BLACK,
        WHITE,
        BROWN,
        GREY;
        @Override
        public String toString() {
            return switch (this) {
                case ORANGE -> "Orange";
                case RED -> "Red";
                case BLACK -> "Black";
                case WHITE -> "White";
                case BROWN -> "Brown";
                case GREY -> "Grey";
                default -> throw new IllegalArgumentException();
            };
        }
}
