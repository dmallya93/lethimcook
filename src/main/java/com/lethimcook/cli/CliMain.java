package com.lethimcook.cli;

import com.lethimcook.natural.NaturalLanguageConverter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

/**
 * Command-line interface for the lethimcook library.
 * <p>
 * This CLI accepts natural language conversion queries and delegates parsing
 * and conversion to the NaturalLanguageConverter.
 * </p>
 * <p>
 * Usage examples:
 * <pre>
 *   java -jar lethimcook-cli.jar "2 cups to ml"
 *   java -jar lethimcook-cli.jar "convert 1 pound to grams"
 *   java -jar lethimcook-cli.jar "how many ml in 3 teaspoons"
 * </pre>
 * </p>
 */
@Command(
    name = "lethimcook",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    description = "Unit Conversion Library - Natural language unit conversions"
)
public class CliMain implements Callable<Integer> {

    @Parameters(
        arity = "1..*",
        description = "Conversion query (e.g., '2 cups to ml', 'convert 1 pound to grams', 'how many ml in 3 teaspoons')"
    )
    private String[] query;

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
        // When no query is provided, Picocli will show usage automatically
        // due to arity = "1..*" requirement

        // Join all query words with spaces
        String queryText = String.join(" ", query);

        try {
            // Delegate to NaturalLanguageConverter for parsing and conversion
            String result = NaturalLanguageConverter.convertNatural(queryText);
            System.out.println(result);
            return 0;
        } catch (IllegalArgumentException e) {
            // Print error to stderr and return non-zero exit code
            // Matching Python CLI format: "Error: {message}"
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
    }
}
