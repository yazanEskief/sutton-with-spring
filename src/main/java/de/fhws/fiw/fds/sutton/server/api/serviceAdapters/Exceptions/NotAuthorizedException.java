package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

public class NotAuthorizedException extends SuttonWebAppException {
    public NotAuthorizedException(String exceptionMessage) {
        super(Status.UNAUTHORIZED, exceptionMessage);
    }

    public NotAuthorizedException(String message, String exceptionMessage) {
        super(message, Status.UNAUTHORIZED, exceptionMessage);
    }

    public NotAuthorizedException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause, Status.UNAUTHORIZED, exceptionMessage);
    }

    public NotAuthorizedException(Throwable cause, String exceptionMessage) {
        super(cause, Status.UNAUTHORIZED, exceptionMessage);
    }
}
