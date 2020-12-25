package com.srpeper.calc.enums;

public enum Volume {
    KM("km\u00B3", 0.000000001),
    HM("hm\u00B3", 0.000001),
    DAM("dam\u00B3", 0.001),
    M("m\u00B3", 1.0),
    DM("dm\u00B3", 1000.0),
    CM("cm\u00B3", 1000000.0),
    MM("mm\u00B3", 1000000000.0);

    private final String symbol;
    private final Double value;

    Volume(String symbol, Double value) {
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
