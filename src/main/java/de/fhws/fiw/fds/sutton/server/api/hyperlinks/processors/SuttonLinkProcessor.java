package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.Style;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SuttonLinkProcessor extends SuttonAnnotation {

    public SuttonLinkProcessor(SuttonUriInfo uriInfo) {
        super(uriInfo);
    }

    @Override
    public boolean isAnnotationPresent(Field field) {
        return field.isAnnotationPresent(SuttonLink.class);
    }

    @Override
    public void processField(final Field field, final AbstractModel model) {
        field.setAccessible(true);

        SuttonLink suttonLink = field.getAnnotation(SuttonLink.class);

        if (!suttonLink.conditionMethod().method().isEmpty()) {
            ConditionMethodProcessor processor = new ConditionMethodProcessor(model, suttonLink.conditionMethod());
            boolean isLinkInjectable = processor.processConditionMethod();
            if (!isLinkInjectable) {
                return;
            }
        } else if (!suttonLink.condition().field().isEmpty() && !suttonLink.condition().value().isEmpty()) {
            ConditionProcessor conditionProcessor = new ConditionProcessor(model, suttonLink.condition());
            boolean isLinkInjectable = conditionProcessor.processCondition();
            if (!isLinkInjectable) {
                return;
            }
        }

        Link linkToInject = new Link();

        if (!suttonLink.value().isEmpty()) {
            final String href = processHref(suttonLink.value(), suttonLink.style(), model);
            linkToInject.setHref(href);
        }

        if (!suttonLink.rel().isEmpty()) {
            linkToInject.setRel(suttonLink.rel());
        }

        linkToInject.setType(suttonLink.type());

        try {
            field.set(model, linkToInject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String processHref(final String href, final Style injectionStyle, Object entity) {
        List<String> templateVariables = extractUriTemplateVariables(href);

        if (templateVariables.isEmpty()) {
            return createHref(injectionStyle, href);
        }

        String result = href;

        for (String variable : templateVariables) {
            String field = extractVariable(variable);

            Object fieldValue = getFieldValue(field, entity);

            result = result.replace(variable, fieldValue.toString());
        }

        return createHref(injectionStyle, result);
    }

    private Object getFieldValue(final String field, Object entity) {
        List<Field> entityFields = Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toList());

        List<Field> superclassFields = Arrays.stream(entity.getClass().getSuperclass().getDeclaredFields())
                .toList();

        entityFields.addAll(superclassFields);

        Optional<Field> result = entityFields.stream()
                .filter(f -> f.getName().equals(field))
                .findFirst();

        if (result.isPresent()) {
            Field entityField = result.get();
            entityField.setAccessible(true);
            try {
                return entityField.get(entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(field + " is not a valid field of the " +
                    entity.getClass().getSimpleName());
        }
    }

    private String extractVariable(final String variableTemplate) {
        return variableTemplate.substring(2, variableTemplate.length() - 1);
    }

    private List<String> extractUriTemplateVariables(final String href) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
        Matcher matcher = pattern.matcher(href);

        return matcher.results()
                .map(MatchResult::group)
                .toList();
    }
}
