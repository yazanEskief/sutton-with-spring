package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SecondarySelfLink;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SuttonAnnotationsProcessor {

    private SuttonUriInfo uriInfo;

    private final Map<String, SuttonAnnotation> suttonAnnotations;

    public SuttonAnnotationsProcessor(SuttonUriInfo uriInfo) {
        this.uriInfo = uriInfo;
        this.suttonAnnotations = new HashMap<>();
        suttonAnnotations.put(SuttonLink.class.getSimpleName(), new SuttonLinkProcessor(this.uriInfo));
        suttonAnnotations.put(SelfLink.class.getSimpleName(), new SelfLinkProcessor(this.uriInfo));
        suttonAnnotations.put(SecondarySelfLink.class.getSimpleName(), new SecondarySelfLinkProcessor(this.uriInfo));
    }

    public void addAnnotation(String annotationName, Supplier<SuttonAnnotation> callBack) {
        SuttonAnnotation suttonAnnotation = callBack.get();
        this.suttonAnnotations.put(annotationName, suttonAnnotation);
    }

    public void processSuttonAnnotations(final AbstractModel model) {
        Field[] fields = model.getClass().getDeclaredFields();

        List<Field> annotatedFields = Arrays.stream(fields)
                .filter(f -> f.getType().equals(Link.class))
                .toList();

        for (Field link : annotatedFields) {
            suttonAnnotations.forEach((annotation, annotationProcessor) -> {
                annotationProcessor.processAnnotation(link, model);
            });
        }
    }
}
