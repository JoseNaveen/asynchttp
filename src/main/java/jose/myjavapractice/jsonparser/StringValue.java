package jose.myjavapractice.jsonparser;

public class StringValue implements Value {
	
	public StringValue() {
		
	}

	private String value;

	public Value getValue() {
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}

}
