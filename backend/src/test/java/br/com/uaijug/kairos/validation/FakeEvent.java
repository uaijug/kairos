package br.com.uaijug.kairos.validation;

import org.joda.time.LocalDate;

@AssertMethodAsTrue(value = "isValid", message=FakeEvent.INVALID_FAKE_EVENT_MESSAGE)

public class FakeEvent {
	public static final String INVALID_FAKE_EVENT_MESSAGE = "Invalid fake event";

	LocalDate start;
	LocalDate end;

	public boolean isValid() {
		return this.start.isBefore(this.end) || this.start.isEqual(this.end);
	}
}