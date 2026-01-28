package com.lethimcook.units;

/**
 * Immutable record representing a unit of measurement.
 * Holds the unit's name, symbol, type, and conversion factor to the base unit.
 *
 * For VOLUME, WEIGHT, and COUNT types, the conversionFactor is the multiplier to convert
 * to the base unit (ml for volume, g for weight, count for count).
 *
 * For TEMPERATURE, the conversionFactor is not used as temperature conversions are non-linear.
 */
public record Unit(
    String name,
    String symbol,
    UnitType type,
    double conversionFactor
) {
    /**
     * Creates a Unit with validation.
     *
     * @param name the canonical name of the unit
     * @param symbol the short form/abbreviation (may be same as name)
     * @param type the category of the unit
     * @param conversionFactor multiplier to base unit (ignored for TEMPERATURE)
     * @throws IllegalArgumentException if name, symbol, or type is null
     */
    public Unit {
        if (name == null) {
            throw new IllegalArgumentException("Unit name cannot be null");
        }
        if (symbol == null) {
            throw new IllegalArgumentException("Unit symbol cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Unit type cannot be null");
        }
    }
}
