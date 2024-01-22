package constant;

public enum Endpoint {

	GET_GREETINGS("/greetings"),
	EMPLOYEES("/employees"),
	EMPLOYEE("/employees/{id}"),
	GET_EMPLOYEE_LAST_NAME("/employees/search/{lastName}");
	
	private String endpoint;
	
	private Endpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getEndpointValue() {
		return endpoint;
	}
}
