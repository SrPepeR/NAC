package com.srpeper.calc.enums;

public enum Length {
    KM("km", 0.001),
    HM("hm", 0.01),
    DAM("dam", 0.1),
    M("m", 1.0),
    DM("dm", 10.0),
    CM("cm", 100.0),
    MM("mm", 1000.1);

    private final String symbol;
    private final Double value;

    Length(String symbol, Double value) {
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
