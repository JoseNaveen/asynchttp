package jose.myjavapractice.jsonparser;

import jose.myjavapractice.*;
import java.util.*;

public interface ParserState {
	
	public void setPrev(ParserState prev);
	public ParserState getPrev();
	public void setNext(ParserState next);
	public ParserState getNext();
	public Value parse(Iterator<token> it);

}
