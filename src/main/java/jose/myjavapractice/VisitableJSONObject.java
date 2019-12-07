package jose.myjavapractice;

import org.json.JSONException;
import org.json.JSONObject;

public class VisitableJSONObject extends JSONObject implements Visitable{
	
	public VisitableJSONObject(String s) throws JSONException {
		super(s);
	}

	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		
	}

	public String accept(AnotherVisitor v) {
		// TODO Auto-generated method stub
		v.visitJSONObject(this);
		return null;
	}

}
