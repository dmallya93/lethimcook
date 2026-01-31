package com.lethimcook.cli;

import com.lethimcook.natural.NaturalLanguageConverter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Shell commands for unit conversion.
 */
@ShellComponent
public class ConversionCommands {

    private final NaturalLanguageConverter naturalConverter;

    public ConversionCommands() {
        this.naturalConverter = new NaturalLanguageConverter();
    }

    /**
     * Convert units using natural language input.
     * <p>
     * Examples:
     * - convert "2 cups to ml"
     * - convert "1.5 pounds to grams"
     * - convert "how many ml in 3 teaspoons"
     *
     * @param query The natural language conversion query
     * @return The conversion result
     */
    @ShellMethod(value = "Convert units using natural language", key = "convert")
    public String convert(@ShellOption(help = "Conversion query like '2 cups to ml'") String query) {
        try {
            return naturalConverter.convertNatural(query);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
