package jose.myjavapractice;

import java.util.Deque;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotationVisitor implements AnotherVisitor{
	
	private StringBuilder sb = new StringBuilder();
	private Deque<String> q1 = new LinkedList<String>();
	public String visitDocument(Document d) {
		// TODO Auto-generated method stub
		return null;
	}

	public String visitjNode(jNode jnode) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(jnode.getKey());
		Object o = jnode.getValue();
		if ( o instanceof Visitable) {
			sb.append(((Visitable)o).accept(this));
		}
		return sb.toString();
	}

	public String visitjString(jStringValue jstringv) {
		// TODO Auto-generated method stub
		return jstringv.getStringValue();
	}

	public String visitjNodeValue(jNodeValue jnodev) {
		// TODO Auto-generated method stub
		return null;
	}

	public String visitjNumberValue(jNumberValue jnumberv) {
		// TODO Auto-generated method stub
		return jnumberv.getNumberValue().toString();
	}

	public String visitjArrayValue(jArrayValue jarrayv) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String visitJSONObject(VisitableJSONObject vjsonobj) {
		JSONArray keys = vjsonobj.names();
		//System.out.println("{");
		for (int i = 0; i < keys.length() ; i++ ) {
			String k = null;
			Object o = null;
			try {
				k = keys.get(i).toString();
				//System.out.println(k);
				o = vjsonobj.get(k);
				//System.out.println(o.getClass());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.q1.add("." + k);
			if (o instanceof JSONObject) {
				VisitableJSONObject vs = null;
				try {
					vs = new VisitableJSONObject(o.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Visitable)vs).accept(this);
			}
			if (o instanceof JSONArray) {
				VisitableJSONArray vs = null;
				try {
					vs = new VisitableJSONArray(o.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Visitable)vs).accept(this);
			}
			if ( o instanceof String) {
				this.q1.add("="+o.toString());
				System.out.println(String.join("", this.q1));
				this.q1.removeLast();
			}
			if ( o instanceof Integer) {
				this.q1.add("="+o.toString());
				System.out.println(String.join("", this.q1));
				this.q1.removeLast();
			}
			this.q1.removeLast();
			
		}
		//System.out.println("}");
		return null;
	}

	public String VisitJSONArray(VisitableJSONArray vjsonarr) {
		// TODO Auto-generated method stub
		for (int i=0; i<vjsonarr.length(); i++ ) {
			this.q1.add(".[" + i + "]");
			Object o = null;
			try {
				o = vjsonarr.get(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (o instanceof JSONObject) {
				VisitableJSONObject vs = null;
				try {
					vs = new VisitableJSONObject(o.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Visitable)vs).accept(this);
			}
			else if ( o instanceof String) {
				this.q1.add("="+o.toString());
				System.out.println(String.join("", this.q1));
				this.q1.removeLast();
			}
			else if ( o instanceof Integer) {
				this.q1.add("="+o.toString());
				System.out.println(String.join("", this.q1));
				this.q1.removeLast();
			} 
			else if ( o instanceof Double) {
				this.q1.add("="+o.toString());
				System.out.println(String.join("", this.q1));
				this.q1.removeLast();
			}
			this.q1.removeLast();
				
			
		}
		return null;
	}
	

}
