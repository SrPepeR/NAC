package com.srpeper.calc.enums;

public enum Speed {
    KM("km/h", 3.6),
    MPH("mph", 2.23693629),
    M("m/s", 1.0),
    KN("kn", 1.94384449),
    FT("ft/s", 3.2808399);

    private final String symbol;
    private final Double value;

    Speed(String symbol, Double value) {
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
