package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

public class SecondarySelfLinkProcessor extends SuttonAnnotation {

    public SecondarySelfLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    protected void processField(Field field, final AbstractModel model) {
        field.setAccessible(true);

        SecondarySelfLink secondarySelfLink = field.getAnnotation(SecondarySelfLink.class);

        if (!hasPrimaryResource(model)) {
            return;
        }

        Link linkToInject = new Link();

        setHref(secondarySelfLink, linkToInject, model);

        linkToInject.setRel("self");

        linkToInject.setType(secondarySelfLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setHref(SecondarySelfLink link, Link linkToInject, final AbstractModel model) {
        String primaryResourceName = link.PrimaryResourceName();
        if(primaryResourceName.isEmpty()) {
            throw new IllegalArgumentException("The primaryResourceName field can't be omitted");
        }
        long primaryId = model.getPrimaryId();
        String secondaryResourceName = !link.secondaryResourceName().isEmpty() ? link.secondaryResourceName() :
                getResourceName(model);
        long id = model.getId();
        String relativeHref = primaryResourceName + "/" + primaryId + "/" + secondaryResourceName + "/" + id;
        String href = createHref(link.style(), relativeHref);
        linkToInject.setHref(href);
    }

    private String getResourceName(final AbstractModel model) {
        final String name = model.getClass().getSimpleName();
        return name.substring(0, 1).toLowerCase() + name.substring(1) + "s";
    }

    @Override
    protected boolean isAnnotationPresent(Field field) {
        return field.isAnnotationPresent(SecondarySelfLink.class);
    }

    private boolean hasPrimaryResource(final AbstractModel model) {
        return model.getPrimaryId() != 0;
    }
}
