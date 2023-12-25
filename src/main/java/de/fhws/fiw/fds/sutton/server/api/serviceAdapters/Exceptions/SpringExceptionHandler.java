package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class SpringExceptionHandler {

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
