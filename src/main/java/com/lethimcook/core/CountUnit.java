package com.lethimcook.core;

import java.util.Set;

/**
 * Enumeration of all supported count units for dimensionless quantities.
 * <p>
 * Count units are used for discrete items or pieces where conversion is an identity operation.
 * All count conversions simply return the value unchanged since all count units are equivalent
 * (1 item = 1 piece = 1 count = 1 whole).
 * </p>
 * <p>
 * Supported units:
 * <ul>
 *   <li>Count</li>
 *   <li>Item</li>
 *   <li>Piece</li>
 *   <li>Whole</li>
 * </ul>
 * </p>
 */
public enum CountUnit implements Unit {
    COUNT("count", Set.of("count")),
    ITEM("item", Set.of("item", "items")),
    PIECE("piece", Set.of("piece", "pieces")),
    WHOLE("whole", Set.of("whole"));

    private final String primaryName;
    private final Set<String> aliases;

    /**
     * Constructor for count unit enum constants.
     *
     * @param primaryName the primary display name for this unit
     * @param aliases all recognized string representations (lowercase)
     */
    CountUnit(String primaryName, Set<String> aliases) {
        this.primaryName = primaryName;
        this.aliases = aliases;
    }

    @Override
    public UnitType getType() {
        return UnitType.COUNT;
    }

    /**
     * Converts a count value to the base unit (identity operation).
     * <p>
     * Since all count units are dimensionless and equivalent, this simply returns
     * the value unchanged. 1 item = 1 piece = 1 count = 1 whole.
     * </p>
     *
     * @param value the count value in this unit
     * @return the same value (identity function)
     */
    @Override
    public double toBase(double value) {
        return value;
    }

    /**
     * Converts a count value from the base unit (identity operation).
     * <p>
     * Since all count units are dimensionless and equivalent, this simply returns
     * the value unchanged. 1 item = 1 piece = 1 count = 1 whole.
     * </p>
     *
     * @param baseValue the count value in the base unit
     * @return the same value (identity function)
     */
    @Override
    public double fromBase(double baseValue) {
        return baseValue;
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
     * Parses a string representation of a count unit and returns the corresponding enum constant.
     * <p>
     * The parsing is case-insensitive and trims leading/trailing whitespace.
     * </p>
     *
     * @param raw the string representation of the unit (e.g., "items", "COUNT", "piece")
     * @return the matching CountUnit enum constant
     * @throws IllegalArgumentException if the string does not match any known count unit
     */
    public static CountUnit fromString(String raw) {
        String normalized = raw.toLowerCase().trim();
        for (CountUnit unit : values()) {
            if (unit.aliases.contains(normalized)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown count unit: " + raw);
    }
}
