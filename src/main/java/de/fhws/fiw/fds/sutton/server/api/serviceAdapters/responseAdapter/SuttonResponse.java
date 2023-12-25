package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter.SuttonCacheController;

import java.net.URI;

public interface SuttonResponse<R, T> {

    R build();

    SuttonResponse<R, T> location(final URI location);

    SuttonResponse<R, T> cacheControl(final SuttonCacheController cacheController);

    SuttonResponse<R, T> entityTag(final String entityTag);

    SuttonResponse<R, T> link(final URI uri, final String rel);

    SuttonResponse<R, T> entity(final T entity);

    SuttonResponse<R, T> status(final Status status);

    SuttonResponse<R, T> header(final String headerName, final Object headerValue);
}
