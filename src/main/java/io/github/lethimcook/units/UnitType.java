package io.github.lethimcook.units;

/**
 * Unit categories for measurement conversions.
 * Defines the four main types of units supported by the library.
 */
public enum UnitType {
    /**
     * Volume units (e.g., teaspoons, cups, milliliters, liters).
     */
    VOLUME("volume"),

    /**
     * Weight/mass units (e.g., ounces, pounds, grams, kilograms).
     */
    WEIGHT("weight"),

    /**
     * Temperature units (e.g., Fahrenheit, Celsius, Kelvin).
     */
    TEMPERATURE("temperature"),

    /**
     * Count units (dimensionless units like items, pieces).
     */
    COUNT("count");

    private final String value;

    /**
     * Constructs a UnitType with the given string value.
     *
     * @param value the string representation of the unit type
     */
    UnitType(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this unit type.
     *
     * @return the string representation
     */
    public String getValue() {
        return value;
    }
}
