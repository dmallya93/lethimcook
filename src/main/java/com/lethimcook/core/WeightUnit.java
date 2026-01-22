package com.lethimcook.core;

import java.util.Set;

/**
 * Enumeration of all supported weight units with conversion factors to grams (g).
 * <p>
 * All weight conversions are linear: multiply by the conversion factor to convert to grams,
 * and divide by the conversion factor to convert from grams.
 * </p>
 * <p>
 * Supported units:
 * <ul>
 *   <li>Ounce (oz)</li>
 *   <li>Pound (lb)</li>
 *   <li>Gram (g)</li>
 *   <li>Kilogram (kg)</li>
 * </ul>
 * </p>
 */
public enum WeightUnit implements Unit {
    OUNCE("oz", 28.3495, Set.of("oz", "ounce", "ounces")),
    POUND("lb", 453.592, Set.of("lb", "lbs", "pound", "pounds")),
    GRAM("g", 1.0, Set.of("g", "gram", "grams")),
    KILOGRAM("kg", 1000.0, Set.of("kg", "kilogram", "kilograms"));

    private final String primaryName;
    private final double toBaseFactor;
    private final Set<String> aliases;

    /**
     * Constructor for weight unit enum constants.
     *
     * @param primaryName the primary display name for this unit
     * @param toBaseFactor the multiplication factor to convert to grams
     * @param aliases all recognized string representations (lowercase)
     */
    WeightUnit(String primaryName, double toBaseFactor, Set<String> aliases) {
        this.primaryName = primaryName;
        this.toBaseFactor = toBaseFactor;
        this.aliases = aliases;
    }

    @Override
    public UnitType getType() {
        return UnitType.WEIGHT;
    }

    @Override
    public double toBase(double value) {
        return value * toBaseFactor;
    }

    @Override
    public double fromBase(double baseValue) {
        return baseValue / toBaseFactor;
    }

    @Override
    public String primaryName() {
        return primaryName;
    }

    @Override
    public Set<String> aliases() {
        return aliases;
    }

    /**
     * Parses a string representation of a weight unit and returns the corresponding enum constant.
     * <p>
     * The parsing is case-insensitive and trims leading/trailing whitespace.
     * </p>
     *
     * @param raw the string representation of the unit (e.g., "pounds", "OZ", "kg")
     * @return the matching WeightUnit enum constant
     * @throws IllegalArgumentException if the string does not match any known weight unit
     */
    public static WeightUnit fromString(String raw) {
        String normalized = raw.toLowerCase().trim();
        for (WeightUnit unit : values()) {
            if (unit.aliases.contains(normalized)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown weight unit: " + raw);
    }
}
