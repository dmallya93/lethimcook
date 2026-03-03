package com.lethimcook;

/**
 * Core unit conversion functionality.
 * <p>
 * Translates the Python {@code converter.py} module, including volume/weight
 * base-unit multiplication, non-linear temperature formulas (using Java 21
 * switch expressions), and count pass-through.
 */
public final class Converter {

    private Converter() {
        // utility class
    }

    /**
     * Convert a value from one unit to another.
     *
     * @param value    the numeric value to convert
     * @param fromUnit the source unit (case-insensitive)
     * @param toUnit   the target unit (case-insensitive)
     * @return the converted value
     * @throws IllegalArgumentException if units are unknown or incompatible
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        String from = Units.normalizeUnit(fromUnit);
        String to = Units.normalizeUnit(toUnit);

        // Get unit types (throws if unknown)
        UnitType fromType = Units.getUnitType(from);
        UnitType toType = Units.getUnitType(to);

        // Check compatibility
        if (fromType != toType) {
            throw new IllegalArgumentException(
                    "Cannot convert between " + fromType + " and " + toType);
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
        double baseValue = value * Units.CONVERSIONS.get(from);
        return baseValue / Units.CONVERSIONS.get(to);
    }

    /**
     * Convert temperature between different scales via Celsius as the intermediate.
     */
    private static double convertTemperature(double value, String fromUnit, String toUnit) {
        // First convert to Celsius
        double celsius = switch (fromUnit) {
            case "celsius", "c" -> value;
            case "fahrenheit", "f" -> (value - 32) * 5.0 / 9.0;
            case "kelvin", "k" -> value - 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        };

        // Then convert from Celsius to target
        return switch (toUnit) {
            case "celsius", "c" -> celsius;
            case "fahrenheit", "f" -> celsius * 9.0 / 5.0 + 32;
            case "kelvin", "k" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        };
    }
}
