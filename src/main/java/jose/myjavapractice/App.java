package jose.myjavapractice;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.*;

import com.mongodb.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.cedarsoftware.util.io.JsonWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
	String[] myarray = new String[3];
	static MongoClient mongo;
	static JSONObject routing_indicator = new JSONObject();
	
	
	static JSONObject pei = new JSONObject();
	static JSONObject secu_cont = new JSONObject();
	static JSONObject mm_context = new JSONObject();
	static JSONObject user_defined = new JSONObject();
	static JSONObject conn_info = new JSONObject();
	static Deque<String> queue1 = new LinkedList<String>();
	static Deque<String> queue2 = new LinkedList<String>();
	static Deque<String> aqueue = new LinkedList<String>();
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_RESET = "\u001B[0m";
	public String jsonPath = "$.";
	public static String mobile_Context_Verification[] = new String[] {
			"access-and-mobility-subscription-data/nssai/default-single-nssais",
			"access-and-mobility-subscription-data/subscribed-ue-ambr",
			 "access-and-mobility-subscription-data/gpsi-list",
			 "conn-info/user-location-info/nr-location-info/tai",
			 "conn-info/user-location-info/nr-location-info/nr-cgi",
			 "conn-info/user-location-info/nr-location-info/ue-location-timestamp",
			 "mm-context-3gpp/tai-list",
			 "security-context",
			 "smf-selection-subscription-data/subscribed-snssai-info/single-nssais",
			 "smf-selection-subscription-data/subscribed-snssai-info/dnn-info","udm-information"
			 };
	public static String mobile_Context_Verification_default_keys[] = new String[] {
			"slice-service-type=1",
			"slice-differentiator=0000a2",
			"uplink=400 Mbps",
			"rfsp-index=1",
			"gpsi=msisdn-919725551212",
    		"location-type=nr",
    		"plmn=214001",
    		"tac=100001",
    		"cell-id=1",
    		"rrc-establishment-cause=0",
    		"routing-indicator=1234",
    		"5g-mm-capability=03000000000000000000000000",
    		"ue-security-capability=80404040",
    		"pei=imeisv-0010011234567191",
    		"selected-integrity-algorithm=snow-3g",
    		"selected-ciphering-algorithm=null",
    		"security-context-status=integrity-and-ciphering",
    		"dnn=5ghomer.affirmednetworks.tx.com",
    		"default-dnn-indicator=1"};
	public static void updateNestedKey(String ...strings) throws JSONException {
		String[] outerKeyValue,keys;
		for (int i=0; i<strings.length; i++ ) {
			outerKeyValue = strings[i].split("=");
			if (outerKeyValue[0].contains(".")) {
				System.out.println("Processing a nested key structure: "+ outerKeyValue[0]);
				keys = outerKeyValue[0].split("\\.");
				System.out.println(keys[0]);
				System.out.println(keys[1]);
				if (keys[0].equals("security-context")) {
					if (keys[1].equals("selected-integrity-algorithm")) {
						System.out.println(secu_cont.getJSONObject("security-context").getString("selected-integrity-algorithm"));
						secu_cont.getJSONObject("security-context").put("selected-integrity-algorithm",outerKeyValue[1]);
						System.out.println(secu_cont.getJSONObject("security-context").getString("selected-integrity-algorithm"));
					}
					if (keys[1].equals("security-context-status")) {
						System.out.println(secu_cont.getJSONObject("security-context").getString("security-context-status"));
						secu_cont.getJSONObject("security-context").put("security-context-status",outerKeyValue[1]);
						System.out.println(secu_cont.getJSONObject("security-context").getString("security-context-status"));
					}
					if (keys[1].equals("selected-ciphering-algorithm")) {
						System.out.println(secu_cont.getJSONObject("security-context").getString("selected-ciphering-algorithm"));
						secu_cont.getJSONObject("security-context").put("selected-ciphering-algorithm",outerKeyValue[1]);
						System.out.println(secu_cont.getJSONObject("security-context").getString("selected-ciphering-algorithm"));
					}
				}
			continue;
			}
			
		}
	}
	public static String updatekey(String ... strings) throws JSONException {
		if (strings.length==0) {
			System.out.println("Cannot process empty string for updatekey() method");
			return "error";
		}
		String[] keyValue,outerKeyValue,keys;
		//System.out.println("Strings input length: " + strings.length);
		for ( int i=0; i<strings.length; i++ ) {
			keyValue = strings[i].split("=");
			outerKeyValue = strings[i].split("=");
			if (outerKeyValue[0].contains(".")) {
				keys = outerKeyValue[0].split("\\.");
				if (keys[0].equals("security-context")) {
					if (keys[1].equals("selected-integrity-algorithm")) {
						secu_cont.getJSONObject("security-context").put("selected-integrity-algorithm",outerKeyValue[1]);
					}
					if (keys[1].equals("security-context-status")) {
						secu_cont.getJSONObject("security-context").put("security-context-status",outerKeyValue[1]);
					}
					if (keys[1].equals("selected-ciphering-algorithm")) {
						secu_cont.getJSONObject("security-context").put("selected-ciphering-algorithm",outerKeyValue[1]);
					}
				}
				if (keys[0].equals("mm-context-3gpp")) {
					if (keys[1].equals("ue-security-capability")) {
						mm_context.getJSONObject("mm-context-3gpp").put("ue-security-capability",outerKeyValue[1]);
					}
					if (keys[1].equals("5g-mm-capability")) {
						mm_context.getJSONObject("mm-context-3gpp").put("5g-mm-capability",outerKeyValue[1]);
					}
					
				}
				if (keys[0].equals("conn-info")) {
					if (keys[1].equals("user-location-info")) {
						if (keys[2].equals("location-type")) {
							conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").put("location-type",outerKeyValue[1]);
						}
						if (keys[2].equals("nr-location-info")) {
							if (keys[3].equals("nr-cgi")) {
								if (keys[4].equals("cell-id")) {
									conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").getJSONObject("nr-location-info").
									getJSONObject("nr-cgi").put("cell-id",outerKeyValue[1]);
									
								}
								
							}
							if (keys[3].equals("tai")) {
								if (keys[4].equals("plmn")) {
									conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").
									getJSONObject("nr-location-info").getJSONObject("tai").put("plmn",outerKeyValue[1]);
								}
								if (keys[4].equals("tac")) {
									conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").
									getJSONObject("nr-location-info").getJSONObject("tai").put("tac",outerKeyValue[1]);									
								}
								
							}
							
						}
						
					}
					if (keys[1].equals("rrc-establishment-cause")) {
						conn_info.getJSONObject("conn-info").put("rrc-establishment-cause",outerKeyValue[1]);
					}
				}
			continue;
			}
			if (keyValue[0].equals("routing-indicator")) {
				routing_indicator.put("routing-indicator",keyValue[1]);
				continue;
			}
			if (keyValue[0].contentEquals("pei")) {
				pei.put("pei",keyValue[1]);
				continue;
			}
			
			JSONObject temp = new JSONObject();
			user_defined.put(keyValue[0], keyValue[1]);
			//user_defined.put(temp);
		}
		return "processed";
	}
    public static void main( String[] args ) throws IOException, JSONException
    {
    	App app = new App();
        System.out.println( "Hello World!" );
        System.out.println(mobile_Context_Verification[1]);
        JSONObject defaultQueries = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject arrayElement = new JSONObject();
        arrayElement.put("slice-service-type","1");
        arrayElement.put("slice-differentiator","0000a2");
        
        JSONObject default_single_nssais = new JSONObject();
        array.put(arrayElement);
        //array.add(arrayElement);
        default_single_nssais.put("default-single-nssais",array);
        
        JSONObject nssai = new JSONObject();
        nssai.put("nssai",default_single_nssais);
        
        JSONObject access_and_mobility_subscription_data = new JSONObject();
        access_and_mobility_subscription_data.put("access-and-mobility-subscription-data",nssai);
        
        JSONObject subscribed_ue_ambr = new JSONObject();
        subscribed_ue_ambr.put("uplink","400 Mbps");
        access_and_mobility_subscription_data.put("subscribed-ue-ambr",subscribed_ue_ambr);
        
        JSONObject user_location_info = new JSONObject();
        user_location_info.put("location-type","nr");
        
        
        
        
        JSONObject tai = new JSONObject();
       
        tai.put("tac","100001");
        
        
        tai.put("plmn","214001");
        
        JSONObject nr_cgi = new JSONObject();
        nr_cgi.put("cell-id","1");
        
       
        JSONObject nr_location_info = new JSONObject();
        nr_location_info.put("tai",tai);
        nr_location_info.put("nr-cgi",nr_cgi);
        
        //JSONObject conn_info = new JSONObject();
        //user_location_info.put("user-location-info",location_type);
        user_location_info.put("nr-location-info",nr_location_info);
        
        JSONObject ulinfo = new JSONObject();
        ulinfo.put("user-location-info",user_location_info);
        defaultQueries.put("access-and-mobility-subscription-data",nssai);
        defaultQueries.put("conn-info",ulinfo);
        defaultQueries.put("rrc-establishment-cause", "mo_Signalling");
        
        JSONObject rrc = new JSONObject();
        rrc.put("rrc-establishment-cause", "mo_Signalling");
        //conn_info.put()
        ulinfo.put("rrc-establishment-cause", "mo_Signalling");
       
        conn_info.put("conn-info", ulinfo);
        defaultQueries.put("routing-indicator","1234");
        System.out.println(conn_info.toString(4));
        
        JSONObject mm_context_3gpp = new JSONObject();
        mm_context_3gpp.put("5g-mm-capability","03000000000000000000000000");
        mm_context_3gpp.put("ue-security-capability","80404040");
        defaultQueries.put("mm-context-3gpp",mm_context_3gpp);
        mm_context.put("mm-context-3gpp",mm_context_3gpp);
        defaultQueries.put("pei","imeisv-0010011234567191");
        
        
        
        JSONObject security_context = new JSONObject();
        security_context.put("selected-integrity-algorithm","snow-3g");
        security_context.put("selected-ciphering-algorithm","null");
        security_context.put("security-context-status","integrity-and-ciphering");
        defaultQueries.put("security-context",security_context);
        secu_cont.put("security-context",security_context);
        
        JSONArray dnn_info = new JSONArray();
        JSONObject e_one = new JSONObject();
        e_one.put("dnn","5ghomer.affirmednetworks.tx.com");
        e_one.put("default-dnn-indicator","1");
        dnn_info.put(e_one);
        //dnn_info.add(e_one);
        
        JSONObject dinfo = new JSONObject();
        dinfo.put("dnn_info",dnn_info);
        JSONArray subscribed_snssai_info = new JSONArray();
        subscribed_snssai_info.put(dinfo);
        //subscribed_snssai_info.add(dinfo);
        
        JSONObject smf_selection_subscription_data = new JSONObject();
        smf_selection_subscription_data.put("subscribed-snssai-info",subscribed_snssai_info);
        
        defaultQueries.put("smf-selection-subscription-data",smf_selection_subscription_data);
        
        
        
        
        
        /*StringWriter out = new StringWriter();
        access_and_mobility_subscription_data.writeJSONString(out);

    	String jsonText = out.toString();
    	System.out.print(jsonText);
    	*/
        //String jsonString = "json_string_plain_text";
        //System.out.println(JsonWriter.formatJson(defaultQueries.toString()));
        
        /*
        defaultQueries.put("rfsp-index","1");
        defaultQueries.put("gpsi","");
        
        
        
        defaultQueries.put("rrc-establishing-cause","0");
        */
        routing_indicator.put("routing-indicator","1234");
        pei.put("pei","imeisv-0010011234567191");
        
        
        /*
        MongoDBClient mongoClient = new MongoDBClient("10.15.10.46",27017);
        BasicDBObject fullQuery = new BasicDBObject();
        fullQuery.put("supi","imsi-31041079331635");
        MongoDatabase database = mongoClient.mongo.getDatabase("amf_FED_AMF-1");
    	FindIterable<Document> cursor = database.getCollection("amf-call-control_mobile-context").find(fullQuery);
    	MongoCursor<Document> it = cursor.iterator();
    	String jsonString = null;
    	JSONObject query_result = null;
    	*/
        
        
    	/*
    	JSONObject b = new JSONObject();
    	b.put("c","10");
    	b.put("g", "13");
    	JSONObject a = new JSONObject();
    	a.put("b", b);
    	a.put("d", "11");
    	a.put("f", "12");
    	*/
    	
    	JSONObject top = new JSONObject();
    	/*
    	top.put("a", a);
    	top.put("h", "14");
    	JSONObject i = new JSONObject();
    	i.put("j","15");
    	i.put("k", "16");
    	top.put("i", i);
    	*/
    	top = new JSONObject();
    	JSONObject d  = new JSONObject();
    	d.put("d", 11);
    	JSONArray c = new JSONArray();
    	c.put(d);
    	JSONObject ef = new JSONObject();
    	ef.put("e", 12);
    	ef.put("f", 13);
    	c.put(ef);
    	JSONObject b = new JSONObject();
    	b.put("c", c);
    	b.put("g", 14);
    	JSONObject a = new JSONObject();
    	a.put("b", b);
    	a.put("h", 15);
    	top.put("a", a);
    	top.put("i", 16);
    	System.out.println(top.toString(4));
    	
    	
    	JSONObject top2 = new JSONObject();
    	d  = new JSONObject();
    	d.put("d", 17);
    	c = new JSONArray();
    	c.put(d);
    	ef = new JSONObject();
    	ef.put("e", 12);
    	ef.put("f", 13);
    	ef.put("d", 11);
    	c.put(ef);
    	b = new JSONObject();
    	b.put("c", c);
    	b.put("g", 14);
    	a = new JSONObject();
    	a.put("b", b);
    	a.put("h", 15);
    	top2.put("a", a);
    	top2.put("i", 16);
    	
    	System.out.println(top2.toString(4));
    	
    	
    	
    	
    	
    	/*
    	try {
    		jsonString = it.next().toJson();
    		query_result = new JSONObject(jsonString);
    		//recursiveParser(query_result);
    		//System.out.println("Printing record from local database");
    		System.out.println(query_result.toString(4));
    	} catch (Exception e) {
    		System.out.println("Query failed. Unable to find record");
    	}
    	*/
    	String test = "Jose[4]";
    	//System.out.println(test.matches("([a-zA-Z,0-9,\\-]*)\\[\\d*\\]"));
    	//app.recursiveParser(top,top2,false,null);
    	//recursiveParser(top,top,false,null);
    	
    	//checkRoutingIndicator(query_result);
    	//updatekey("routing-indicator=1232");
    	//checkRoutingIndicator(query_result);
    	//checkPEI(query_result);
    	//updatekey("ue-state=connected");
    	//updatekey("ue-state1=connected1");
    	//System.out.println(user_defined);
    	//checkUserDefined(query_result);
    	//checkSecurityContext(query_result);
    	//checkMMContext3GPP(query_result);
    	//System.out.println("Before Update Operation");
    	//System.out.println(mm_context.getJSONObject("mm-context-3gpp").getString("5g-mm-capability"));
    	//updatekey("security-context.security-context-status=josenaveen");
    	//updatekey("mm-context-3gpp.ue-security-capability=8080");
    	//updatekey("mm-context-3gpp.5g-mm-capability=00");
    	//System.out.println("After Update Operation");
    	//System.out.println(mm_context.getJSONObject("mm-context-3gpp").getString("5g-mm-capability"));
    	//checkMMContext3GPP(query_result);
    	//checkConnInfo(query_result);
    	//String jsonString = it.next().toJson();
    	//System.out.println(jsonString);
    	
    	//System.out.println(JsonWriter.formatJson(jsonString.toString()));
    	/*
    	JSONObject ams_data = new JSONObject();
    	ams_data.put("rfsp-index",1);
    	JSONObject access_and_mobility_subscription_data1 = new JSONObject();

    	JSONObject uplink = new JSONObject();
    	uplink.put("uplink","400 Mbps");
    	ams_data.put("subscribed-ue-ambr", uplink);
    	access_and_mobility_subscription_data1.put("access-and-mobility-subscription-data", ams_data);
    	updatekey("ue-state=connected");
    	recursiveParser(secu_cont,query_result,false,null);
    	recursiveParser(conn_info,query_result,false,null);
    	recursiveParser(mm_context,query_result,false,null);
    	recursiveParser(user_defined,query_result,false,null);
    	recursiveParser(routing_indicator,query_result,false,null);
    	recursiveParser(pei,query_result,false,null);
    	updatekey("ue-state=connected1");
    	updatekey("security-context.security-context-status=josenaveen");
    	updatekey("mm-context-3gpp.ue-security-capability=8080");
    	updatekey("mm-context-3gpp.5g-mm-capability=00");
    	updatekey("routing-indicator=1223234");
    	updatekey("pei=1223234");
        System.out.println("After Update Operation");
    	recursiveParser(secu_cont,query_result,false,null);
    	recursiveParser(mm_context,query_result,false,null);
    	recursiveParser(user_defined,query_result,false,null);
    	recursiveParser(routing_indicator,query_result,false,null);
    	recursiveParser(pei,query_result,false,null);
    	System.out.println(access_and_mobility_subscription_data1.toString(4));
    	recursiveParser(access_and_mobility_subscription_data1,query_result,false,null);
    	updatekey("conn-info.user-location-info.nr-location-info.tai.tac=100010");
    	recursiveParser(conn_info,query_result,false,null);
    	*/
    	
    	//recursiveParser(user_defined,query_result,false,null);
    	
    	/*
    	recursiveParser(defaultQueries);
    	System.out.println("=====================================================");
    	
    	mongoClient = new MongoDBClient("127.0.0.1",27017);
    	database = mongoClient.mongo.getDatabase("test");
    	DBObject dbObject = (DBObject)JSON.parse(jsonString);
    	MongoCollection<Document> collection = database.getCollection("jose");
    	if (jsonString!=null) {
    		Document record = Document.parse(jsonString);
        	collection.insertOne(record);    	
    	}
    	*/
    	String uri = "http://"+"10.15.15.104"+":"+"30000"+"/nnssf-nsselection/v1/network-slice-informations";
    	String[] uriPart = uri.split("//");
    	System.out.println(uriPart[1]);
    	String apiName = uriPart[1].split("/")[1];
    	System.out.println(apiName);
    	String apiVersion = uriPart[1].split("/")[2];
    	System.out.println(apiVersion);
    	String[] apiFull = uri.split("//")[1].split("/");
    	String[] full = Arrays.copyOfRange(apiFull, 1, apiFull.length);
    	System.out.println(String.join("/", full));
    	System.out.println(apiFull[2]);
    	if (apiFull[2].matches("v(0-9)")) {
    		System.out.println("true");
    	} 
    	else {
    		System.out.println("False");
    	}
    	String s = "amf-cc      3/3     Running    0   52m";
    	String s1 = s.replaceAll("\\s+", " ");
    	System.out.println(s1.split(" ")[3]);
    	
    	
    }
    public static void fetchAndValidate() {
    	
    }
    public static void recursiveCheck() {
    	
    	
    }
    public static void checkConnInfo(JSONObject query_result) throws JSONException {
    	JSONArray first_level_keys = conn_info.names();
    	JSONArray second_level_keys;
    	String actual=null,expected=null;
    	System.out.println(first_level_keys.toString());
    	for ( int i = 0; i<first_level_keys.length(); i++ ) {
    		if (first_level_keys.getString(i).equals("conn-info")) {
    			JSONArray cinfokeys = conn_info.getJSONObject("conn-info").names();
    			System.out.println(cinfokeys.toString());
    			for (int j=0; j<cinfokeys.length();j++) {
    				if ( cinfokeys.getString(j).equals("user-location-info")) {
    					JSONArray uinfokeys = conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").names();
    					System.out.println(uinfokeys.toString());
    					for (int k=0; k<uinfokeys.length();k++) {
    						if (uinfokeys.getString(k).equals("location-type")) {
    							expected = conn_info.getJSONObject("conn-info").getJSONObject("user-location-info").getString("location-type").toString();
    							if (query_result.has("conn-info")) {
    								if (query_result.getJSONObject("conn-info").has("user-location-info")) {
    									if (query_result.getJSONObject("conn-info").getJSONObject("user-location-info").has("location-type")) {
    										actual = query_result.getJSONObject("conn-info").getJSONObject("user-location-info").getString("location-type").toString();
    										if (expected.equals(actual)) {
    											System.out.println("conn-info.user-location-info.location-type value matched");
    											System.out.println("Expected: "+ expected + " Actual: " + actual);
    										}
    										else {
    											System.out.println("conn-info.user-location-info.location-type value not matched");
    											System.out.println("Expected: "+ expected + " Actual: " + actual);
    										}
    									}
    									else {
    										System.out.println("ERROR conn-info.user-location-info.location-type key not found in query record");
    									}
    								}
    								else {
    									System.out.println("ERROR conn-info.user-location-info key not found in query record");
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }
    public static void checkMMContext3GPP(JSONObject query_result) throws JSONException {
    	JSONArray first_level_keys = mm_context.names();
    	//System.out.println("First level keys: " + first_level_keys);
    	JSONArray second_level_keys;
    	String actual=null,expected=null;
    	for ( int i=0; i<first_level_keys.length(); i++) {
    		second_level_keys = mm_context.getJSONObject(first_level_keys.getString(i)).names();
    		
    		//System.out.println("Second Level Keys: " + second_level_keys);
    		for ( int j=0; j<second_level_keys.length(); j++) {
    			if (query_result.has(first_level_keys.getString(i))) {
    				//System.out.println("Record has the first level key: "+ first_level_keys.getString(i));
    				if (query_result.getJSONObject(first_level_keys.getString(i)).has(second_level_keys.getString(j))){
    					//System.out.println("Record has the second level key: " + second_level_keys.getString(j));
    					expected = mm_context.getJSONObject(first_level_keys.getString(i)).getString(second_level_keys.getString(j));
    					actual = query_result.getJSONObject(first_level_keys.getString(i)).getString(second_level_keys.getString(j));
    				if (actual.equals(expected)) {
    					System.out.println(first_level_keys.getString(i) + "." + second_level_keys.getString(j) + " values matched");
    					System.out.println("Expected: "+ expected + " Actual: "+ actual);
    				}
    				else {
    					System.out.println(first_level_keys.getString(i) + "." + second_level_keys.getString(j) + " values dont match");
    					System.out.println("Expected: "+ expected + " Actual: "+ actual);
    				}
    				}
    			}
    		}
    	}
    }
    public static void checkSecurityContext(JSONObject query_result) throws JSONException {
    	JSONArray first_level_keys = secu_cont.names();
    	System.out.println(first_level_keys);
    	JSONArray second_level_keys;
    	String actual,expected;
    	for ( int i=0; i<first_level_keys.length(); i++) {
    		second_level_keys = secu_cont.getJSONObject(first_level_keys.getString(i)).names();
    		System.out.println(second_level_keys);
    		for ( int j=0; j<second_level_keys.length(); j++) {
    			if (query_result.has(first_level_keys.getString(i))) {
    				if (query_result.getJSONObject(first_level_keys.getString(i)).has(second_level_keys.getString(j))){
    				expected = secu_cont.getJSONObject(first_level_keys.getString(i)).getString(second_level_keys.getString(j));
    				actual = query_result.getJSONObject(first_level_keys.getString(i)).getString(second_level_keys.getString(j));
    				if (actual.equals(expected)) {
    					System.out.println(first_level_keys.getString(i) + "." + second_level_keys.getString(j) + " values matched");
    					System.out.println("Expected: "+ expected + " Actual: "+ actual);
    				}
    				else {
    					System.out.println(first_level_keys.getString(i) + "." + second_level_keys.getString(j) + " values dont match");
    					System.out.println("Expected: "+ expected + " Actual: "+ actual);
    				}
    				}
    			}
    		}
    	}
    }
    public static void checkPEI(JSONObject query_result) throws JSONException {
    	JSONArray r_names = pei.names();
    	Object o;
    	for ( int i=0; i<r_names.length(); i++) {
    		o = pei.getString(r_names.getString(i));
    		System.out.println("Printing local object");
    		System.out.println(o);
    		if (query_result.has(r_names.getString(i))) {
    			System.out.println("Printing from queried result");
    			
    			System.out.println(query_result.getString(r_names.getString(i)));
    			if (o.equals(query_result.get(r_names.getString(i)))) {
    				System.out.println("values matched");
    			}
    			else {
    				System.out.println("PEI doesnt match");
    			}
    		}
    	}
    }
    
    public static void checkRoutingIndicator(JSONObject query_result) throws JSONException {
    	JSONArray r_names = routing_indicator.names();
    	Object o;
    	for ( int i=0; i<r_names.length(); i++) {
    		o = routing_indicator.getString(r_names.getString(i));
    		System.out.println("Printing local object");
    		System.out.println(o);
    		if (query_result.has(r_names.getString(i))) {
    			System.out.println("Printing from queried result");
    			
    			System.out.println(query_result.getString(r_names.getString(i)));
    			if (o.equals(query_result.get(r_names.getString(i)))) {
    				System.out.println("values matched");
    			}
    			else {
    				System.out.println("Routing indicator doesnt match");
    			}
    		}
    	}
    }
    /*
    public static void checkUserDefined(JSONObject query_result) throws JSONException {
    	JSONArray r_names;
    	Object o;
    	for (int i=0; i<user_defined.length(); i++) {
    		r_names = user_defined.getJSONObject(i).names();
    		for (int j = 0; j<r_names.length(); j++ ) {
    			o = user_defined.getJSONObject(i).getString(r_names.getString(j));
    			if (query_result.has(r_names.getString(j))) {
    				if (o.equals(query_result.get(r_names.getString(j)))) {
    					System.out.println(r_names.getString(j) + " values matched");
    				}
    				else {
        				System.out.println(r_names.getString(j) + " doesnt match");
        			}
    			}
    			else {
    				System.out.println(r_names.getString(j) + " is not present in query result");
    			}
    		}
    	}
    }
    */
    private Integer depth = 0;
    public void recursiveParser(JSONArray jsonArray,JSONObject query_result,boolean compare) throws JSONException {
    	System.out.println("Method for array called");
    	String current = null;
    	String temp = null;
    	depth = 0;
    	Deque<String> tempQueue = new LinkedList<String>();
    	//get the identity of the key
    	current = queue1.removeLast();
    	//queue1.add(current+"["+0+"]");
    	for (int i = 0; i<jsonArray.length(); i++ ) {
    		if (jsonArray.get(i) instanceof JSONObject) {
    			if ( jsonArray.getJSONObject(i).length() > 0) {
    				
    				//modify the key to denote that we are processing an array
    				
    			    System.out.println("Modifying key to denote that an array is processed");
    			    queue1.add(current+"["+i+"]");
    			    System.out.println("Current queue state: "+ queue1);
    			    //queue1.removeLast();
    			    recursiveParser(jsonArray.getJSONObject(i),query_result,true,null);
    			    //queue1.add(current);
    			    //System.out.println("Inside JSONArray Adding to queue: " + current);
    			    depth = 0;
    			}
    		} else if (jsonArray.get(i) instanceof JSONArray) {
    			current = queue1.removeLast();
				queue1.add(current+"["+i+"]");
    			recursiveParser(jsonArray.getJSONArray(i),query_result,compare);
    		}
    		else {
    			//System.out.println(jsonArray.get(i) + " is a string");
    		}
    	//remove the key from the queue after this instance of loop is processed. Next loop iteration will restore the key
    	//System.out.println("Remove element after cuurent loop processed");
   		queue1.removeLast();
   		//System.out.println("Current queue state: "+ queue1);
    	}
    	
    	
    }
    public String recursiveParser(JSONObject jsonObject,JSONObject query_result, boolean compare,String parent) throws JSONException {
    	JSONArray keys = jsonObject.names();
    	Object o;
    	//System.out.println(jsonObject.length());
    	String actual,expected;
    	boolean r;
    	if (compare) {
    		depth = depth + 1;
    	}
    	System.out.println("Current object has keys: "+keys.toString(3));
    	for (int i = 0; i<keys.length();i++) {
         	o = jsonObject.get(keys.getString(i));
    		if (o instanceof JSONObject ) {
    			System.out.println(keys.getString(i) + " is a JSONObject");
    			if ( jsonObject.getJSONObject(keys.getString(i)).length() > 0 ) {
    				System.out.println("putting in queue: "+  keys.getString(i));
    				queue1.add(keys.getString(i));
    				//System.out.println(jsonObject.getJSONObject(keys.getString(i)).toString(4));
    				System.out.println("Current queue state: "+queue1);
    				recursiveParser(jsonObject.getJSONObject(keys.getString(i)),query_result,false,keys.getString(i));
    				System.out.println("If o is JSONObject Removing from queue: " + queue1.removeLast());
    				System.out.println("Queue status now: "+queue1);
    			}
            }
    		else if (o instanceof JSONArray ) {
    			System.out.println(keys.getString(i) + " is a JSONArray");
    			//System.out.println(jsonObject.getString(keys.getString(i)));
    			System.out.println("putting in queue: "+  keys.getString(i));
    			queue1.add(keys.getString(i));
    			System.out.println("Current queue state: "+queue1);
    			recursiveParser(jsonObject.getJSONArray(keys.getString(i)),query_result,false);
    			//System.out.println("if o is JSONArray Removing from queue: " + queue1.removeLast());
    		}
    		else {
    			queue1.add(keys.getString(i));
    			System.out.println("Current queue state: "+queue1);
    			System.out.println("putting in queue: "+  keys.getString(i));
    			System.out.println(keys.getString(i) + " is a STRING");
    			System.out.println(jsonObject.getString(keys.getString(i)));
    			//actual = findValue(query_result);
    			expected = jsonObject.getString(keys.getString(i));
    			System.out.println("$." + String.join(".", queue1)+"=="+jsonObject.getString(keys.getString(i)));
    			actual = findValue(query_result,expected,false);
    			/*
    			if (expected.equals(actual)) {
    				//System.out.println(queue2);
    				
    				System.out.println(String.join(".", queue2) + " Matched");
    			}
    			else {
    				//System.out.println(queue2);
    				
    				System.out.println(String.join(".", queue2) + " Not matched");
    			}
    			*/
    			queue1 = new LinkedList<String>(queue2);
    			queue2.clear();
    			System.out.println("New queue1 now:" + queue1);
    			System.out.println("New queue2 now: "+ queue2);
    			System.out.println("if o is neither object or array Removing from queue: " + queue1.removeLast());
    			
    		}
    	    
    	}
    	System.out.println("After processing");
    	System.out.println("current object keys: "+keys.toString(3));
    	System.out.println("JSONObject end queue1 now: "+queue1);
    	System.out.println("JSONObject end queue2 now: "+queue2);
		return "true";
    	
    }
    public static String findValue(JSONObject result,String expected,boolean restore) throws JSONException {
    	String key = null,value=null;
    	String sub = null;
    	Integer len = 0;
    	System.out.println("Before removal Queue1: "+ queue1);
    	System.out.println("Before removal Queue2: "+ queue2);
    	key = queue1.removeFirst();
    	queue2.add(key);
    	System.out.println("After removal Queue1: "+ queue1);
    	System.out.println("After removal Queue2: "+ queue2);
    	System.out.println("Got "+key+" key from the queue");
    	Pattern p = Pattern.compile("\\[\\d*\\]");
    	Matcher m = p.matcher(key);
    	int index = 0;
    	if (m.find()) {
    		
    		System.out.println("matched: "+m.group(0).replaceAll("\\D+",""));
    		index = Integer.parseInt(m.group(0).replaceAll("\\D+",""));
    		
    	}
    	
    	if (key.matches("([a-zA-Z,0-9,\\-]*)\\[\\d*\\]")) {
    		System.out.println("We seem to be processing an array... wat to do..");
    		
    		
    		if (queue1.isEmpty()) {
    			sub = key.replaceAll("\\[\\d*\\]", "");
    			len = result.getJSONArray(sub).length();
    			for (int i=0; i<len; i++) {
    				System.out.println("calling for index: " + i); 
    				value = findValue(result.getJSONArray(sub),i,expected);
    				if (value.equals("true")) {
    					break;
    				}
    				System.out.println("iteration on index: " + i + " completed. Proceeding...");
    			}
    			System.out.println("End of array");
    			
    			//return findValue(result.getJSONArray(sub),index,expected);
    		}
    		else {
    			sub = key.replaceAll("\\[\\d*\\]", " ");
    			len = result.getJSONArray(sub).length();
    			System.out.println("Length of the array is " + len);
    			for (int i=0;i<len; i++) {
    				System.out.println("calling for index: " + i); 
    				value = findValue(result.getJSONArray(sub),i,expected);
    				if (value.equals("true")) {
    					return "true";
    					
    				}
    				System.out.println("iteration on index: " + index + " completed. Proceeding...");
    				
    			}
    			System.out.println("End of array");
    		}
    	}
    	//System.out.println(result.toString(4));
    	if (result.has(key)) {
    		if (queue1.isEmpty()) {
    			String output = result.getString(key);
    			if (output.equals(expected)) {
    				System.out.println(String.join(".", queue2) + " matched");
    				System.out.println(ANSI_RED_BACKGROUND + "Expected: "+expected+" Actual: "+output+ANSI_RESET);
    				System.out.println(String.join(".", queue1) + " Queue1 from matched");
        			System.out.println(String.join(".", queue2) + " Queue2 from matched");
    				
    				if (restore) {
    					queue1.add(key);
    					System.out.println("Restoring key: "+key);
    					//queue2.removeLast();
    				}
    				
    				//value = queue2.removeLast();
        			//queue1.add(value);
    				return "true";
    			}
    			else {
    				System.out.println(String.join(".", queue2) + " no match");
    				System.out.println(ANSI_RED_BACKGROUND + "Expected: "+expected+" Actual: "+output+ANSI_RESET);
    				if (restore) {
    					queue1.add(key);
    					System.out.println("Restoring key: "+key);
    					queue2.removeLast();
    				}
    				System.out.println(String.join(".", queue1) + " Queue1 from not matched");
        			System.out.println(String.join(".", queue2) + " Queue2 from not matched");
    				System.out.println("Expected: "+expected+" Actual: "+output);
    				System.out.println("Could not match. Replacing the key");
    				//value = queue2.removeLast();
        			//queue1.add(value);
        			//System.out.println(String.join(".", queue1) + " Queue1");
        			//System.out.println(String.join(".", queue2) + " Queue2");
    				return "false";
    			}
    			
    		}
    		else {
    			return findValue(result.getJSONObject(key),expected,false);
    		}
    		
    	}
    	if (restore) {
    		System.out.println("Could not match. Replacing the key");
			queue1.add(key);
			queue2.removeLast();
		}
    	System.out.println(result.toString(4));
    	System.out.println("Inside object function");
		return "key not found";
    	
    }
    public static String findValue(JSONArray result,Integer index,String expected) throws JSONException {
    	String key = null,value = null;
    	String[] keysvalues = null,keys=null,sub=null;
    	Object o = null;
    	if (queue1.isEmpty()) {
    		o = result.get(index);
    		System.out.println("Inside findValue(JSONArray result) when empty");
    		if (o instanceof JSONObject) {
    			System.out.println("Calling findValue again with object: "+result.getJSONObject(index).toString(1));
    			return findValue(result.getJSONObject(index),expected,true);
    		}
    	}
    	else {
    		o = result.get(index);
    		System.out.println("Calling findValue again with object: "+result.getJSONObject(index).toString(1));
    		return findValue(result.getJSONObject(index),expected,true);
    	}
    	
		return "key not found";
    	
    }
    public static void findAndUpdate(String ...strings) {
    	if (strings.length==0) {
			System.out.println("Cannot process empty string for updatekey() method");
			
		}
    	String[] keysvalues = null;
    	String[] keys;
    	String[] sub = null;
    	String newkeys = null;
    	JSONObject obj = new JSONObject();
    	
    	for (int i=0; i< strings.length;i++) {
    		Deque<String> findqueue = new LinkedList<String>();
    		keysvalues = strings[i].split("=");
    		if (keysvalues.length > 1) {
    			for (int j=0;j<keysvalues.length; j++) {
    				findqueue.add(keysvalues[j]);
    			}
    			obj = new JSONObject();
    			while (!findqueue.isEmpty()) {
    				
    			}
    		}
    	}
    	
    }
    
    
}
