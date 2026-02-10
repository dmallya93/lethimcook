package com.lethimcook.exceptions;

/**
 * Thrown when natural-language input cannot be parsed into a valid conversion request.
 * Maps to Python's {@code ValueError("Could not parse ...")} in natural.py.
 */
public class ParsingException extends RuntimeException {

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
