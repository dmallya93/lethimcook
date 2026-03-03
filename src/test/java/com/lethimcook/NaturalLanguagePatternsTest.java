package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for various natural language input patterns.
 * <p>
 * Port of Python {@code TestNaturalLanguagePatterns} from {@code test_natural.py}.
 */
class NaturalLanguagePatternsTest {

    @Test
    void basicToPattern() {
        String result = NaturalConverter.convertNatural("2 cups to ml");
        assertThat(result.toLowerCase()).contains("2 cups");
        assertThat(result.toLowerCase()).contains("ml");
        assertThat(result).contains("473");
    }

    @Test
    void convertPattern() {
        String result = NaturalConverter.convertNatural("convert 1 pound to grams");
        assertThat(result.toLowerCase()).contains("1 pound");
        assertThat(result.toLowerCase()).contains("gram");
        assertThat(result).contains("453");
    }

    @Test
    void howManyPattern() {
        String result = NaturalConverter.convertNatural("how many ml in 3 teaspoons");
        assertThat(result.toLowerCase()).contains("3 teaspoon");
        assertThat(result.toLowerCase()).contains("ml");
    }

    @Test
    void decimalValues() {
        String result = NaturalConverter.convertNatural("1.5 cups to ml");
        assertThat(result).contains("1.5");
    }

    @Test
    void temperatureConversion() {
        String result = NaturalConverter.convertNatural("350 fahrenheit to celsius");
        assertThat(result).contains("350");
        assertThat(result.toLowerCase()).contains("fahrenheit");
        assertThat(result.toLowerCase()).contains("celsius");
    }

    @Test
    void caseInsensitive() {
        String result1 = NaturalConverter.convertNatural("2 CUPS to ML");
        String result2 = NaturalConverter.convertNatural("2 cups to ml");
        // Both should contain the same numeric result
        assertThat(result1).contains("473");
        assertThat(result2).contains("473");
    }

    @Test
    void multiWordUnits() {
        String result = NaturalConverter.convertNatural("5 fluid ounce to ml");
        assertThat(result.toLowerCase()).contains("fluid ounce");
        assertThat(result.toLowerCase()).contains("ml");
    }
}
