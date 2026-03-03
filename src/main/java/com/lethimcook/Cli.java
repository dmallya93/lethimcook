package com.lethimcook;

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
        if (args.length == 0) {
            System.out.println("LetHimCook - Unit Conversion Library");
            System.out.println();
            System.out.println("Usage:");
            System.out.println("  lethimcook '2 cups to ml'");
            System.out.println("  lethimcook 'convert 1 pound to grams'");
            System.out.println("  lethimcook 'how many ml in 3 teaspoons'");
            System.out.println();
            System.out.println("Supported units:");
            System.out.println("  Volume: tsp, tbsp, fl oz, cup, pint, quart, gallon, ml, liter");
            System.out.println("  Weight: oz, pound, gram, kilogram");
            System.out.println("  Temperature: fahrenheit, celsius, kelvin");
            System.exit(1);
        }

        String query = String.join(" ", args);

        try {
            // NaturalConverter integration deferred to Milestone 2.
            // For now, indicate that natural language parsing is not yet available.
            System.err.println("Natural language conversion not yet implemented. Query: " + query);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
