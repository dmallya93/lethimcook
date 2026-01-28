package com.lethimcook;

import com.lethimcook.converter.UnitConverter;

/**
 * Main facade class for the LetHimCook library.
 * Provides a unified API for unit conversions, natural language parsing,
 * and recipe scaling operations.
 *
 * This facade mirrors the Python __init__.py exports and provides a clean,
 * user-friendly API for the library.
 */
public class LetHimCook {

    /**
     * Convert a value from one unit to another.
     * This is the primary conversion method for the library.
     *
     * @param value the numeric value to convert
     * @param fromUnit the source unit
     * @param toUnit the target unit
     * @return the converted value
     * @throws IllegalArgumentException if units are incompatible or unknown
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        return UnitConverter.convert(value, fromUnit, toUnit);
    }

    /**
     * Returns the version of the LetHimCook library.
     *
     * @return the library version
     */
    public String getVersion() {
        return "0.1.0";
    }

    /**
     * Returns a greeting message to verify the library is functional.
     *
     * @return a greeting message
     */
    public String greet() {
        return "Welcome to LetHimCook - A library for cooking unit conversions!";
    }
}
