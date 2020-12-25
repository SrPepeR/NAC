package com.srpeper.calc.enums;

public enum EspP {
    PERCENTAGE('%'),
    FRACTION('/'),
    COMMA('.');

    private final char symbol;

    EspP(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public boolean equals (char prev) {
        boolean isEqual = false;

        if (prev == PERCENTAGE.getSymbol() || prev == FRACTION.getSymbol() || prev == COMMA.getSymbol()) {
            isEqual = true;
        }

        return isEqual;
    }
}
