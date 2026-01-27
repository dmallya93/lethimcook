package io.github.lethimcook.converter;

import io.github.lethimcook.units.UnitType;
import io.github.lethimcook.units.Units;

/**
 * Core unit conversion functionality.
 * Provides methods to convert values between compatible units.
 */
public final class Converter {

    // Private constructor to prevent instantiation
    private Converter() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Convert a value from one unit to another.
     *
     * @param value    the numeric value to convert
     * @param fromUnit the source unit
     * @param toUnit   the target unit
     * @return the converted value
     * @throws IllegalArgumentException if units are incompatible or unknown
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        // Normalize units (trim and lowercase)
        String from = Units.normalizeUnit(fromUnit);
        String to = Units.normalizeUnit(toUnit);

        // Get unit types
        UnitType fromType = Units.getUnitType(from);
        UnitType toType = Units.getUnitType(to);

        // Check compatibility
        if (fromType != toType) {
            throw new IllegalArgumentException(
                "Cannot convert between " + fromType.getValue() + " and " + toType.getValue()
            );
        }

        // Handle temperature separately (non-linear conversion)
        if (fromType == UnitType.TEMPERATURE) {
            return convertTemperature(value, from, to);
        }

        // Handle count (no conversion needed)
        if (fromType == UnitType.COUNT) {
            return value;
        }

        // Convert: from_unit -> base_unit -> to_unit
        Double fromFactor = Units.CONVERSIONS.get(from);
        Double toFactor = Units.CONVERSIONS.get(to);

        if (fromFactor == null || toFactor == null) {
            throw new IllegalArgumentException("Unknown unit(s): " + fromUnit + " or " + toUnit);
        }

        if (fromFactor == 0.0 || toFactor == 0.0) {
            throw new IllegalArgumentException("Invalid conversion factor for units: " + fromUnit + ", " + toUnit);
        }

        double baseValue = value * fromFactor;
        return baseValue / toFactor;
    }

    /**
     * Convert temperature between different scales.
     * Uses Celsius as an intermediate canonical form.
     *
     * @param value    the temperature value to convert
     * @param fromUnit the source temperature unit (normalized)
     * @param toUnit   the target temperature unit (normalized)
     * @return the converted temperature
     * @throws IllegalArgumentException if the temperature unit is unknown
     */
    private static double convertTemperature(double value, String fromUnit, String toUnit) {
        // First convert to Celsius (canonical form)
        double celsius = switch (fromUnit) {
            case "celsius", "c" -> value;
            case "fahrenheit", "f" -> (value - 32.0) * 5.0 / 9.0;
            case "kelvin", "k" -> value - 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        };

        // Then convert from Celsius to target
        return switch (toUnit) {
            case "celsius", "c" -> celsius;
            case "fahrenheit", "f" -> celsius * 9.0 / 5.0 + 32.0;
            case "kelvin", "k" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        };
    }
}
