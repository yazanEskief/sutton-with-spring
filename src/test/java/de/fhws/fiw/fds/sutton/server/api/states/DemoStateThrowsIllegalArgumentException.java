package de.fhws.fiw.fds.sutton.server.api.states;

import org.springframework.http.ResponseEntity;

public class DemoStateThrowsIllegalArgumentException extends AbstractState<ResponseEntity<Void>, Void> {
    public DemoStateThrowsIllegalArgumentException() {
        super(new AbstractStateBuilder<>() {
            @Override
            public AbstractState<ResponseEntity<Void>, Void> build() {
                return null;
            }
        });
    }

    @Override
    protected ResponseEntity<Void> buildInternal() {
        throw new IllegalArgumentException();
    }
}
