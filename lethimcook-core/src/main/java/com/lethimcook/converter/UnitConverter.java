package com.lethimcook.converter;

import com.lethimcook.units.ConversionRegistry;
import com.lethimcook.units.UnitType;

/**
 * Core unit conversion functionality.
 * Provides methods to convert values between different units of measurement.
 *
 * This class mirrors the functionality of Python's converter.py module.
 * It handles volume, weight, temperature, and count conversions.
 *
 * This class is stateless and thread-safe. It can be instantiated and used
 * as an instance, or its static methods can be called directly.
 */
public final class UnitConverter {

    /**
     * Constructs a new UnitConverter instance.
     * The converter is stateless and thread-safe.
     */
    public UnitConverter() {
        // Default constructor for instantiation
    }

    /**
     * Convert a value from one unit to another.
     *
     * @param value the numeric value to convert
     * @param fromUnit the source unit
     * @param toUnit the target unit
     * @return the converted value
     * @throws IllegalArgumentException if units are incompatible or unknown
     */
    public double convert(double value, String fromUnit, String toUnit) {
        // Normalize unit strings
        String normalizedFromUnit = ConversionRegistry.normalizeUnit(fromUnit);
        String normalizedToUnit = ConversionRegistry.normalizeUnit(toUnit);

        // Get unit types
        UnitType fromType = ConversionRegistry.getUnitType(normalizedFromUnit);
        UnitType toType = ConversionRegistry.getUnitType(normalizedToUnit);

        // Check compatibility
        if (fromType != toType) {
            throw new IllegalArgumentException(
                String.format("Cannot convert between %s and %s", fromType, toType)
            );
        }

        // Handle temperature separately (non-linear conversion)
        if (fromType == UnitType.TEMPERATURE) {
            return _convertTemperature(value, normalizedFromUnit, normalizedToUnit);
        }

        // Handle count (no conversion needed)
        if (fromType == UnitType.COUNT) {
            return value;
        }

        // Convert: from_unit -> base_unit -> to_unit
        double fromFactor = ConversionRegistry.getConversionFactor(normalizedFromUnit);
        double toFactor = ConversionRegistry.getConversionFactor(normalizedToUnit);
        double baseValue = value * fromFactor;
        double result = baseValue / toFactor;

        return result;
    }

    /**
     * Convert temperature between different scales.
     * Handles non-linear temperature conversions using Celsius as the intermediate format.
     *
     * @param value the temperature value to convert
     * @param fromUnit the source temperature unit (normalized)
     * @param toUnit the target temperature unit (normalized)
     * @return the converted temperature
     * @throws IllegalArgumentException if temperature unit is unknown
     */
    private static double _convertTemperature(double value, String fromUnit, String toUnit) {
        // First convert to Celsius using enhanced switch expression
        double celsius = switch (fromUnit) {
            case "celsius", "c" -> value;
            case "fahrenheit", "f" -> (value - 32) * 5.0 / 9.0;
            case "kelvin", "k" -> value - 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        };

        // Then convert from Celsius to target using enhanced switch expression
        return switch (toUnit) {
            case "celsius", "c" -> celsius;
            case "fahrenheit", "f" -> celsius * 9.0 / 5.0 + 32;
            case "kelvin", "k" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        };
    }
}
