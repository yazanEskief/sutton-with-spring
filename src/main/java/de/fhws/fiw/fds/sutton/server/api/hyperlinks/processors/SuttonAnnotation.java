package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.Style;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;

public abstract class SuttonAnnotation {

    private SuttonUriInfo uriInfo;

    public SuttonAnnotation(SuttonUriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public void processAnnotation(final Field field, final AbstractModel model) {
        if(!isAnnotationPresent(field)) {
            return;
        }

        processField(field, model);
    }

    public abstract void processField(final Field field, final AbstractModel model);

    public abstract boolean isAnnotationPresent(final Field field);

    protected String createHref(final Style injectionStyle, final String href) {
        if (injectionStyle.equals(Style.ABSOLUTE)) {
            return uriInfo.appendToBaseUri(href);
        }

        if (injectionStyle.equals(Style.RELATIVE_PATH)) {
            return href;
        }

        return uriInfo.appendToBaseUriWithoutSchemePortHost(href);
    }
}
