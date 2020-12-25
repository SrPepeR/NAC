package com.srpeper.calc.enums;

public enum Temperature {
    CELSIUS("celsius", 1.0),
    KELVIN("kelvin", 274.15),
    FAHRENHEIT("fahrenheit", 33.8);

    private final String symbol;
    private final Double value;

    Temperature(String symbol, Double value) {
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
