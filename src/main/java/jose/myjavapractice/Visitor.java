package jose.myjavapractice;

public interface Visitor {
	
	public void visitDocument(Document d);
	public void visitjNode(jNode jnode);
	public void visitjString(jStringValue jstringv);
	public void visitjNodeValue(jNodeValue jnodev);
	public void visitjNumberValue(jNumberValue jnumberv);
	public void visitjArrayValue(jArrayValue jarrayv);
}
