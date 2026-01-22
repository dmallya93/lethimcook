package com.lethimcook.natural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for natural language conversion parsing.
 * <p>
 * This test suite mirrors the Python test_natural.py file, verifying that all
 * natural language patterns are correctly parsed and conversion results are
 * properly formatted.
 * </p>
 */
@DisplayName("Natural Language Converter")
class NaturalLanguageConverterTest {

    @Nested
    @DisplayName("Natural Language Patterns")
    class NaturalLanguagePatterns {

        @Test
        @DisplayName("should parse basic 'to' pattern")
        void testBasicToPattern() {
            // Test case from test_natural.py lines 10-14
            String result = NaturalLanguageConverter.convertNatural("2 cups to ml");

            assertThat(result.toLowerCase()).contains("2 cups");
            assertThat(result.toLowerCase()).contains("ml");
            assertThat(result).contains("473");
        }

        @Test
        @DisplayName("should parse 'convert' pattern")
        void testConvertPattern() {
            // Test case from test_natural.py lines 16-20
            String result = NaturalLanguageConverter.convertNatural("convert 1 pound to grams");

            assertThat(result.toLowerCase()).contains("1 pound");
            assertThat(result.toLowerCase()).contains("gram");
            assertThat(result).contains("453");
        }

        @Test
        @DisplayName("should parse 'how many' pattern")
        void testHowManyPattern() {
            // Test case from test_natural.py lines 22-26
            String result = NaturalLanguageConverter.convertNatural("how many ml in 3 teaspoons");

            assertThat(result.toLowerCase()).contains("3 teaspoon");
            assertThat(result.toLowerCase()).contains("ml");
        }

        @Test
        @DisplayName("should handle decimal values")
        void testDecimalValues() {
            // Test case from test_natural.py lines 27-29
            String result = NaturalLanguageConverter.convertNatural("1.5 cups to ml");

            assertThat(result).contains("1.5");
        }

        @Test
        @DisplayName("should handle temperature conversion")
        void testTemperatureConversion() {
            // Test case from test_natural.py lines 31-35
            String result = NaturalLanguageConverter.convertNatural("350 fahrenheit to celsius");

            assertThat(result).contains("350");
            assertThat(result.toLowerCase()).contains("fahrenheit");
            assertThat(result.toLowerCase()).contains("celsius");
        }

        @Test
        @DisplayName("should be case insensitive")
        void testCaseInsensitive() {
            // Test case from test_natural.py lines 37-42
            String result1 = NaturalLanguageConverter.convertNatural("2 CUPS to ML");
            String result2 = NaturalLanguageConverter.convertNatural("2 cups to ml");

            // Both should contain the same numeric result
            assertThat(result1).contains("473");
            assertThat(result2).contains("473");
        }

        @Test
        @DisplayName("should handle multi-word units")
        void testMultiWordUnits() {
            // Test case from test_natural.py lines 44-47
            String result = NaturalLanguageConverter.convertNatural("5 fluid ounce to ml");

            assertThat(result.toLowerCase()).contains("fluid ounce");
            assertThat(result.toLowerCase()).contains("ml");
        }
    }

    @Nested
    @DisplayName("Natural Language Errors")
    class NaturalLanguageErrors {

        @Test
        @DisplayName("should reject unparseable input")
        void testUnparseableInput() {
            // Test case from test_natural.py lines 53-55
            assertThatThrownBy(() -> NaturalLanguageConverter.convertNatural("this is gibberish"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
        }

        @Test
        @DisplayName("should reject missing value")
        void testMissingValue() {
            // Test case from test_natural.py lines 57-59
            assertThatThrownBy(() -> NaturalLanguageConverter.convertNatural("cups to ml"))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should reject invalid unit")
        void testInvalidUnit() {
            // Test case from test_natural.py lines 61-64
            // Note: This throws IllegalArgumentException from Converter, not from the parser
            assertThatThrownBy(() -> NaturalLanguageConverter.convertNatural("2 blorg to ml"))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
