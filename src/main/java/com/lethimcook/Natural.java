package com.lethimcook;

import com.lethimcook.exceptions.ParsingException;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Natural language conversion utility using regex and string matching.
 * Migrated from natural.py.
 */
public final class Natural {

    /**
     * Structured result of parsing a natural language conversion request.
     */
    public record ParsedConversion(double amount, String fromUnit, String toUnit) {}

    // Pattern 1: "X unit to unit" or "X unit in unit"
    private static final Pattern PATTERN1 = Pattern.compile(
        "(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+(?:to|in)\\s+([a-z\\s]+)"
    );

    // Pattern 2: "convert X unit to unit"
    private static final Pattern PATTERN2 = Pattern.compile(
        "convert\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+?)\\s+to\\s+([a-z\\s]+)"
    );

    // Pattern 3: "how many unit in X unit"
    private static final Pattern PATTERN3 = Pattern.compile(
        "how\\s+many\\s+([a-z\\s]+?)\\s+in\\s+(\\d+\\.?\\d*)\\s+([a-z\\s]+)"
    );

    private Natural() {}

    /**
     * Parse a natural language conversion request into structured components.
     *
     * @param input the natural language input string
     * @return a ParsedConversion with amount, fromUnit, and toUnit
     * @throws ParsingException if the input cannot be parsed
     */
    public static ParsedConversion parse(String input) {
        String text = input.toLowerCase(Locale.ROOT).strip();

        // Try pattern 1: "X unit to/in unit"
        Matcher m1 = PATTERN1.matcher(text);
        if (m1.matches()) {
            double value = Double.parseDouble(m1.group(1));
            String fromUnit = m1.group(2).strip();
            String toUnit = m1.group(3).strip();
            return new ParsedConversion(value, fromUnit, toUnit);
        }

        // Try pattern 2: "convert X unit to unit"
        Matcher m2 = PATTERN2.matcher(text);
        if (m2.matches()) {
            double value = Double.parseDouble(m2.group(1));
            String fromUnit = m2.group(2).strip();
            String toUnit = m2.group(3).strip();
            return new ParsedConversion(value, fromUnit, toUnit);
        }

        // Try pattern 3: "how many unit in X unit"
        Matcher m3 = PATTERN3.matcher(text);
        if (m3.matches()) {
            String toUnit = m3.group(1).strip();
            double value = Double.parseDouble(m3.group(2));
            String fromUnit = m3.group(3).strip();
            return new ParsedConversion(value, fromUnit, toUnit);
        }

        throw new ParsingException(
            "Could not parse conversion request: " + text + "\n"
            + "Try formats like: '2 cups to ml' or 'convert 1 pound to grams'"
        );
    }

    /**
     * Convert using natural language input.
     *
     * <p>Supports patterns like:
     * <ul>
     *   <li>"2 cups to ml"</li>
     *   <li>"convert 1.5 pounds to grams"</li>
     *   <li>"how many ml in 3 teaspoons"</li>
     *   <li>"5 fahrenheit to celsius"</li>
     * </ul>
     *
     * @param text natural language conversion request
     * @return formatted string with conversion result
     * @throws ParsingException if the input cannot be parsed
     */
    public static String convertNatural(String text) {
        ParsedConversion parsed = parse(text);
        double result = Converter.convert(parsed.amount(), parsed.fromUnit(), parsed.toUnit());
        return String.format("%s %s = %.2f %s",
            formatNumber(parsed.amount()),
            parsed.fromUnit(),
            result,
            parsed.toUnit()
        );
    }

    /**
     * Format a number, removing .0 for integer values.
     * Mirrors Python's _format_number().
     */
    static String formatNumber(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
