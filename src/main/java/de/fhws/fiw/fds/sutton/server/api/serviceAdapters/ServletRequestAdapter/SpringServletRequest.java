package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.ServletRequestAdapter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@link SpringServletRequest} class is an implementation of the {@link SuttonServletRequest}
 * interface specifically designed to support the Spring framework. It wraps an
 * {@link HttpServletRequest} object to provide the required functionality for accessing
 * HTTP request information.
 */
public class SpringServletRequest implements SuttonServletRequest {

    private final HttpServletRequest httpServletRequest;

    /**
     * Constructs a {@link SpringServletRequest} with the specified {@link HttpServletRequest}.
     *
     * @param httpServletRequest The {@link HttpServletRequest} object that this class wraps.
     */
    public SpringServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getHeader(final String headerName) {
        return this.httpServletRequest.getHeader(headerName);
    }
}
