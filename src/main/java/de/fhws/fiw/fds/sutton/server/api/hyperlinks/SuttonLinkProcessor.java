package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.uriInfoAdapter.SuttonUriInfo;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SuttonLinkProcessor {

    private final SuttonUriInfo uriInfo;

    public SuttonLinkProcessor(SuttonUriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public void processEntity(Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        List<Field> annotatedFields = Arrays.stream(fields)
                .filter(f -> f.getType().equals(Link.class) && f.isAnnotationPresent(SuttonLink.class))
                .toList();

        if (annotatedFields.isEmpty()) {
            return;
        }

        for (Field link : annotatedFields) {
            link.setAccessible(true);

            SuttonLink suttonLink = link.getAnnotation(SuttonLink.class);

            if (!suttonLink.conditionMethod().method().isEmpty()) {
                ConditionMethodProcessor processor = new ConditionMethodProcessor(entity, suttonLink.conditionMethod());
                boolean isLinkInjectable = processor.processConditionMethod();
                if (!isLinkInjectable) {
                    continue;
                }
            } else if (!suttonLink.condition().field().isEmpty() && !suttonLink.condition().value().isEmpty()) {
                ConditionProcessor conditionProcessor = new ConditionProcessor(entity, suttonLink.condition());
                boolean isLinkInjectable = conditionProcessor.processCondition();
                if (!isLinkInjectable) {
                    continue;
                }
            }

            Link linkToInject = new Link();

            if (!suttonLink.value().isEmpty()) {
                final String href = processHref(suttonLink, entity);
                linkToInject.setHref(href);
            }

            if (!suttonLink.rel().isEmpty()) {
                linkToInject.setRel(suttonLink.rel());
            }

            linkToInject.setType(suttonLink.type().getType());

            try {
                link.set(entity, linkToInject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private String processHref(final SuttonLink suttonLink, Object entity) {
        final String href = suttonLink.value();
        List<String> templateVariables = extractUriTemplateVariables(href);

        if (templateVariables.isEmpty()) {
            return createHref(suttonLink, href);
        }

        String result = href;

        for (String variable : templateVariables) {
            String field = extractVariable(variable);

            Object fieldValue = getFieldValue(field, entity);

            result = result.replace(variable, fieldValue.toString());
        }

        return createHref(suttonLink, result);
    }

    private String createHref(final SuttonLink suttonLink, final String href) {
        final SuttonLink.Style style = suttonLink.style();

        if (style.equals(SuttonLink.Style.ABSOLUTE)) {
            return uriInfo.appendToBaseUri(href);
        }

        if (style.equals(SuttonLink.Style.RELATIVE_PATH)) {
            return href;
        }

        return uriInfo.appendToBaseUriWithoutSchemePortHost(href);
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
