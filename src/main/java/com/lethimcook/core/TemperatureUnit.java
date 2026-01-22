package com.lethimcook.core;

import java.util.Set;

/**
 * Enumeration of all supported temperature units with non-linear conversion formulas.
 * <p>
 * Unlike volume and weight, temperature conversions are non-linear because temperature
 * scales have different zero points. All conversions are performed through Celsius as
 * an intermediate base unit:
 * <ul>
 *   <li>Source unit → Celsius (via toBase)</li>
 *   <li>Celsius → Target unit (via fromBase)</li>
 * </ul>
 * </p>
 * <p>
 * Supported units:
 * <ul>
 *   <li>Fahrenheit (F)</li>
 *   <li>Celsius (C)</li>
 *   <li>Kelvin (K)</li>
 * </ul>
 * </p>
 */
public enum TemperatureUnit implements Unit {
    FAHRENHEIT("f", Set.of("fahrenheit", "f")),
    CELSIUS("c", Set.of("celsius", "c")),
    KELVIN("k", Set.of("kelvin", "k"));

    private final String primaryName;
    private final Set<String> aliases;

    /**
     * Constructor for temperature unit enum constants.
     *
     * @param primaryName the primary display name for this unit
     * @param aliases all recognized string representations (lowercase)
     */
    TemperatureUnit(String primaryName, Set<String> aliases) {
        this.primaryName = primaryName;
        this.aliases = aliases;
    }

    @Override
    public UnitType getType() {
        return UnitType.TEMPERATURE;
    }

    /**
     * Converts a temperature value from this unit to Celsius.
     * <p>
     * Conversion formulas (matching Python's converter.py lines 57-65):
     * <ul>
     *   <li>Fahrenheit to Celsius: (value - 32) * 5 / 9</li>
     *   <li>Celsius to Celsius: value (identity)</li>
     *   <li>Kelvin to Celsius: value - 273.15</li>
     * </ul>
     * </p>
     *
     * @param value the temperature value in this unit
     * @return the equivalent temperature in Celsius
     */
    @Override
    public double toBase(double value) {
        return switch (this) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5 / 9;
            case KELVIN -> value - 273.15;
        };
    }

    /**
     * Converts a temperature value from Celsius to this unit.
     * <p>
     * Conversion formulas (matching Python's converter.py lines 67-76):
     * <ul>
     *   <li>Celsius to Fahrenheit: value * 9 / 5 + 32</li>
     *   <li>Celsius to Celsius: value (identity)</li>
     *   <li>Celsius to Kelvin: value + 273.15</li>
     * </ul>
     * </p>
     *
     * @param baseValue the temperature value in Celsius
     * @return the equivalent temperature in this unit
     */
    @Override
    public double fromBase(double baseValue) {
        return switch (this) {
            case CELSIUS -> baseValue;
            case FAHRENHEIT -> baseValue * 9 / 5 + 32;
            case KELVIN -> baseValue + 273.15;
        };
    }

    @Override
    public String primaryName() {
        return primaryName;
    }

    @Override
    public Set<String> aliases() {
        return aliases;
    }

    /**
     * Parses a string representation of a temperature unit and returns the corresponding enum constant.
     * <p>
     * The parsing is case-insensitive and trims leading/trailing whitespace.
     * </p>
     *
     * @param raw the string representation of the unit (e.g., "fahrenheit", "F", "celsius")
     * @return the matching TemperatureUnit enum constant
     * @throws IllegalArgumentException if the string does not match any known temperature unit
     */
    public static TemperatureUnit fromString(String raw) {
        String normalized = raw.toLowerCase().trim();
        for (TemperatureUnit unit : values()) {
            if (unit.aliases.contains(normalized)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown temperature unit: " + raw);
    }
}
