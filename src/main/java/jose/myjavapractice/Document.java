package jose.myjavapractice;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Document extends jast implements Visitable{
  private List<jNode> elements = new ArrayList<jNode>();
  
  public void add(jNode element) {
	  this.elements.add(element);
  }
  
  public List<jNode> getAll() {
	  return this.elements;
  }
  
  public String toString() {
	  StringJoiner sj = new StringJoiner(",\n");
	  StringBuilder sb = new StringBuilder();
	  sb.append("\n{\n");
	  for (int i=0; i<elements.size(); i++) {
		  sj.add(this.elements.get(i).toString());
	  }
	  sb.append(sj.toString());
	  sb.append("\n}\n");
	  return sb.toString();
  }

  public void accept(Visitor v) {
	  v.visitDocument(this);
	// TODO Auto-generated method stub
	
}

 public String accept(AnotherVisitor v) {
	return null;
	// TODO Auto-generated method stub
	
}
}
