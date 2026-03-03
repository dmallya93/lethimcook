package com.lethimcook;

import java.io.PrintStream;

/**
 * Command-line interface for the lethimcook library.
 * <p>
 * Mirrors the Python {@code cli.py} behaviour: when no arguments are provided,
 * prints usage information and exits with code 1. When arguments are provided,
 * they are joined into a single query string and passed to
 * {@link NaturalConverter#convertNatural(String)}, printing the result to stdout.
 */
public final class Cli {

    private Cli() {
        // utility class
    }

    /**
     * CLI entry point that delegates to {@link #run(String[], PrintStream, PrintStream)}
     * with the standard system streams and terminates the JVM with the returned exit code.
     * <p>
     * If no arguments are supplied, a usage banner listing supported units is printed to
     * stdout and the process exits with code 1. When arguments are present, they are
     * joined into a single query string and forwarded to the natural language converter
     * (integration deferred to Milestone 2), printing the result to stdout on success
     * or an error message to stderr on failure.
     *
     * @param args command-line arguments forming a natural-language conversion query
     *             (e.g. {@code "2", "cups", "to", "ml"})
     */
    public static void main(String[] args) {
        final int exitCode = run(args, System.out, System.err);
        System.exit(exitCode);
    }

    /**
     * Executes the CLI logic, writing output to the provided streams.
     * <p>
     * This method encapsulates all CLI behaviour so that it can be tested without
     * triggering {@link System#exit(int)}. The caller supplies the output and error
     * streams, allowing tests to capture printed text.
     * <p>
     * Behaviour:
     * <ul>
     *   <li>If {@code args} is empty, prints a usage banner (application name, example
     *       invocations, and supported unit categories) to {@code out} and returns 1.</li>
     *   <li>Otherwise, joins all arguments with spaces into a single query string and
     *       passes it to {@link NaturalConverter#convertNatural(String)}. On success
     *       the converted result is printed to {@code out} and 0 is returned. On failure
     *       the error message is printed to {@code err} and 1 is returned.</li>
     * </ul>
     *
     * @param args command-line arguments representing a conversion query; may be empty
     * @param out  the stream to write normal output (usage banner, conversion results)
     * @param err  the stream to write error messages (conversion failures, missing input)
     * @return 0 on successful conversion, 1 on error or when printing usage information
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

        final String query = String.join(" ", args);

        try {
            final String result = NaturalConverter.convertNatural(query);
            out.println(result);
            return 0;
        } catch (Exception e) {
            err.println("Error: " + e.getMessage());
            return 1;
        }
    }
}
