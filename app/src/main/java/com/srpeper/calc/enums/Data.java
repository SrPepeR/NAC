package com.srpeper.calc.enums;

public enum Data {
    PB("petabyte", 0.000000000953674445),
    TB("terabyte", 0.00097656),
    GB("gigabyte", 1.0),
    MB("megabyte", 1024.0),
    KB("kilobyte", 1048576.0),
    B("byte", 1073741824.0);

    private final String symbol;
    private final Double value;

    Data(String symbol, Double value) {
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
