package jose.myjavapractice.jsonparser;

public class NumberValue implements Value {
	public NumberValue() {
		// TODO Auto-generated constructor stub
	}

	private Float numberValue;

	public Float getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(Float numberValue) {
		this.numberValue = numberValue;
	}

	@Override
	public Value getValue() {
		// TODO Auto-generated method stub
		return this;
	}
}
