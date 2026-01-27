package io.github.lethimcook.units;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Unit conversion constants and utilities.
 * <p>
 * This class provides immutable maps of conversion factors and unit type mappings
 * for all supported units. Conversion factors represent multipliers to convert
 * to base units (milliliters for volume, grams for weight, count for dimensionless).
 */
public final class Units {

    /**
     * Base units for each unit type.
     */
    public static final Map<UnitType, String> BASE_UNITS = Map.ofEntries(
        entry(UnitType.VOLUME, "ml"),
        entry(UnitType.WEIGHT, "g"),
        entry(UnitType.TEMPERATURE, "celsius"),
        entry(UnitType.COUNT, "count")
    );

    /**
     * Conversion factors to base units.
     * For volume and weight: multiply by this factor to get the base unit value.
     * Keys are normalized to lowercase.
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
     * Unit type mapping.
     * Maps unit strings (lowercase) to their corresponding UnitType.
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

    /**
     * Private constructor to prevent instantiation.
     */
    private Units() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Normalizes a unit string to lowercase and strips whitespace.
     * <p>
     * This method should be used before looking up units in the CONVERSIONS
     * or UNIT_TYPES maps to ensure consistent key matching.
     *
     * @param unit the unit string to normalize
     * @return the normalized unit string (lowercase, trimmed)
     */
    public static String normalizeUnit(String unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        return unit.toLowerCase().strip();
    }

    /**
     * Gets the UnitType for a given unit string.
     * <p>
     * The unit string is normalized (lowercase, trimmed) before lookup.
     *
     * @param unit the unit string to look up
     * @return the UnitType for the unit
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
