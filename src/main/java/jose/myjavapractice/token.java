package jose.myjavapractice;

public class token {
	
	
	enum tokenType {
		LBRACKET,
		RBRACKET,
		LSBRACKET,
		RSBRACKET,
		STRING,
		NUMBER,
		SEPARATOR,
		COMMA,
	}
	private String tokenStringValue;
	private float tokenNumberValue;
	private boolean booleanValue;
	private tokenType tType;
	
	
	public token(tokenType tType) {
		this.tType = tType;
	}
	
	public void setStringValue(String value) {
		this.tokenStringValue = value;
		
	}
	
	public String getStringValue() {
		return this.tokenStringValue;
	}
	
	public void setNumberValue(float value) {
		this.tokenNumberValue = value;		
	}
	
	public float getNumberValue() {
		return this.tokenNumberValue;
	}
	
	public void setBooleanValue(boolean value) {
		
	}
	
	public void getBooleanValue() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public tokenType getTokenType() {
		return this.tType;
	}
	
	public String getTokenValue() {
		switch (this.tType)
		{
		case LBRACKET:
			return "{";
		
		case RBRACKET:
			return "}";
			
		case LSBRACKET:
			return "[";
			
		case RSBRACKET:
			return "]";
		
		case STRING:
			return this.getStringValue();
		
		case NUMBER:
			return Float.toString(this.getNumberValue());
		
		case SEPARATOR:
			return ":";
		
		case COMMA:
			return ",";
		}
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tokentype: " + this.getTokenType() + "\n");
		sb.append("Tokenvalue: " + this.getTokenValue() + "\n");
		return sb.toString();
	}

}
