package com.shilov.common.exceptions;

public class SpaceReservationException extends Exception {

    public SpaceReservationException(String message) {
        super(message);
    }

    public SpaceReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpaceReservationException(Throwable cause) {
        super(cause);
    }
}
