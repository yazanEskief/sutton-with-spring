package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConditionProcessor {

    private final Object entity;

    private final Condition condition;

    public ConditionProcessor(Object entity, Condition condition) {
        this.entity = entity;
        this.condition = condition;
    }

    public boolean processCondition() {
        evaluatePropertiesExistence();

        Field entityField = getEntityField();

        entityField.setAccessible(true);

        try {
            var fieldValue = entityField.get(entity);

            return evaluateCondition(fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean evaluateCondition(Object fieldValue) {
        if (fieldValue instanceof Integer) {
            Integer value = Integer.valueOf(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Double) {
            Double value = Double.parseDouble(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Long) {
            Long value = Long.parseLong(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof Float) {
            Float value = Float.parseFloat(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        if (fieldValue instanceof String) {
            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(condition.value());
        }

        if (fieldValue instanceof Boolean) {
            Boolean value = Boolean.parseBoolean(condition.value());

            return condition.operation().equals(Condition.Operation.EQUAL) == fieldValue.equals(value);
        }

        throw new IllegalArgumentException(condition.value() + " is neither a String nor a primitive java type");
    }

    private void evaluatePropertiesExistence() {
        final String conditionFieldAsString = this.condition.field();
        final String conditionValue = this.condition.value();

        if (conditionFieldAsString.isEmpty()) {
            throw new IllegalArgumentException("field property of the @Condition annotation can't be omitted");
        }

        if (conditionValue.isEmpty()) {
            throw new IllegalArgumentException("Value property of the @Condition annotation can't be omitted");
        }
    }

    private Field getEntityField() {
        List<Field> entityFields = Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toList());

        List<Field> superclassFields = Arrays.stream(entity.getClass().getSuperclass().getDeclaredFields())
                .toList();

        entityFields.addAll(superclassFields);

        Optional<Field> result = entityFields.stream()
                .filter(f -> f.getName().equals(this.condition.field()))
                .findFirst();

        return result.orElseThrow(() -> {
            throw new IllegalArgumentException(condition.field() + " is not a valid field of the " +
                    entity.getClass().getSimpleName());
        });
    }
}
