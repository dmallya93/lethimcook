package com.lethimcook.exceptions;

/**
 * Thrown when an unknown or unrecognized unit string is encountered.
 * Maps to Python's {@code ValueError("Unknown unit: ...")} in units.py.
 */
public class InvalidUnitException extends RuntimeException {

    public InvalidUnitException(String message) {
        super(message);
    }

    public InvalidUnitException(String message, Throwable cause) {
        super(message, cause);
    }
}
