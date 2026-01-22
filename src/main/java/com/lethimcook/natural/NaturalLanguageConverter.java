package com.lethimcook.natural;

import com.lethimcook.config.ConversionConfig;
import com.lethimcook.core.Converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Natural language conversion utility using regex pattern matching.
 * <p>
 * This class provides a user-friendly interface for unit conversions by parsing
 * natural language queries such as:
 * <ul>
 *   <li>"2 cups to ml"</li>
 *   <li>"convert 1.5 pounds to grams"</li>
 *   <li>"how many ml in 3 teaspoons"</li>
 *   <li>"5 fahrenheit to celsius"</li>
 * </ul>
 * </p>
 * <p>
 * The parser uses precompiled regex patterns for performance and matches
 * queries in a case-insensitive manner. It delegates actual conversion to
 * the Converter API and formats results according to ConversionConfig settings.
 * </p>
 * <p>
 * This class replicates the Python convert_natural() function from natural.py.
 * </p>
 */
public final class NaturalLanguageConverter {

    // Pattern 1: "X unit to unit" or "X unit in unit"
    // Examples: "2 cups to ml", "5 fahrenheit in celsius"
    // Groups: (1) numeric value, (2) from unit, (3) to unit
    private static final Pattern PATTERN1 =
        Pattern.compile("(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+(?:to|in)\\s+([a-z\\s]+)");

    // Pattern 2: "convert X unit to unit"
    // Examples: "convert 1.5 pounds to grams"
    // Groups: (1) numeric value, (2) from unit, (3) to unit
    private static final Pattern PATTERN2 =
        Pattern.compile("convert\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+to\\s+([a-z\\s]+)");

    // Pattern 3: "how many unit in X unit"
    // Examples: "how many ml in 3 teaspoons"
    // Groups: (1) to unit, (2) numeric value, (3) from unit
    private static final Pattern PATTERN3 =
        Pattern.compile("how\\s+many\\s+([a-z\\s]+?)\\s+in\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+)");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NaturalLanguageConverter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Formats a number, removing ".0" for integer values.
     * <p>
     * This helper ensures that integer values are displayed without decimal points
     * (e.g., "2" instead of "2.0") while preserving decimals for non-integer values
     * (e.g., "1.5" remains "1.5").
     * </p>
     * <p>
     * Replicates Python's _format_number() function from natural.py lines 7-11.
     * </p>
     *
     * @param value the numeric value to format
     * @return formatted string representation of the number
     */
    private static String formatNumber(double value) {
        // Check if value is effectively an integer
        if (value == (int) value) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }

    /**
     * Converts using natural language input.
     * <p>
     * This method parses natural language conversion queries and returns formatted
     * conversion results. It supports three main input patterns:
     * </p>
     * <ol>
     *   <li>"X unit to/in unit" - e.g., "2 cups to ml"</li>
     *   <li>"convert X unit to unit" - e.g., "convert 1 pound to grams"</li>
     *   <li>"how many unit in X unit" - e.g., "how many ml in 3 teaspoons"</li>
     * </ol>
     * <p>
     * The parser is case-insensitive and handles multi-word units (e.g., "fluid ounce").
     * Numeric values can be integers or decimals. The result is formatted using
     * the configured decimal precision from ConversionConfig.
     * </p>
     * <p>
     * Replicates Python's convert_natural() function from natural.py lines 14-68.
     * </p>
     *
     * @param text natural language conversion request
     * @return formatted string with conversion result in the format
     *         "{inputValue} {fromUnit} = {resultValue} {toUnit}"
     * @throws IllegalArgumentException if the input cannot be parsed or units are invalid
     */
    public static String convertNatural(String text) {
        // Normalize input: lowercase and trim whitespace
        // (matching Python's natural.py line 33)
        String normalized = text.toLowerCase().strip();

        // Try Pattern 1: "X unit to/in unit"
        Matcher matcher = PATTERN1.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();

            double result = Converter.convert(value, fromUnit, toUnit);
            int precision = ConversionConfig.getDecimalPrecision();
            String format = "%." + precision + "f";

            return formatNumber(value) + " " + fromUnit + " = " +
                   String.format(format, result) + " " + toUnit;
        }

        // Try Pattern 2: "convert X unit to unit"
        matcher = PATTERN2.matcher(normalized);
        if (matcher.matches()) {
            double value = Double.parseDouble(matcher.group(1));
            String fromUnit = matcher.group(2).strip();
            String toUnit = matcher.group(3).strip();

            double result = Converter.convert(value, fromUnit, toUnit);
            int precision = ConversionConfig.getDecimalPrecision();
            String format = "%." + precision + "f";

            return formatNumber(value) + " " + fromUnit + " = " +
                   String.format(format, result) + " " + toUnit;
        }

        // Try Pattern 3: "how many unit in X unit"
        // Note: Group ordering is different - (1) to unit, (2) value, (3) from unit
        matcher = PATTERN3.matcher(normalized);
        if (matcher.matches()) {
            String toUnit = matcher.group(1).strip();
            double value = Double.parseDouble(matcher.group(2));
            String fromUnit = matcher.group(3).strip();

            double result = Converter.convert(value, fromUnit, toUnit);
            int precision = ConversionConfig.getDecimalPrecision();
            String format = "%." + precision + "f";

            return formatNumber(value) + " " + fromUnit + " = " +
                   String.format(format, result) + " " + toUnit;
        }

        // No pattern matched - throw helpful error
        // (matching Python's natural.py lines 65-68)
        throw new IllegalArgumentException(
            "Could not parse conversion request: " + text + "\n" +
            "Try formats like: '2 cups to ml' or 'convert 1 pound to grams'"
        );
    }
}
