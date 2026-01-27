package io.github.lethimcook.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static io.github.lethimcook.converter.Converter.convert;

/**
 * Tests for core conversion functionality.
 * Migrated from Python's test_converter.py
 */
@DisplayName("Converter Tests")
class ConverterTest {

    @Nested
    @DisplayName("Volume Conversions")
    class VolumeConversions {

        @Test
        @DisplayName("Convert cups to milliliters")
        void testCupsToMl() {
            double result = convert(2, "cups", "ml");
            assertThat(result).isCloseTo(473.176, within(0.01));
        }

        @Test
        @DisplayName("Convert teaspoons to tablespoons")
        void testTspToTbsp() {
            double result = convert(3, "tsp", "tbsp");
            assertThat(result).isCloseTo(1, within(0.01));
        }

        @Test
        @DisplayName("Convert gallons to liters")
        void testGallonToLiter() {
            double result = convert(1, "gallon", "l");
            assertThat(result).isCloseTo(3.785, within(0.01));
        }

        @Test
        @DisplayName("Convert fluid ounces to milliliters")
        void testFlozToMl() {
            double result = convert(8, "fl oz", "ml");
            assertThat(result).isCloseTo(236.588, within(0.01));
        }

        @Test
        @DisplayName("Convert same unit (identity)")
        void testSameUnit() {
            double result = convert(5, "cup", "cup");
            assertThat(result).isEqualTo(5.0);
        }
    }

    @Nested
    @DisplayName("Weight Conversions")
    class WeightConversions {

        @Test
        @DisplayName("Convert pounds to grams")
        void testPoundsToGrams() {
            double result = convert(1, "pound", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("Convert ounces to grams")
        void testOzToGrams() {
            double result = convert(16, "oz", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("Convert kilograms to pounds")
        void testKgToLbs() {
            double result = convert(1, "kg", "lb");
            assertThat(result).isCloseTo(2.205, within(0.01));
        }

        @Test
        @DisplayName("Convert grams to ounces")
        void testGramsToOunces() {
            double result = convert(100, "g", "oz");
            assertThat(result).isCloseTo(3.527, within(0.01));
        }
    }

    @Nested
    @DisplayName("Temperature Conversions")
    class TemperatureConversions {

        @Test
        @DisplayName("Convert Fahrenheit to Celsius")
        void testFahrenheitToCelsius() {
            double result = convert(32, "fahrenheit", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));

            result = convert(212, "f", "c");
            assertThat(result).isCloseTo(100, within(0.01));

            result = convert(350, "f", "c");
            assertThat(result).isCloseTo(176.67, within(0.1));
        }

        @Test
        @DisplayName("Convert Celsius to Fahrenheit")
        void testCelsiusToFahrenheit() {
            double result = convert(0, "celsius", "fahrenheit");
            assertThat(result).isCloseTo(32, within(0.01));

            result = convert(100, "c", "f");
            assertThat(result).isCloseTo(212, within(0.01));
        }

        @Test
        @DisplayName("Convert Celsius to Kelvin")
        void testCelsiusToKelvin() {
            double result = convert(0, "celsius", "kelvin");
            assertThat(result).isCloseTo(273.15, within(0.01));
        }

        @Test
        @DisplayName("Convert Kelvin to Celsius")
        void testKelvinToCelsius() {
            double result = convert(273.15, "kelvin", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));
        }
    }

    @Nested
    @DisplayName("Count Conversions")
    class CountConversions {

        @Test
        @DisplayName("Convert count to count (identity)")
        void testCountToCount() {
            double result = convert(5, "count", "item");
            assertThat(result).isEqualTo(5.0);
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        @DisplayName("Throw exception for incompatible units")
        void testIncompatibleUnits() {
            assertThatThrownBy(() -> convert(1, "cups", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }

        @Test
        @DisplayName("Throw exception for unknown unit")
        void testUnknownUnit() {
            assertThatThrownBy(() -> convert(1, "blorg", "ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit");
        }

        @Test
        @DisplayName("Throw exception for temperature to weight conversion")
        void testTemperatureWeightMix() {
            assertThatThrownBy(() -> convert(100, "celsius", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }
    }

    @Nested
    @DisplayName("Unit Variations")
    class UnitVariations {

        @Test
        @DisplayName("Handle teaspoon variations")
        void testTeaspoonVariations() {
            double result1 = convert(1, "tsp", "ml");
            double result2 = convert(1, "teaspoon", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }

        @Test
        @DisplayName("Handle pound variations")
        void testPoundVariations() {
            double result1 = convert(1, "lb", "g");
            double result2 = convert(1, "lbs", "g");
            double result3 = convert(1, "pound", "g");
            assertThat(result1).isCloseTo(result2, within(0.001));
            assertThat(result1).isCloseTo(result3, within(0.001));
        }

        @Test
        @DisplayName("Handle case insensitive units")
        void testCaseInsensitive() {
            double result1 = convert(1, "CUP", "ML");
            double result2 = convert(1, "cup", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }
    }
}
