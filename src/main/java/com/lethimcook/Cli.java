package com.lethimcook;

/**
 * Command-line interface for the lethimcook library.
 * Migrated from cli.py.
 */
public final class Cli {

    private Cli() {}

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

        // Natural language parsing will be implemented in Milestone 2.
        // For now, print usage and exit.
        System.out.println("Natural language conversion not yet implemented.");
        System.out.println("Run without arguments to see usage information.");
        System.exit(1);
    }
}
