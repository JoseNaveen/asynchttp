package jose.myjavapractice;

import org.json.JSONArray;
import org.json.JSONException;

public class VisitableJSONArray extends JSONArray implements Visitable{
	
	public VisitableJSONArray(String string) throws JSONException {
		super(string);
	}

	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		
	}

	public String accept(AnotherVisitor v) {
		// TODO Auto-generated method stub
		v.VisitJSONArray(this);
		return null;
	}

}
