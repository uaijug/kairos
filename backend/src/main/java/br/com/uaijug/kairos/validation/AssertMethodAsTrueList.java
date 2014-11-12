package br.com.uaijug.kairos.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value={TYPE, ANNOTATION_TYPE})
@Retention(value=RUNTIME)
@Documented
public @interface AssertMethodAsTrueList {
    AssertMethodAsTrue[] value() default {};
}