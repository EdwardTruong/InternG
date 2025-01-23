package com.example.jwt.infrastructure.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User not found !";

    /**
     * Constructs a new {@link UserNotFoundException} with the default message.
     */
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new {@link ClinicNotFoundException} with the default message and
     * an additional message.
     *
     * @param message the additional message to include.
     */
    public UserNotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }
}
