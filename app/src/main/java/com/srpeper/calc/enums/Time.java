package com.srpeper.calc.enums;

public enum Time {
    D("day", 0.000016534),
    H("hour", 0.0002777),
    M("minute", 0.01666666666),
    S("second", 1.0),
    MS("millisecond", 1000.0);

    private final String symbol;
    private final Double value;

    Time(String symbol, Double value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol () {
        return this.symbol;
    }

    public Double getValue () {
        return this.value;
    }
}
