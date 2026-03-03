package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test temperature unit conversions.
 * Ported from Python {@code TestTemperatureConversions} in {@code test_converter.py}.
 */
class TemperatureConversionsTest {

    @Test
    void fahrenheitToCelsius() {
        double result1 = Converter.convert(32, "fahrenheit", "celsius");
        assertThat(result1).isCloseTo(0.0, within(0.01));

        double result2 = Converter.convert(212, "f", "c");
        assertThat(result2).isCloseTo(100.0, within(0.01));

        double result3 = Converter.convert(350, "f", "c");
        assertThat(result3).isCloseTo(176.67, within(0.1));
    }

    @Test
    void celsiusToFahrenheit() {
        double result1 = Converter.convert(0, "celsius", "fahrenheit");
        assertThat(result1).isCloseTo(32.0, within(0.01));

        double result2 = Converter.convert(100, "c", "f");
        assertThat(result2).isCloseTo(212.0, within(0.01));
    }

    @Test
    void celsiusToKelvin() {
        double result = Converter.convert(0, "celsius", "kelvin");
        assertThat(result).isCloseTo(273.15, within(0.01));
    }

    @Test
    void kelvinToCelsius() {
        double result = Converter.convert(273.15, "kelvin", "celsius");
        assertThat(result).isCloseTo(0.0, within(0.01));
    }
}
