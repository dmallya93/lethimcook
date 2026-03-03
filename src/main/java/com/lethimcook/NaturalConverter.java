package com.lethimcook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Natural language conversion utility using regex and string matching.
 * <p>
 * Translates the Python {@code natural.py} module. Supports three input
 * patterns for natural-language conversion queries:
 * <ul>
 *   <li>Pattern 1: {@code "X unit to/in unit"} (e.g., "2 cups to ml")</li>
 *   <li>Pattern 2: {@code "convert X unit to unit"} (e.g., "convert 1 pound to grams")</li>
 *   <li>Pattern 3: {@code "how many unit in X unit"} (e.g., "how many ml in 3 teaspoons")</li>
 * </ul>
 * Each pattern extracts a numeric value, source unit, and target unit, delegates
 * to {@link Converter#convert(double, String, String)}, and returns a formatted
 * result string.
 */
public final class NaturalConverter {

    private NaturalConverter() {
        // utility class
    }

    // Pattern 1: "X unit to unit" or "X unit in unit"
    private static final Pattern PATTERN1 = Pattern.compile(
            "(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+(?:to|in)\\s+([a-z\\s]+)");

    // Pattern 2: "convert X unit to unit"
    private static final Pattern PATTERN2 = Pattern.compile(
            "convert\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+to\\s+([a-z\\s]+)");

    // Pattern 3: "how many unit in X unit"
    private static final Pattern PATTERN3 = Pattern.compile(
            "how\\s+many\\s+([a-z\\s]+?)\\s+in\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+)");

    /**
     * Convert using natural language input.
     * <p>
     * Supports patterns like:
     * <ul>
     *   <li>"2 cups to ml"</li>
     *   <li>"convert 1.5 pounds to grams"</li>
     *   <li>"how many ml in 3 teaspoons"</li>
     *   <li>"5 fahrenheit to celsius"</li>
     * </ul>
     *
     * @param text natural language conversion request
     * @return formatted string with conversion result (e.g., "2 cups = 473.18 ml")
     * @throws IllegalArgumentException if the input cannot be parsed or the conversion fails
     */
    public static String convertNatural(String text) {
        String normalized = text.toLowerCase().strip();

        // Pattern 1: "X unit to unit" or "X unit in unit"
        Matcher matcher = PATTERN1.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();
            double result = Converter.convert(value, fromUnit, toUnit);
            return formatNumber(value) + " " + fromUnit + " = "
                    + String.format("%.2f", result) + " " + toUnit;
        }

        // Pattern 2: "convert X unit to unit"
        matcher = PATTERN2.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();
            double result = Converter.convert(value, fromUnit, toUnit);
            return formatNumber(value) + " " + fromUnit + " = "
                    + String.format("%.2f", result) + " " + toUnit;
        }

        // Pattern 3: "how many unit in X unit"
        matcher = PATTERN3.matcher(normalized);
        if (matcher.matches()) {
            String toUnit = matcher.group(1).strip();
            double value = Double.parseDouble(matcher.group(2));
            String fromUnit = matcher.group(3).strip();
            double result = Converter.convert(value, fromUnit, toUnit);
            return formatNumber(value) + " " + fromUnit + " = "
                    + String.format("%.2f", result) + " " + toUnit;
        }

        throw new IllegalArgumentException(
                "Could not parse conversion request: " + normalized + "\n"
                        + "Try formats like: '2 cups to ml' or 'convert 1 pound to grams'");
    }

    /**
     * Format a number, removing .0 for integer values.
     * <p>
     * Mirrors the Python {@code _format_number()} helper: if the value equals
     * its integer truncation, the decimal portion is omitted (e.g. 2.0 becomes "2").
     * Otherwise the original decimal representation is preserved.
     *
     * @param value the number to format
     * @return the formatted string representation
     */
    private static String formatNumber(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
