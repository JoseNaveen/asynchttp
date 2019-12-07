package jose.myjavapractice;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jose.myjavapractice.token;

public class lexer {
	static final char LBRACKET = '{';
	static final char RBRACKET = '}';
	static final char QUOTE = '"';
	static final char LSBRACKET = '[';
	static final char RSBRACKET = ']';
	static final char SEPARATOR = ':';
	static final char COMMA = ',';
	static final char PERIOD = '.';
	final public String STRING = null;
	final public String NUMBER = null;
	final public String BOOLEAN = null;
	List<token> tokenList = new ArrayList<token>();
	
	public void tokenizer(String data) throws IOException {
		Reader jsonReader1 = new StringReader(data);
		BufferedReader br = new BufferedReader(jsonReader1);
		int i;
		token t = null;
		while (true) {
			br.mark(1);
			i = br.read();
			if (i==-1) {
				//throw new Exception("Invalid character" + (char) i + " found");
				break;
			}
			if (Character.isWhitespace((char) i)) {
				//if character is white space do nothing and proceed to read next token if available
				continue;
			}
			if (Character.isDigit(i)) {
				br.reset(); //reset to the beginning of the token
				// we know its a number so call the method to read the number
				t = this.readNumber(br);
				tokenList.add(t);
				continue;
			}
			switch ((char) i)
			{
			case lexer.LBRACKET:
				t = new token(token.tokenType.LBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.RBRACKET:
				t = new token(token.tokenType.RBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.QUOTE:
				t = this.readString(br);
				tokenList.add(t);
				break;
			
			case lexer.SEPARATOR:
				t = new token(token.tokenType.SEPARATOR);
				tokenList.add(t);
				break;
			
			case lexer.COMMA:
				t = new token(token.tokenType.COMMA);
				tokenList.add(t);
				break;
			
			case lexer.LSBRACKET:
				t = new token(token.tokenType.LSBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.RSBRACKET:
				t = new token(token.tokenType.RSBRACKET);
				tokenList.add(t);
				break;
			}
			
		}
		br.close();
		jsonReader1.close();
	}
	
	public token readString(BufferedReader br) {
		StringBuilder sb = new StringBuilder();
		JSONObject jsonObject = new JSONObject();
		token t = null;
		t = new token(token.tokenType.STRING);
		int i = 0;
		while (true) {
			try {
				i = br.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == -1) {
				break;
			}
			if ((char) i == lexer.QUOTE) { // found end of string. stop reading more characters and return token
				break;
			}
				
			if (Character.isDefined(i)) {
				sb.append((char) i);
			}
		}
		t.setStringValue(sb.toString());
		return t;
	}
	
	public token readNumber(BufferedReader br) {
		StringBuilder sb = new StringBuilder();
		token t = new token(token.tokenType.NUMBER);
		int i = 0;
		while (true) {
			try {
				br.mark(1);
				i = br.read();
				//System.out.println("read: " + (char) i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if ((char) i == lexer.PERIOD) {
				sb.append(lexer.PERIOD);		
			}
			if (Character.isDigit((char) i)) {
				//System.out.println("Found a digit: "+(char) i);
				sb.append((char) i);
			}
			else {
				try {
					br.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // found a number so break out of read loop and return token
				break;
			}
		}
		Float f = new Float(sb.toString());
		t.setNumberValue(f);
		return t;
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		lexer lex = new lexer();
		List<token> tokenList = new ArrayList<token>(); //list to store the tokens identified from string in order
		
		JSONObject jsonObject = new JSONObject(); //example object for testing
		try {
			jsonObject.put("a", "1");
			jsonObject.put("b", (new JSONObject()).put("c", 5));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String example = "{\"a\": \"1\",  \"b\": {\"c\": 5},\"d\":[\"hello\",\"world\"]}";
		Reader jsonReader = new StringReader(jsonObject.toString());
		Reader jsonReader1 = new StringReader(example);
		System.out.println(System.currentTimeMillis());
		JSONObject j = new JSONObject(example);
		System.out.println(j.toString(2)); // printing the example string as json
		System.out.println(j.getJSONObject("b").get("c").getClass());
		System.out.println(System.currentTimeMillis());
		BufferedReader br = new BufferedReader(jsonReader1);
		int i;
		token t = null;
		while (true) {
			br.mark(1);
			i = br.read();
			if (i==-1) {
				//throw new Exception("Invalid character" + (char) i + " found");
				break;
			}
			if (Character.isWhitespace((char) i)) {
				//if character is white space do nothing and proceed to read next token if available
				continue;
			}
			if (Character.isDigit(i)) {
				br.reset(); //reset to the beginning of the token
				// we know its a number so call the method to read the number
				t = lex.readNumber(br);
				tokenList.add(t);
				continue;
			}
			switch ((char) i)
			{
			case lexer.LBRACKET:
				t = new token(token.tokenType.LBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.RBRACKET:
				t = new token(token.tokenType.RBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.QUOTE:
				t = lex.readString(br);
				tokenList.add(t);
				break;
			
			case lexer.SEPARATOR:
				t = new token(token.tokenType.SEPARATOR);
				tokenList.add(t);
				break;
			
			case lexer.COMMA:
				t = new token(token.tokenType.COMMA);
				tokenList.add(t);
				break;
			
			case lexer.LSBRACKET:
				t = new token(token.tokenType.LSBRACKET);
				tokenList.add(t);
				break;
			
			case lexer.RSBRACKET:
				t = new token(token.tokenType.RSBRACKET);
				tokenList.add(t);
				break;
			}
			
		}
		br.close();
		jsonReader.close();/*
		for (token tokens: tokenList) {
			//System.out.println(tokens.toString());
		}
		Iterator<token> itr = tokenList.iterator();
		while (true) {
			if (itr.hasNext()) {
				itr.next();
				//System.out.print(itr.next().toString());
			}
			else {
				break;
			}
		}*/
		System.out.println(System.currentTimeMillis());
		parser p = new parser();
		Document jn = p.parse(tokenList);
		System.out.println(jn.toString());
		System.out.println(System.currentTimeMillis());
		
		PrintVisitor pv = new PrintVisitor();
		pv.visitDocument(jn);
	}

}
