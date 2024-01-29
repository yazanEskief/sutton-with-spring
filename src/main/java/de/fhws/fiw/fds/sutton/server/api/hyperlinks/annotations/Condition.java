package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import jakarta.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Nullable
public @interface Condition {

    String field() default "";

    Operation operation() default Operation.EQUAL;

    String value() default "";

    enum Operation {
        EQUAL, NOT_EQUAL
    }
}
