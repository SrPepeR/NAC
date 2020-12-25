package com.srpeper.calc.enums;

public enum Money {
    BITCOIN("bitcoin", 0.0000525),
    EURO("euro", 1.0),
    DOLLAR("dollar", 1.22),
    POUND("pound", 0.9),
    RUBLE("ruble", 91.5),
    YEN("yen", 126.25);

    private final String symbol;
    private final Double value;

    Money(String symbol, Double value) {
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
