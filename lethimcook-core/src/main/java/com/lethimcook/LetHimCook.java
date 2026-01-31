package com.lethimcook;

import com.lethimcook.converter.UnitConverter;
import com.lethimcook.natural.NaturalLanguageConverter;

/**
 * Main library facade for LetHimCook unit conversion library.
 * <p>
 * This class provides a simple, cohesive API over the underlying conversion
 * components, making the library easy to use for common conversion tasks.
 */
public class LetHimCook {

    private final UnitConverter unitConverter;
    private final NaturalLanguageConverter naturalLanguageConverter;

    /**
     * Create a new LetHimCook instance with provided converters.
     */
    public LetHimCook(UnitConverter unitConverter, NaturalLanguageConverter naturalLanguageConverter) {
        this.unitConverter = unitConverter;
        this.naturalLanguageConverter = naturalLanguageConverter;
    }

    /**
     * Create a new LetHimCook instance with default converters.
     */
    public LetHimCook() {
        this.unitConverter = new UnitConverter();
        this.naturalLanguageConverter = new NaturalLanguageConverter(unitConverter);
    }

    /**
     * Convert a value from one unit to another.
     *
     * @param value    The numeric value to convert
     * @param fromUnit The source unit
     * @param toUnit   The target unit
     * @return The converted value
     * @throws IllegalArgumentException if units are incompatible or unknown
     */
    public double convert(double value, String fromUnit, String toUnit) {
        return unitConverter.convert(value, fromUnit, toUnit);
    }

    /**
     * Convert using natural language input.
     * <p>
     * Supports patterns like:
     * - "2 cups to ml"
     * - "convert 1.5 pounds to grams"
     * - "how many ml in 3 teaspoons"
     *
     * @param query Natural language conversion request
     * @return Formatted string with conversion result
     * @throws IllegalArgumentException if the input cannot be parsed
     */
    public String convertNatural(String query) {
        return naturalLanguageConverter.convertNatural(query);
    }
}
