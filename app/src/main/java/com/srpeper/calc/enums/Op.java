package com.srpeper.calc.enums;

public enum Op {
    SUM('+'),
    SUB('-'),
    MUL('\u00D7'),
    DIV('\u00F7'),
    POT('\u005E');

    private final char symbol;

    Op (char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public boolean equals (char prev) {
        boolean isEqual = false;

        if (prev == SUM.getSymbol() || prev == SUB.getSymbol() || prev == MUL.getSymbol()
                || prev == DIV.getSymbol() || prev == POT.getSymbol()) {
            isEqual = true;
        }

        return isEqual;
    }

    public boolean isHighPriority (char actual) {
        boolean isEqual = false;

        if (actual == MUL.getSymbol() || actual == DIV.getSymbol() || actual == POT.getSymbol()) {
            isEqual = true;
        }

        return isEqual;
    }
}
