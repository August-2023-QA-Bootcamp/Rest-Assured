package constant;

public enum Key {

	BASE_URL("baseUrl");
	
	private String key;
	
	private Key(String key) {
		this.key = key;
	}
	
	public String getKeyValue() {
		return key;
	}
}
