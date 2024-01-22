package constant;

public enum Param {

	ID("id"),
	LAST_NAME("lastName");
	
	String param;
	
	private Param(String param) {
		this.param = param;
	}
	
	public String getParamValue() {
		return param;
	}
}
