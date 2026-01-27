package io.github.lethimcook.cli;

import picocli.CommandLine;

/**
 * Main entry point for the lethimcook CLI application.
 * This class bootstraps the Picocli command and handles the exit code.
 */
public final class Main {

    private Main() {
        // Utility class - prevent instantiation
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Main method that creates and executes the CLI command.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliMain()).execute(args);
        System.exit(exitCode);
    }
}
