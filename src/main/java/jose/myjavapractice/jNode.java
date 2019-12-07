package jose.myjavapractice;

import java.util.List;


//node denotes a key value pair
/*
 * key is always a string
 * value can be a string, number, or a jsonObject denoted here as a document,
 * or an array of elements
 */
public class jNode extends jast implements Visitable{
	private String key;
	private jValue jValue;
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(jValue jv) {
		this.jValue = jv;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.key);
		sb.append("\"");
		sb.append(":");
		sb.append(this.jValue.toString());
		return sb.toString();
	}
	public void accept(Visitor v) {
		v.visitjNode(this);
		// TODO Auto-generated method stub
		
	}
	
	public String getKey() {
		return this.key;
	}
	public jValue getValue() {
		return this.jValue;
	}
	public String accept(AnotherVisitor v) {
		return v.visitjNode(this);
		// TODO Auto-generated method stub
		
	}
}
