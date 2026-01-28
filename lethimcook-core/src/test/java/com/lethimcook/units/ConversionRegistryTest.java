package com.lethimcook.units;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for the ConversionRegistry.
 * Verifies unit lookups, type mappings, normalization, and error handling.
 */
@DisplayName("ConversionRegistry Tests")
class ConversionRegistryTest {

    @Nested
    @DisplayName("Unit Normalization Tests")
    class NormalizationTests {

        @Test
        @DisplayName("Should normalize to lowercase")
        void shouldNormalizeToLowercase() {
            assertThat(ConversionRegistry.normalizeUnit("CUP")).isEqualTo("cup");
            assertThat(ConversionRegistry.normalizeUnit("ML")).isEqualTo("ml");
            assertThat(ConversionRegistry.normalizeUnit("TEASPOON")).isEqualTo("teaspoon");
        }

        @Test
        @DisplayName("Should strip whitespace")
        void shouldStripWhitespace() {
            assertThat(ConversionRegistry.normalizeUnit("  cup  ")).isEqualTo("cup");
            assertThat(ConversionRegistry.normalizeUnit("\tml\n")).isEqualTo("ml");
            assertThat(ConversionRegistry.normalizeUnit(" teaspoon ")).isEqualTo("teaspoon");
        }

        @Test
        @DisplayName("Should handle already normalized units")
        void shouldHandleAlreadyNormalized() {
            assertThat(ConversionRegistry.normalizeUnit("cup")).isEqualTo("cup");
            assertThat(ConversionRegistry.normalizeUnit("ml")).isEqualTo("ml");
        }

