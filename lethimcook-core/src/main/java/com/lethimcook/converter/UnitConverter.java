package com.lethimcook.converter;

import com.lethimcook.units.ConversionRegistry;
import com.lethimcook.units.UnitType;

/**
 * Core unit conversion functionality.
 */
public class UnitConverter {

    /**
     * Convert a value from one unit to another.
     *
     * @param value    The numeric value to convert
     * @param fromUnit The source unit
     * @param toUnit   The target unit
     * @return The converted value
     * @throws IllegalArgumentException if units are incompatible or unknown
     */
    public double convert(double value, String fromUnit, String toUnit) {
        String normalizedFrom = ConversionRegistry.normalizeUnit(fromUnit);
        String normalizedTo = ConversionRegistry.normalizeUnit(toUnit);

        // Get unit types
        UnitType fromType = ConversionRegistry.getUnitType(normalizedFrom);
        UnitType toType = ConversionRegistry.getUnitType(normalizedTo);

        // Check compatibility
        if (fromType != toType) {
            throw new IllegalArgumentException(
                    "Cannot convert between " + fromType + " and " + toType
            );
        }

        // Handle temperature separately (non-linear conversion)
        if (fromType == UnitType.TEMPERATURE) {
            return TemperatureConverter.convert(value, normalizedFrom, normalizedTo);
        }

        // Handle count (no conversion needed)
        if (fromType == UnitType.COUNT) {
            return value;
        }

        // Convert: from_unit -> base_unit -> to_unit
        double baseValue = value * ConversionRegistry.getConversionFactor(normalizedFrom);
        double result = baseValue / ConversionRegistry.getConversionFactor(normalizedTo);

        return result;
    }
}
