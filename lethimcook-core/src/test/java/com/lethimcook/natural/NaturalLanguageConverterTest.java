package com.lethimcook.natural;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for natural language conversion.
 */
@DisplayName("NaturalLanguageConverter")
class NaturalLanguageConverterTest {

    private NaturalLanguageConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NaturalLanguageConverter();
    }

    @Nested
    @DisplayName("Natural Language Patterns")
    class NaturalLanguagePatterns {

        @Test
        @DisplayName("should handle basic to pattern")
        void testBasicToPattern() {
            String result = converter.convertNatural("2 cups to ml");
            assertThat(result.toLowerCase()).contains("2 cups");
            assertThat(result.toLowerCase()).contains("ml");
            assertThat(result).contains("473");
        }

        @Test
        @DisplayName("should handle convert pattern")
        void testConvertPattern() {
            String result = converter.convertNatural("convert 1 pound to grams");
            assertThat(result.toLowerCase()).contains("1 pound");
            assertThat(result.toLowerCase()).contains("gram");
            assertThat(result).contains("453");
        }

        @Test
        @DisplayName("should handle how many pattern")
        void testHowManyPattern() {
            String result = converter.convertNatural("how many ml in 3 teaspoons");
            assertThat(result.toLowerCase()).contains("3 teaspoon");
            assertThat(result.toLowerCase()).contains("ml");
        }

        @Test
        @DisplayName("should preserve decimal values")
        void testDecimalValues() {
            String result = converter.convertNatural("1.5 cups to ml");
            assertThat(result).contains("1.5");
        }

        @Test
        @DisplayName("should handle temperature conversion")
        void testTemperatureConversion() {
            String result = converter.convertNatural("350 fahrenheit to celsius");
            assertThat(result).contains("350");
            assertThat(result.toLowerCase()).contains("fahrenheit");
            assertThat(result.toLowerCase()).contains("celsius");
        }

        @Test
        @DisplayName("should be case insensitive")
        void testCaseInsensitive() {
            String result1 = converter.convertNatural("2 CUPS to ML");
            String result2 = converter.convertNatural("2 cups to ml");
            // Both should contain the same numeric result
            assertThat(result1).contains("473");
            assertThat(result2).contains("473");
        }

        @Test
        @DisplayName("should handle multi-word units")
        void testMultiWordUnits() {
            String result = converter.convertNatural("5 fluid ounce to ml");
            assertThat(result.toLowerCase()).contains("fluid ounce");
            assertThat(result.toLowerCase()).contains("ml");
        }
    }

    @Nested
    @DisplayName("Natural Language Errors")
    class NaturalLanguageErrors {

        @Test
        @DisplayName("should throw error for unparseable input")
        void testUnparseableInput() {
            assertThatThrownBy(() -> converter.convertNatural("this is gibberish"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Could not parse");
        }

        @Test
        @DisplayName("should throw error for missing value")
        void testMissingValue() {
            assertThatThrownBy(() -> converter.convertNatural("cups to ml"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should throw error for invalid unit")
        void testInvalidUnit() {
            assertThatThrownBy(() -> converter.convertNatural("2 blorg to ml"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
