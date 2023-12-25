package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.Optional;


public class SpringRequest implements SuttonRequest {
    private final HttpServletRequest request;

    public SpringRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean clientKnowsCurrentModel(final String entityTag) {
        final String tag = createETag(entityTag);
        final Optional<String> ifMatchHeader = Optional.ofNullable(request.getHeader(HttpHeaders.IF_MATCH));
        final Optional<String> ifNonMatchHeader = Optional.ofNullable(request.getHeader(HttpHeaders.IF_NONE_MATCH));

        return ifMatchHeader.map(s -> !s.equals(tag))
                .orElseGet(() -> ifNonMatchHeader.map(s -> s.equals(tag)).orElse(false));

    }

    private String createETag(final String entityTag) {
        return "\"" + entityTag + "\"";
    }
}
