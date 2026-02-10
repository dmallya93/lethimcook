package com.lethimcook;

import com.lethimcook.exceptions.InvalidUnitException;

import java.util.Map;

/**
 * Unit conversion definitions and constants.
 * Migrated from units.py.
 */
public final class Units {

    private Units() {}

    /**
     * Classification of measurement units.
     * Overrides toString() to return lowercase names matching Python's StrEnum behavior.
     */
    public enum UnitType {
        VOLUME,
        WEIGHT,
        TEMPERATURE,
        COUNT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /** Base units for each type. */
    public static final Map<UnitType, String> BASE_UNITS = Map.of(
        UnitType.VOLUME, "ml",
        UnitType.WEIGHT, "g",
        UnitType.TEMPERATURE, "celsius",
        UnitType.COUNT, "count"
    );

    /**
     * Conversion factors to base units.
     * For volume: multiply by factor to get milliliters.
     * For weight: multiply by factor to get grams.
     * For count: factor is 1.0 (dimensionless).
     */
    public static final Map<String, Double> CONVERSIONS = Map.ofEntries(
        // Volume (to milliliters)
        Map.entry("tsp", 4.92892),
        Map.entry("teaspoon", 4.92892),
        Map.entry("teaspoons", 4.92892),
        Map.entry("tbsp", 14.7868),
        Map.entry("tablespoon", 14.7868),
        Map.entry("tablespoons", 14.7868),
        Map.entry("floz", 29.5735),
        Map.entry("fl oz", 29.5735),
        Map.entry("fluid ounce", 29.5735),
        Map.entry("fluid ounces", 29.5735),
        Map.entry("cup", 236.588),
        Map.entry("cups", 236.588),
        Map.entry("pint", 473.176),
        Map.entry("pints", 473.176),
        Map.entry("quart", 946.353),
        Map.entry("quarts", 946.353),
        Map.entry("gallon", 3785.41),
        Map.entry("gallons", 3785.41),
        Map.entry("ml", 1.0),
        Map.entry("milliliter", 1.0),
        Map.entry("milliliters", 1.0),
        Map.entry("l", 1000.0),
        Map.entry("liter", 1000.0),
        Map.entry("liters", 1000.0),

        // Weight (to grams)
        Map.entry("oz", 28.3495),
        Map.entry("ounce", 28.3495),
        Map.entry("ounces", 28.3495),
        Map.entry("lb", 453.592),
        Map.entry("lbs", 453.592),
        Map.entry("pound", 453.592),
        Map.entry("pounds", 453.592),
        Map.entry("g", 1.0),
        Map.entry("gram", 1.0),
        Map.entry("grams", 1.0),
        Map.entry("kg", 1000.0),
        Map.entry("kilogram", 1000.0),
        Map.entry("kilograms", 1000.0),

        // Count (dimensionless)
        Map.entry("count", 1.0),
        Map.entry("item", 1.0),
        Map.entry("items", 1.0),
        Map.entry("piece", 1.0),
        Map.entry("pieces", 1.0),
        Map.entry("whole", 1.0)
    );

    /** Unit type mapping. */
    public static final Map<String, UnitType> UNIT_TYPES = Map.ofEntries(
        // Volume
        Map.entry("tsp", UnitType.VOLUME),
        Map.entry("teaspoon", UnitType.VOLUME),
        Map.entry("teaspoons", UnitType.VOLUME),
        Map.entry("tbsp", UnitType.VOLUME),
        Map.entry("tablespoon", UnitType.VOLUME),
        Map.entry("tablespoons", UnitType.VOLUME),
        Map.entry("floz", UnitType.VOLUME),
        Map.entry("fl oz", UnitType.VOLUME),
        Map.entry("fluid ounce", UnitType.VOLUME),
        Map.entry("fluid ounces", UnitType.VOLUME),
        Map.entry("cup", UnitType.VOLUME),
        Map.entry("cups", UnitType.VOLUME),
        Map.entry("pint", UnitType.VOLUME),
        Map.entry("pints", UnitType.VOLUME),
        Map.entry("quart", UnitType.VOLUME),
        Map.entry("quarts", UnitType.VOLUME),
        Map.entry("gallon", UnitType.VOLUME),
        Map.entry("gallons", UnitType.VOLUME),
        Map.entry("ml", UnitType.VOLUME),
        Map.entry("milliliter", UnitType.VOLUME),
        Map.entry("milliliters", UnitType.VOLUME),
        Map.entry("l", UnitType.VOLUME),
        Map.entry("liter", UnitType.VOLUME),
        Map.entry("liters", UnitType.VOLUME),

        // Weight
        Map.entry("oz", UnitType.WEIGHT),
        Map.entry("ounce", UnitType.WEIGHT),
        Map.entry("ounces", UnitType.WEIGHT),
        Map.entry("lb", UnitType.WEIGHT),
        Map.entry("lbs", UnitType.WEIGHT),
        Map.entry("pound", UnitType.WEIGHT),
        Map.entry("pounds", UnitType.WEIGHT),
        Map.entry("g", UnitType.WEIGHT),
        Map.entry("gram", UnitType.WEIGHT),
        Map.entry("grams", UnitType.WEIGHT),
        Map.entry("kg", UnitType.WEIGHT),
        Map.entry("kilogram", UnitType.WEIGHT),
        Map.entry("kilograms", UnitType.WEIGHT),

        // Temperature
        Map.entry("fahrenheit", UnitType.TEMPERATURE),
        Map.entry("f", UnitType.TEMPERATURE),
        Map.entry("celsius", UnitType.TEMPERATURE),
        Map.entry("c", UnitType.TEMPERATURE),
        Map.entry("kelvin", UnitType.TEMPERATURE),
        Map.entry("k", UnitType.TEMPERATURE),

        // Count
        Map.entry("count", UnitType.COUNT),
        Map.entry("item", UnitType.COUNT),
        Map.entry("items", UnitType.COUNT),
        Map.entry("piece", UnitType.COUNT),
        Map.entry("pieces", UnitType.COUNT),
        Map.entry("whole", UnitType.COUNT)
    );

    /**
     * Normalize unit string to lowercase and strip whitespace.
     */
    public static String normalizeUnit(String unit) {
        return unit.toLowerCase().strip();
    }

    /**
     * Get the type of a unit.
     *
     * @throws InvalidUnitException if the unit is not recognized
     */
    public static UnitType getUnitType(String unit) {
        String normalized = normalizeUnit(unit);
        UnitType type = UNIT_TYPES.get(normalized);
        if (type == null) {
            throw new InvalidUnitException("Unknown unit: " + unit);
        }
        return type;
    }
}
