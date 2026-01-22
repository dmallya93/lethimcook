package com.lethimcook.core;

import java.util.Set;

/**
 * Defines the contract for all unit implementations.
 * <p>
 * Each unit type (volume, weight, temperature, count) implements this interface
 * to provide conversion capabilities to and from a base unit. Conversions work
 * by converting the source value to the base unit, then converting from the base
 * unit to the target unit.
 * </p>
 * <p>
 * Base units for each type:
 * <ul>
 *   <li>VOLUME: milliliter (ml)</li>
 *   <li>WEIGHT: gram (g)</li>
 *   <li>TEMPERATURE: Celsius (°C) - note: temperature conversions are non-linear</li>
 *   <li>COUNT: count (dimensionless)</li>
 * </ul>
 * </p>
 */
public interface Unit {

    /**
     * Returns the category of this unit for compatibility checking.
     *
     * @return the unit type (VOLUME, WEIGHT, TEMPERATURE, or COUNT)
     */
    UnitType getType();

    /**
     * Converts a value from this unit to the base unit.
     * <p>
     * For linear conversions (volume, weight, count), this multiplies by a conversion factor.
     * For non-linear conversions (temperature), this applies the appropriate conversion formula.
     * </p>
     *
     * @param value the value in this unit
     * @return the equivalent value in the base unit
     */
    double toBase(double value);

    /**
     * Converts a value from the base unit to this unit.
     * <p>
     * For linear conversions (volume, weight, count), this divides by a conversion factor.
     * For non-linear conversions (temperature), this applies the appropriate conversion formula.
     * </p>
     *
     * @param baseValue the value in the base unit
     * @return the equivalent value in this unit
     */
    double fromBase(double baseValue);

    /**
     * Returns the primary display name for this unit.
     *
     * @return the primary name (e.g., "tsp", "ml", "g")
     */
    String primaryName();

    /**
     * Returns all recognized string representations for this unit.
     * <p>
     * This includes the primary name and all aliases (e.g., "tsp", "teaspoon", "teaspoons").
     * All aliases should be lowercase for case-insensitive matching.
     * </p>
     *
     * @return an immutable set of all recognized aliases
     */
    Set<String> aliases();
}
