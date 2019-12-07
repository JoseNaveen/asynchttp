package jose.myjavapractice.jsonparser;

import java.util.ArrayList;
import java.util.List;

public class JSONTest {

	public static void main(String[] args) {
		Value doc = new Document();
		Value node1 = new Node();
		Key key1 = new StringKey();
		key1.setkey("jose");
		Value val1 = new StringValue();
		((StringValue)val1).setValue("naveen");
		((Node)node1).setKey(key1);
		((Node)node1).setValue(val1);
		List<Value> arr = new ArrayList<Value>();
		arr.add(node1);
		((Document)doc).setElements(arr);
		System.out.println(doc.toString());

	}

}
