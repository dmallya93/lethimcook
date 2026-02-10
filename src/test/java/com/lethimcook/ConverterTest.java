package com.lethimcook;

import com.lethimcook.exceptions.IncompatibleConversionException;
import com.lethimcook.exceptions.InvalidUnitException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

/**
 * Tests for core conversion functionality.
 * Ported from tests/test_converter.py.
 */
class ConverterTest {

    @Nested
    class TestVolumeConversions {

        @Test
        void convertsCupsToMl() {
            double result = Converter.convert(2, "cups", "ml");
            assertThat(result).isCloseTo(473.176, within(0.01));
        }

        @Test
        void convertsTspToTbsp() {
            double result = Converter.convert(3, "tsp", "tbsp");
            assertThat(result).isCloseTo(1.0, within(0.01));
        }

        @Test
        void convertsGallonToLiter() {
            double result = Converter.convert(1, "gallon", "l");
            assertThat(result).isCloseTo(3.785, within(0.01));
        }

        @Test
        void convertsFlozToMl() {
            double result = Converter.convert(8, "fl oz", "ml");
            assertThat(result).isCloseTo(236.588, within(0.01));
        }

        @Test
        void convertsSameUnit() {
            double result = Converter.convert(5, "cup", "cup");
            assertThat(result).isEqualTo(5.0);
        }
    }

    @Nested
    class TestWeightConversions {

        @Test
        void convertsPoundsToGrams() {
            double result = Converter.convert(1, "pound", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        void convertsOzToGrams() {
            double result = Converter.convert(16, "oz", "g");
            assertThat(result).isCloseTo(453.592, within(0.01));
        }

        @Test
        void convertsKgToLbs() {
            double result = Converter.convert(1, "kg", "lb");
            assertThat(result).isCloseTo(2.205, within(0.01));
        }

        @Test
        void convertsGramsToOunces() {
            double result = Converter.convert(100, "g", "oz");
            assertThat(result).isCloseTo(3.527, within(0.01));
        }
    }

    @Nested
    class TestTemperatureConversions {

        @Test
        void convertsFahrenheitToCelsius() {
            double result = Converter.convert(32, "fahrenheit", "celsius");
            assertThat(result).isCloseTo(0.0, within(0.01));

            double result2 = Converter.convert(212, "f", "c");
            assertThat(result2).isCloseTo(100.0, within(0.01));

            double result3 = Converter.convert(350, "f", "c");
            assertThat(result3).isCloseTo(176.67, within(0.1));
        }

        @Test
        void convertsCelsiusToFahrenheit() {
            double result = Converter.convert(0, "celsius", "fahrenheit");
            assertThat(result).isCloseTo(32.0, within(0.01));

            double result2 = Converter.convert(100, "c", "f");
            assertThat(result2).isCloseTo(212.0, within(0.01));
        }

        @Test
        void convertsCelsiusToKelvin() {
            double result = Converter.convert(0, "celsius", "kelvin");
            assertThat(result).isCloseTo(273.15, within(0.01));
        }

        @Test
        void convertsKelvinToCelsius() {
            double result = Converter.convert(273.15, "kelvin", "celsius");
            assertThat(result).isCloseTo(0.0, within(0.01));
        }
    }

    @Nested
    class TestCountConversions {

        @Test
        void convertsCountToCount() {
            double result = Converter.convert(5, "count", "item");
            assertThat(result).isEqualTo(5.0);
        }
    }

    @Nested
    class TestErrorHandling {

        @Test
        void throwsOnIncompatibleUnits() {
            assertThatThrownBy(() -> Converter.convert(1, "cups", "grams"))
                .isInstanceOf(IncompatibleConversionException.class)
                .hasMessageContaining("Cannot convert between");
        }

        @Test
        void throwsOnUnknownUnit() {
            assertThatThrownBy(() -> Converter.convert(1, "blorg", "ml"))
                .isInstanceOf(InvalidUnitException.class)
                .hasMessageContaining("Unknown unit");
        }

        @Test
        void throwsOnTemperatureWeightMix() {
            assertThatThrownBy(() -> Converter.convert(100, "celsius", "grams"))
                .isInstanceOf(IncompatibleConversionException.class)
                .hasMessageContaining("Cannot convert between");
        }
    }

    @Nested
    class TestUnitVariations {

        @Test
        void handlesTeaspoonVariations() {
            double result1 = Converter.convert(1, "tsp", "ml");
            double result2 = Converter.convert(1, "teaspoon", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }

        @Test
        void handlesPoundVariations() {
            double result1 = Converter.convert(1, "lb", "g");
            double result2 = Converter.convert(1, "lbs", "g");
            double result3 = Converter.convert(1, "pound", "g");
            assertThat(result1).isCloseTo(result2, within(0.001));
            assertThat(result1).isCloseTo(result3, within(0.001));
        }

        @Test
        void handlesCaseInsensitive() {
            double result1 = Converter.convert(1, "CUP", "ML");
            double result2 = Converter.convert(1, "cup", "ml");
            assertThat(result1).isCloseTo(result2, within(0.001));
        }
    }
}
