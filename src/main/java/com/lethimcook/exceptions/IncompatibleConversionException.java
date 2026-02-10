package com.lethimcook.exceptions;

/**
 * Thrown when a conversion is attempted between incompatible unit categories
 * (e.g., volume to weight).
 * Maps to Python's {@code ValueError("Cannot convert between ...")} in converter.py.
 */
public class IncompatibleConversionException extends RuntimeException {

    public IncompatibleConversionException(String message) {
        super(message);
    }

    public IncompatibleConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
