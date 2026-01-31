package com.lethimcook.converter;

/**
 * Handles temperature conversions between Celsius, Fahrenheit, and Kelvin.
 */
public class TemperatureConverter {

    /**
     * Convert temperature between different scales.
     *
     * @param value    The temperature value to convert
     * @param fromUnit The source temperature unit (normalized)
     * @param toUnit   The target temperature unit (normalized)
     * @return The converted temperature value
     * @throws IllegalArgumentException if units are invalid
     */
    public static double convert(double value, String fromUnit, String toUnit) {
        // First convert to Celsius
        double celsius = toCelsius(value, fromUnit);

        // Then convert from Celsius to target
        return fromCelsius(celsius, toUnit);
    }

    private static double toCelsius(double value, String fromUnit) {
        return switch (fromUnit) {
            case "celsius", "c" -> value;
            case "fahrenheit", "f" -> (value - 32) * 5.0 / 9.0;
            case "kelvin", "k" -> value - 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        };
    }

    private static double fromCelsius(double celsius, String toUnit) {
        return switch (toUnit) {
            case "celsius", "c" -> celsius;
            case "fahrenheit", "f" -> celsius * 9.0 / 5.0 + 32;
            case "kelvin", "k" -> celsius + 273.15;
            default -> throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        };
    }
}
