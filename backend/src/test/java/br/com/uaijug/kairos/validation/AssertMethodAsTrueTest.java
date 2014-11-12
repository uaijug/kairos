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

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class AssertMethodAsTrueTest {

	private Validator validator;

	@Before
	public void init() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
		this.validator = vf.getValidator();
	}

	@Test
	public void testIsValidRange() {
		FakeEvent fakeEvent = new FakeEvent();
		fakeEvent.start = new LocalDate(2000, 12, 31);
		fakeEvent.end = new LocalDate(2001, 12, 31);

		Set<ConstraintViolation<FakeEvent>> errors = this.validator.validate(fakeEvent);

		assertThat("O validador não está validando corretamente", errors, is(empty()));
	}

	@Test
	public void testIsInvalidRange() {
		FakeEvent fakeEvent = new FakeEvent();
		fakeEvent.start = new LocalDate(2001, 12, 31);
		fakeEvent.end = new LocalDate(2000, 12, 31);

		Set<ConstraintViolation<FakeEvent>> errors = this.validator.validate(fakeEvent);

		assertThat("O validador não está validando corretamente", errors, hasSize(1));
	}

	@Test
	public void testIsValidEqualDate() {
		FakeEvent fakeEvent = new FakeEvent();
		fakeEvent.start = new LocalDate(2000, 12, 31);
		fakeEvent.end = new LocalDate(2000, 12, 31);

		Set<ConstraintViolation<FakeEvent>> errors = this.validator.validate(fakeEvent);

		assertThat("O validador não está validando corretamente", errors, is(empty()));
	}

	@Test
	public void testInvalidMessage() {
		FakeEvent fakeEvent = new FakeEvent();
		fakeEvent.start = new LocalDate(2001, 12, 31);
		fakeEvent.end = new LocalDate(2000, 12, 31);

		Set<ConstraintViolation<FakeEvent>> errors = this.validator.validate(fakeEvent);

		assertThat("O validador não está validando corretamente", errors, hasSize(1));
		ConstraintViolation<FakeEvent> error = errors.iterator().next();
		String message = error.getMessage();
		assertThat("A mensagem devera ser " + FakeEvent.INVALID_FAKE_EVENT_MESSAGE, message, is(equalTo(FakeEvent.INVALID_FAKE_EVENT_MESSAGE)));
	}

}
