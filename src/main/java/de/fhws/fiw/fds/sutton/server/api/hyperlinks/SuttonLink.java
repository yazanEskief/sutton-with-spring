package de.fhws.fiw.fds.sutton.server.api.hyperlinks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuttonLink {

    String value() default "";

    String rel() default "";

    MediaType type() default MediaType.APPLICATION_JSON;

    Condition condition() default @Condition;

    ConditionMethod conditionMethod() default @ConditionMethod;

    Style style() default Style.ABSOLUTE;

    enum Style {

        ABSOLUTE_PATH,

        RELATIVE_PATH,

        ABSOLUTE
    }

    enum MediaType {
        APPLICATION_JSON("application/json"),
        APPLICATION_XML("application/xml");

        private String type;

        MediaType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
