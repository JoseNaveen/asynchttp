package jose.myjavapractice.jsonparser;

public class StringKey implements Key {
	public StringKey() {
	}

	private String key;

	public String getKey() {
		return key;
	}

	public void setkey(String key) {
		this.key = key;
	}
	
	public String toString() {
		return key;
	}
}
