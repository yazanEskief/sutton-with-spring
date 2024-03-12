package de.fhws.fiw.fds.sutton.server.api.serviceAdapters.requestAdapter;

import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import org.springframework.web.context.request.WebRequest;

/**
 * The {@link SpringRequest} class is an adapter that implements the {@link SuttonRequest}
 * interface, specifically designed to integrate with the Spring framework for processing
 * conditional HTTP requests. It utilizes the {@link WebRequest} interface from the Spring
 * framework to evaluate preconditions based on the client's version of a resource.
 *
 * This class facilitates the handling of conditional requests within the Spring framework
 * by leveraging ETags to determine if the client's version of a resource matches the current
 * version on the server.
 */
public class SpringRequest implements SuttonRequest {

    private final WebRequest webRequest;

    /**
     * Constructs a {@link SpringRequest} with the specified Spring {@link WebRequest}.
     *
     * @param webRequest The Spring {@link WebRequest} instance used for precondition evaluation.
     */
    public SpringRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    @Override
    public boolean clientKnowsCurrentModel(final AbstractModel model) {
        final String eTagOfModel = EtagGenerator.createEtag(model);
        return webRequest.checkNotModified(eTagOfModel);
    }
}
