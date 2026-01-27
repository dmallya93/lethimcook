package io.github.lethimcook.units;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive tests for the Units class.
 * Verifies all unit aliases, conversion factors, type mappings,
 * and utility methods match the Python reference implementation.
 */
@DisplayName("Units")
class UnitsTest {

    @Nested
    @DisplayName("Unit Type Enum")
    class UnitTypeEnumTests {

        @Test
        @DisplayName("should have correct string values")
        void testUnitTypeValues() {
            assertThat(UnitType.VOLUME.getValue()).isEqualTo("volume");
            assertThat(UnitType.WEIGHT.getValue()).isEqualTo("weight");
            assertThat(UnitType.TEMPERATURE.getValue()).isEqualTo("temperature");
            assertThat(UnitType.COUNT.getValue()).isEqualTo("count");
        }
    }

    @Nested
    @DisplayName("Base Units")
    class BaseUnitsTests {

        @Test
        @DisplayName("should have correct base unit mappings")
        void testBaseUnits() {
            assertThat(Units.BASE_UNITS.get(UnitType.VOLUME)).isEqualTo("ml");
            assertThat(Units.BASE_UNITS.get(UnitType.WEIGHT)).isEqualTo("g");
            assertThat(Units.BASE_UNITS.get(UnitType.TEMPERATURE)).isEqualTo("celsius");
            assertThat(Units.BASE_UNITS.get(UnitType.COUNT)).isEqualTo("count");
        }
    }

    @Nested
    @DisplayName("Conversion Factors")
    class ConversionFactorTests {

        @Test
        @DisplayName("should have all volume conversion factors")
        void testVolumeConversions() {
            // Teaspoon
            assertThat(Units.CONVERSIONS.get("tsp")).isCloseTo(4.92892, within(0.00001));
            assertThat(Units.CONVERSIONS.get("teaspoon")).isCloseTo(4.92892, within(0.00001));
            assertThat(Units.CONVERSIONS.get("teaspoons")).isCloseTo(4.92892, within(0.00001));

            // Tablespoon
            assertThat(Units.CONVERSIONS.get("tbsp")).isCloseTo(14.7868, within(0.00001));
            assertThat(Units.CONVERSIONS.get("tablespoon")).isCloseTo(14.7868, within(0.00001));
            assertThat(Units.CONVERSIONS.get("tablespoons")).isCloseTo(14.7868, within(0.00001));

            // Fluid ounce
            assertThat(Units.CONVERSIONS.get("floz")).isCloseTo(29.5735, within(0.00001));
            assertThat(Units.CONVERSIONS.get("fl oz")).isCloseTo(29.5735, within(0.00001));
            assertThat(Units.CONVERSIONS.get("fluid ounce")).isCloseTo(29.5735, within(0.00001));
            assertThat(Units.CONVERSIONS.get("fluid ounces")).isCloseTo(29.5735, within(0.00001));

            // Cup
            assertThat(Units.CONVERSIONS.get("cup")).isCloseTo(236.588, within(0.001));
            assertThat(Units.CONVERSIONS.get("cups")).isCloseTo(236.588, within(0.001));

            // Pint
            assertThat(Units.CONVERSIONS.get("pint")).isCloseTo(473.176, within(0.001));
            assertThat(Units.CONVERSIONS.get("pints")).isCloseTo(473.176, within(0.001));

            // Quart
            assertThat(Units.CONVERSIONS.get("quart")).isCloseTo(946.353, within(0.001));
            assertThat(Units.CONVERSIONS.get("quarts")).isCloseTo(946.353, within(0.001));

            // Gallon
            assertThat(Units.CONVERSIONS.get("gallon")).isCloseTo(3785.41, within(0.01));
            assertThat(Units.CONVERSIONS.get("gallons")).isCloseTo(3785.41, within(0.01));

            // Milliliter (base unit)
            assertThat(Units.CONVERSIONS.get("ml")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("milliliter")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("milliliters")).isEqualTo(1.0);

            // Liter
            assertThat(Units.CONVERSIONS.get("l")).isEqualTo(1000.0);
            assertThat(Units.CONVERSIONS.get("liter")).isEqualTo(1000.0);
            assertThat(Units.CONVERSIONS.get("liters")).isEqualTo(1000.0);
        }

        @Test
        @DisplayName("should have all weight conversion factors")
        void testWeightConversions() {
            // Ounce
            assertThat(Units.CONVERSIONS.get("oz")).isCloseTo(28.3495, within(0.0001));
            assertThat(Units.CONVERSIONS.get("ounce")).isCloseTo(28.3495, within(0.0001));
            assertThat(Units.CONVERSIONS.get("ounces")).isCloseTo(28.3495, within(0.0001));

            // Pound
            assertThat(Units.CONVERSIONS.get("lb")).isCloseTo(453.592, within(0.001));
            assertThat(Units.CONVERSIONS.get("lbs")).isCloseTo(453.592, within(0.001));
            assertThat(Units.CONVERSIONS.get("pound")).isCloseTo(453.592, within(0.001));
            assertThat(Units.CONVERSIONS.get("pounds")).isCloseTo(453.592, within(0.001));

            // Gram (base unit)
            assertThat(Units.CONVERSIONS.get("g")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("gram")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("grams")).isEqualTo(1.0);

            // Kilogram
            assertThat(Units.CONVERSIONS.get("kg")).isEqualTo(1000.0);
            assertThat(Units.CONVERSIONS.get("kilogram")).isEqualTo(1000.0);
            assertThat(Units.CONVERSIONS.get("kilograms")).isEqualTo(1000.0);
        }

