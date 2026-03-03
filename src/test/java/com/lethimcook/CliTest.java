package com.lethimcook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the CLI skeleton ({@link Cli}).
 * <p>
 * In Milestone 1 the CLI is a skeleton: it prints usage when no arguments are
 * given and outputs a "not yet implemented" message when arguments are provided.
 * Full integration tests with actual conversion queries are deferred to
 * Milestone 2.
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
    // With-arguments tests (not yet implemented placeholder)
    // -----------------------------------------------------------------------

    @Test
    void singleArg_returnsExitCode1() {
        int exitCode = Cli.run(new String[]{"hello"}, out, err);
        assertThat(exitCode).isEqualTo(1);
    }

    @Test
    void singleArg_printsNotYetImplementedToStderr() {
        Cli.run(new String[]{"hello"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
    }

    @Test
    void singleArg_includesQueryInErrorMessage() {
        Cli.run(new String[]{"hello"}, out, err);
        assertThat(stderr()).contains("Query: hello");
    }

    @Test
    void singleArg_writesNothingToStdout() {
        Cli.run(new String[]{"hello"}, out, err);
        assertThat(stdout()).isEmpty();
    }

    @Test
    void multipleArgs_areJoinedIntoSingleQuery() {
        Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(stderr()).contains("Query: 2 cups to ml");
    }

    @Test
    void multipleArgs_returnsExitCode1() {
        int exitCode = Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(exitCode).isEqualTo(1);
    }

    @Test
    void multipleArgs_writesNothingToStdout() {
        Cli.run(new String[]{"convert", "1", "pound", "to", "grams"}, out, err);
        assertThat(stdout()).isEmpty();
    }

    @Test
    void conversionQuery_cupsToMl() {
        Cli.run(new String[]{"2", "cups", "to", "ml"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
        assertThat(stderr()).contains("Query: 2 cups to ml");
    }

    @Test
    void conversionQuery_poundsToGrams() {
        Cli.run(new String[]{"convert", "1", "pound", "to", "grams"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
        assertThat(stderr()).contains("Query: convert 1 pound to grams");
    }

    @Test
    void conversionQuery_temperatureQuery() {
        Cli.run(new String[]{"100", "fahrenheit", "to", "celsius"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
        assertThat(stderr()).contains("Query: 100 fahrenheit to celsius");
    }

    @Test
    void conversionQuery_howManyPattern() {
        Cli.run(new String[]{"how", "many", "ml", "in", "3", "teaspoons"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
        assertThat(stderr()).contains("Query: how many ml in 3 teaspoons");
    }

    @Test
    void singleWordArg_producesNotYetImplemented() {
        Cli.run(new String[]{"test"}, out, err);
        assertThat(stderr()).contains("not yet implemented");
        assertThat(stderr()).contains("Query: test");
    }

    @Test
    void argWithSpecialCharacters_isPassedThrough() {
        Cli.run(new String[]{"1.5", "cups"}, out, err);
        assertThat(stderr()).contains("Query: 1.5 cups");
    }

    @Test
    void emptyStringArg_treatedAsArgPresent() {
        // An empty-string argument still counts as an argument (args.length > 0),
        // so the CLI should not print usage but instead process the (blank) query.
        int exitCode = Cli.run(new String[]{""}, out, err);
        assertThat(exitCode).isEqualTo(1);
        assertThat(stdout()).isEmpty();
        assertThat(stderr()).contains("not yet implemented");
    }

    @Test
    void multipleEmptyArgs_joinedWithSpaces() {
        Cli.run(new String[]{"", ""}, out, err);
        assertThat(stderr()).contains("Query:  ");
    }

    @Test
    void longQuery_isFullyPreserved() {
        String[] args = {"this", "is", "a", "very", "long", "query", "with", "many", "words"};
        Cli.run(args, out, err);
        assertThat(stderr()).contains("Query: this is a very long query with many words");
    }
}
