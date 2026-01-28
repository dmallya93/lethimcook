package com.lethimcook.units;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for the UnitType enum.
 * Verifies enum values, toString() behavior, and fromString() conversion.
 */
@DisplayName("UnitType Tests")
class UnitTypeTest {

    @Test
    @DisplayName("Should have all four unit type values")
    void shouldHaveAllUnitTypes() {
        UnitType[] types = UnitType.values();
        assertThat(types).hasSize(4);
        assertThat(types).containsExactlyInAnyOrder(
            UnitType.VOLUME,
            UnitType.WEIGHT,
            UnitType.TEMPERATURE,
            UnitType.COUNT
        );
    }

    @Test
    @DisplayName("toString() should return lowercase string values")
    void toStringShouldReturnLowercase() {
        assertThat(UnitType.VOLUME.toString()).isEqualTo("volume");
        assertThat(UnitType.WEIGHT.toString()).isEqualTo("weight");
        assertThat(UnitType.TEMPERATURE.toString()).isEqualTo("temperature");
        assertThat(UnitType.COUNT.toString()).isEqualTo("count");
    }

    @Test
    @DisplayName("fromString() should parse lowercase strings")
    void fromStringShouldParseLowercase() {
        assertThat(UnitType.fromString("volume")).isEqualTo(UnitType.VOLUME);
        assertThat(UnitType.fromString("weight")).isEqualTo(UnitType.WEIGHT);
        assertThat(UnitType.fromString("temperature")).isEqualTo(UnitType.TEMPERATURE);
        assertThat(UnitType.fromString("count")).isEqualTo(UnitType.COUNT);
    }

    @Test
    @DisplayName("fromString() should parse uppercase strings")
    void fromStringShouldParseUppercase() {
        assertThat(UnitType.fromString("VOLUME")).isEqualTo(UnitType.VOLUME);
        assertThat(UnitType.fromString("WEIGHT")).isEqualTo(UnitType.WEIGHT);
        assertThat(UnitType.fromString("TEMPERATURE")).isEqualTo(UnitType.TEMPERATURE);
        assertThat(UnitType.fromString("COUNT")).isEqualTo(UnitType.COUNT);
    }

    @Test
    @DisplayName("fromString() should parse mixed case strings")
    void fromStringShouldParseMixedCase() {
        assertThat(UnitType.fromString("Volume")).isEqualTo(UnitType.VOLUME);
        assertThat(UnitType.fromString("WeIgHt")).isEqualTo(UnitType.WEIGHT);
        assertThat(UnitType.fromString("Temperature")).isEqualTo(UnitType.TEMPERATURE);
        assertThat(UnitType.fromString("Count")).isEqualTo(UnitType.COUNT);
    }

    @Test
    @DisplayName("fromString() should strip whitespace")
    void fromStringShouldStripWhitespace() {
        assertThat(UnitType.fromString("  volume  ")).isEqualTo(UnitType.VOLUME);
        assertThat(UnitType.fromString("\tweight\n")).isEqualTo(UnitType.WEIGHT);
        assertThat(UnitType.fromString(" temperature ")).isEqualTo(UnitType.TEMPERATURE);
    }

    @Test
    @DisplayName("fromString() should throw on null input")
    void fromStringShouldThrowOnNull() {
        assertThatThrownBy(() -> UnitType.fromString(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("cannot be null");
    }

    @Test
    @DisplayName("fromString() should throw on unknown type")
    void fromStringShouldThrowOnUnknownType() {
        assertThatThrownBy(() -> UnitType.fromString("invalid"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown unit type: invalid");
    }

    @Test
    @DisplayName("fromString() should throw on empty string")
    void fromStringShouldThrowOnEmptyString() {
        assertThatThrownBy(() -> UnitType.fromString(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown unit type");
    }
}
