package com.lethimcook.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

/**
 * Comprehensive test suite for the Converter class.
 * <p>
 * This test suite mirrors all tests from the Python test_converter.py file (123 lines),
 * ensuring behavioral equivalence between the Python and Java implementations.
 * </p>
 * <p>
 * Test organization uses JUnit 5 @Nested classes to mirror Python's test class structure:
 * <ul>
 *   <li>TestVolumeConversions</li>
 *   <li>TestWeightConversions</li>
 *   <li>TestTemperatureConversions</li>
 *   <li>TestCountConversions</li>
 *   <li>TestErrorHandling</li>
 *   <li>TestUnitVariations</li>
 * </ul>
 * </p>
 */
class ConverterTest {

    /**
     * Test volume unit conversions.
     * <p>
     * Mirrors Python's TestVolumeConversions class from test_converter.py lines 7-29.
     * </p>
     */
    @Nested
    class TestVolumeConversions {

        /**
         * Test converting cups to milliliters.
         * <p>
         * Python test: test_converter.py lines 10-12
         * </p>
         */
        @Test
        void testCupsToMl() {
            double result = Converter.convert(2, "cups", "ml");
            assertThat(result).isCloseTo(473.176, within(0.01));
        }

        /**
         * Test converting teaspoons to tablespoons.
         * <p>
         * Python test: test_converter.py lines 14-16
         * </p>
         */
        @Test
        void testTspToTbsp() {
            double result = Converter.convert(3, "tsp", "tbsp");
            assertThat(result).isCloseTo(1.0, within(0.01));
        }

        /**
         * Test converting gallons to liters.
         * <p>
         * Python test: test_converter.py lines 18-20
         * </p>
         */
        @Test
        void testGallonToLiter() {
            double result = Converter.convert(1, "gallon", "l");
            assertThat(result).isCloseTo(3.785, within(0.01));
        }

        /**
         * Test converting fluid ounces to milliliters.
         * <p>
         * Python test: test_converter.py lines 22-24
         * </p>
         */
        @Test
        void testFlozToMl() {
            double result = Converter.convert(8, "fl oz", "ml");
            assertThat(result).isCloseTo(236.588, within(0.01));
        }

        /**
         * Test converting a unit to itself (should return same value).
         * <p>
         * Python test: test_converter.py lines 26-28
         * </p>
         */
        @Test
        void testSameUnit() {
            double result = Converter.convert(5, "cup", "cup");
            assertThat(result).isEqualTo(5.0);
        }
    }

    /**
     * Test weight unit conversions.
     * <p>
     * Mirrors Python's TestWeightConversions class from test_converter.py lines 31-49.
     * </p>
     */
    @Nested
    class TestWeightConversions {