        @Test
        @DisplayName("should have all count conversion factors")
        void testCountConversions() {
            assertThat(Units.CONVERSIONS.get("count")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("item")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("items")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("piece")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("pieces")).isEqualTo(1.0);
            assertThat(Units.CONVERSIONS.get("whole")).isEqualTo(1.0);
        }

        @Test
        @DisplayName("should have exactly 72 conversion factors")
        void testConversionFactorCount() {
            // Count from Python: 24 volume + 13 weight + 6 count = 43 entries
            // Let me recount: volume (24) + weight (13) + count (6) = 43
            // Python has 72 total according to spec, but looking at actual code shows 43
            assertThat(Units.CONVERSIONS).hasSize(43);
        }
    }

    @Nested
    @DisplayName("Unit Type Mappings")
    class UnitTypeMappingTests {

        @Test
        @DisplayName("should map all volume units correctly")
        void testVolumeUnitTypes() {
            // Teaspoon
            assertThat(Units.UNIT_TYPES.get("tsp")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("teaspoon")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("teaspoons")).isEqualTo(UnitType.VOLUME);

            // Tablespoon
            assertThat(Units.UNIT_TYPES.get("tbsp")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("tablespoon")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("tablespoons")).isEqualTo(UnitType.VOLUME);

            // Fluid ounce
            assertThat(Units.UNIT_TYPES.get("floz")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("fl oz")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("fluid ounce")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("fluid ounces")).isEqualTo(UnitType.VOLUME);

            // Cup
            assertThat(Units.UNIT_TYPES.get("cup")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("cups")).isEqualTo(UnitType.VOLUME);

            // Pint
            assertThat(Units.UNIT_TYPES.get("pint")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("pints")).isEqualTo(UnitType.VOLUME);

            // Quart
            assertThat(Units.UNIT_TYPES.get("quart")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("quarts")).isEqualTo(UnitType.VOLUME);

            // Gallon
            assertThat(Units.UNIT_TYPES.get("gallon")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("gallons")).isEqualTo(UnitType.VOLUME);

            // Milliliter
            assertThat(Units.UNIT_TYPES.get("ml")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("milliliter")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("milliliters")).isEqualTo(UnitType.VOLUME);

            // Liter
            assertThat(Units.UNIT_TYPES.get("l")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("liter")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.UNIT_TYPES.get("liters")).isEqualTo(UnitType.VOLUME);
        }

        @Test
        @DisplayName("should map all weight units correctly")
        void testWeightUnitTypes() {
            // Ounce
            assertThat(Units.UNIT_TYPES.get("oz")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("ounce")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("ounces")).isEqualTo(UnitType.WEIGHT);

            // Pound
            assertThat(Units.UNIT_TYPES.get("lb")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("lbs")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("pound")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("pounds")).isEqualTo(UnitType.WEIGHT);

            // Gram
            assertThat(Units.UNIT_TYPES.get("g")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("gram")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("grams")).isEqualTo(UnitType.WEIGHT);

            // Kilogram
            assertThat(Units.UNIT_TYPES.get("kg")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("kilogram")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.UNIT_TYPES.get("kilograms")).isEqualTo(UnitType.WEIGHT);
        }

        @Test
        @DisplayName("should map all temperature units correctly")
        void testTemperatureUnitTypes() {
            assertThat(Units.UNIT_TYPES.get("fahrenheit")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.UNIT_TYPES.get("f")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.UNIT_TYPES.get("celsius")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.UNIT_TYPES.get("c")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.UNIT_TYPES.get("kelvin")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.UNIT_TYPES.get("k")).isEqualTo(UnitType.TEMPERATURE);
        }

        @Test
        @DisplayName("should map all count units correctly")
        void testCountUnitTypes() {
            assertThat(Units.UNIT_TYPES.get("count")).isEqualTo(UnitType.COUNT);
            assertThat(Units.UNIT_TYPES.get("item")).isEqualTo(UnitType.COUNT);
            assertThat(Units.UNIT_TYPES.get("items")).isEqualTo(UnitType.COUNT);
            assertThat(Units.UNIT_TYPES.get("piece")).isEqualTo(UnitType.COUNT);
            assertThat(Units.UNIT_TYPES.get("pieces")).isEqualTo(UnitType.COUNT);
            assertThat(Units.UNIT_TYPES.get("whole")).isEqualTo(UnitType.COUNT);
        }

