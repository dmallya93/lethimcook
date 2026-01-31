package com.lethimcook.units;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for unit conversion factors and unit type mappings.
 */
public class ConversionRegistry {

    // Conversion factors to base units (ml for volume, g for weight)
    private static final Map<String, Double> CONVERSIONS = new HashMap<>();

    // Unit type mappings
    private static final Map<String, UnitType> UNIT_TYPES = new HashMap<>();

    // Base units for each type
    private static final Map<UnitType, String> BASE_UNITS = new HashMap<>();

    static {
        // Initialize base units
        BASE_UNITS.put(UnitType.VOLUME, "ml");
        BASE_UNITS.put(UnitType.WEIGHT, "g");
        BASE_UNITS.put(UnitType.TEMPERATURE, "celsius");
        BASE_UNITS.put(UnitType.COUNT, "count");

        // Volume conversions (to milliliters)
        registerUnit("tsp", 4.92892, UnitType.VOLUME);
        registerUnit("teaspoon", 4.92892, UnitType.VOLUME);
        registerUnit("teaspoons", 4.92892, UnitType.VOLUME);
        registerUnit("tbsp", 14.7868, UnitType.VOLUME);
        registerUnit("tablespoon", 14.7868, UnitType.VOLUME);
        registerUnit("tablespoons", 14.7868, UnitType.VOLUME);
        registerUnit("floz", 29.5735, UnitType.VOLUME);
        registerUnit("fl oz", 29.5735, UnitType.VOLUME);
        registerUnit("fluid ounce", 29.5735, UnitType.VOLUME);
        registerUnit("fluid ounces", 29.5735, UnitType.VOLUME);
        registerUnit("cup", 236.588, UnitType.VOLUME);
        registerUnit("cups", 236.588, UnitType.VOLUME);
        registerUnit("pint", 473.176, UnitType.VOLUME);
        registerUnit("pints", 473.176, UnitType.VOLUME);
        registerUnit("quart", 946.353, UnitType.VOLUME);
        registerUnit("quarts", 946.353, UnitType.VOLUME);
        registerUnit("gallon", 3785.41, UnitType.VOLUME);
        registerUnit("gallons", 3785.41, UnitType.VOLUME);
        registerUnit("ml", 1.0, UnitType.VOLUME);
        registerUnit("milliliter", 1.0, UnitType.VOLUME);
        registerUnit("milliliters", 1.0, UnitType.VOLUME);
        registerUnit("l", 1000.0, UnitType.VOLUME);
        registerUnit("liter", 1000.0, UnitType.VOLUME);
        registerUnit("liters", 1000.0, UnitType.VOLUME);

        // Weight conversions (to grams)
        registerUnit("oz", 28.3495, UnitType.WEIGHT);
        registerUnit("ounce", 28.3495, UnitType.WEIGHT);
        registerUnit("ounces", 28.3495, UnitType.WEIGHT);
        registerUnit("lb", 453.592, UnitType.WEIGHT);
        registerUnit("lbs", 453.592, UnitType.WEIGHT);
        registerUnit("pound", 453.592, UnitType.WEIGHT);
        registerUnit("pounds", 453.592, UnitType.WEIGHT);
        registerUnit("g", 1.0, UnitType.WEIGHT);
        registerUnit("gram", 1.0, UnitType.WEIGHT);
        registerUnit("grams", 1.0, UnitType.WEIGHT);
        registerUnit("kg", 1000.0, UnitType.WEIGHT);
        registerUnit("kilogram", 1000.0, UnitType.WEIGHT);
        registerUnit("kilograms", 1000.0, UnitType.WEIGHT);

        // Temperature units (no conversion factors, handled separately)
        UNIT_TYPES.put("fahrenheit", UnitType.TEMPERATURE);
        UNIT_TYPES.put("f", UnitType.TEMPERATURE);
        UNIT_TYPES.put("celsius", UnitType.TEMPERATURE);
        UNIT_TYPES.put("c", UnitType.TEMPERATURE);
        UNIT_TYPES.put("kelvin", UnitType.TEMPERATURE);
        UNIT_TYPES.put("k", UnitType.TEMPERATURE);

        // Count units (dimensionless)
        registerUnit("count", 1.0, UnitType.COUNT);
        registerUnit("item", 1.0, UnitType.COUNT);
        registerUnit("items", 1.0, UnitType.COUNT);
        registerUnit("piece", 1.0, UnitType.COUNT);
        registerUnit("pieces", 1.0, UnitType.COUNT);
        registerUnit("whole", 1.0, UnitType.COUNT);
    }

    private static void registerUnit(String unit, double conversionFactor, UnitType type) {
        CONVERSIONS.put(unit, conversionFactor);
        UNIT_TYPES.put(unit, type);
    }

    /**
     * Normalize a unit string to lowercase and trim whitespace.
     */
    public static String normalizeUnit(String unit) {
        return unit.toLowerCase().strip();
    }

    /**
     * Get the type of a unit.
     *
     * @throws IllegalArgumentException if the unit is unknown
     */
    public static UnitType getUnitType(String unit) {
        String normalized = normalizeUnit(unit);
        if (!UNIT_TYPES.containsKey(normalized)) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
        return UNIT_TYPES.get(normalized);
    }

    /**
     * Get the conversion factor for a unit to its base unit.
     *
     * @throws IllegalArgumentException if the unit has no conversion factor
     */
    public static double getConversionFactor(String unit) {
        String normalized = normalizeUnit(unit);
        if (!CONVERSIONS.containsKey(normalized)) {
            throw new IllegalArgumentException("No conversion factor for unit: " + unit);
        }
        return CONVERSIONS.get(normalized);
    }

    /**
     * Check if a unit is registered.
     */
    public static boolean isKnownUnit(String unit) {
        String normalized = normalizeUnit(unit);
        return UNIT_TYPES.containsKey(normalized);
    }

    /**
     * Get the base unit for a given unit type.
     */
    public static String getBaseUnit(UnitType type) {
        return BASE_UNITS.get(type);
    }
}
