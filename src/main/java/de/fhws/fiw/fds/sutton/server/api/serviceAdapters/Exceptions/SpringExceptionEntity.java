package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import java.time.LocalDateTime;

public class SpringExceptionEntity {

    private final String message;

    private final LocalDateTime timestamp;

    private final int statusCode;

    public SpringExceptionEntity(String message, LocalDateTime timestamp, int statusCode) {
        this.message = message;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
