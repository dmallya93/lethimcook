package com.lethimcook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for the LetHimCook facade.
 * Verifies that the public API exposes the core conversion functionality
 * and serves as the recommended entry point for the library.
 *
 * These tests ensure end-to-end functionality through the public facade,
 * covering various conversion types and error cases.
 */
@DisplayName("LetHimCook Facade")
class LetHimCookTest {

    private LetHimCook facade;

    @BeforeEach
    void setUp() {
        facade = new LetHimCook();
    }

    @Nested
    @DisplayName("Instantiation and Thread Safety")
    class TestInstantiation {

        @Test
        @DisplayName("should create facade instance successfully")
        void testInstantiation() {
            LetHimCook instance = new LetHimCook();
            assertThat(instance).isNotNull();
        }

        @Test
        @DisplayName("should be stateless (multiple instances work independently)")
        void testStatelessness() {
            LetHimCook facade1 = new LetHimCook();
            LetHimCook facade2 = new LetHimCook();

            double result1 = facade1.convert(2, "cups", "ml");
            double result2 = facade2.convert(2, "cups", "ml");

            assertThat(result1).isCloseTo(result2, within(0.001));
        }

        @Test
        @DisplayName("should throw NullPointerException when UnitConverter is null")
        void testNullUnitConverter() {
            assertThatThrownBy(() -> new LetHimCook(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("UnitConverter cannot be null");
        }
    }

    @Nested
    @DisplayName("Volume Conversions via Facade")
    class TestVolumeConversions {

        @Test
        @DisplayName("should convert cups to milliliters")
        void testCupsToMl() {
            double result = facade.convert(2, "cups", "ml");
            assertThat(result).isCloseTo(473.176, within(0.01));
        }

        @Test
        @DisplayName("should convert teaspoons to tablespoons")
        void testTspToTbsp() {
            double result = facade.convert(3, "tsp", "tbsp");
            assertThat(result).isCloseTo(1, within(0.01));
        }

        @Test
        @DisplayName("should convert gallons to liters")
        void testGallonToLiter() {
            double result = facade.convert(1, "gallon", "l");
            assertThat(result).isCloseTo(3.785, within(0.01));
        }

        @Test
        @DisplayName("should convert fluid ounces to milliliters")
        void testFlOzToMl() {
            double result = facade.convert(8, "fl oz", "ml");
            assertThat(result).isCloseTo(236.588, within(0.01));
        }

        @Test
        @DisplayName("should handle same unit conversion")
        void testSameUnit() {
            double result = facade.convert(5, "cup", "cup");
            assertThat(result).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("Weight Conversions via Facade")
    class TestWeightConversions {

        @Test
        @DisplayName("should convert pounds to grams")
        void testPoundsToGrams() {
            double result = facade.convert(1, "pound", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("should convert ounces to grams")
        void testOzToGrams() {
            double result = facade.convert(16, "oz", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        @DisplayName("should convert kilograms to pounds")
        void testKgToLbs() {
            double result = facade.convert(1, "kg", "lb");
            assertThat(result).isCloseTo(2.205, within(0.01));
        }

        @Test
        @DisplayName("should convert grams to ounces")
        void testGramsToOunces() {
            double result = facade.convert(100, "g", "oz");
            assertThat(result).isCloseTo(3.527, within(0.01));
        }
    }

    @Nested
    @DisplayName("Temperature Conversions via Facade")
    class TestTemperatureConversions {

        @Test
        @DisplayName("should convert Fahrenheit to Celsius")
        void testFahrenheitToCelsius() {
            // Freezing point
            double result = facade.convert(32, "fahrenheit", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));

            // Boiling point
            result = facade.convert(212, "f", "c");
            assertThat(result).isCloseTo(100, within(0.01));

            // Typical oven temperature
            result = facade.convert(350, "f", "c");
            assertThat(result).isCloseTo(176.67, within(0.1));
        }

        @Test
        @DisplayName("should convert Celsius to Fahrenheit")
        void testCelsiusToFahrenheit() {
            // Freezing point
            double result = facade.convert(0, "celsius", "fahrenheit");
            assertThat(result).isCloseTo(32, within(0.01));

            // Boiling point
            result = facade.convert(100, "c", "f");
            assertThat(result).isCloseTo(212, within(0.01));
        }

        @Test
        @DisplayName("should convert Celsius to Kelvin")
        void testCelsiusToKelvin() {
            double result = facade.convert(0, "celsius", "kelvin");
            assertThat(result).isCloseTo(273.15, within(0.01));
        }

        @Test
        @DisplayName("should convert Kelvin to Celsius")
        void testKelvinToCelsius() {
            double result = facade.convert(273.15, "kelvin", "celsius");
            assertThat(result).isCloseTo(0, within(0.01));
        }
    }

    @Nested
    @DisplayName("Count Conversions via Facade")
    class TestCountConversions {

        @Test
        @DisplayName("should handle count to count conversion")
        void testCountToCount() {
            double result = facade.convert(5, "count", "item");
            assertThat(result).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("Error Handling via Facade")
    class TestErrorHandling {

        @Test
        @DisplayName("should throw exception for incompatible units")
        void testIncompatibleUnits() {
            assertThatThrownBy(() -> facade.convert(1, "cups", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }

        @Test
        @DisplayName("should throw exception for unknown unit")
        void testUnknownUnit() {
            assertThatThrownBy(() -> facade.convert(1, "blorg", "ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit");
        }

        @Test
        @DisplayName("should throw exception for temperature and weight mix")
        void testTemperatureWeightMix() {
            assertThatThrownBy(() -> facade.convert(100, "celsius", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
        }

        @Test
        @DisplayName("should handle case insensitive units")
        void testCaseInsensitive() {
            double result1 = facade.convert(1, "CUP", "ML");
            double result2 = facade.convert(1, "cup", "ml");
            assertThat(Math.abs(result1 - result2)).isLessThan(0.001);
        }
    }

    @Nested
    @DisplayName("Placeholder Methods")
    class TestPlaceholderMethods {

        @Test
        @DisplayName("should throw UnsupportedOperationException for convertNatural")
        void testConvertNaturalNotImplemented() {
            assertThatThrownBy(() -> facade.convertNatural("2 cups to ml"))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Milestone 2");
        }

        @Test
        @DisplayName("should throw UnsupportedOperationException for scaleRecipe")
        void testScaleRecipeNotImplemented() {
            assertThatThrownBy(() -> facade.scaleRecipe(null, 4))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Milestone 3");
        }
    }

    @Nested
    @DisplayName("End-to-End Integration Tests")
    class TestEndToEndIntegration {

        @Test
        @DisplayName("should perform complex conversion workflow")
        void testComplexWorkflow() {
            // Typical cooking scenario: converting recipe ingredients
            LetHimCook converter = new LetHimCook();

            // Convert volume
            double cupsToMl = converter.convert(2.5, "cups", "ml");
            assertThat(cupsToMl).isCloseTo(591.47, within(0.1));

            // Convert weight
            double poundsToGrams = converter.convert(0.5, "lb", "g");
            assertThat(poundsToGrams).isCloseTo(226.796, within(0.1));

            // Convert temperature
            double fahrenheitToCelsius = converter.convert(375, "f", "c");
            assertThat(fahrenheitToCelsius).isCloseTo(190.56, within(0.1));
        }

        @Test
        @DisplayName("should be usable as library entry point")
        void testAsLibraryEntryPoint() {
            // Demonstrates the intended usage pattern for external consumers
            LetHimCook api = new LetHimCook();

            // API should expose clean, documented interface
            double result = api.convert(1.0, "cup", "ml");

            assertThat(result).isGreaterThan(0);
            assertThat(result).isCloseTo(236.588, within(0.1));
        }
    }
}
