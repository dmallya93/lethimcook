package io.github.lethimcook.cli;

import io.github.lethimcook.converter.Converter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

/**
 * Command-line interface for the lethimcook library.
 * Provides unit conversion functionality via command-line arguments.
 */
@Command(
    name = "lethimcook",
    description = "LetHimCook - Unit Conversion Library",
    mixinStandardHelpOptions = true,
    version = "lethimcook Java 1.0.0"
)
public class CliMain implements Callable<Integer> {

    @Parameters(
        index = "0..*",
        description = "Conversion query in format: <value> <from_unit> <to_unit> (e.g., '2 cups ml')",
        arity = "0..*"
    )
    private String[] queryParts;

    @Override
    public Integer call() {
        if (queryParts == null || queryParts.length == 0) {
            // Display usage information similar to Python CLI
            System.out.println("LetHimCook - Unit Conversion Library");
            System.out.println();
            System.out.println("Usage:");
            System.out.println("  lethimcook <value> <from_unit> <to_unit>");
            System.out.println();
            System.out.println("Examples:");
            System.out.println("  lethimcook 2 cups ml");
            System.out.println("  lethimcook 1 pound grams");
            System.out.println("  lethimcook 100 celsius fahrenheit");
            System.out.println();
            System.out.println("Supported units:");
            System.out.println("  Volume: tsp, tbsp, fl oz, cup, pint, quart, gallon, ml, liter");
            System.out.println("  Weight: oz, pound, gram, kilogram");
            System.out.println("  Temperature: fahrenheit, celsius, kelvin");
            return 1;
        }

        try {
            // Parse the query using simplified parser
            ConversionQuery query = parseSimplifiedQuery(queryParts);

            // Perform the conversion
            double result = Converter.convert(query.value(), query.fromUnit(), query.toUnit());

            // Print the result
            System.out.println(result);
            return 0;

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Parses a simplified conversion query from command-line arguments.
     * Expected format: <value> <from_unit> <to_unit>
     *
     * @param parts the command-line arguments
     * @return the parsed conversion query
     * @throws IllegalArgumentException if the format is invalid
     */
    private ConversionQuery parseSimplifiedQuery(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException(
                "Invalid format. Expected: <value> <from_unit> <to_unit>. Example: 2 cups ml"
            );
        }

        // Parse the numeric value
        double value;
        try {
            value = Double.parseDouble(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Invalid number: " + parts[0] + ". First argument must be a numeric value."
            );
        }

        // Extract from and to units
        // For multi-word units, we'll join the remaining parts intelligently
        // For this simplified version, we expect single-word units
        String fromUnit = parts[1];
        String toUnit = parts[2];

        // If there are more than 3 parts, join them as multi-word units
        // fromUnit gets parts[1] to parts[parts.length/2]
        // toUnit gets parts[parts.length/2 + 1] to parts[parts.length-1]
        if (parts.length > 3) {
            // For simplicity in Milestone 1, we'll join parts 1 to n-1 as fromUnit
            // and use the last part as toUnit
            StringBuilder fromBuilder = new StringBuilder(parts[1]);
            for (int i = 2; i < parts.length - 1; i++) {
                fromBuilder.append(" ").append(parts[i]);
            }
            fromUnit = fromBuilder.toString();
            toUnit = parts[parts.length - 1];
        }

        return new ConversionQuery(value, fromUnit, toUnit);
    }

    /**
     * Record representing a parsed conversion query.
     */
    private record ConversionQuery(double value, String fromUnit, String toUnit) {}
}
