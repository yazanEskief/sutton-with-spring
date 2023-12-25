package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

public class SuttonWebAppException extends RuntimeException {

    private final Status status;

    private final String exceptionMessage;

    public SuttonWebAppException(Status status, String exceptionMessage) {
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    public SuttonWebAppException(String message, Status status, String exceptionMessage) {
        super(message);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    public SuttonWebAppException(String message, Throwable cause, Status status, String exceptionMessage) {
        super(message, cause);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    public SuttonWebAppException(Throwable cause, Status status, String exceptionMessage) {
        super(cause);
        this.status = status;
        this.exceptionMessage = exceptionMessage;
    }

    public Status getStatus() {
        return status;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
