package br.com.uaijug.kairos.validation;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class AssertMethodAsTrueListTest {
	private Validator validator;

	@Before
	public void init() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.getValidator();
	}

	@Test
	public void testBothValid() {
		TwoBooleanValue value = new TwoBooleanValue(true,true);

		Set<ConstraintViolation<TwoBooleanValue>> errors = this.validator.validate(value);
		assertThat("A validação não deveria encontrar nenhum erro", errors, is(empty()));
	}

	@Test
	public void testValue1IsInvalid() {
		TwoBooleanValue value = new TwoBooleanValue(false,true);

		Set<ConstraintViolation<TwoBooleanValue>> errors = this.validator.validate(value);
		assertThat("A validação deveria encontrar 1 erro", errors, hasSize(1));

		ConstraintViolation<TwoBooleanValue> error = errors.iterator().next();
		String message = error.getMessage();
		assertThat("A mensagem devera ser " + TwoBooleanValue.VALUE1_IS_INVALID, message, is(equalTo(TwoBooleanValue.VALUE1_IS_INVALID)));
	}

	@Test
	public void testValue2IsInvalid() {
		TwoBooleanValue value = new TwoBooleanValue(true,false);

		Set<ConstraintViolation<TwoBooleanValue>> errors = this.validator.validate(value);
		assertThat("A validação deveria encontrar 1 erro", errors, hasSize(1));

		ConstraintViolation<TwoBooleanValue> error = errors.iterator().next();
		String message = error.getMessage();
		assertThat("A mensagem devera ser " + AssertMethodAsTrue.DEFAUL_INVALID_MESSAGE, message, is(equalTo(AssertMethodAsTrue.DEFAUL_INVALID_MESSAGE)));
	}

	@Test
	public void testBothInvalid() {
		TwoBooleanValue value = new TwoBooleanValue(false,false);

		Set<ConstraintViolation<TwoBooleanValue>> errors = this.validator.validate(value);
		assertThat("A validação deveria encontrar 2 erro", errors, hasSize(2));

		ConstraintViolation<TwoBooleanValue> error1 = errors.iterator().next();
		String message1 = error1.getMessage();
		assertThat("A mensagem devera ser " + TwoBooleanValue.VALUE1_IS_INVALID, message1, is(equalTo(TwoBooleanValue.VALUE1_IS_INVALID)));

		ConstraintViolation<TwoBooleanValue> error2 = errors.iterator().next();
		String message2 = error2.getMessage();
		assertThat("A mensagem devera ser " + AssertMethodAsTrue.DEFAUL_INVALID_MESSAGE, message2, is(equalTo(AssertMethodAsTrue.DEFAUL_INVALID_MESSAGE)));
	}

}
