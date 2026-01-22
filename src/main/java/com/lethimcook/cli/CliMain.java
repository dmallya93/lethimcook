package com.lethimcook.cli;

import com.lethimcook.core.Converter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

/**
 * Command-line interface for the lethimcook library.
 * <p>
 * For Milestone 1, this CLI accepts programmatic API-style invocations rather than
 * natural language queries. Natural language parsing will be integrated in Milestone 2.
 * </p>
 * <p>
 * Usage examples:
 * <pre>
 *   java -jar lethimcook-cli.jar convert 2 cups ml
 *   java -jar lethimcook-cli.jar convert 32 fahrenheit celsius
 *   java -jar lethimcook-cli.jar convert 16 oz grams
 * </pre>
 * </p>
 */
@Command(
    name = "lethimcook",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    description = "Unit Conversion Library - Convert between volume, weight, and temperature units",
    subcommands = {CliMain.ConvertCommand.class}
)
public class CliMain implements Callable<Integer> {

    /**
     * Main entry point for the CLI.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliMain()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        // When no subcommand is provided, show usage information
        System.out.println("LetHimCook - Unit Conversion Library");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -jar lethimcook-cli.jar convert <value> <fromUnit> <toUnit>");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar lethimcook-cli.jar convert 2 cups ml");
        System.out.println("  java -jar lethimcook-cli.jar convert 32 fahrenheit celsius");
        System.out.println("  java -jar lethimcook-cli.jar convert 16 oz grams");
        System.out.println();
        System.out.println("Supported units:");
        System.out.println("  Volume: tsp, tbsp, fl oz, cup, pint, quart, gallon, ml, liter");
        System.out.println("  Weight: oz, pound, gram, kilogram");
        System.out.println("  Temperature: fahrenheit, celsius, kelvin");
        System.out.println();
        System.out.println("Use --help for more information.");
        return 0;
    }

    /**
     * Subcommand for performing unit conversions.
     */
    @Command(
        name = "convert",
        description = "Convert a value from one unit to another"
    )
    static class ConvertCommand implements Callable<Integer> {

        @Parameters(
            index = "0",
            description = "The numeric value to convert"
        )
        private double value;

        @Parameters(
            index = "1",
            description = "The source unit (e.g., cups, grams, fahrenheit)"
        )
        private String fromUnit;

        @Parameters(
            index = "2",
            description = "The target unit (e.g., ml, oz, celsius)"
        )
        private String toUnit;

        @Override
        public Integer call() {
            try {
                double result = Converter.convert(value, fromUnit, toUnit);

                // Format output with 2 decimal places (matching Python default)
                // Remove trailing zeros and decimal point if not needed
                String formatted = String.format("%.2f", result);
                formatted = formatted.replaceAll("\\.?0+$", "");

                System.out.println(formatted);
                return 0;
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                return 1;
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                return 1;
            }
        }
    }
}
