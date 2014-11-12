package br.com.uaijug.kairos.validation.validator;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uaijug.kairos.validation.AssertMethodAsTrue;

public class AssertMethodAsTrueValidator implements ConstraintValidator<AssertMethodAsTrue, Object>{

	private final Logger log = LoggerFactory.getLogger(AssertMethodAsTrueValidator.class);
	private String methodName;

    @Override
	public void initialize(AssertMethodAsTrue assertMethodAsTrue) {
        this.methodName =  assertMethodAsTrue.value();
    }

    @Override
	public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Class<? extends Object> clazz = object.getClass();
            Method validate = clazz.getMethod(this.methodName, new Class[0]);
            Boolean isValid = (Boolean) validate.invoke(object);
			return isValid;
        } catch (Throwable e) {
            this.log.error("Ocorreu um erro ao validar o objeto usando o método " + this.methodName, e );
        }
        return false;
    }

}