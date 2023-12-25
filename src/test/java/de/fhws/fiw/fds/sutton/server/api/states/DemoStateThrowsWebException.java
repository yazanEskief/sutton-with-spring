package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.Status;
import org.springframework.http.ResponseEntity;

public class DemoStateThrowsWebException extends AbstractState<ResponseEntity<Void>, Void> {
    public DemoStateThrowsWebException() {
        super(new AbstractStateBuilder<>() {
            @Override
            public AbstractState<ResponseEntity<Void>, Void> build() {
                return null;
            }
        });
    }

    @Override
    protected ResponseEntity<Void> buildInternal() {
        throw new SuttonWebAppException(Status.INTERNAL_SERVER_ERROR, "test exception");
    }
}
