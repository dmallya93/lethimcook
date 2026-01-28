package com.lethimcook.units;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of all supported units and their conversion factors.
 * Provides thread-safe, immutable access to unit definitions and type mappings.
 *
 * This class mirrors the functionality of Python's units.py module, including
 * CONVERSIONS, UNIT_TYPES, BASE_UNITS dictionaries, and helper functions.
 */
public final class ConversionRegistry {

    private static final Map<String, Unit> UNITS;
    private static final Map<UnitType, String> BASE_UNITS;

    static {
        // Initialize base units map
        Map<UnitType, String> baseUnits = new HashMap<>();
        baseUnits.put(UnitType.VOLUME, "ml");
        baseUnits.put(UnitType.WEIGHT, "g");
        baseUnits.put(UnitType.TEMPERATURE, "celsius");
        baseUnits.put(UnitType.COUNT, "count");
        BASE_UNITS = Map.copyOf(baseUnits);

        // Initialize units registry
        Map<String, Unit> units = new HashMap<>();

        // Volume units (to milliliters)
        addVolumeUnit(units, "tsp", "tsp", 4.92892);
        addVolumeUnit(units, "teaspoon", "teaspoon", 4.92892);
        addVolumeUnit(units, "teaspoons", "teaspoons", 4.92892);
        addVolumeUnit(units, "tbsp", "tbsp", 14.7868);
        addVolumeUnit(units, "tablespoon", "tablespoon", 14.7868);
        addVolumeUnit(units, "tablespoons", "tablespoons", 14.7868);
        addVolumeUnit(units, "floz", "floz", 29.5735);
        addVolumeUnit(units, "fl oz", "fl oz", 29.5735);
        addVolumeUnit(units, "fluid ounce", "fluid ounce", 29.5735);
        addVolumeUnit(units, "fluid ounces", "fluid ounces", 29.5735);
        addVolumeUnit(units, "cup", "cup", 236.588);
        addVolumeUnit(units, "cups", "cups", 236.588);
        addVolumeUnit(units, "pint", "pint", 473.176);
        addVolumeUnit(units, "pints", "pints", 473.176);
        addVolumeUnit(units, "quart", "quart", 946.353);
        addVolumeUnit(units, "quarts", "quarts", 946.353);
        addVolumeUnit(units, "gallon", "gallon", 3785.41);
        addVolumeUnit(units, "gallons", "gallons", 3785.41);
        addVolumeUnit(units, "ml", "ml", 1.0);
        addVolumeUnit(units, "milliliter", "milliliter", 1.0);
        addVolumeUnit(units, "milliliters", "milliliters", 1.0);
        addVolumeUnit(units, "l", "l", 1000.0);
        addVolumeUnit(units, "liter", "liter", 1000.0);
        addVolumeUnit(units, "liters", "liters", 1000.0);

        // Weight units (to grams)
        addWeightUnit(units, "oz", "oz", 28.3495);
        addWeightUnit(units, "ounce", "ounce", 28.3495);
        addWeightUnit(units, "ounces", "ounces", 28.3495);
        addWeightUnit(units, "lb", "lb", 453.592);
        addWeightUnit(units, "lbs", "lbs", 453.592);
        addWeightUnit(units, "pound", "pound", 453.592);
        addWeightUnit(units, "pounds", "pounds", 453.592);
        addWeightUnit(units, "g", "g", 1.0);
        addWeightUnit(units, "gram", "gram", 1.0);
        addWeightUnit(units, "grams", "grams", 1.0);
        addWeightUnit(units, "kg", "kg", 1000.0);
        addWeightUnit(units, "kilogram", "kilogram", 1000.0);
        addWeightUnit(units, "kilograms", "kilograms", 1000.0);

        // Temperature units (conversion factor not used)
        addTemperatureUnit(units, "fahrenheit", "fahrenheit");
        addTemperatureUnit(units, "f", "f");
        addTemperatureUnit(units, "celsius", "celsius");
        addTemperatureUnit(units, "c", "c");
        addTemperatureUnit(units, "kelvin", "kelvin");
        addTemperatureUnit(units, "k", "k");

        // Count units (dimensionless)
        addCountUnit(units, "count", "count", 1.0);
        addCountUnit(units, "item", "item", 1.0);
        addCountUnit(units, "items", "items", 1.0);
        addCountUnit(units, "piece", "piece", 1.0);
        addCountUnit(units, "pieces", "pieces", 1.0);
        addCountUnit(units, "whole", "whole", 1.0);

        UNITS = Map.copyOf(units);
    }

    private static void addVolumeUnit(Map<String, Unit> units, String name, String symbol, double factor) {
        units.put(name, new Unit(name, symbol, UnitType.VOLUME, factor));
    }

    private static void addWeightUnit(Map<String, Unit> units, String name, String symbol, double factor) {
        units.put(name, new Unit(name, symbol, UnitType.WEIGHT, factor));
    }

    private static void addTemperatureUnit(Map<String, Unit> units, String name, String symbol) {
        // Temperature conversions are non-linear, so conversion factor is not used
        units.put(name, new Unit(name, symbol, UnitType.TEMPERATURE, 0.0));
    }

    private static void addCountUnit(Map<String, Unit> units, String name, String symbol, double factor) {
        units.put(name, new Unit(name, symbol, UnitType.COUNT, factor));
    }

    // Private constructor to prevent instantiation
    private ConversionRegistry() {
        throw new UnsupportedOperationException("ConversionRegistry is a utility class and cannot be instantiated");
    }

    /**
     * Normalizes a unit string to lowercase and strips whitespace.
     * Mirrors Python's normalize_unit() function.
     *
     * @param unit the unit string to normalize
     * @return normalized unit string
     */
    public static String normalizeUnit(String unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        return unit.toLowerCase().strip();
    }

    /**
     * Gets the Unit object for a given unit name.
     *
     * @param unit the unit name (case-insensitive)
     * @return the Unit object
     * @throws IllegalArgumentException if the unit is not recognized
     */
    public static Unit getUnit(String unit) {
        String normalized = normalizeUnit(unit);
        Unit result = UNITS.get(normalized);
        if (result == null) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
        return result;
    }

    /**
     * Gets the UnitType for a given unit name.
     * Mirrors Python's get_unit_type() function.
     *
     * @param unit the unit name (case-insensitive)
     * @return the UnitType
     * @throws IllegalArgumentException if the unit is not recognized
     */
    public static UnitType getUnitType(String unit) {
        return getUnit(unit).type();
    }

    /**
     * Gets the base unit name for a given unit type.
     *
     * @param type the unit type
     * @return the base unit name (e.g., "ml" for VOLUME, "g" for WEIGHT)
     */
    public static String getBaseUnit(UnitType type) {
        return BASE_UNITS.get(type);
    }

    /**
     * Gets the conversion factor for a given unit.
     * For VOLUME, WEIGHT, and COUNT: returns the multiplier to convert to base unit.
     * For TEMPERATURE: returns 0.0 as temperature conversions are non-linear.
     *
     * @param unit the unit name (case-insensitive)
     * @return the conversion factor
     * @throws IllegalArgumentException if the unit is not recognized
     */
    public static double getConversionFactor(String unit) {
        return getUnit(unit).conversionFactor();
    }

    /**
     * Checks if a unit is recognized by the registry.
     *
     * @param unit the unit name (case-insensitive)
     * @return true if the unit is recognized, false otherwise
     */
    public static boolean isKnownUnit(String unit) {
        try {
            String normalized = normalizeUnit(unit);
            return UNITS.containsKey(normalized);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
