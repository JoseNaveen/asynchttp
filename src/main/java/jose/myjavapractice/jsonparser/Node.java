package jose.myjavapractice.jsonparser;

public class Node implements Value{
	public Node() {
	}
	private Key key;
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	private Value value;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.key.toString());
		sb.append(":");
		sb.append(this.value.toString());
		return sb.toString();
	}
}
