package dev.igor.apiaccount.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        this("User not found", null);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
