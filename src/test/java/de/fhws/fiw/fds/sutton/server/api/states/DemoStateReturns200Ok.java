package de.fhws.fiw.fds.sutton.server.api.states;

import org.springframework.http.ResponseEntity;

public class DemoStateReturns200Ok extends AbstractState<ResponseEntity<Void>, Void> {
    public DemoStateReturns200Ok() {
        super(new AbstractStateBuilder<>() {
            @Override
            public AbstractState<ResponseEntity<Void>, Void> build() {
                return null;
            }
        });
    }

    @Override
    protected ResponseEntity<Void> buildInternal() {
        return ResponseEntity.ok(null);
    }
}