        @Test
        @DisplayName("Should throw on null input")
        void shouldThrowOnNull() {
            assertThatThrownBy(() -> ConversionRegistry.normalizeUnit(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be null");
        }
    }

    @Nested
    @DisplayName("Unit Type Lookup Tests")
    class UnitTypeLookupTests {

        @ParameterizedTest
        @CsvSource({
            "cup, VOLUME",
            "CUP, VOLUME",
            "cups, VOLUME",
            "tsp, VOLUME",
            "teaspoon, VOLUME",
            "ml, VOLUME",
            "liter, VOLUME"
        })
        @DisplayName("Should return VOLUME type for volume units")
        void shouldReturnVolumeType(String unit, UnitType expected) {
            assertThat(ConversionRegistry.getUnitType(unit)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
            "g, WEIGHT",
            "gram, WEIGHT",
            "kg, WEIGHT",
            "oz, WEIGHT",
            "pound, WEIGHT",
            "lb, WEIGHT"
        })
        @DisplayName("Should return WEIGHT type for weight units")
        void shouldReturnWeightType(String unit, UnitType expected) {
            assertThat(ConversionRegistry.getUnitType(unit)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
            "celsius, TEMPERATURE",
            "c, TEMPERATURE",
            "fahrenheit, TEMPERATURE",
            "f, TEMPERATURE",
            "kelvin, TEMPERATURE",
            "k, TEMPERATURE"
        })
        @DisplayName("Should return TEMPERATURE type for temperature units")
        void shouldReturnTemperatureType(String unit, UnitType expected) {
            assertThat(ConversionRegistry.getUnitType(unit)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
            "count, COUNT",
            "item, COUNT",
            "items, COUNT",
            "piece, COUNT",
            "pieces, COUNT",
            "whole, COUNT"
        })
        @DisplayName("Should return COUNT type for count units")
        void shouldReturnCountType(String unit, UnitType expected) {
            assertThat(ConversionRegistry.getUnitType(unit)).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should throw for unknown unit with proper message")
        void shouldThrowForUnknownUnit() {
            assertThatThrownBy(() -> ConversionRegistry.getUnitType("blorg"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown unit: blorg");
        }

        @Test
        @DisplayName("Should be case insensitive")
        void shouldBeCaseInsensitive() {
            assertThat(ConversionRegistry.getUnitType("CUP")).isEqualTo(UnitType.VOLUME);
            assertThat(ConversionRegistry.getUnitType("Cup")).isEqualTo(UnitType.VOLUME);
            assertThat(ConversionRegistry.getUnitType("cUp")).isEqualTo(UnitType.VOLUME);
        }
    }

    @Nested
    @DisplayName("Base Unit Tests")
    class BaseUnitTests {

        @Test
        @DisplayName("Should return correct base units")
        void shouldReturnCorrectBaseUnits() {
            assertThat(ConversionRegistry.getBaseUnit(UnitType.VOLUME)).isEqualTo("ml");
            assertThat(ConversionRegistry.getBaseUnit(UnitType.WEIGHT)).isEqualTo("g");
            assertThat(ConversionRegistry.getBaseUnit(UnitType.TEMPERATURE)).isEqualTo("celsius");
            assertThat(ConversionRegistry.getBaseUnit(UnitType.COUNT)).isEqualTo("count");
        }
    }

    @Nested
    @DisplayName("Conversion Factor Tests")
    class ConversionFactorTests {

        @ParameterizedTest
        @CsvSource({
            "tsp, 4.92892",
            "tbsp, 14.7868",
            "cup, 236.588",
            "ml, 1.0",
            "l, 1000.0",
            "pint, 473.176",
            "quart, 946.353",
            "gallon, 3785.41"
        })
        @DisplayName("Should return correct volume conversion factors")
        void shouldReturnVolumeFactors(String unit, double expected) {
            assertThat(ConversionRegistry.getConversionFactor(unit))
                .isCloseTo(expected, within(0.00001));
        }

        @ParameterizedTest
        @CsvSource({
            "oz, 28.3495",
            "lb, 453.592",
            "pound, 453.592",
            "g, 1.0",
            "kg, 1000.0"
        })
        @DisplayName("Should return correct weight conversion factors")
        void shouldReturnWeightFactors(String unit, double expected) {
            assertThat(ConversionRegistry.getConversionFactor(unit))
                .isCloseTo(expected, within(0.00001));
        }

        @Test
        @DisplayName("Should return 0.0 for temperature units")
        void shouldReturnZeroForTemperature() {
            assertThat(ConversionRegistry.getConversionFactor("celsius")).isEqualTo(0.0);
            assertThat(ConversionRegistry.getConversionFactor("fahrenheit")).isEqualTo(0.0);
            assertThat(ConversionRegistry.getConversionFactor("kelvin")).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should return 1.0 for count units")
        void shouldReturnOneForCount() {
            assertThat(ConversionRegistry.getConversionFactor("count")).isEqualTo(1.0);
            assertThat(ConversionRegistry.getConversionFactor("item")).isEqualTo(1.0);
            assertThat(ConversionRegistry.getConversionFactor("piece")).isEqualTo(1.0);
        }
    }

    @Nested
    @DisplayName("Unit Aliases Tests")
    class UnitAliasesTests {

        @ParameterizedTest
        @ValueSource(strings = {"tsp", "teaspoon", "teaspoons"})
        @DisplayName("Should recognize teaspoon aliases")
        void shouldRecognizeTeaspoonAliases(String alias) {
            assertThat(ConversionRegistry.getUnitType(alias)).isEqualTo(UnitType.VOLUME);
            assertThat(ConversionRegistry.getConversionFactor(alias))
                .isCloseTo(4.92892, within(0.00001));
        }

        @ParameterizedTest
        @ValueSource(strings = {"tbsp", "tablespoon", "tablespoons"})
        @DisplayName("Should recognize tablespoon aliases")
        void shouldRecognizeTablespoonAliases(String alias) {
            assertThat(ConversionRegistry.getUnitType(alias)).isEqualTo(UnitType.VOLUME);
            assertThat(ConversionRegistry.getConversionFactor(alias))
                .isCloseTo(14.7868, within(0.00001));
        }

        @ParameterizedTest
        @ValueSource(strings = {"floz", "fl oz", "fluid ounce", "fluid ounces"})
        @DisplayName("Should recognize fluid ounce aliases")
        void shouldRecognizeFluidOunceAliases(String alias) {
            assertThat(ConversionRegistry.getUnitType(alias)).isEqualTo(UnitType.VOLUME);
            assertThat(ConversionRegistry.getConversionFactor(alias))
                .isCloseTo(29.5735, within(0.00001));
        }

        @ParameterizedTest
        @ValueSource(strings = {"lb", "lbs", "pound", "pounds"})
        @DisplayName("Should recognize pound aliases")
        void shouldRecognizePoundAliases(String alias) {
            assertThat(ConversionRegistry.getUnitType(alias)).isEqualTo(UnitType.WEIGHT);
            assertThat(ConversionRegistry.getConversionFactor(alias))
                .isCloseTo(453.592, within(0.00001));
        }

        @ParameterizedTest
        @ValueSource(strings = {"oz", "ounce", "ounces"})
        @DisplayName("Should recognize ounce aliases")
        void shouldRecognizeOunceAliases(String alias) {
            assertThat(ConversionRegistry.getUnitType(alias)).isEqualTo(UnitType.WEIGHT);
            assertThat(ConversionRegistry.getConversionFactor(alias))
                .isCloseTo(28.3495, within(0.00001));
        }
    }

    @Nested
    @DisplayName("Unit Retrieval Tests")
    class UnitRetrievalTests {

        @Test
        @DisplayName("Should return Unit object with correct properties")
        void shouldReturnUnitWithCorrectProperties() {
            Unit cup = ConversionRegistry.getUnit("cup");
            assertThat(cup.name()).isEqualTo("cup");
            assertThat(cup.symbol()).isEqualTo("cup");
            assertThat(cup.type()).isEqualTo(UnitType.VOLUME);
            assertThat(cup.conversionFactor()).isCloseTo(236.588, within(0.00001));
        }

        @Test
        @DisplayName("Should throw for unknown unit")
        void shouldThrowForUnknownUnit() {
            assertThatThrownBy(() -> ConversionRegistry.getUnit("invalid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown unit: invalid");
        }

        @Test
        @DisplayName("Should be case insensitive for unit retrieval")
        void shouldBeCaseInsensitiveForRetrieval() {
            Unit cup1 = ConversionRegistry.getUnit("cup");
            Unit cup2 = ConversionRegistry.getUnit("CUP");
            Unit cup3 = ConversionRegistry.getUnit("Cup");

            assertThat(cup1).isEqualTo(cup2);
            assertThat(cup2).isEqualTo(cup3);
        }
    }

    @Nested
    @DisplayName("Known Unit Tests")
    class KnownUnitTests {

        @Test
        @DisplayName("Should return true for known units")
        void shouldReturnTrueForKnownUnits() {
            assertThat(ConversionRegistry.isKnownUnit("cup")).isTrue();
            assertThat(ConversionRegistry.isKnownUnit("ml")).isTrue();
            assertThat(ConversionRegistry.isKnownUnit("kg")).isTrue();
            assertThat(ConversionRegistry.isKnownUnit("celsius")).isTrue();
        }

        @Test
        @DisplayName("Should return false for unknown units")
        void shouldReturnFalseForUnknownUnits() {
            assertThat(ConversionRegistry.isKnownUnit("blorg")).isFalse();
            assertThat(ConversionRegistry.isKnownUnit("invalid")).isFalse();
        }

        @Test
        @DisplayName("Should be case insensitive")
        void shouldBeCaseInsensitive() {
            assertThat(ConversionRegistry.isKnownUnit("CUP")).isTrue();
            assertThat(ConversionRegistry.isKnownUnit("Cup")).isTrue();
            assertThat(ConversionRegistry.isKnownUnit("cUp")).isTrue();
        }
    }

    @Nested
    @DisplayName("All Unit Definitions Tests")
    class AllUnitDefinitionsTests {

        @Test
        @DisplayName("Should have all 24 volume units from Python")
        void shouldHaveAllVolumeUnits() {
            String[] volumeUnits = {
                "tsp", "teaspoon", "teaspoons",
                "tbsp", "tablespoon", "tablespoons",
                "floz", "fl oz", "fluid ounce", "fluid ounces",
                "cup", "cups",
                "pint", "pints",
                "quart", "quarts",
                "gallon", "gallons",
                "ml", "milliliter", "milliliters",
                "l", "liter", "liters"
            };

            for (String unit : volumeUnits) {
                assertThat(ConversionRegistry.getUnitType(unit))
                    .as("Unit %s should be VOLUME", unit)
                    .isEqualTo(UnitType.VOLUME);
            }
        }

        @Test
        @DisplayName("Should have all 13 weight units from Python")
        void shouldHaveAllWeightUnits() {
            String[] weightUnits = {
                "oz", "ounce", "ounces",
                "lb", "lbs", "pound", "pounds",
                "g", "gram", "grams",
                "kg", "kilogram", "kilograms"
            };

            for (String unit : weightUnits) {
                assertThat(ConversionRegistry.getUnitType(unit))
                    .as("Unit %s should be WEIGHT", unit)
                    .isEqualTo(UnitType.WEIGHT);
            }
        }

        @Test
        @DisplayName("Should have all 6 temperature units from Python")
        void shouldHaveAllTemperatureUnits() {
            String[] temperatureUnits = {
                "fahrenheit", "f",
                "celsius", "c",
                "kelvin", "k"
            };

            for (String unit : temperatureUnits) {
                assertThat(ConversionRegistry.getUnitType(unit))
                    .as("Unit %s should be TEMPERATURE", unit)
                    .isEqualTo(UnitType.TEMPERATURE);
            }
        }

        @Test
        @DisplayName("Should have all 6 count units from Python")
        void shouldHaveAllCountUnits() {
            String[] countUnits = {
                "count", "item", "items",
                "piece", "pieces", "whole"
            };

            for (String unit : countUnits) {
                assertThat(ConversionRegistry.getUnitType(unit))
                    .as("Unit %s should be COUNT", unit)
                    .isEqualTo(UnitType.COUNT);
            }
        }
    }
}
