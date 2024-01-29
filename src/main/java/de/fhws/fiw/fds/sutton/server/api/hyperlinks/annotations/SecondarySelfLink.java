package de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondarySelfLink {

    String PrimaryResourceName();

    String secondaryResourceName() default "";

    String type() default "application/json";

    Style style() default Style.ABSOLUTE;

}
