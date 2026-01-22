package com.lethimcook.core;

import java.util.Set;

/**
 * Enumeration of all supported volume units with conversion factors to milliliters (ml).
 * <p>
 * All volume conversions are linear: multiply by the conversion factor to convert to milliliters,
 * and divide by the conversion factor to convert from milliliters.
 * </p>
 * <p>
 * Supported units:
 * <ul>
 *   <li>Teaspoon (tsp)</li>
 *   <li>Tablespoon (tbsp)</li>
 *   <li>Fluid ounce (floz)</li>
 *   <li>Cup</li>
 *   <li>Pint</li>
 *   <li>Quart</li>
 *   <li>Gallon</li>
 *   <li>Milliliter (ml)</li>
 *   <li>Liter (l)</li>
 * </ul>
 * </p>
 */
public enum VolumeUnit implements Unit {
    TEASPOON("tsp", 4.92892, Set.of("tsp", "teaspoon", "teaspoons")),
    TABLESPOON("tbsp", 14.7868, Set.of("tbsp", "tablespoon", "tablespoons")),
    FLUID_OUNCE("floz", 29.5735, Set.of("floz", "fl oz", "fluid ounce", "fluid ounces")),
    CUP("cup", 236.588, Set.of("cup", "cups")),
    PINT("pint", 473.176, Set.of("pint", "pints")),
    QUART("quart", 946.353, Set.of("quart", "quarts")),
    GALLON("gallon", 3785.41, Set.of("gallon", "gallons")),
    MILLILITER("ml", 1.0, Set.of("ml", "milliliter", "milliliters")),
    LITER("l", 1000.0, Set.of("l", "liter", "liters"));

    private final String primaryName;
    private final double toBaseFactor;
    private final Set<String> aliases;

    /**
     * Constructor for volume unit enum constants.
     *
     * @param primaryName the primary display name for this unit
     * @param toBaseFactor the multiplication factor to convert to milliliters
     * @param aliases all recognized string representations (lowercase)
     */
    VolumeUnit(String primaryName, double toBaseFactor, Set<String> aliases) {
        this.primaryName = primaryName;
        this.toBaseFactor = toBaseFactor;
        this.aliases = aliases;
    }

    @Override
    public UnitType getType() {
        return UnitType.VOLUME;
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
     * Parses a string representation of a volume unit and returns the corresponding enum constant.
     * <p>
     * The parsing is case-insensitive and trims leading/trailing whitespace.
     * </p>
     *
     * @param raw the string representation of the unit (e.g., "cups", "TSP", "fl oz")
     * @return the matching VolumeUnit enum constant
     * @throws IllegalArgumentException if the string does not match any known volume unit
     */
    public static VolumeUnit fromString(String raw) {
        String normalized = raw.toLowerCase().trim();
        for (VolumeUnit unit : values()) {
            if (unit.aliases.contains(normalized)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown volume unit: " + raw);
    }
}
