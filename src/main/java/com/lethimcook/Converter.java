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
     * Convert a numeric value from one measurement unit to another.
     * <p>
     * Both unit strings are normalized to lowercase before lookup. The method
     * resolves each unit to its {@link UnitType} via {@link Units#getUnitType(String)}
     * and verifies they belong to the same category (e.g. both VOLUME, both WEIGHT).
     * <p>
     * Conversion strategies by category:
     * <ul>
     *   <li><strong>Volume / Weight</strong> — uses a base-unit multiplication approach.
     *       The value is first converted to the base unit (milliliters for volume, grams
     *       for weight) by multiplying with the source unit's factor, then divided by the
     *       target unit's factor.</li>
     *   <li><strong>Temperature</strong> — delegates to {@link #convertTemperature(double, String, String)},
     *       which routes through Celsius as an intermediate to handle the non-linear
     *       Fahrenheit/Celsius/Kelvin formulas.</li>
     *   <li><strong>Count</strong> — returns the value unchanged, since count units are
     *       dimensionless and interchangeable.</li>
     * </ul>
     *
     * @param value    the numeric value to convert (e.g. 2.0)
     * @param fromUnit the source unit as a human-readable string (case-insensitive,
     *                 whitespace-tolerant); must be a recognized alias such as "cups",
     *                 "lb", "fahrenheit", or "ml"
     * @param toUnit   the target unit (same constraints as {@code fromUnit}); must be
     *                 in the same unit category as {@code fromUnit}
     * @return the converted numeric value in the target unit
     * @throws IllegalArgumentException if either unit string is not recognized, or if
     *         the two units belong to different categories (e.g. converting cups to grams)
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        final String from = Units.normalizeUnit(fromUnit);
        final String to = Units.normalizeUnit(toUnit);

        // Get unit types (throws if unknown)
        final UnitType fromType = Units.getUnitType(from);
        final UnitType toType = Units.getUnitType(to);

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
        final double baseValue = value * Units.CONVERSIONS.get(from);
        return baseValue / Units.CONVERSIONS.get(to);
    }

    /**
     * Convert a temperature value between Fahrenheit, Celsius, and Kelvin scales.
     * <p>
     * The conversion uses Celsius as the pivot: the input value is first translated
     * to Celsius, then from Celsius to the target scale. This avoids a combinatorial
     * explosion of direct formulas between every pair of scales.
     * <p>
     * Supported conversions:
     * <ul>
     *   <li>Fahrenheit to Celsius: {@code (value - 32) * 5 / 9}</li>
     *   <li>Kelvin to Celsius: {@code value - 273.15}</li>
     *   <li>Celsius to Fahrenheit: {@code celsius * 9 / 5 + 32}</li>
     *   <li>Celsius to Kelvin: {@code celsius + 273.15}</li>
     * </ul>
     *
     * @param value    the temperature value to convert
     * @param fromUnit the source temperature scale, already normalized to lowercase
     *                 (one of "celsius", "c", "fahrenheit", "f", "kelvin", "k")
     * @param toUnit   the target temperature scale, already normalized to lowercase
     * @return the converted temperature value in the target scale
     * @throws IllegalArgumentException if either unit string does not match a known
     *         temperature scale (should not occur when called from {@link #convert})
     */
    private static double convertTemperature(double value, String fromUnit, String toUnit) {
        // First convert to Celsius
        final double celsius = switch (fromUnit) {
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
