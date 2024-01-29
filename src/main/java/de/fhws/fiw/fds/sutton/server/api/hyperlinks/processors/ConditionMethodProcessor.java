package de.fhws.fiw.fds.sutton.server.api.hyperlinks.processors;

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.ConditionMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ConditionMethodProcessor {

    private final Object entity;

    private final ConditionMethod conditionMethod;

    public ConditionMethodProcessor(Object entity, ConditionMethod conditionMethod) {
        this.entity = entity;
        this.conditionMethod = conditionMethod;
    }

    public boolean processConditionMethod() {
        if (conditionMethod.method().isEmpty()) {
            throw new IllegalArgumentException("Method name can't be omitted");
        }

        Method[] entityDeclairedMethod = entity.getClass().getDeclaredMethods();

        Method method = Arrays.stream(entityDeclairedMethod)
                .filter(m -> m.getName().equals(conditionMethod.method()))
                .findFirst()
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(conditionMethod.method() + " isn't a valid method from the "
                            + entity.getClass().getSimpleName() + " class");
                });

        Class<?> methodReturnType = method.getReturnType();

        if (!(methodReturnType.getSimpleName().equalsIgnoreCase(Boolean.class.getSimpleName()))) {
            System.out.println(method.getReturnType().getSimpleName());
            throw new IllegalArgumentException(conditionMethod.method() + " return type must be of type boolean");
        }

        if(method.getParameterCount() != 0) {
            throw new IllegalArgumentException(conditionMethod.method() + " is not allowed to have parameters");
        }

        method.setAccessible(true);

        try {
            return (boolean) method.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