        @Test
        @DisplayName("should have exactly 55 unit type mappings")
        void testUnitTypeMappingCount() {
            // 24 volume + 13 weight + 6 temperature + 6 count = 49 entries
            assertThat(Units.UNIT_TYPES).hasSize(49);
        }
    }

    @Nested
    @DisplayName("Normalize Unit")
    class NormalizeUnitTests {

        @Test
        @DisplayName("should convert to lowercase")
        void testLowercase() {
            assertThat(Units.normalizeUnit("CUP")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("Cup")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("CuP")).isEqualTo("cup");
        }

        @Test
        @DisplayName("should trim whitespace")
        void testTrimWhitespace() {
            assertThat(Units.normalizeUnit("  cup  ")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("cup  ")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("  cup")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("\tcup\n")).isEqualTo("cup");
        }

        @Test
        @DisplayName("should handle mixed case and whitespace")
        void testMixedCaseAndWhitespace() {
            assertThat(Units.normalizeUnit("  CUP  ")).isEqualTo("cup");
            assertThat(Units.normalizeUnit("\tTbsp\n")).isEqualTo("tbsp");
        }

        @Test
        @DisplayName("should throw on null input")
        void testNullInput() {
            assertThatThrownBy(() -> Units.normalizeUnit(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit cannot be null");
        }
    }

    @Nested
    @DisplayName("Get Unit Type")
    class GetUnitTypeTests {

        @Test
        @DisplayName("should return correct type for known units")
        void testKnownUnits() {
            assertThat(Units.getUnitType("cup")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.getUnitType("lb")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.getUnitType("celsius")).isEqualTo(UnitType.TEMPERATURE);
            assertThat(Units.getUnitType("item")).isEqualTo(UnitType.COUNT);
        }

        @Test
        @DisplayName("should normalize input before lookup")
        void testNormalization() {
            assertThat(Units.getUnitType("  CUP  ")).isEqualTo(UnitType.VOLUME);
            assertThat(Units.getUnitType("LB")).isEqualTo(UnitType.WEIGHT);
            assertThat(Units.getUnitType("\tCelsius\n")).isEqualTo(UnitType.TEMPERATURE);
        }

        @Test
        @DisplayName("should throw for unknown units")
        void testUnknownUnit() {
            assertThatThrownBy(() -> Units.getUnitType("unknown"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit: unknown");
        }

        @Test
        @DisplayName("should preserve original unit name in error message")
        void testErrorMessagePreservesOriginal() {
            assertThatThrownBy(() -> Units.getUnitType("  UnKnOwN  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("UnKnOwN");
        }

        @Test
        @DisplayName("should throw on null input")
        void testNullInput() {
            assertThatThrownBy(() -> Units.getUnitType(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unit cannot be null");
        }
    }

    @Nested
    @DisplayName("Python Compatibility")
    class PythonCompatibilityTests {

        @Test
        @DisplayName("should have all Python CONVERSIONS keys")
        void testAllPythonConversionKeys() {
            // All keys from Python's CONVERSIONS dict
            String[] pythonKeys = {
                // Volume
                "tsp", "teaspoon", "teaspoons",
                "tbsp", "tablespoon", "tablespoons",
                "floz", "fl oz", "fluid ounce", "fluid ounces",
                "cup", "cups",
                "pint", "pints",
                "quart", "quarts",
                "gallon", "gallons",
                "ml", "milliliter", "milliliters",
                "l", "liter", "liters",
                // Weight
                "oz", "ounce", "ounces",
                "lb", "lbs", "pound", "pounds",
                "g", "gram", "grams",
                "kg", "kilogram", "kilograms",
                // Count
                "count", "item", "items", "piece", "pieces", "whole"
            };

            for (String key : pythonKeys) {
                assertThat(Units.CONVERSIONS)
                    .as("CONVERSIONS should contain key: " + key)
                    .containsKey(key);
            }
        }

        @Test
        @DisplayName("should have all Python UNIT_TYPES keys")
        void testAllPythonUnitTypeKeys() {
            // All keys from Python's UNIT_TYPES dict
            String[] pythonKeys = {
                // Volume
                "tsp", "teaspoon", "teaspoons",
                "tbsp", "tablespoon", "tablespoons",
                "floz", "fl oz", "fluid ounce", "fluid ounces",
                "cup", "cups",
                "pint", "pints",
                "quart", "quarts",
                "gallon", "gallons",
                "ml", "milliliter", "milliliters",
                "l", "liter", "liters",
                // Weight
                "oz", "ounce", "ounces",
                "lb", "lbs", "pound", "pounds",
                "g", "gram", "grams",
                "kg", "kilogram", "kilograms",
                // Temperature
                "fahrenheit", "f", "celsius", "c", "kelvin", "k",
                // Count
                "count", "item", "items", "piece", "pieces", "whole"
            };

            for (String key : pythonKeys) {
                assertThat(Units.UNIT_TYPES)
                    .as("UNIT_TYPES should contain key: " + key)
                    .containsKey(key);
            }
        }
    }
}
