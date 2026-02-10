package com.lethimcook;

import com.lethimcook.exceptions.InvalidUnitException;
import com.lethimcook.exceptions.ParsingException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for natural language conversion parsing.
 * Ported from tests/test_natural.py.
 */
class NaturalTest {

    @Nested
    class TestNaturalLanguagePatterns {

        @Test
        void testBasicToPattern() {
            String result = Natural.convertNatural("2 cups to ml");
            assertThat(result.toLowerCase()).contains("2 cups");
            assertThat(result.toLowerCase()).contains("ml");
            assertThat(result).contains("473");
        }

        @Test
        void testConvertPattern() {
            String result = Natural.convertNatural("convert 1 pound to grams");
            assertThat(result.toLowerCase()).contains("1 pound");
            assertThat(result.toLowerCase()).contains("gram");
            assertThat(result).contains("453");
        }

        @Test
        void testHowManyPattern() {
            String result = Natural.convertNatural("how many ml in 3 teaspoons");
            assertThat(result.toLowerCase()).contains("3 teaspoon");
            assertThat(result.toLowerCase()).contains("ml");
        }

        @Test
        void testDecimalValues() {
            String result = Natural.convertNatural("1.5 cups to ml");
            assertThat(result).contains("1.5");
        }

        @Test
        void testTemperatureConversion() {
            String result = Natural.convertNatural("350 fahrenheit to celsius");
            assertThat(result).contains("350");
            assertThat(result.toLowerCase()).contains("fahrenheit");
            assertThat(result.toLowerCase()).contains("celsius");
        }

        @Test
        void testCaseInsensitive() {
            String result1 = Natural.convertNatural("2 CUPS to ML");
            String result2 = Natural.convertNatural("2 cups to ml");
            assertThat(result1).contains("473");
            assertThat(result2).contains("473");
        }

        @Test
        void testMultiWordUnits() {
            String result = Natural.convertNatural("5 fluid ounce to ml");
            assertThat(result.toLowerCase()).contains("fluid ounce");
            assertThat(result.toLowerCase()).contains("ml");
        }
    }

    @Nested
    class TestNaturalLanguageErrors {

        @Test
        void testUnparseableInput() {
            assertThatThrownBy(() -> Natural.convertNatural("this is gibberish"))
                .isInstanceOf(ParsingException.class)
                .hasMessageContaining("Could not parse");
        }

        @Test
        void testMissingValue() {
            assertThatThrownBy(() -> Natural.convertNatural("cups to ml"))
                .isInstanceOf(ParsingException.class);
        }

        @Test
        void testInvalidUnit() {
            assertThatThrownBy(() -> Natural.convertNatural("2 blorg to ml"))
                .isInstanceOf(InvalidUnitException.class);
        }
    }
}
