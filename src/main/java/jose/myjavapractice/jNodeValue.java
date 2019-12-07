package jose.myjavapractice;

import java.util.List;

public class jNodeValue extends jValue implements Visitable {
	//private List<jNode> nodevalue;
	private Document nodevalue;
	public void setNodeValue(Document nodevalue) {
		this.nodevalue = nodevalue;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//StringBuilder sb = new StringBuilder();
		//for ( jNode jn: nodevalue) {
		//	sb.append(jn.toString());
		//}
		return this.nodevalue.toString();
	}
	public void accept(Visitor v) {
		v.visitjNodeValue(this);
		// TODO Auto-generated method stub
		
	}
	
	public Document getNodeValue() {
		return this.nodevalue;
	}
	public String accept(AnotherVisitor v) {
		return v.visitjNodeValue(this);
		// TODO Auto-generated method stub
		
	}

}
