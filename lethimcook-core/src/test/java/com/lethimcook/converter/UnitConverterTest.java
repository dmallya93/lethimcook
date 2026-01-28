package com.lethimcook.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for core conversion functionality.
 * Mirrors test_converter.py from the Python implementation.
 */
@DisplayName("UnitConverter")
class UnitConverterTest {

    @Nested
    @DisplayName("Volume Conversions")
    class TestVolumeConversions {

        @Test
        @DisplayName("should convert cups to ml")
        void test_cups_to_ml() {
            double result = UnitConverter.convert(2, "cups", "ml");
            assertThat(result).isCloseTo(473.176, within(0.01));
        }

        @Test
        @DisplayName("should convert tsp to tbsp")
        void test_tsp_to_tbsp() {
            double result = UnitConverter.convert(3, "tsp", "tbsp");
            assertThat(result).isCloseTo(1, within(0.01));
        }

        @Test
        @DisplayName("should convert gallon to liter")
        void test_gallon_to_liter() {
            double result = UnitConverter.convert(1, "gallon", "l");
            assertThat(result).isCloseTo(3.785, within(0.01));
        }

        @Test
        @DisplayName("should convert fl oz to ml")
        void test_floz_to_ml() {
            double result = UnitConverter.convert(8, "fl oz", "ml");
            assertThat(result).isCloseTo(236.588, within(0.01));
        }

        @Test
        @DisplayName("should handle same unit conversion")
        void test_same_unit() {
            double result = UnitConverter.convert(5, "cup", "cup");
            assertThat(result).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("Weight Conversions")
    class TestWeightConversions {

        @Test
        @DisplayName("should convert pounds to grams")
        void test_pounds_to_grams() {
            double result = UnitConverter.convert(1, "pound", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("should convert oz to grams")
        void test_oz_to_grams() {
            double result = UnitConverter.convert(16, "oz", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("should convert kg to lbs")
        void test_kg_to_lbs() {
            double result = UnitConverter.convert(1, "kg", "lb");
            assertThat(result).isCloseTo(2.205, within(0.01));
        }

        @Test
        @DisplayName("should convert grams to ounces")
        void test_grams_to_ounces() {
            double result = UnitConverter.convert(100, "g", "oz");
            assertThat(result).isCloseTo(3.527, within(0.01));
        }
    }

    @Nested
    @DisplayName("Temperature Conversions")
    class TestTemperatureConversions {

        @Test
        @DisplayName("should convert fahrenheit to celsius")
        void test_fahrenheit_to_celsius() {
            // Test freezing point
            double result = UnitConverter.convert(32, "fahrenheit", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));

            // Test boiling point with abbreviation
            result = UnitConverter.convert(212, "f", "c");
            assertThat(result).isCloseTo(100, within(0.01));

            // Test typical oven temperature
            result = UnitConverter.convert(350, "f", "c");
            assertThat(result).isCloseTo(176.67, within(0.1));
        }

        @Test
        @DisplayName("should convert celsius to fahrenheit")
        void test_celsius_to_fahrenheit() {
            // Test freezing point
            double result = UnitConverter.convert(0, "celsius", "fahrenheit");
            assertThat(result).isCloseTo(32, within(0.01));

            // Test boiling point with abbreviation
            result = UnitConverter.convert(100, "c", "f");
            assertThat(result).isCloseTo(212, within(0.01));
        }

        @Test
        @DisplayName("should convert celsius to kelvin")
        void test_celsius_to_kelvin() {
            double result = UnitConverter.convert(0, "celsius", "kelvin");
            assertThat(result).isCloseTo(273.15, within(0.01));
        }

        @Test
        @DisplayName("should convert kelvin to celsius")
        void test_kelvin_to_celsius() {
            double result = UnitConverter.convert(273.15, "kelvin", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));
        }
    }

    @Nested
    @DisplayName("Count Conversions")
    class TestCountConversions {

        @Test
        @DisplayName("should handle count to count conversion")
        void test_count_to_count() {
            double result = UnitConverter.convert(5, "count", "item");
            assertThat(result).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class TestErrorHandling {

        @Test
        @DisplayName("should throw exception for incompatible units")
        void test_incompatible_units() {
            assertThatThrownBy(() -> UnitConverter.convert(1, "cups", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }

        @Test
        @DisplayName("should throw exception for unknown unit")
        void test_unknown_unit() {
            assertThatThrownBy(() -> UnitConverter.convert(1, "blorg", "ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit");
        }

        @Test
        @DisplayName("should throw exception for temperature and weight mix")
        void test_temperature_weight_mix() {
            assertThatThrownBy(() -> UnitConverter.convert(100, "celsius", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }
    }

    @Nested
    @DisplayName("Unit Variations")
    class TestUnitVariations {

        @Test
        @DisplayName("should handle teaspoon variations")
        void test_teaspoon_variations() {
            double result1 = UnitConverter.convert(1, "tsp", "ml");
            double result2 = UnitConverter.convert(1, "teaspoon", "ml");
            assertThat(Math.abs(result1 - result2)).isLessThan(0.001);
        }

        @Test
        @DisplayName("should handle pound variations")
        void test_pound_variations() {
            double result1 = UnitConverter.convert(1, "lb", "g");
            double result2 = UnitConverter.convert(1, "lbs", "g");
            double result3 = UnitConverter.convert(1, "pound", "g");
            assertThat(Math.abs(result1 - result2)).isLessThan(0.001);
            assertThat(Math.abs(result1 - result3)).isLessThan(0.001);
        }

        @Test
        @DisplayName("should be case insensitive")
        void test_case_insensitive() {
            double result1 = UnitConverter.convert(1, "CUP", "ML");
            double result2 = UnitConverter.convert(1, "cup", "ml");
            assertThat(Math.abs(result1 - result2)).isLessThan(0.001);
        }
    }
}
