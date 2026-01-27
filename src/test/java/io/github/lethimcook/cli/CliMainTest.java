package io.github.lethimcook.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the CLI command-line interface.
 */
@DisplayName("CliMain")
class CliMainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Nested
    @DisplayName("Successful conversions")
    class SuccessfulConversions {

        @Test
        @DisplayName("should convert volume units")
        void shouldConvertVolumeUnits() {
            int exitCode = execute("2", "cups", "ml");

            assertThat(exitCode).isEqualTo(0);
            assertThat(outContent.toString().trim())
                .matches("\\d+\\.\\d+")
                .satisfies(output -> {
                    double result = Double.parseDouble(output.trim());
                    assertThat(result).isCloseTo(473.176, org.assertj.core.data.Offset.offset(0.01));
                });
            assertThat(errContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should convert weight units")
        void shouldConvertWeightUnits() {
            int exitCode = execute("1", "pound", "grams");

            assertThat(exitCode).isEqualTo(0);
            double result = Double.parseDouble(outContent.toString().trim());
            assertThat(result).isCloseTo(453.592, org.assertj.core.data.Offset.offset(0.01));
            assertThat(errContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should convert temperature units")
        void shouldConvertTemperatureUnits() {
            int exitCode = execute("100", "celsius", "fahrenheit");

            assertThat(exitCode).isEqualTo(0);
            double result = Double.parseDouble(outContent.toString().trim());
            assertThat(result).isCloseTo(212.0, org.assertj.core.data.Offset.offset(0.01));
            assertThat(errContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should convert with decimal values")
        void shouldConvertWithDecimals() {
            int exitCode = execute("2.5", "cups", "ml");

            assertThat(exitCode).isEqualTo(0);
            double result = Double.parseDouble(outContent.toString().trim());
            assertThat(result).isCloseTo(591.47, org.assertj.core.data.Offset.offset(0.01));
            assertThat(errContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should handle unit aliases")
        void shouldHandleUnitAliases() {
            int exitCode = execute("1", "tsp", "ml");

            assertThat(exitCode).isEqualTo(0);
            double result = Double.parseDouble(outContent.toString().trim());
            assertThat(result).isCloseTo(4.92892, org.assertj.core.data.Offset.offset(0.01));
            assertThat(errContent.toString()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Error handling")
    class ErrorHandling {

        @Test
        @DisplayName("should handle no arguments")
        void shouldHandleNoArguments() {
            int exitCode = execute();

            assertThat(exitCode).isEqualTo(1);
            assertThat(outContent.toString())
                .contains("LetHimCook - Unit Conversion Library")
                .contains("Usage:")
                .contains("Examples:");
            assertThat(errContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should handle insufficient arguments")
        void shouldHandleInsufficientArguments() {
            int exitCode = execute("2", "cups");

            assertThat(exitCode).isEqualTo(1);
            assertThat(errContent.toString())
                .contains("Error:")
                .contains("Invalid format");
            assertThat(outContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should handle invalid number format")
        void shouldHandleInvalidNumberFormat() {
            int exitCode = execute("abc", "cups", "ml");

            assertThat(exitCode).isEqualTo(1);
            assertThat(errContent.toString())
                .contains("Error:")
                .contains("Invalid number");
            assertThat(outContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should handle unknown units")
        void shouldHandleUnknownUnits() {
            int exitCode = execute("2", "foobar", "ml");

            assertThat(exitCode).isEqualTo(1);
            assertThat(errContent.toString())
                .contains("Error:")
                .contains("Unknown unit");
            assertThat(outContent.toString()).isEmpty();
        }

        @Test
        @DisplayName("should handle incompatible unit types")
        void shouldHandleIncompatibleUnitTypes() {
            int exitCode = execute("2", "cups", "grams");

            assertThat(exitCode).isEqualTo(1);
            assertThat(errContent.toString())
                .contains("Error:")
                .contains("Cannot convert");
            assertThat(outContent.toString()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Help and version")
    class HelpAndVersion {

        @Test
        @DisplayName("should display help with --help")
        void shouldDisplayHelpWithLongOption() {
            int exitCode = execute("--help");

            assertThat(exitCode).isEqualTo(0);
            assertThat(outContent.toString())
                .contains("Usage:")
                .contains("lethimcook")
                .contains("Conversion query");
        }

        @Test
        @DisplayName("should display help with -h")
        void shouldDisplayHelpWithShortOption() {
            int exitCode = execute("-h");

            assertThat(exitCode).isEqualTo(0);
            assertThat(outContent.toString())
                .contains("Usage:")
                .contains("lethimcook");
        }

        @Test
        @DisplayName("should display version with --version")
        void shouldDisplayVersionWithLongOption() {
            int exitCode = execute("--version");

            assertThat(exitCode).isEqualTo(0);
            assertThat(outContent.toString())
                .contains("lethimcook")
                .contains("1.0.0");
        }

        @Test
        @DisplayName("should display version with -V")
        void shouldDisplayVersionWithShortOption() {
            int exitCode = execute("-V");

            assertThat(exitCode).isEqualTo(0);
            assertThat(outContent.toString())
                .contains("lethimcook")
                .contains("1.0.0");
        }
    }

    @Nested
    @DisplayName("Multi-word units")
    class MultiWordUnits {

        @Test
        @DisplayName("should handle multi-word from unit")
        void shouldHandleMultiWordFromUnit() {
            // Test with extra arguments - simplified parser treats middle args as fromUnit
            int exitCode = execute("1", "fl", "oz", "ml");

            assertThat(exitCode).isEqualTo(0);
            double result = Double.parseDouble(outContent.toString().trim());
            // "fl oz" should convert properly
            assertThat(result).isGreaterThan(0);
            assertThat(errContent.toString()).isEmpty();
        }
    }

    /**
     * Helper method to execute CLI command with given arguments.
     *
     * @param args command-line arguments
     * @return exit code
     */
    private int execute(String... args) {
        return new CommandLine(new CliMain()).execute(args);
    }
}
