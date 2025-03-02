package com.shilov.common.exceptions;

public class ConsoleInputException extends Exception {

    public ConsoleInputException(String message) {
        super(message);
    }

    public ConsoleInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsoleInputException(Throwable cause) {
        super(cause);
    }
}
