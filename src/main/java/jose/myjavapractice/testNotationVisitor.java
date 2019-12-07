package jose.myjavapractice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class testNotationVisitor {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String example = "{\"a\": \"1\","
				+ "\"b\": {\"c\": 5,\"e\":\"some\"},"
				+ "\"d\":[\"hello\",\"world\"],"
				+ "\"f\":[{\"g\":[{\"some\":\"text\",\"some1\":\"text\"},"
				+ "{\"some1\":\"text1\",\"some\":\"text\"}],\"h\":[{\"some2\":\"text2\",\"some\":\"text\"},"
				+ "{\"some1\":\"text1\",\"some\":\"text\"}]}],"
				+ "\"k\":[{\"g\":{\"m\":11}}]"
				+ "}";
		String examplearray = "[{\"a\": \"1\",  \"b\": {\"c\": 5},\"d\":[\"hello\",\"world\"]}]";
		//System.out.println(new JSONArray(examplearray).toString(2));
		System.out.println(new JSONObject(example).toString(2));
		VisitableJSONObject vjsonobj = null;
		try {
			vjsonobj = new VisitableJSONObject(example);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NotationVisitor nv = new NotationVisitor();
		nv.visitJSONObject(vjsonobj);

	}

}
