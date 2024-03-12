package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import java.time.LocalDateTime;

/**
 * The {@link SpringExceptionEntity} class is used to map an exception that occurs within
 * a Sutton application to an entity that can be sent to the client. This entity provides
 * useful information about the issue that occurred, including a message, a timestamp, and
 * an HTTP status code.
 */
public class SpringExceptionEntity {

    private final String message;

    private final LocalDateTime timestamp;

    private final int statusCode;

    /**
     * Constructs a {@link SpringExceptionEntity} with the specified message, timestamp,
     * and HTTP status code.
     *
     * @param message    The message of the exception to be sent to the client.
     * @param timestamp  The date and time when the exception occurred.
     * @param statusCode The HTTP status code associated with the error.
     */
    public SpringExceptionEntity(String message, LocalDateTime timestamp, int statusCode) {
        this.message = message;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
    }

    /**
     * Retrieves the exception message.
     *
     * @return The message of the exception.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the timestamp when the exception occurred.
     *
     * @return The timestamp of the exception.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the HTTP status code associated with the exception.
     *
     * @return The HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }
}
