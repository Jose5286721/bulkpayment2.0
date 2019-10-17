package py.com.global.spm.model.util;

/**
 * 
 * @author Lino Chamorro
 * 
 */
public enum BooleanValueEnum {

	TRUE(1), 
	FALSE(0);

	private int value;

	private BooleanValueEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
