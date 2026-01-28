package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Test class for the LetHimCook facade.
 * Verifies that the public API exposes the core conversion functionality.
 */
class LetHimCookTest {

    @Test
    void testGetVersion() {
        LetHimCook letHimCook = new LetHimCook();
        String version = letHimCook.getVersion();

        assertThat(version).isNotNull();
        assertThat(version).isEqualTo("0.1.0");
    }

    @Test
    void testGreet() {
        LetHimCook letHimCook = new LetHimCook();
        String greeting = letHimCook.greet();

        assertThat(greeting).isNotNull();
        assertThat(greeting).contains("LetHimCook");
        assertThat(greeting).contains("cooking");
    }

    @Test
    void testInstantiation() {
        LetHimCook letHimCook = new LetHimCook();

        assertThat(letHimCook).isNotNull();
    }

    @Test
    void testConvertMethodExposed() {
        // Test that the facade exposes the convert method
        double result = LetHimCook.convert(2, "cups", "ml");
        assertThat(result).isCloseTo(473.176, within(0.01));
    }
}
