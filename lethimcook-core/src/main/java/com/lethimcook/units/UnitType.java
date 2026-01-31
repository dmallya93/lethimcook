package com.lethimcook.units;

/**
 * Enumeration of unit types for conversions.
 */
public enum UnitType {
    VOLUME("volume"),
    WEIGHT("weight"),
    TEMPERATURE("temperature"),
    COUNT("count");

    private final String value;

    UnitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