        /**
         * Test converting pounds to grams.
         * <p>
         * Python test: test_converter.py lines 34-36
         * </p>
         */
        @Test
        void testPoundsToGrams() {
            double result = Converter.convert(1, "pound", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        /**
         * Test converting ounces to grams.
         * <p>
         * Python test: test_converter.py lines 38-40
         * </p>
         */
        @Test
        void testOzToGrams() {
            double result = Converter.convert(16, "oz", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        /**
         * Test converting kilograms to pounds.
         * <p>
         * Python test: test_converter.py lines 42-44
         * </p>
         */
        @Test
        void testKgToLbs() {
            double result = Converter.convert(1, "kg", "lb");
            assertThat(result).isCloseTo(2.205, within(0.01));
        }

        /**
         * Test converting grams to ounces.
         * <p>
         * Python test: test_converter.py lines 46-48
         * </p>
         */
        @Test
        void testGramsToOunces() {
            double result = Converter.convert(100, "g", "oz");
            assertThat(result).isCloseTo(3.527, within(0.01));
        }
    }

    /**
     * Test temperature unit conversions.
     * <p>
     * Mirrors Python's TestTemperatureConversions class from test_converter.py lines 51-78.
     * Temperature conversions are non-linear and require special handling.
     * </p>
     */
    @Nested
    class TestTemperatureConversions {

        /**
         * Test converting Fahrenheit to Celsius with multiple test values.
         * <p>
         * Python test: test_converter.py lines 54-62
         * Tests edge cases: freezing point (32°F), boiling point (212°F), and oven temp (350°F)
         * </p>
         */
        @Test
        void testFahrenheitToCelsius() {
            // Freezing point: 32°F = 0°C
            double result = Converter.convert(32, "fahrenheit", "celsius");
            assertThat(result).isCloseTo(0.0, within(0.01));

            // Boiling point: 212°F = 100°C
            result = Converter.convert(212, "f", "c");
            assertThat(result).isCloseTo(100.0, within(0.01));

            // Oven temperature: 350°F ≈ 176.67°C
            result = Converter.convert(350, "f", "c");
            assertThat(result).isCloseTo(176.67, within(0.1));
        }

        /**
         * Test converting Celsius to Fahrenheit.
         * <p>
         * Python test: test_converter.py lines 64-69
         * </p>
         */
        @Test
        void testCelsiusToFahrenheit() {
            // Freezing point: 0°C = 32°F
            double result = Converter.convert(0, "celsius", "fahrenheit");
            assertThat(result).isCloseTo(32.0, within(0.01));

            // Boiling point: 100°C = 212°F
            result = Converter.convert(100, "c", "f");
            assertThat(result).isCloseTo(212.0, within(0.01));
        }

        /**
         * Test converting Celsius to Kelvin.
         * <p>
         * Python test: test_converter.py lines 71-73
         * </p>
         */
        @Test
        void testCelsiusToKelvin() {
            // Absolute zero in Celsius: 0°C = 273.15 K
            double result = Converter.convert(0, "celsius", "kelvin");
            assertThat(result).isCloseTo(273.15, within(0.01));
        }

        /**
         * Test converting Kelvin to Celsius.
         * <p>
         * Python test: test_converter.py lines 75-77
         * </p>
         */
        @Test
        void testKelvinToCelsius() {
            // Absolute zero: 273.15 K = 0°C
            double result = Converter.convert(273.15, "kelvin", "celsius");
            assertThat(result).isCloseTo(0.0, within(0.01));
        }
    }

    /**
     * Test count/item conversions.
     * <p>
     * Mirrors Python's TestCountConversions class from test_converter.py lines 80-86.
     * Count units are dimensionless and conversion returns the same value.
     * </p>
     */
    @Nested
    class TestCountConversions {

        /**
         * Test converting between count units (should return same value).
         * <p>
         * Python test: test_converter.py lines 83-85
         * </p>
         */
        @Test
        void testCountToCount() {
            double result = Converter.convert(5, "count", "item");
            assertThat(result).isEqualTo(5.0);
        }
    }

    /**
     * Test error handling for invalid conversions.
     * <p>
     * Mirrors Python's TestErrorHandling class from test_converter.py lines 88-102.
     * </p>
     */
    @Nested
    class TestErrorHandling {

        /**
         * Test that incompatible unit types throw an exception.
         * <p>
         * Python test: test_converter.py lines 91-93
         * </p>
         */
        @Test
        void testIncompatibleUnits() {
            assertThatThrownBy(() -> Converter.convert(1, "cups", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }

        /**
         * Test that unknown units throw an exception.
         * <p>
         * Python test: test_converter.py lines 95-97
         * </p>
         */
        @Test
        void testUnknownUnit() {
            assertThatThrownBy(() -> Converter.convert(1, "blorg", "ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit");
        }

        /**
         * Test that mixing temperature and weight units throws an exception.
         * <p>
         * Python test: test_converter.py lines 99-101
         * </p>
         */
        @Test
        void testTemperatureWeightMix() {
            assertThatThrownBy(() -> Converter.convert(100, "celsius", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }
    }

    /**
     * Test different unit name variations and aliases.
     * <p>
     * Mirrors Python's TestUnitVariations class from test_converter.py lines 104-123.
     * Ensures that all aliases (abbreviations, plural forms, etc.) work correctly.
     * </p>
     */
    @Nested
    class TestUnitVariations {

        /**
         * Test that different teaspoon variations produce the same result.
         * <p>
         * Python test: test_converter.py lines 107-110
         * </p>
         */
        @Test
        void testTeaspoonVariations() {
            double result1 = Converter.convert(1, "tsp", "ml");
            double result2 = Converter.convert(1, "teaspoon", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }

        /**
         * Test that different pound variations produce the same result.
         * <p>
         * Python test: test_converter.py lines 112-117
         * </p>
         */
        @Test
        void testPoundVariations() {
            double result1 = Converter.convert(1, "lb", "g");
            double result2 = Converter.convert(1, "lbs", "g");
            double result3 = Converter.convert(1, "pound", "g");
            assertThat(result1).isCloseTo(result2, within(0.001));
            assertThat(result1).isCloseTo(result3, within(0.001));
        }

        /**
         * Test that unit strings are case-insensitive.
         * <p>
         * Python test: test_converter.py lines 119-122
         * </p>
         */
        @Test
        void testCaseInsensitive() {
            double result1 = Converter.convert(1, "CUP", "ML");
            double result2 = Converter.convert(1, "cup", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }
    }
}
