package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.cachingAdapter.SuttonCacheController;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * The {@link SpringResponse} class is an implementation of the {@link SuttonResponse} interface
 * tailored for the Spring framework. It facilitates the creation and configuration of HTTP responses
 * using Spring's {@link ResponseEntity}.
 *
 * @param <T> The type of the entity to be sent in the HTTP response body.
 */
public class SpringResponse<T> implements SuttonResponse<ResponseEntity<T>, T> {

    private Status status;

    private T body;

    private final HttpHeaders headers;

    private CacheControl cacheControl = CacheControl.empty();

    private String eTag;

    /**
     * Constructs a {@link SpringResponse} with default values.
     */
    public SpringResponse() {
        this.body = null;
        this.status = Status.OK;
        this.headers = new HttpHeaders();
    }

    @Override
    public ResponseEntity<T> build() {
        var result = ResponseEntity.status(this.status.getCode())
                .headers(this.headers)
                .cacheControl(this.cacheControl);

        if(this.eTag != null) {
            result.eTag(this.eTag);
        }

        return result.body(this.body);
    }

    @Override
    public SpringResponse<T> location(final URI location) {
        this.headers.setLocation(location);
        return this;
    }

    @Override
    public SpringResponse<T> cacheControl(final SuttonCacheController suttonCacheController) {

        final CacheControl cacheControl = suttonCacheController.isNoStoreFlag() ? CacheControl.noStore() :
                suttonCacheController.isNoCacheFlag() ? CacheControl.noCache() :
                        CacheControl.maxAge(suttonCacheController.getMaxAge(), TimeUnit.SECONDS);
        if(suttonCacheController.isMustRevalidateFlag()) {
            cacheControl.mustRevalidate();
        }
        if(suttonCacheController.isProxyRevalidate()) {
            cacheControl.proxyRevalidate();
        }
        if(suttonCacheController.isNoTransformFlag()) {
            cacheControl.noTransform();
        }
        if(suttonCacheController.isPrivateFlag()) {
            cacheControl.cachePrivate();
        } else {
            cacheControl.cachePublic();
        }

        this.cacheControl = cacheControl;

        return this;
    }

    @Override
    public SpringResponse<T> entityTag(final String entityTag) {
        this.eTag = entityTag;
        return this;
    }

    @Override
    public SpringResponse<T> link(final URI uri, final String rel) {
        this.headers.add("Link", linkHeader(uri.toASCIIString(), rel));
        return this;
    }

    @Override
    public SpringResponse<T> entity(final T entity) {
        this.body = entity;
        return this;
    }

    @Override
    public SpringResponse<T> status(final Status status) {
        this.status = status;
        return this;
    }

    @Override
    public SpringResponse<T> header(final String headerName, final Object headerValue) {
        this.headers.add(headerName, headerValue.toString());
        return this;
    }

    /**
     * Generates a string representation of a link header.
     *
     * @param uri The URI representing the href of the hyperlink.
     * @param rel The relation type of the hyperlink.
     * @return A string representing the link header.
     */
    private static String linkHeader(final String uri, final String rel) {
        final StringBuilder sb = new StringBuilder();
        sb.append('<').append(uri).append(">;");
        sb.append("rel").append("=\"").append(rel).append("\"");
        sb.append(";");
        sb.append("type=\"*\"");

        return sb.toString();
    }
}
