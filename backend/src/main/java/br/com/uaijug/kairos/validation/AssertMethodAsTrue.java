package br.com.uaijug.kairos.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.uaijug.kairos.validation.validator.AssertMethodAsTrueValidator;

@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {AssertMethodAsTrueValidator.class} )
@Documented
public @interface AssertMethodAsTrue {
    public static final String DEFAUL_INVALID_MESSAGE = "{invalid.condition}";

	String message() default DEFAUL_INVALID_MESSAGE;
    String value() default "isValid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}