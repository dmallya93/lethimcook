package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the LetHimCook facade.
 * This placeholder test verifies that the basic project structure and
 * test infrastructure are working correctly.
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
}
