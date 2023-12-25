package de.fhws.fiw.fds.sutton.server.api.states;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractStateTest {
    @Test
    public void testExecute_buildInternal_is_called_no_exception() throws Exception {
        final AbstractState<ResponseEntity<Void>, Void> stateUnderTest = new DemoStateReturns200Ok();
        final ResponseEntity<Void> response = stateUnderTest.execute();
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testExecute_buildInternal_is_called_web_exception() throws Exception {
        assertThrows(SuttonWebAppException.class, () -> {
            final AbstractState<ResponseEntity<Void>, Void> stateUnderTest = new DemoStateThrowsWebException();
            stateUnderTest.execute();
        });
    }

    @Test
    public void testExecute_buildInternal_is_called_illegal_argument_exception() throws Exception {
        final AbstractState<ResponseEntity<Void>, Void> stateUnderTest = new DemoStateThrowsIllegalArgumentException();
        final ResponseEntity<Void> response = stateUnderTest.execute();
        assertEquals(500, response.getStatusCode().value());
    }
}
