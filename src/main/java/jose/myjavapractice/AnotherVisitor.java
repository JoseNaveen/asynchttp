package jose.myjavapractice;

public interface AnotherVisitor {
	public String visitDocument(Document d);
	public String visitjNode(jNode jnode);
	public String visitjString(jStringValue jstringv);
	public String visitjNodeValue(jNodeValue jnodev);
	public String visitjNumberValue(jNumberValue jnumberv);
	public String visitjArrayValue(jArrayValue jarrayv);
	public String visitJSONObject(VisitableJSONObject vjsonobj);
	public String VisitJSONArray(VisitableJSONArray vjsonarr);
}
