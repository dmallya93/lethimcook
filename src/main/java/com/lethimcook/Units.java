package com.lethimcook;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Unit definitions, conversion factors, and type mappings.
 * <p>
 * This is a direct translation of the Python {@code units.py} module, preserving
 * every alias entry (including multi-word entries such as "fl oz" and "fluid ounce").
 */
public final class Units {

    private Units() {
        // utility class
    }

    // ── Base units for each type ──────────────────────────────────────────────

    public static final Map<UnitType, String> BASE_UNITS;

    static {
        var base = new LinkedHashMap<UnitType, String>();
        base.put(UnitType.VOLUME, "ml");
        base.put(UnitType.WEIGHT, "g");
        base.put(UnitType.TEMPERATURE, "celsius");
        base.put(UnitType.COUNT, "count");
        BASE_UNITS = Collections.unmodifiableMap(base);
    }

    // ── Conversion factors to base units ──────────────────────────────────────
    // For volume: multiply by this factor to get milliliters.
    // For weight: multiply by this factor to get grams.
    // For count: factor is always 1.0.

    public static final Map<String, Double> CONVERSIONS;

    static {
        var c = new LinkedHashMap<String, Double>();

        // Volume (to milliliters)
        c.put("tsp", 4.92892);
        c.put("teaspoon", 4.92892);
        c.put("teaspoons", 4.92892);
        c.put("tbsp", 14.7868);
        c.put("tablespoon", 14.7868);
        c.put("tablespoons", 14.7868);
        c.put("floz", 29.5735);
        c.put("fl oz", 29.5735);
        c.put("fluid ounce", 29.5735);
        c.put("fluid ounces", 29.5735);
        c.put("cup", 236.588);
        c.put("cups", 236.588);
        c.put("pint", 473.176);
        c.put("pints", 473.176);
        c.put("quart", 946.353);
        c.put("quarts", 946.353);
        c.put("gallon", 3785.41);
        c.put("gallons", 3785.41);
        c.put("ml", 1.0);
        c.put("milliliter", 1.0);
        c.put("milliliters", 1.0);
        c.put("l", 1000.0);
        c.put("liter", 1000.0);
        c.put("liters", 1000.0);

        // Weight (to grams)
        c.put("oz", 28.3495);
        c.put("ounce", 28.3495);
        c.put("ounces", 28.3495);
        c.put("lb", 453.592);
        c.put("lbs", 453.592);
        c.put("pound", 453.592);
        c.put("pounds", 453.592);
        c.put("g", 1.0);
        c.put("gram", 1.0);
        c.put("grams", 1.0);
        c.put("kg", 1000.0);
        c.put("kilogram", 1000.0);
        c.put("kilograms", 1000.0);

        // Count (dimensionless)
        c.put("count", 1.0);
        c.put("item", 1.0);
        c.put("items", 1.0);
        c.put("piece", 1.0);
        c.put("pieces", 1.0);
        c.put("whole", 1.0);

        CONVERSIONS = Collections.unmodifiableMap(c);
    }

    // ── Unit type mapping ─────────────────────────────────────────────────────

    public static final Map<String, UnitType> UNIT_TYPES;

