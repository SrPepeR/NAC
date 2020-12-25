package com.srpeper.calc.enums;

public enum Type {
    LENGTH("length"),
    AREA("area"),
    VOLUME("volume"),
    SPEED("speed"),
    TIME("time"),
    WEIGHT("weight"),
    TEMP("temp"),
    MONEY("money"),
    DATA("data");

    private final String symbol;

    Type (String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol () {
        return this.symbol;
    }
}
