package com.srpeper.calc.enums;

public enum Area {
    KM("km\u00B2", 0.000001),
    HM("hm\u00B2", 0.0001),
    DAM("dam\u00B2", 0.01),
    M("m\u00B2", 1.0),
    DM("dm\u00B2", 100.0),
    CM("cm\u00B2", 10000.0),
    MM("mm\u00B2", 1000000.0);

    private final String symbol;
    private final Double value;

    Area(String symbol, Double value) {
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
