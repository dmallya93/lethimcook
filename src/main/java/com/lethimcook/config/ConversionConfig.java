package com.lethimcook.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration management for the lethimcook library.
 *
 * This class loads configuration settings from a properties file on the classpath
 * (lethimcook.properties) and allows environment variable overrides.
 *
 * Configuration is loaded statically during class initialization, ensuring
 * settings are available immediately when needed. If the properties file is
 * missing, the class gracefully falls back to sensible defaults.
 *
 * Supported configuration options:
 * - precision: Number of decimal places for formatted conversion results (default: 2)
 *
 * Environment variable overrides:
 * - LETHIMCOOK_PRECISION: Overrides the precision setting
 */
public final class ConversionConfig {

    private static final Properties props = new Properties();
    private static final String PROPERTIES_FILE = "/lethimcook.properties";
    private static final String ENV_PRECISION = "LETHIMCOOK_PRECISION";

    // Default values
    private static final int DEFAULT_PRECISION = 2;

    static {
        // Load properties from classpath resource
        try (InputStream is = ConversionConfig.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (is != null) {
                props.load(is);
            }
            // If the file is missing, we'll just use defaults (no error)
        } catch (IOException e) {
            // Fallback to defaults silently - don't fail class initialization
            // In a production system, might log this at debug level
        }

        // Environment variable overrides take precedence over file settings
        String envPrecision = System.getenv(ENV_PRECISION);
        if (envPrecision != null && !envPrecision.trim().isEmpty()) {
            props.setProperty("precision", envPrecision.trim());
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ConversionConfig() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Gets the configured decimal precision for formatting conversion results.
     *
     * This value determines how many decimal places to show in formatted
     * conversion results (e.g., "2" means "473.18" instead of "473.176").
     *
     * The precision can be configured via:
     * 1. The lethimcook.properties file (precision=N)
     * 2. The LETHIMCOOK_PRECISION environment variable (overrides file)
     * 3. Default value of 2 if not configured
     *
     * @return the decimal precision (number of decimal places)
     */
    public static int getDecimalPrecision() {
        String precisionStr = props.getProperty("precision");
        if (precisionStr != null) {
            try {
                return Integer.parseInt(precisionStr);
            } catch (NumberFormatException e) {
                // Invalid value in config, fall back to default
                return DEFAULT_PRECISION;
            }
        }
        return DEFAULT_PRECISION;
    }
}
