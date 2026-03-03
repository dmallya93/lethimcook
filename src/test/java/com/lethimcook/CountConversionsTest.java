package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test count/item conversions.
 * Ported from Python {@code TestCountConversions} in {@code test_converter.py}.
 */
class CountConversionsTest {

    @Test
    void countToCount() {
        double result = Converter.convert(5, "count", "item");
        assertThat(result).isEqualTo(5.0);
    }
}
