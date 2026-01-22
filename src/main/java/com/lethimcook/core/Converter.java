package com.lethimcook.core;

/**
 * Core unit conversion functionality.
 * <p>
 * This class provides the primary public API for unit conversions. It validates unit
 * compatibility, resolves unit strings via the Units utility, and performs conversions
 * by delegating to the Unit interface methods (toBase and fromBase).
 * </p>
 * <p>
 * The conversion algorithm:
 * <ol>
 *   <li>Parse both unit strings using Units.parse() to get Unit objects</li>
 *   <li>Check unit type compatibility (volume-to-volume, weight-to-weight, etc.)</li>
 *   <li>Convert source value to base unit using from.toBase(value)</li>
 *   <li>Convert base value to target unit using to.fromBase(baseValue)</li>
 * </ol>
 * </p>
 * <p>
 * This replicates the Python convert() function from converter.py lines 11-51.
 * </p>
 */
public final class Converter {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Converter() {
        // Utility class - no instantiation
    }

    /**
     * Converts a value from one unit to another.
     * <p>
     * This method handles all unit conversions including:
     * <ul>
     *   <li>Linear conversions (volume, weight) via base unit conversion</li>
     *   <li>Non-linear conversions (temperature) via formulas in TemperatureUnit</li>
     *   <li>Count conversions (dimensionless quantities)</li>
     * </ul>
     * </p>
     * <p>
     * Unit strings are case-insensitive and whitespace is trimmed. Both full names
     * (e.g., "tablespoon") and abbreviations (e.g., "tbsp") are supported.
     * </p>
     *
     * @param value the numeric value to convert
     * @param fromUnitStr the source unit as a string (e.g., "cups", "grams", "fahrenheit")
     * @param toUnitStr the target unit as a string (e.g., "ml", "oz", "celsius")
     * @return the converted value
     * @throws IllegalArgumentException if units are incompatible (e.g., volume to weight)
     * @throws IllegalArgumentException if either unit string is unknown
     */
    public static double convert(double value, String fromUnitStr, String toUnitStr) {
        // Parse unit strings to Unit objects (throws IllegalArgumentException if unknown)
        Unit from = Units.parse(fromUnitStr);
        Unit to = Units.parse(toUnitStr);

        // Check type compatibility (matching Python's converter.py lines 34-37)
        if (from.getType() != to.getType()) {
            throw new IllegalArgumentException(
                "Cannot convert between " + from.getType() + " and " + to.getType()
            );
        }

        // Perform conversion: source -> base unit -> target unit
        // (matching Python's converter.py lines 47-50)
        // Note: Temperature and Count units handle their special cases in their
        // toBase/fromBase implementations
        double baseValue = from.toBase(value);
        return to.fromBase(baseValue);
    }
}
