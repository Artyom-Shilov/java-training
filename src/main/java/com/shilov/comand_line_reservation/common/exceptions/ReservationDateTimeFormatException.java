package com.shilov.common.exceptions;

public class ReservationDateTimeFormatException extends Exception {

    public ReservationDateTimeFormatException(String message) {
        super(message);
    }

    public ReservationDateTimeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationDateTimeFormatException(Throwable cause) {
        super(cause);
    }
}
