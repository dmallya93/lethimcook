package io.github.lethimcook.units;

/**
 * Enum representing the different categories of units supported by the library.
 * Each unit belongs to one of these types, which determines compatibility for conversions.
 */
public enum UnitType {
    /** Volume units (e.g., cups, milliliters, liters) */
    VOLUME("volume"),

    /** Weight/mass units (e.g., grams, pounds, ounces) */
    WEIGHT("weight"),

    /** Temperature units (e.g., Celsius, Fahrenheit, Kelvin) */
    TEMPERATURE("temperature"),

    /** Count/dimensionless units (e.g., items, pieces) */
    COUNT("count");

    private final String value;

    UnitType(String value) {
        this.value = value;
    }

    /**
     * Returns the string representation of this unit type.
     *
     * @return the unit type as a string
     */
    public String getValue() {
        return value;
    }
}
