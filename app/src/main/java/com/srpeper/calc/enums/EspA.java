package com.srpeper.calc.enums;

public enum EspA {
    SIN('s', "sin"),
    COS('c', "cos"),
    TAN('t', "tan"),
    LOG('l', "log"),
    LN('n', "n"),
    ROOT('√', "√");

    private final char symbol;
    private final String name;

    EspA(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public char getSymbol () {
        return this.symbol;
    }

    public String getName () {
        return this.name;
    }

    public boolean equals (char prev) {
        boolean isEqual = false;

        if (prev == SIN.getSymbol() || prev == COS.getSymbol() || prev == TAN.getSymbol()
                || prev == LOG.getSymbol() || prev == LN.getSymbol() || prev == ROOT.getSymbol()) {
            isEqual = true;
        }

        return isEqual;
    }
}
