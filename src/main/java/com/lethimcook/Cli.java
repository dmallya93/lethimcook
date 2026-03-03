package com.lethimcook;

import java.io.PrintStream;

/**
 * Command-line interface for the lethimcook library.
 * <p>
 * Mirrors the Python {@code cli.py} behaviour: when no arguments are provided,
 * prints usage information and exits with code 1. When arguments are provided,
 * they are joined into a single query string and passed to the natural language
 * converter (to be integrated in a later milestone).
 */
public final class Cli {

    private Cli() {
        // utility class
    }

    /**
     * CLI entry point.
     *
     * @param args command-line arguments forming a natural-language conversion query
     */
    public static void main(String[] args) {
        int exitCode = run(args, System.out, System.err);
        System.exit(exitCode);
    }

    /**
     * Executes the CLI logic, writing output to the provided streams.
     *
     * @param args command-line arguments
     * @param out  standard output stream
     * @param err  standard error stream
     * @return exit code (0 for success, 1 for error/usage)
     */
    static int run(String[] args, PrintStream out, PrintStream err) {
        if (args.length == 0) {
            out.println("LetHimCook - Unit Conversion Library");
            out.println();
            out.println("Usage:");
            out.println("  lethimcook '2 cups to ml'");
            out.println("  lethimcook 'convert 1 pound to grams'");
            out.println("  lethimcook 'how many ml in 3 teaspoons'");
            out.println();
            out.println("Supported units:");
            out.println("  Volume: tsp, tbsp, fl oz, cup, pint, quart, gallon, ml, liter");
            out.println("  Weight: oz, pound, gram, kilogram");
            out.println("  Temperature: fahrenheit, celsius, kelvin");
            return 1;
        }

        String query = String.join(" ", args);

        try {
            // NaturalConverter integration deferred to Milestone 2.
            // For now, indicate that natural language parsing is not yet available.
            err.println("Natural language conversion not yet implemented. Query: " + query);
            return 1;
        } catch (Exception e) {
            err.println("Error: " + e.getMessage());
            return 1;
        }
    }
}
