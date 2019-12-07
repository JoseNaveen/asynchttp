package jose.myjavapractice;

public class jStringValue extends jValue implements Visitable{
	private String value;
	public void setStringValue(String value) {
		this.value = value;
	}
	public String getStringValue() {
		return this.value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.value);
		sb.append("\"");
		return sb.toString();
	}
	public void accept(Visitor v) {
		v.visitjString(this);
		// TODO Auto-generated method stub
		
	}
	public String accept(AnotherVisitor v) {
		return value;
		// TODO Auto-generated method stub
		
	}

}
