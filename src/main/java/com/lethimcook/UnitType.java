package com.lethimcook;

/**
 * Categories of measurement units supported by the conversion library.
 */
public enum UnitType {
    VOLUME("volume"),
    WEIGHT("weight"),
    TEMPERATURE("temperature"),
    COUNT("count");

    private final String label;

    UnitType(String label) {
        this.label = label;
    }

    /**
     * Returns the lowercase label for this unit type, matching the Python StrEnum value.
     */
    public String label() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
