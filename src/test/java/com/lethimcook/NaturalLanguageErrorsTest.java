package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for error handling in natural language parsing.
 * <p>
 * Port of Python {@code TestNaturalLanguageErrors} from {@code test_natural.py}.
 */
class NaturalLanguageErrorsTest {

    @Test
    void unparseableInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("this is gibberish"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void missingValue() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("cups to ml"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void invalidUnit() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("2 blorg to ml"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
