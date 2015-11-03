package com.codingame.util;

public enum HorizontalDirection {
    LEFT(-1),
    RIGHT(1);

    private int direction;

    HorizontalDirection(int direction) {
        this.direction = direction;
    }

    public static HorizontalDirection getDirection(double number) {
        return number >= 0 ? RIGHT : LEFT;
    }

    public HorizontalDirection opposite() {
        return getDirection(-1 * direction);
    }
}