package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@link SpringUriInfoAdapter} class is an implementation of the {@link SuttonUriInfo}
 * interface specifically designed for the Spring framework. It utilizes the
 * {@link HttpServletRequest} and {@link UriComponentsBuilder} to access and manipulate
 * the HTTP request URI, providing fine control over the construction of a URI.
 */
public class SpringUriInfoAdapter implements SuttonUriInfo {

    private final UriComponentsBuilder uriComponentsBuilder;

    private final HttpServletRequest httpServletRequest;

    /**
     * Constructs a {@link SpringUriInfoAdapter} with the specified {@link HttpServletRequest}.
     *
     * @param httpServletRequest The {@link HttpServletRequest} object that this adapter uses to initialize a
     *                           {@link UriComponentsBuilder} that this class will use to retrieve information from
     *                           the URI and to manipulate it.
     */
    public SpringUriInfoAdapter(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        final StringBuffer requestUri = httpServletRequest.getRequestURL();

        if (httpServletRequest.getQueryString() != null) {
            requestUri.append("?")
                .append(httpServletRequest.getQueryString());
        }

        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(requestUri.toString());
    }

    @Override
    public String getUriTemplate(final String path) {
        UriComponentsBuilder clone = uriComponentsBuilder.cloneBuilder();
        clone.replacePath(beforeQuestionMark(path));
        clone.replaceQuery(afterQuestionMark(path));
        return clone.build().toUriString();
    }

    @Override
    public URI getURI() {
        return this.uriComponentsBuilder.build().toUri();
    }

    @Override
    public String createURIWithQueryParamTemplates(String... queryParams) {
        String queryString = UriComponentsBuilder.newInstance()
                .queryParams(createQueryParamsMap(queryParams))
                .build().toUriString();
        UriComponentsBuilder clone = uriComponentsBuilder.cloneBuilder();
        clone.replaceQueryParam(queryString);
        return clone.build().toUriString();
    }

    @Override
    public URI getURI(final String URI, Map<String, ?> queryParams) {
        var queryParamsMultiMap = new LinkedMultiValueMap<String, String>();
        queryParams.forEach((k, v) -> {
            queryParamsMultiMap.put(k, List.of(v.toString()));
        });

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URI);
        builder.replaceQueryParams(queryParamsMultiMap);
        return builder.build().toUri();
    }

    @Override
    public URI appendIdToPath(long id) {
        UriComponentsBuilder clone = uriComponentsBuilder.cloneBuilder();
        return clone.path(Long.toString(id)).build().toUri();
    }

    /**
     * Retrieves the base URI from the HTTP request, which consists of the scheme, host, port, and
     * the context path.
     *
     * @return A string representing the base URI.
     */
    private String getBaseUri() {
        UriComponents uriComponents = this.uriComponentsBuilder.cloneBuilder().build();
        return UriComponentsBuilder.newInstance()
                .scheme(uriComponents.getScheme())
                .host(uriComponents.getHost())
                .port(uriComponents.getPort())
                .path(this.httpServletRequest.getContextPath())
                .build()
                .toUriString();
    }

    @Override
    public String appendToBaseUri(String uri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getBaseUri());
        builder.pathSegment(beforeQuestionMark(uri));
        builder.replaceQuery(afterQuestionMark(uri));
        return builder.build().toUriString();
    }

    @Override
    public String appendToBaseUriWithoutSchemePortHost(String uri) {
        return UriComponentsBuilder.newInstance()
                .path(this.httpServletRequest.getContextPath())
                .path(beforeQuestionMark(uri))
                .query(afterQuestionMark(uri))
                .build()
                .toUriString();
    }

    /**
     * Creates a map of query parameters with template variables as values.
     *
     * @param queryParams The names of the query parameters.
     * @return A {@link MultiValueMap} representing the query parameters map.
     */
    private MultiValueMap<String, String> createQueryParamsMap(String... queryParams) {
        Map<String, List<String>> queriesMap = new HashMap<>();
        for (String param : queryParams) {
            queriesMap.put(param, List.of(getQueryParamAsTemplate(param)));
        }
        return new LinkedMultiValueMap<>(queriesMap);
    }
}
