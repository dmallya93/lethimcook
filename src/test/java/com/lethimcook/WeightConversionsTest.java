package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test weight unit conversions.
 * Ported from Python {@code TestWeightConversions} in {@code test_converter.py}.
 */
class WeightConversionsTest {

    @Test
    void poundsToGrams() {
        double result = Converter.convert(1, "pound", "g");
        assertThat(result).isCloseTo(453.592, within(0.01));
    }

    @Test
    void ozToGrams() {
        double result = Converter.convert(16, "oz", "g");
        assertThat(result).isCloseTo(453.592, within(0.01));
    }

    @Test
    void kgToLbs() {
        double result = Converter.convert(1, "kg", "lb");
        assertThat(result).isCloseTo(2.205, within(0.01));
    }

    @Test
    void gramsToOunces() {
        double result = Converter.convert(100, "g", "oz");
        assertThat(result).isCloseTo(3.527, within(0.01));
    }
}
