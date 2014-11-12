package br.com.uaijug.kairos.validation;

@AssertMethodAsTrueList({
    @AssertMethodAsTrue(value="isValid1", message=TwoBooleanValue.VALUE1_IS_INVALID),
    @AssertMethodAsTrue(value="isValid2")
})
public class TwoBooleanValue {

	public static final String VALUE1_IS_INVALID = "Value1 is invalid!";
	private boolean value1;
	private boolean value2;

	public TwoBooleanValue(boolean value1, boolean value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public boolean isValue1() {
		return this.value1;
	}

	public boolean isValue2() {
		return this.value2;
	}
}
