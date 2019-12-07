package jose.myjavapractice;

public class jNumberValue extends jValue implements Visitable{
	private Float value;
	public void setNumberValue(Float value) {
		this.value = value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.value.toString();
	}
	
	public Float getNumberValue() {
		return this.value;
	}
	public void accept(Visitor v) {
		v.visitjNumberValue(this);
		// TODO Auto-generated method stub
		
	}
	public String accept(AnotherVisitor v) {
		return null;
		// TODO Auto-generated method stub
		
	}
}
