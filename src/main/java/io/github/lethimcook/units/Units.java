package io.github.lethimcook.units;

import static java.util.Map.entry;

import java.util.Map;

/**
 * Constants class containing unit conversion factors and type mappings.
 * All conversion factors are relative to base units (ml for volume, g for weight).
 * All map keys are lowercase to ensure case-insensitive matching.
 */
public final class Units {

    /**
     * Conversion factors to base units.
     * Volume units convert to milliliters (ml).
     * Weight units convert to grams (g).
     * Count units are dimensionless (factor = 1.0).
     */
    public static final Map<String, Double> CONVERSIONS = Map.ofEntries(
        // Volume (to milliliters)
        entry("tsp", 4.92892),
        entry("teaspoon", 4.92892),
        entry("teaspoons", 4.92892),
        entry("tbsp", 14.7868),
        entry("tablespoon", 14.7868),
        entry("tablespoons", 14.7868),
        entry("floz", 29.5735),
        entry("fl oz", 29.5735),
        entry("fluid ounce", 29.5735),
        entry("fluid ounces", 29.5735),
        entry("cup", 236.588),
        entry("cups", 236.588),
        entry("pint", 473.176),
        entry("pints", 473.176),
        entry("quart", 946.353),
        entry("quarts", 946.353),
        entry("gallon", 3785.41),
        entry("gallons", 3785.41),
        entry("ml", 1.0),
        entry("milliliter", 1.0),
        entry("milliliters", 1.0),
        entry("l", 1000.0),
        entry("liter", 1000.0),
        entry("liters", 1000.0),

        // Weight (to grams)
        entry("oz", 28.3495),
        entry("ounce", 28.3495),
        entry("ounces", 28.3495),
        entry("lb", 453.592),
        entry("lbs", 453.592),
        entry("pound", 453.592),
        entry("pounds", 453.592),
        entry("g", 1.0),
        entry("gram", 1.0),
        entry("grams", 1.0),
        entry("kg", 1000.0),
        entry("kilogram", 1000.0),
        entry("kilograms", 1000.0),

        // Count (dimensionless)
        entry("count", 1.0),
        entry("item", 1.0),
        entry("items", 1.0),
        entry("piece", 1.0),
        entry("pieces", 1.0),
        entry("whole", 1.0)
    );

    /**
     * Mapping of unit strings to their types.
     * Used to validate unit compatibility before conversion.
     */
    public static final Map<String, UnitType> UNIT_TYPES = Map.ofEntries(
        // Volume
        entry("tsp", UnitType.VOLUME),
        entry("teaspoon", UnitType.VOLUME),
        entry("teaspoons", UnitType.VOLUME),
        entry("tbsp", UnitType.VOLUME),
        entry("tablespoon", UnitType.VOLUME),
        entry("tablespoons", UnitType.VOLUME),
        entry("floz", UnitType.VOLUME),
        entry("fl oz", UnitType.VOLUME),
        entry("fluid ounce", UnitType.VOLUME),
        entry("fluid ounces", UnitType.VOLUME),
        entry("cup", UnitType.VOLUME),
        entry("cups", UnitType.VOLUME),
        entry("pint", UnitType.VOLUME),
        entry("pints", UnitType.VOLUME),
        entry("quart", UnitType.VOLUME),
        entry("quarts", UnitType.VOLUME),
        entry("gallon", UnitType.VOLUME),
        entry("gallons", UnitType.VOLUME),
        entry("ml", UnitType.VOLUME),
        entry("milliliter", UnitType.VOLUME),
        entry("milliliters", UnitType.VOLUME),
        entry("l", UnitType.VOLUME),
        entry("liter", UnitType.VOLUME),
        entry("liters", UnitType.VOLUME),

        // Weight
        entry("oz", UnitType.WEIGHT),
        entry("ounce", UnitType.WEIGHT),
        entry("ounces", UnitType.WEIGHT),
        entry("lb", UnitType.WEIGHT),
        entry("lbs", UnitType.WEIGHT),
        entry("pound", UnitType.WEIGHT),
        entry("pounds", UnitType.WEIGHT),
        entry("g", UnitType.WEIGHT),
        entry("gram", UnitType.WEIGHT),
        entry("grams", UnitType.WEIGHT),
        entry("kg", UnitType.WEIGHT),
        entry("kilogram", UnitType.WEIGHT),
        entry("kilograms", UnitType.WEIGHT),

        // Temperature
        entry("fahrenheit", UnitType.TEMPERATURE),
        entry("f", UnitType.TEMPERATURE),
        entry("celsius", UnitType.TEMPERATURE),
        entry("c", UnitType.TEMPERATURE),
        entry("kelvin", UnitType.TEMPERATURE),
        entry("k", UnitType.TEMPERATURE),

        // Count
        entry("count", UnitType.COUNT),
        entry("item", UnitType.COUNT),
        entry("items", UnitType.COUNT),
        entry("piece", UnitType.COUNT),
        entry("pieces", UnitType.COUNT),
        entry("whole", UnitType.COUNT)
    );

    // Private constructor to prevent instantiation
    private Units() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Normalize a unit string by converting to lowercase and trimming whitespace.
     *
     * @param unit the unit string to normalize
     * @return the normalized unit string
     */
    public static String normalizeUnit(String unit) {
        return unit.toLowerCase().strip();
    }

    /**
     * Get the type of a unit.
     *
     * @param unit the unit string (will be normalized)
     * @return the UnitType
     * @throws IllegalArgumentException if the unit is unknown
     */
    public static UnitType getUnitType(String unit) {
        String normalized = normalizeUnit(unit);
        UnitType type = UNIT_TYPES.get(normalized);
        if (type == null) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
        return type;
    }
}
