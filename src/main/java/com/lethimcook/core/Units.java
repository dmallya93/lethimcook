package com.lethimcook.core;

/**
 * Utility class for parsing unit strings across all unit types.
 * <p>
 * This class provides centralized unit string parsing functionality that attempts
 * to resolve a unit string (e.g., "cups", "grams", "fahrenheit") to the appropriate
 * Unit enum constant across all supported unit types (Volume, Weight, Temperature, Count).
 * </p>
 * <p>
 * The parsing strategy tries each unit type in sequence:
 * <ol>
 *   <li>VolumeUnit (most common)</li>
 *   <li>WeightUnit (most common)</li>
 *   <li>TemperatureUnit</li>
 *   <li>CountUnit</li>
 * </ol>
 * This provides O(n) lookup where n is the number of unit types (currently 4).
 * </p>
 * <p>
 * All parsing is case-insensitive and trims leading/trailing whitespace, matching
 * the Python normalize_unit() behavior.
 * </p>
 */
public final class Units {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Units() {
        // Utility class - no instantiation
    }

    /**
     * Normalizes a unit string to lowercase and strips whitespace.
     * <p>
     * This matches the Python normalize_unit() function from units.py (lines 135-137).
     * </p>
     *
     * @param unit the raw unit string
     * @return the normalized unit string (lowercase, trimmed)
     */
    public static String normalize(String unit) {
        return unit.toLowerCase().strip();
    }

    /**
     * Parses a unit string and returns the corresponding Unit enum constant.
     * <p>
     * This method tries to parse the string as each unit type in sequence:
     * VolumeUnit, WeightUnit, TemperatureUnit, and CountUnit. The first successful
     * match is returned.
     * </p>
     * <p>
     * The parsing is case-insensitive and trims leading/trailing whitespace before
     * attempting to match against known unit aliases.
     * </p>
     * <p>
     * This replicates the Python get_unit_type() function behavior (units.py lines 140-145)
     * with the addition of returning the actual Unit enum rather than just its type.
     * </p>
     *
     * @param unitStr the string representation of a unit (e.g., "cups", "grams", "fahrenheit", "items")
     * @return the matching Unit enum constant
     * @throws IllegalArgumentException if the string does not match any known unit across all types
     */
    public static Unit parse(String unitStr) {
        // Try each unit type in order (volume and weight are most common, so try them first)
        try {
            return VolumeUnit.fromString(unitStr);
        } catch (IllegalArgumentException e) {
            // Not a volume unit, try next type
        }

        try {
            return WeightUnit.fromString(unitStr);
        } catch (IllegalArgumentException e) {
            // Not a weight unit, try next type
        }

        try {
            return TemperatureUnit.fromString(unitStr);
        } catch (IllegalArgumentException e) {
            // Not a temperature unit, try next type
        }

        try {
            return CountUnit.fromString(unitStr);
        } catch (IllegalArgumentException e) {
            // Not a count unit either
        }

        // No unit type recognized this string
        throw new IllegalArgumentException("Unknown unit: " + unitStr);
    }
}
