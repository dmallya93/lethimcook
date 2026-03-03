package com.lethimcook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for error handling in natural language parsing.
 * <p>
 * Port of Python {@code TestNaturalLanguageErrors} from {@code test_natural.py},
 * plus additional edge-case error scenarios not present in the Python source.
 */
class NaturalLanguageErrorsTest {

    // -----------------------------------------------------------------------
    // Original Python-ported error tests
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Additional edge-case error tests
    // -----------------------------------------------------------------------

    @Test
    void emptyStringInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void whitespaceOnlyInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void tabAndNewlineOnlyInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("\t\n"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void numericOnlyInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("42"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void numericWithDecimalOnlyInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("3.14"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void numberWithTrailingSpaces() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("42   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void numberToNumberWithoutUnits() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("42 to 100"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void incompatibleUnitsViaPattern1() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("2 cups to grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
    }

    @Test
    void incompatibleUnitsViaConvertPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("convert 5 fahrenheit to grams"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
    }

    @Test
    void incompatibleUnitsViaHowManyPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("how many grams in 2 cups"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot convert between");
    }

    @Test
    void incompleteConvertPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("convert 5 cups"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void incompleteHowManyPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("how many cups"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void convertKeywordAlone() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("convert"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void howManyKeywordAlone() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("how many"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void toKeywordAlone() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("to"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void specialCharactersInInput() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("2 cups @ ml"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void unitWithoutTargetUnit() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("1.5 cups"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void invalidSourceUnitInConvertPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("convert 2 zorps to ml"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void invalidTargetUnitInConvertPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("convert 2 cups to zorps"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bothUnitsInvalidInPattern1() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("2 foo to bar"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void negativeNumberDoesNotMatchPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("-5 cups to ml"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Could not parse");
    }

    @Test
    void punctuationInQueryBreaksPattern() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("2 cups, to ml"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void questionMarkFormat() {
        assertThatThrownBy(() -> NaturalConverter.convertNatural("how many ml in 3 teaspoons?"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
