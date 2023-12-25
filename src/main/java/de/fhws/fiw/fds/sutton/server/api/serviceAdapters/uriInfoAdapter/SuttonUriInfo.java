package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter;

import java.net.URI;
import java.util.Map;

public interface SuttonUriInfo {

    String getUriTemplate(final String path);

    URI getURI();

    String createURIWithQueryParamTemplates(String... queryParams);
    URI getURI(final String URI, Map<String, ? extends Object> queryParams);

    URI appendIdToPath(final long id);

    String getBaseUri();

    String appendToBaseUri(final String uri);

    String appendToBaseUriWithoutSchemePortHost(final String uri);

    default String getQueryParamAsTemplate(final String queryParam) {
        return "{" + queryParam + "}";
    }

    default String beforeQuestionMark(final String path) {
        if (path.contains("?")) {
            return path.substring(0, path.indexOf("?"));
        } else {
            return path;
        }
    }

    default String afterQuestionMark(final String path) {
        if (path.contains("?")) {
            return path.substring(path.indexOf("?") + 1);
        } else {
            return "";
        }
    }
}
