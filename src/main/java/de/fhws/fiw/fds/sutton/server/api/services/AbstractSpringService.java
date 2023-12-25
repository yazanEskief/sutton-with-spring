package de.fhws.fiw.fds.sutton.server.api.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class AbstractSpringService {

    protected HttpServletRequest httpServletRequest;

    protected AbstractSpringService(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }
}
