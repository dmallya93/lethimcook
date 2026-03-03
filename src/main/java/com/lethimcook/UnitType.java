package com.lethimcook;

/**
 * Categories of measurement units supported by the conversion library.
 */
public enum UnitType {
    VOLUME("volume"),
    WEIGHT("weight"),
    TEMPERATURE("temperature"),
    COUNT("count");

    private final String label;

    UnitType(String label) {
        this.label = label;
    }

    /**
     * Returns the lowercase human-readable label for this unit type.
     * <p>
     * The label matches the Python {@code StrEnum} value used in the original
     * implementation (e.g. "volume", "weight", "temperature", "count"). It is
     * used in error messages produced by {@link Converter} when reporting
     * incompatible unit types, and can also serve as a display-friendly name
     * for the category.
     *
     * @return the lowercase label string (never {@code null})
     */
    public String label() {
        return label;
    }

    /**
     * Returns the lowercase label as the string representation of this enum constant.
     * <p>
     * Overrides the default {@link Enum#toString()} (which returns the constant name
     * in uppercase, e.g. "VOLUME") so that error messages and formatted output use the
     * more readable lowercase form (e.g. "volume"). This mirrors the Python
     * {@code StrEnum} behaviour where {@code str(UnitType.VOLUME)} produces "volume".
     *
     * @return the lowercase label identical to {@link #label()}
     */
    @Override
    public String toString() {
        return label;
    }
}
