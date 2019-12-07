package jose.myjavapractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

public class JSONUtilities {
	
	public static JSONObject convertNotationToJSON(String notation) throws JSONException {
		String[] keyvalue = notation.split("=");
		String[] outer = notation.split("\\.");
		Pattern p = Pattern.compile("\\[\\d*\\]");
    	Matcher m;
    	int index;
    	JSONArray jarray = new JSONArray();
    	JSONObject jobject = new JSONObject();
    	JSONObject jtmpObj = null;
    	String key;
    	
    	for (int i=1;i<=outer.length;i++) {
    		jobject = new JSONObject();
    		
			if (outer[outer.length - i].split("=").length > 0) {

				if (outer[outer.length - i].split("=")[0].contains("[")) {
					m = p.matcher(outer[outer.length - i].split("=")[0]);
					if (m.find(0)) {
						index = Integer.parseInt(m.group(0).replaceAll("\\D+", ""));
						if (jtmpObj == null) {
							jarray.put(index, outer[outer.length - i].split("=")[1]);
						}
					}
					key = outer[outer.length - i].split("=")[0].replaceAll("\\[\\d*\\]", "");
					jobject.put(key, jarray);
					jtmpObj = new JSONObject(jobject.toString());
					System.out.println("here");
					continue;
				}
			}
			if (outer[outer.length-i].contains("[")) {
    			m = p.matcher(outer[outer.length-i]);
    			if (m.find(0)) {
    				index = Integer.parseInt(m.group(0).replaceAll("\\D+",""));
    				if (jtmpObj == null) {
    					
    				}
    				else {
    					jarray.put(index, new JSONObject(jtmpObj));
    				}
    			}
    			key = outer[outer.length-i].replaceAll("\\[\\d*\\]", "");
    			jobject.put(key, jarray);
    			jtmpObj = new JSONObject(jobject.toString());
    		}
    		else {
    			jobject.put(outer[outer.length-i], new JSONObject(jtmpObj.toString()));
    			jtmpObj = new JSONObject(jobject.toString());
    		}
    		
    		System.out.println("here1");
    		
    	}
    	return jtmpObj;
	}
	
	public static List<JSONObject> updateOrAddJSON(List<JSONObject> list,String notation) {
		for (JSONObject j: list) {
			String[] outer = notation.split("\\.");
			
		}
		
		return null;
	}
	
	public static void updateObject(JSONObject jobj, String notation) throws JSONException {
		String[] outer = notation.split("\\.");
		Iterator it = jobj.keys();
		while (it.hasNext()) {
			if (it.next() == outer[0]) {
				StringBuilder sb = new StringBuilder();
				List<String> notationList = Arrays.asList(outer);
				Iterator<String> notIt = notationList.iterator();
				if (notIt.hasNext()) {
					notIt.next();
				}
				while (notIt.hasNext()) {
					sb.append(notIt.next());
					if (notIt.hasNext()) {
						sb.append(".");
					}
				}
				
				updateObject(jobj.getJSONObject(outer[0]),sb.toString());
			}
		}
		
	}
	public static void main(String[] args) throws JSONException {
		
		String notation = "a.b.c[1]=something";
		
		String notation1 = "a.b.c[0]=somethingelse";
		
		JSONObject jtmpObj = JSONUtilities.convertNotationToJSON(notation);
		
		
		System.out.println(jtmpObj.toString(2));
		
		Object tmp = new JSONObject(jtmpObj.toString());;
		JSONObject n = new JSONObject(jtmpObj.toString());
		
		List<JSONObject> j = new ArrayList<JSONObject>();
		j.add(new JSONObject(jtmpObj.toString()));
		
		System.out.println(j.toString());
		
		final String prefix = "$.";
		
		String jsonPathNotation = prefix + notation.replaceAll("\\[\\d+\\]", "\\[\\*\\]").split("=")[0];
		
		System.out.println(jsonPathNotation);
		
		List<String> value = JsonPath.read(jtmpObj.toString(), jsonPathNotation);
		System.out.println(value);
		
		for (String s: value) {
			if (notation.split("=")[1].equals(s)) {
				System.out.println("Matched");
			}
		}
		
		
		/*JSONObject j = new JSONObject().put(outer[outer.length-1].split("=")[0],outer[outer.length-1].split("=")[1]);
		
		
		System.out.println(j.toString());
		for (int i = keyvalue.length-1; i>=0; i-- ) {
			System.out.println(keyvalue[i]);
		}


		for (int i = outer.length-1; i>=0; i-- ) {
			System.out.println(outer[i]);
		}*/

		
		
		
		
		
		
	}

}
