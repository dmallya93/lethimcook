package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test error handling.
 * Ported from Python {@code TestErrorHandling} in {@code test_converter.py}.
 */
class ErrorHandlingTest {

    @Test
    void incompatibleUnits() {
        assertThatThrownBy(() -> Converter.convert(1, "cups", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
    }

    @Test
    void unknownUnit() {
        assertThatThrownBy(() -> Converter.convert(1, "blorg", "ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown unit");
    }

    @Test
    void temperatureWeightMix() {
        assertThatThrownBy(() -> Converter.convert(100, "celsius", "grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
    }
}
