package com.lethimcook.core;

/**
 * Categorizes units into high-level types for compatibility checking.
 * <p>
 * Units of the same type can be converted to each other (e.g., cups to milliliters),
 * while conversions between different types are incompatible and will fail.
 * </p>
 */
public enum UnitType {
    /**
     * Volume measurements (base unit: milliliter)
     */
    VOLUME,

    /**
     * Weight/mass measurements (base unit: gram)
     */
    WEIGHT,

    /**
     * Temperature measurements (base unit: Celsius)
     */
    TEMPERATURE,

    /**
     * Dimensionless count measurements (base unit: count)
     */
    COUNT
}
