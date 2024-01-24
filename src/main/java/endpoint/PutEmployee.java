package endpoint;

import base.RestSteps;
import constant.Endpoint;
import constant.Param;
import io.restassured.http.ContentType;

public class PutEmployee extends RestSteps{

	public PutEmployee() {
		setup();
		setEndpoint(Endpoint.EMPLOYEE);
	}
	
	public void addPathParam(Object idString) {
		addPathParam(Param.ID, idString);
	}
	
	public void addBody(Object object) {
		setContentType(ContentType.JSON);
		addRequestBody(object);
	}
}
