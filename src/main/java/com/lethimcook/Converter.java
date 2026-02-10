package com.lethimcook;

import com.lethimcook.Units.UnitType;
import com.lethimcook.exceptions.IncompatibleConversionException;

/**
 * Core unit conversion functionality.
 * Migrated from converter.py.
 */
public final class Converter {

    private Converter() {}

    /**
     * Convert a value from one unit to another.
     *
     * @param value    the numeric value to convert
     * @param fromUnit the source unit
     * @param toUnit   the target unit
     * @return the converted value
     * @throws com.lethimcook.exceptions.InvalidUnitException          if a unit is not recognized
     * @throws IncompatibleConversionException if units are of different types
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        String from = Units.normalizeUnit(fromUnit);
        String to = Units.normalizeUnit(toUnit);

        UnitType fromType = Units.getUnitType(from);
        UnitType toType = Units.getUnitType(to);

        if (fromType != toType) {
            throw new IncompatibleConversionException(
                "Cannot convert between " + fromType + " and " + toType
            );
        }

        if (fromType == UnitType.TEMPERATURE) {
            return convertTemperature(value, from, to);
        }

        if (fromType == UnitType.COUNT) {
            return value;
        }

        // Convert: from_unit -> base_unit -> to_unit
        double baseValue = value * Units.CONVERSIONS.get(from);
        return baseValue / Units.CONVERSIONS.get(to);
    }

    /**
     * Convert temperature between different scales using Celsius as intermediate.
     */
    private static double convertTemperature(double value, String fromUnit, String toUnit) {
        // First convert to Celsius
        double celsius = switch (fromUnit) {
            case "celsius", "c" -> value;
            case "fahrenheit", "f" -> (value - 32) * 5 / 9;
            case "kelvin", "k" -> value - 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        };

        // Then convert from Celsius to target
        return switch (toUnit) {
            case "celsius", "c" -> celsius;
            case "fahrenheit", "f" -> celsius * 9 / 5 + 32;
            case "kelvin", "k" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        };
    }
}
