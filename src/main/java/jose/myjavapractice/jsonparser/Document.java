package jose.myjavapractice.jsonparser;

import java.util.Iterator;
import java.util.List;

public class Document implements Value {
	private Key key;
	private List<Value> elements;
	public List<Value> getElements() {
		return elements;
	}
	public void setElements(List<Value> elements) {
		this.elements = elements;
	}
	@Override
	public Document getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Iterator<Value> it = this.elements.iterator();
		Value val;
		while (true) {
			if (it.hasNext()) {
				val = it.next();
				sb.append(val.toString());
				if (it.hasNext()) {
					sb.append(",");
				}
			}
			else {
				break;
			}
		}
		
		sb.append("}");
		return sb.toString();
	}
}
