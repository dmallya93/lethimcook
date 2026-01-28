package com.lethimcook.units;

/**
 * Enumeration of unit categories for cooking measurements.
 * Mirrors Python's UnitType StrEnum from units.py.
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

    /**
     * Returns the lowercase string representation of the unit type,
     * matching Python's StrEnum behavior.
     *
     * @return lowercase string representation (e.g., "volume", "weight")
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Converts a string to a UnitType enum value.
     * Matches case-insensitively.
     *
     * @param text the string to convert
     * @return the matching UnitType
     * @throws IllegalArgumentException if the string doesn't match any UnitType
     */
    public static UnitType fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Unit type string cannot be null");
        }

        String normalized = text.toLowerCase().strip();
        for (UnitType type : UnitType.values()) {
            if (type.value.equals(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown unit type: " + text);
    }
}
