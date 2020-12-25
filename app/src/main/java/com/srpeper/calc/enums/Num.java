package com.srpeper.calc.enums;

public enum Num {
    ZERO('0', 0),
    ONE('1', 1),
    TWO('2', 2),
    THREE('3', 3),
    FOUR('4', 4),
    FIVE('5', 5),
    SIX('6', 6),
    SEVEN('7', 7),
    EIGHT('8', 8),
    NINE('9', 9),
    PI('\u03C0', Math.PI);

    private final char symbol;
    private final double value;

    Num (char symbol, double value) {
        this.symbol = symbol;
        this.value = value;
    }

    public char getSymbol () {
        return this.symbol;
    }

    public double getValue () {
        return this.value;
    }

    public boolean equals (char prev) {
        boolean isEqual = false;

        if (prev == ZERO.getSymbol() || prev == ONE.getSymbol() || prev == TWO.getSymbol() || prev == THREE.getSymbol() || prev == FOUR.getSymbol()
                || prev == FIVE.getSymbol() || prev == SIX.getSymbol() || prev == SEVEN.getSymbol() || prev == EIGHT.getSymbol()
                    || prev == NINE.getSymbol() || prev == PI.getSymbol()) {
            isEqual = true;
        }

        return isEqual;
    }
}
