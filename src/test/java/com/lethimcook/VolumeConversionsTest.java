package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test volume unit conversions.
 * Ported from Python {@code TestVolumeConversions} in {@code test_converter.py}.
 */
class VolumeConversionsTest {

    @Test
    void cupsToMl() {
        double result = Converter.convert(2, "cups", "ml");
        assertThat(result).isCloseTo(473.176, within(0.01));
    }

    @Test
    void tspToTbsp() {
        double result = Converter.convert(3, "tsp", "tbsp");
        assertThat(result).isCloseTo(1.0, within(0.01));
    }

    @Test
    void gallonToLiter() {
        double result = Converter.convert(1, "gallon", "l");
        assertThat(result).isCloseTo(3.785, within(0.01));
    }

    @Test
    void flozToMl() {
        double result = Converter.convert(8, "fl oz", "ml");
        assertThat(result).isCloseTo(236.588, within(0.01));
    }

    @Test
    void sameUnit() {
        double result = Converter.convert(5, "cup", "cup");
        assertThat(result).isEqualTo(5.0);
    }
}
