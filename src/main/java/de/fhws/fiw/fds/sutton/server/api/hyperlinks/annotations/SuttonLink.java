package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuttonLink {

    String value() default "";

    String rel() default "";

    String type() default "application/json";

    Condition condition() default @Condition;

    ConditionMethod conditionMethod() default @ConditionMethod;

    Style style() default Style.ABSOLUTE;

}
