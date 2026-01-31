package com.lethimcook.natural;

import com.lethimcook.converter.UnitConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Natural language conversion utility using regex and string matching.
 */
public class NaturalLanguageConverter {

    // Pre-compiled regex patterns for performance
    // Pattern 1: "X unit to unit" or "X unit in unit"
    private static final Pattern PATTERN1 = Pattern.compile("(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+(?:to|in)\\s+([a-z\\s]+)");

    // Pattern 2: "convert X unit to unit"
    private static final Pattern PATTERN2 = Pattern.compile("convert\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+to\\s+([a-z\\s]+)");

    // Pattern 3: "how many unit in X unit"
    private static final Pattern PATTERN3 = Pattern.compile("how\\s+many\\s+([a-z\\s]+?)\\s+in\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+)");

    private final UnitConverter unitConverter;

    public NaturalLanguageConverter(UnitConverter unitConverter) {
        this.unitConverter = unitConverter;
    }

    public NaturalLanguageConverter() {
        this(new UnitConverter());
    }

    /**
     * Convert using natural language input.
     * <p>
     * Supports patterns like:
     * - "2 cups to ml"
     * - "convert 1.5 pounds to grams"
     * - "how many ml in 3 teaspoons"
     * - "5 fahrenheit to celsius"
     *
     * @param text Natural language conversion request
     * @return Formatted string with conversion result
     * @throws IllegalArgumentException if the input cannot be parsed
     */
    public String convertNatural(String text) {
        String normalized = text.toLowerCase().strip();

        // Try Pattern 1: "X unit to unit" or "X unit in unit"
        Matcher matcher = PATTERN1.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();
            double result = unitConverter.convert(value, fromUnit, toUnit);
            return formatConversion(value, fromUnit, result, toUnit);
        }

        // Try Pattern 2: "convert X unit to unit"
        matcher = PATTERN2.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();
            double result = unitConverter.convert(value, fromUnit, toUnit);
            return formatConversion(value, fromUnit, result, toUnit);
        }

        // Try Pattern 3: "how many unit in X unit"
        matcher = PATTERN3.matcher(normalized);
        if (matcher.matches()) {
            String toUnit = matcher.group(1).strip();
            double value = Double.parseDouble(matcher.group(2));
            String fromUnit = matcher.group(3).strip();
            double result = unitConverter.convert(value, fromUnit, toUnit);
            return formatConversion(value, fromUnit, result, toUnit);
        }

        throw new IllegalArgumentException(
                "Could not parse conversion request: " + text + "\n" +
                "Try formats like: '2 cups to ml' or 'convert 1 pound to grams'"
        );
    }

    /**
     * Format a number, removing .0 for integers.
     */
    private String formatNumber(double value) {
        if (value == (int) value) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }

    /**
     * Format the conversion result as a string.
     */
    private String formatConversion(double value, String fromUnit, double result, String toUnit) {
        return String.format("%s %s = %.2f %s",
                formatNumber(value), fromUnit, result, toUnit);
    }
}
