package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

public class SelfLinkProcessor extends SuttonAnnotation {

    public SelfLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    protected boolean isAnnotationPresent(final Field field) {
        return field.isAnnotationPresent(SelfLink.class);
    }
    @Override
    protected void processField(final Field field, final AbstractModel model) {
        field.setAccessible(true);

        if(hasPrimaryResource(model)) {
            return;
        }

        SelfLink selfLink = field.getAnnotation(SelfLink.class);

        Link linkToInject = new Link();

        setHref(selfLink, linkToInject, model);

        linkToInject.setRel("self");

        linkToInject.setType(selfLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setHref(SelfLink link, Link linkToInject, final AbstractModel model) {
        final String resourceName = !link.name().isEmpty() ? link.name() : getResourceName(model);
        final long modelId = model.getId();
        String relativeHref = resourceName + "/" + modelId;
        String href = createHref(link.style(), relativeHref);
        linkToInject.setHref(href);
    }

    private String getResourceName(final AbstractModel model) {
        final String name = model.getClass().getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1) + "s";
    }

    private boolean hasPrimaryResource(final AbstractModel model) {
        return model.getPrimaryId() != 0;
    }
}
