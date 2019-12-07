package jose.myjavapractice;

import java.util.List;
import java.util.StringJoiner;

public class PrintVisitor implements Visitor {

	public void visitDocument(Document d) {
		// TODO Auto-generated method stub
		List<jNode> elements = d.getAll();
		int size = elements.size();
		for (int i = 0; i<size; i++) {
			Object o = elements.get(i);
			if (o instanceof Visitable) {
				((Visitable)o).accept(this);
			}
		}
	}

	public void visitjNode(jNode jnode) {
		// TODO Auto-generated method stub
		if (jnode.getValue() instanceof Visitable) {
			System.out.println(jnode.getKey() + " : ");
			((Visitable)jnode.getValue()).accept(this);
		}

	}

	public void visitjString(jStringValue jstringv) {
		// TODO Auto-generated method stub
		System.out.println(jstringv.getStringValue());
		
	}

	public void visitjNodeValue(jNodeValue jnodev) {
		// TODO Auto-generated method stub
		Object o = jnodev.getNodeValue();
		System.out.println("{");
		if (o instanceof Visitable) {
			((Visitable)o).accept(this);
		}
		System.out.println("}");
		
	}

	public void visitjNumberValue(jNumberValue jnumberv) {
		// TODO Auto-generated method stub
		System.out.println(jnumberv.getNumberValue());
	}
	
	public void visitjArrayValue(jArrayValue jarrayv) {
		System.out.println("[");
		int size = jarrayv.getListStrings().size();
		if (size > 0) {
			for (int i=0; i<size; i++ ) {
				System.out.println(jarrayv.getListStrings().get(i));
			}
		}
		size = jarrayv.getListNumbers().size();
		if ( size > 0) {
			for (int i=0; i<size; i++ ) {
				System.out.println(jarrayv.getListNumbers().get(i));
			}
		}
		size = jarrayv.getListObjects().size();
		if (size > 0) {
			for (int i=0; i<size; i++ ) {
				Object o = jarrayv.getListObjects().get(i);
				if (o instanceof Visitable) {
					((Visitable)o).accept(this);
				}
			}
		}
		System.out.println("]");
	}

}