    static {
        var t = new LinkedHashMap<String, UnitType>();

        // Volume
        t.put("tsp", UnitType.VOLUME);
        t.put("teaspoon", UnitType.VOLUME);
        t.put("teaspoons", UnitType.VOLUME);
        t.put("tbsp", UnitType.VOLUME);
        t.put("tablespoon", UnitType.VOLUME);
        t.put("tablespoons", UnitType.VOLUME);
        t.put("floz", UnitType.VOLUME);
        t.put("fl oz", UnitType.VOLUME);
        t.put("fluid ounce", UnitType.VOLUME);
        t.put("fluid ounces", UnitType.VOLUME);
        t.put("cup", UnitType.VOLUME);
        t.put("cups", UnitType.VOLUME);
        t.put("pint", UnitType.VOLUME);
        t.put("pints", UnitType.VOLUME);
        t.put("quart", UnitType.VOLUME);
        t.put("quarts", UnitType.VOLUME);
        t.put("gallon", UnitType.VOLUME);
        t.put("gallons", UnitType.VOLUME);
        t.put("ml", UnitType.VOLUME);
        t.put("milliliter", UnitType.VOLUME);
        t.put("milliliters", UnitType.VOLUME);
        t.put("l", UnitType.VOLUME);
        t.put("liter", UnitType.VOLUME);
        t.put("liters", UnitType.VOLUME);

        // Weight
        t.put("oz", UnitType.WEIGHT);
        t.put("ounce", UnitType.WEIGHT);
        t.put("ounces", UnitType.WEIGHT);
        t.put("lb", UnitType.WEIGHT);
        t.put("lbs", UnitType.WEIGHT);
        t.put("pound", UnitType.WEIGHT);
        t.put("pounds", UnitType.WEIGHT);
        t.put("g", UnitType.WEIGHT);
        t.put("gram", UnitType.WEIGHT);
        t.put("grams", UnitType.WEIGHT);
        t.put("kg", UnitType.WEIGHT);
        t.put("kilogram", UnitType.WEIGHT);
        t.put("kilograms", UnitType.WEIGHT);

        // Temperature
        t.put("fahrenheit", UnitType.TEMPERATURE);
        t.put("f", UnitType.TEMPERATURE);
        t.put("celsius", UnitType.TEMPERATURE);
        t.put("c", UnitType.TEMPERATURE);
        t.put("kelvin", UnitType.TEMPERATURE);
        t.put("k", UnitType.TEMPERATURE);

        // Count
        t.put("count", UnitType.COUNT);
        t.put("item", UnitType.COUNT);
        t.put("items", UnitType.COUNT);
        t.put("piece", UnitType.COUNT);
        t.put("pieces", UnitType.COUNT);
        t.put("whole", UnitType.COUNT);

        UNIT_TYPES = Collections.unmodifiableMap(t);
    }

    // ── Helper methods ────────────────────────────────────────────────────────

    /**
     * Normalize a unit string to a canonical lowercase, whitespace-stripped form.
     * <p>
     * This is the first step in every unit lookup: the raw user-supplied string
     * (e.g. "Cups", " TBSP ", "Fluid Ounce") is lowercased and leading/trailing
     * whitespace is removed so that it can be matched against the keys in
     * {@link #CONVERSIONS} and {@link #UNIT_TYPES}. Interior whitespace is preserved
     * to support multi-word units like "fl oz" and "fluid ounce".
     *
     * @param unit the raw unit string provided by the caller; must not be {@code null}
     * @return the normalized unit string suitable for map lookups
     */
    public static String normalizeUnit(String unit) {
        return unit.toLowerCase().strip();
    }

    /**
     * Resolve a unit string to its corresponding {@link UnitType} category.
     * <p>
     * The input is first passed through {@link #normalizeUnit(String)} to ensure
     * case-insensitive, whitespace-tolerant matching, and then looked up in the
     * {@link #UNIT_TYPES} map. If no entry is found, an {@link IllegalArgumentException}
     * is thrown with a message identifying the unrecognized unit.
     * <p>
     * Example usage:
     * <pre>{@code
     * Units.getUnitType("Cups");       // returns UnitType.VOLUME
     * Units.getUnitType("fahrenheit");  // returns UnitType.TEMPERATURE
     * Units.getUnitType("xyz");         // throws IllegalArgumentException
     * }</pre>
     *
     * @param unit the unit string to look up (case-insensitive, whitespace-tolerant);
     *             must not be {@code null}
     * @return the {@link UnitType} that the unit belongs to (VOLUME, WEIGHT,
     *         TEMPERATURE, or COUNT)
     * @throws IllegalArgumentException if the unit is not recognized in {@link #UNIT_TYPES}
     */
    public static UnitType getUnitType(String unit) {
        final String normalized = normalizeUnit(unit);
        final UnitType type = UNIT_TYPES.get(normalized);
        if (type == null) {
            throw new IllegalArgumentException("Unknown unit: " + unit);
        }
        return type;
    }
}
