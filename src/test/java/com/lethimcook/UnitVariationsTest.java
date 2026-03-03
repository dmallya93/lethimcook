package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test different unit name variations.
 * Ported from Python {@code TestUnitVariations} in {@code test_converter.py}.
 */
class UnitVariationsTest {

    @Test
    void teaspoonVariations() {
        double result1 = Converter.convert(1, "tsp", "ml");
        double result2 = Converter.convert(1, "teaspoon", "ml");
        assertThat(result1).isCloseTo(result2, within(0.001));
    }

    @Test
    void poundVariations() {
        double result1 = Converter.convert(1, "lb", "g");
        double result2 = Converter.convert(1, "lbs", "g");
        double result3 = Converter.convert(1, "pound", "g");
        assertThat(result1).isCloseTo(result2, within(0.001));
        assertThat(result1).isCloseTo(result3, within(0.001));
    }

    @Test
    void caseInsensitive() {
        double result1 = Converter.convert(1, "CUP", "ML");
        double result2 = Converter.convert(1, "cup", "ml");
        assertThat(result1).isCloseTo(result2, within(0.001));
    }
}
