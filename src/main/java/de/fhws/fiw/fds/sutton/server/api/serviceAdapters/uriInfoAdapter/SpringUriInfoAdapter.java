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

public class SpringUriInfoAdapter implements SuttonUriInfo {

    private final UriComponentsBuilder uriComponentsBuilder;

    private final HttpServletRequest httpServletRequest;

    public SpringUriInfoAdapter(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        final String requestUri = httpServletRequest.getRequestURL().append("?")
                .append(httpServletRequest.getQueryString()).toString();

        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(requestUri);
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

    @Override
    public String getBaseUri() {
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

    private MultiValueMap<String, String> createQueryParamsMap(String... queryParams) {
        Map<String, List<String>> queriesMap = new HashMap<>();
        for (String param : queryParams) {
            queriesMap.put(param, List.of(getQueryParamAsTemplate(param)));
        }
        return new LinkedMultiValueMap<>(queriesMap);
    }
}
