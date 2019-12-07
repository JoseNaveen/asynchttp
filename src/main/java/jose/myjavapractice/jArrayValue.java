package jose.myjavapractice;

import java.util.ArrayList;
import java.util.List;

public class jArrayValue extends jValue implements Visitable {
	private List<jStringValue> liststrings = new ArrayList<jStringValue>();
	private List<jNumberValue> listnumbers = new ArrayList<jNumberValue>();
	private List<Document> listobjects = new ArrayList<Document>();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (liststrings.size() > 0) {
			return this.liststrings.toString(); //array of strings
		}
		if (listnumbers.size() > 0) {
			return this.listnumbers.toString(); // array of numbers
		}
		if (listobjects.size() > 0) {
			return this.listobjects.toString(); //array of objects
		}
		return null;// should not happen
	}
	
	public void add(jStringValue jsv) {
		this.liststrings.add(jsv);
	}
	
	public void add(jNumberValue jnv) {
		this.listnumbers.add(jnv);
	}
	
	public void add(Document d) {
		this.listobjects.add(d);
	}
	
	public List<jStringValue> getListStrings() {
		return this.liststrings;
	}
	
	public List<jNumberValue> getListNumbers() {
		return this.listnumbers;
	}
	
	public List<Document> getListObjects() {
		return this.listobjects;
	}

	public void accept(Visitor v) {
		v.visitjArrayValue(this);
		// TODO Auto-generated method stub
		
	}

	public String accept(AnotherVisitor v) {
		return v.visitjArrayValue(this);
		// TODO Auto-generated method stub
		
	}

}
