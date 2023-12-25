package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;

public class ForbiddenException extends SuttonWebAppException {

    public ForbiddenException(String exceptionMessage) {
        super(Status.FORBIDDEN, exceptionMessage);
    }

    public ForbiddenException(String message, String exceptionMessage) {
        super(message, Status.FORBIDDEN, exceptionMessage);
    }

    public ForbiddenException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause, Status.FORBIDDEN, exceptionMessage);
    }

    public ForbiddenException(Throwable cause, String exceptionMessage) {
        super(cause, Status.FORBIDDEN, exceptionMessage);
    }
}
