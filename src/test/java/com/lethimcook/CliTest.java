package com.lethimcook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the CLI ({@link Cli}).
 * <p>
 * When no arguments are provided, the CLI prints usage information and exits
 * with code 1. When arguments are provided, they are joined into a query
 * string and passed to {@link NaturalConverter#convertNatural(String)}.
 * Valid queries produce output on stdout (exit code 0); invalid queries
 * produce error messages on stderr (exit code 1).
 */
class CliTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream out;
    private PrintStream err;

    @BeforeEach
    void setUp() {
        outStream = new ByteArrayOutputStream();
        errStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        err = new PrintStream(errStream);
    }

    private String stdout() {
        return outStream.toString();
    }

    private String stderr() {
        return errStream.toString();
    }

    // -----------------------------------------------------------------------
    // No-argument tests (usage information)
    // -----------------------------------------------------------------------

    @Test
    void noArgs_returnsExitCode1() {
        int exitCode = Cli.run(new String[]{}, out, err);
        assertThat(exitCode).isEqualTo(1);
    }

    @Test
    void noArgs_printsTitle() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("LetHimCook - Unit Conversion Library");
    }

    @Test
    void noArgs_printsUsageSection() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("Usage:");
    }

    @Test
    void noArgs_printsUsageExampleCups() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("2 cups to ml");
    }

    @Test
    void noArgs_printsUsageExamplePounds() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("convert 1 pound to grams");
    }

    @Test
    void noArgs_printsUsageExampleTeaspoons() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("how many ml in 3 teaspoons");
    }

    @Test
    void noArgs_printsSupportedUnitsHeader() {
        Cli.run(new String[]{}, out, err);
        assertThat(stdout()).contains("Supported units:");
    }

    @Test
    void noArgs_printsVolumeUnits() {
        Cli.run(new String[]{}, out, err);
        String output = stdout();
        assertThat(output).contains("Volume:");
        assertThat(output).contains("tsp");
        assertThat(output).contains("tbsp");
        assertThat(output).contains("cup");
        assertThat(output).contains("pint");
        assertThat(output).contains("quart");
        assertThat(output).contains("gallon");
        assertThat(output).contains("ml");
        assertThat(output).contains("liter");
    }

    @Test
    void noArgs_printsWeightUnits() {
        Cli.run(new String[]{}, out, err);
        String output = stdout();
        assertThat(output).contains("Weight:");
        assertThat(output).contains("oz");
        assertThat(output).contains("pound");
        assertThat(output).contains("gram");
        assertThat(output).contains("kilogram");
    }

    @Test
    void noArgs_printsTemperatureUnits() {
        Cli.run(new String[]{}, out, err);
        String output = stdout();
        assertThat(output).contains("Temperature:");
        assertThat(output).contains("fahrenheit");
        assertThat(output).contains("celsius");
        assertThat(output).contains("kelvin");
    }

    @Test
    void noArgs_writesNothingToStderr() {
        Cli.run(new String[]{}, out, err);
        assertThat(stderr()).isEmpty();
    }

    @Test
    void noArgs_outputMatchesPythonStructure() {
        Cli.run(new String[]{}, out, err);
        String output = stdout();
        // Verify the output follows the same logical structure as the Python CLI:
        // title, then usage section, then supported units
        int titleIdx = output.indexOf("LetHimCook");
        int usageIdx = output.indexOf("Usage:");
        int supportedIdx = output.indexOf("Supported units:");
        assertThat(titleIdx).isGreaterThanOrEqualTo(0);
        assertThat(usageIdx).isGreaterThan(titleIdx);
        assertThat(supportedIdx).isGreaterThan(usageIdx);
    }

    // -----------------------------------------------------------------------
    // With-arguments tests: successful conversions
    // -----------------------------------------------------------------------

    @Test
    void conversionQuery_cupsToMl_returnsExitCode0() {
        final int exitCode = Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(exitCode).isEqualTo(0);
    }

    @Test
    void conversionQuery_cupsToMl_printsResultToStdout() {
        Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(stdout()).contains("2 cups");
        assertThat(stdout()).contains("473");
        assertThat(stdout()).contains("ml");
    }

    @Test
    void conversionQuery_cupsToMl_writesNothingToStderr() {
        Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(stderr()).isEmpty();
    }

    @Test
    void conversionQuery_poundsToGrams() {
        final int exitCode = Cli.run(new String[]{"convert", "1", "pound", "to", "grams"}, out, err);
        assertThat(exitCode).isEqualTo(0);
        assertThat(stdout()).contains("1 pound");
        assertThat(stdout()).contains("453");
        assertThat(stdout()).contains("grams");
        assertThat(stderr()).isEmpty();
    }

    @Test
    void conversionQuery_temperatureQuery() {
        final int exitCode = Cli.run(new String[]{"100", "fahrenheit", "to", "celsius"}, out, err);
        assertThat(exitCode).isEqualTo(0);
        assertThat(stdout()).contains("100 fahrenheit");
        assertThat(stdout()).contains("celsius");
        assertThat(stderr()).isEmpty();
    }

    @Test
    void conversionQuery_howManyPattern() {
        final int exitCode = Cli.run(new String[]{"how", "many", "ml", "in", "3", "teaspoons"}, out, err);
        assertThat(exitCode).isEqualTo(0);
        assertThat(stdout()).contains("3 teaspoons");
        assertThat(stdout()).contains("ml");
        assertThat(stderr()).isEmpty();
    }

    @Test
    void multipleArgs_areJoinedIntoSingleQuery() {
        // "2 cups to ml" should be joined and produce a valid conversion
        final int exitCode = Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(exitCode).isEqualTo(0);
        assertThat(stdout()).contains("2 cups");
    }

    // -----------------------------------------------------------------------
    // With-arguments tests: error cases
    // -----------------------------------------------------------------------

    @Test
    void invalidQuery_returnsExitCode1() {
        final int exitCode = Cli.run(new String[]{"hello"}, out, err);
        assertThat(exitCode).isEqualTo(1);
    }

    @Test
    void invalidQuery_printsErrorToStderr() {
        Cli.run(new String[]{"hello"}, out, err);
        assertThat(stderr()).contains("Error:");
    }

    @Test
    void invalidQuery_writesNothingToStdout() {
        Cli.run(new String[]{"hello"}, out, err);
        assertThat(stdout()).isEmpty();
    }

    @Test
    void singleWordArg_producesError() {
        final int exitCode = Cli.run(new String[]{"test"}, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stderr()).contains("Error:");
        assertThat(stdout()).isEmpty();
    }

    @Test
    void invalidUnit_producesError() {
        final int exitCode = Cli.run(new String[]{"2", "blorg", "to", "ml"}, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stderr()).contains("Error:");
        assertThat(stdout()).isEmpty();
    }

    @Test
    void emptyStringArg_treatedAsArgPresent() {
        // An empty-string argument still counts as an argument (args.length > 0),
        // so the CLI should not print usage but instead attempt to process the query.
        final int exitCode = Cli.run(new String[]{""}, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stdout()).isEmpty();
    }

    @Test
    void incompleteQuery_producesError() {
        // "1.5 cups" has no target unit, so it cannot be parsed
        final int exitCode = Cli.run(new String[]{"1.5", "cups"}, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stderr()).contains("Error:");
    }

    @Test
    void longGibberishQuery_producesError() {
        final String[] args = {"this", "is", "a", "very", "long", "query", "with", "many", "words"};
        final int exitCode = Cli.run(args, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stderr()).contains("Error:");
        assertThat(stdout()).isEmpty();
    }
}
