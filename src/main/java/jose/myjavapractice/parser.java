package jose.myjavapractice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jose.myjavapractice.*;

public class parser {

	public Document parse(List<token> tokens) throws Exception {
		Iterator<token> itr = tokens.iterator();
		int i = 0;
		token t;
		//List<jNode> elements = new ArrayList<jNode>();
		Document doc = new Document();

		// entry point to the parser
		/*
		 * check if the we get '{' as the first token which denotes the start of an json
		 * object
		 */
		t = itr.next();
		// System.out.println(t.getTokenType() + t.getTokenValue());
		if (t.getTokenType() == token.tokenType.LBRACKET) {
			// read object
			doc = readObject(itr);

			// break;
		} else {
			// if it is not a '{' it could be a json array. but for now break the loop and
			// stop parsing

		}

		/*
		 * for (jNode e: elements) { doc.add(e); }
		 */
		return doc;
	}
	
	public jNode readKeyValue(Iterator<token> i) {
		token t = i.next();
		if (t.getTokenType() == token.tokenType.STRING) {
			jNode jn = new jNode();
			jn.setKey(t.getStringValue());
			t = i.next();
			if (t.getTokenType() == token.tokenType.SEPARATOR) {
				t =i.next();
				if (t.getTokenType() == token.tokenType.STRING) {
					jStringValue jsv = new jStringValue();
					jsv.setStringValue(t.getStringValue());
					
					jn.setValue(jsv);
					return jn;					
				}
				if (t.getTokenType() == token.tokenType.NUMBER) {
					jNumberValue jnv = new jNumberValue();
					jnv.setNumberValue(t.getNumberValue());
					
					jn.setValue(jnv);
					return jn;
				}
				if (t.getTokenType() == token.tokenType.LBRACKET) {
					//read object
					jNodeValue nodevalue = new jNodeValue();
					
					nodevalue.setNodeValue(readObject(i));
					jn.setValue(nodevalue);
					return jn;
				}
				if (t.getTokenType() == token.tokenType.LSBRACKET) {
					//readArray
					t = i.next();
					jStringValue ajsv = null;
					jNumberValue ajnv = new jNumberValue();
					jNodeValue anv = null;
					jArrayValue jav = new jArrayValue();
					while (true ) {
					if (t.getTokenType() == token.tokenType.STRING) {
						ajsv = new jStringValue();
						ajsv.setStringValue(t.getStringValue());
						jav.add(ajsv);
						jn.setValue(jav);
						t = i.next();
						if (t.getTokenType() == token.tokenType.RSBRACKET) { //end of array
							return jn;
						}
						if (t.getTokenType() == token.tokenType.COMMA) { //read one more element
							t = i.next();
							continue;
						}
						else {
							//raise exception;
						}
						//read a string
					}
					else if (t.getTokenType() == token.tokenType.NUMBER) {
						ajnv = new jNumberValue();
						ajnv.setNumberValue(t.getNumberValue());
						jav.add(ajnv);
						jn.setValue(jav);
						t = i.next();
						if (t.getTokenType() == token.tokenType.RSBRACKET) { //end of array
							return jn;
						}
						if (t.getTokenType() == token.tokenType.COMMA) { //read one more element
							t = i.next();
							continue;
						}
						else {
							//raise exception;
						}
						//read a number
					}
					else if (t.getTokenType() == token.tokenType.LBRACKET) {
						//read a object
						//for (jNode jnode: readObject(i)) {
						//	arrofobjects.add(jnode);
						//}
						Document doc = readObject(i);
						//jav.add((Document) arrofobjects);
						//jav = new jArrayValue();
						jav.add(doc);
						jn.setValue(jav);
						t = i.next();
						if (t.getTokenType() == token.tokenType.RSBRACKET) { //end of array
							return jn;
						}
						if (t.getTokenType() == token.tokenType.COMMA) { //read one more element
							t = i.next();
							continue;
						}
						else {
							//raise exception;
						}
					}
					}
					
					//return null;
				}
			}
		}
		return null;
	}
	
	public Document readObject(Iterator<token> i) {
		// object has a list of nodes i.e key value pairs
		//token t = i.next();
		//List<jNode> elements = new ArrayList<jNode>();
		Document doc = new Document();
		token t = null;
		while (true) {
			//elements.add(this.readKeyValue(i));
			doc.add(this.readKeyValue(i));
			t = i.next(); // iterate and look for a ',' or a '}'
			if (t.getTokenType()==token.tokenType.COMMA) {
				//more key value pairs to read
				continue;
			}
			else if (t.getTokenType()==token.tokenType.RBRACKET) {
				//end of object
				break;
			}
		}
		return doc;
		
		
	}
	
	public void readStringValue(token t) {
		if (t.getTokenType() == token.tokenType.STRING) {
			
		}
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
