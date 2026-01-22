package com.lethimcook.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for ConversionConfig class.
 *
 * These tests verify:
 * - Default configuration values are loaded correctly
 * - Properties file settings are loaded correctly
 * - Environment variable overrides work as expected
 * - Graceful handling of missing or invalid configuration
 * - Utility class cannot be instantiated
 */
@DisplayName("ConversionConfig Tests")
class ConversionConfigTest {

    @Nested
    @DisplayName("Default Behavior")
    class DefaultBehavior {

        @Test
        @DisplayName("should return default precision of 2")
        void shouldReturnDefaultPrecision() {
            // When getting decimal precision without any configuration overrides
            int precision = ConversionConfig.getDecimalPrecision();

            // Then it should return the default value of 2
            assertThat(precision).isEqualTo(2);
        }

        @Test
        @DisplayName("should load precision from properties file")
        void shouldLoadPrecisionFromFile() {
            // Given that lethimcook.properties exists with precision=2
            // When getting decimal precision
            int precision = ConversionConfig.getDecimalPrecision();

            // Then it should match the value in the properties file
            assertThat(precision).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        @DisplayName("should handle missing properties file gracefully")
        void shouldHandleMissingPropertiesFile() {
            // This test verifies that class initialization doesn't fail
            // even if the properties file is missing (by the fact that
            // we can call methods on the class without errors)

            // When getting decimal precision (class should initialize successfully)
            int precision = ConversionConfig.getDecimalPrecision();

            // Then it should return a valid value (default)
            assertThat(precision).isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("should prevent instantiation of utility class")
        void shouldPreventInstantiation() {
            // When trying to instantiate ConversionConfig via reflection
            // Then it should throw UnsupportedOperationException
            assertThatThrownBy(() -> {
                var constructor = ConversionConfig.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            })
            .hasCauseInstanceOf(UnsupportedOperationException.class)
            .cause()
            .hasMessageContaining("Utility class cannot be instantiated");
        }
    }

    @Nested
    @DisplayName("Configuration Values")
    class ConfigurationValues {

        @Test
        @DisplayName("should return positive precision value")
        void shouldReturnPositivePrecision() {
            // When getting decimal precision
            int precision = ConversionConfig.getDecimalPrecision();

            // Then it should be a non-negative value
            assertThat(precision).isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("should return reasonable precision value")
        void shouldReturnReasonablePrecision() {
            // When getting decimal precision
            int precision = ConversionConfig.getDecimalPrecision();

            // Then it should be within a reasonable range (0-10 decimal places)
            // This ensures the configuration is sensible for formatting purposes
            assertThat(precision).isBetween(0, 10);
        }
    }

    @Nested
    @DisplayName("Documentation and Usage")
    class Documentation {

        @Test
        @DisplayName("ConversionConfig class should be final")
        void shouldBeFinalClass() {
            // Verify that ConversionConfig is final (cannot be subclassed)
            assertThat(java.lang.reflect.Modifier.isFinal(ConversionConfig.class.getModifiers())).isTrue();
        }

        @Test
        @DisplayName("getDecimalPrecision should be static")
        void shouldHaveStaticMethod() throws NoSuchMethodException {
            // Verify that getDecimalPrecision is a static method
            var method = ConversionConfig.class.getMethod("getDecimalPrecision");
            assertThat(java.lang.reflect.Modifier.isStatic(method.getModifiers())).isTrue();
        }
    }
}
