package com.srpeper.calc.enums;

public enum Weight {
    TON("ton", 0.00000001),
    KG("kilogram", 0.001),
    GR("gram", 1.0),
    POUND("pound", 0.00220462),
    OZ("ounce", 0.03527396);

    private final String symbol;
    private final Double value;

    Weight(String symbol, Double value) {
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
