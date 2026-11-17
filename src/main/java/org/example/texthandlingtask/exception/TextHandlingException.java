package org.example.texthandlingtask.exception;

public class TextHandlingException extends Exception {
    public TextHandlingException(String message) {
        super(message);
    }

    public TextHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
