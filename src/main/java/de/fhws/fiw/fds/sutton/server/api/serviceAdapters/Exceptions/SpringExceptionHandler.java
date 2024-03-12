package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * The {@link SpringExceptionHandler} class is used within the Spring version of Sutton
 * to intercept exceptions thrown by the application. It is annotated with {@link @ControllerAdvice}
 * to handle exceptions across the whole application in one global handling component.
 */
@ControllerAdvice
public class SpringExceptionHandler {

    /**
     * Intercepts {@link SuttonWebAppException} or any of its subclasses and maps the exception
     * to a {@link ResponseEntity} that encapsulates a {@link SpringExceptionEntity} in its body.
     * The HTTP status code of the response is set to the status code of the occurred exception.
     *
     * @param e The {@link SuttonWebAppException} that was thrown.
     * @return A {@link ResponseEntity} containing the {@link SpringExceptionEntity} and the
     *         appropriate HTTP status code.
     */
    @ExceptionHandler(value = {SuttonWebAppException.class})
    public ResponseEntity<SpringExceptionEntity> suttonWebExceptionHandler(SuttonWebAppException e) {
        SpringExceptionEntity entity = new SpringExceptionEntity(
                e.getExceptionMessage(),
                LocalDateTime.now(),
                e.getStatus().getCode()
        );

        return new ResponseEntity<>(entity, HttpStatus.resolve(e.getStatus().getCode()));
    }
}
