package endpoint;

import base.RestSteps;
import constant.Endpoint;
import io.restassured.http.ContentType;

public class PostEmployee extends RestSteps{

	public PostEmployee() {
		setup();
		setEndpoint(Endpoint.EMPLOYEES);
	}
	
	public void addBody(Object object) {
		setContentType(ContentType.JSON);
		addRequestBody(object);
	}
}
