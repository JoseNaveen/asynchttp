package jose.myjavapractice.jsonparser;

import java.util.Iterator;
import java.util.List;

import jose.myjavapractice.*;


public class Parser {
	
	private ParserState state = new ReadJSON();
	private List<token> tokens;
	
	public Parser(List<token> tokens) {
		this.tokens = tokens;
	}
	
	public void startParse() {
		Iterator<token> it = this.tokens.iterator();
		Value doc = state.parse(it);
	}

}
