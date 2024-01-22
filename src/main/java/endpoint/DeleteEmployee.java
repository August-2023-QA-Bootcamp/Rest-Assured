package endpoint;

import base.RestSteps;
import constant.Endpoint;
import constant.Param;

public class DeleteEmployee extends RestSteps{

	public DeleteEmployee() {
		setup();
		setEndpoint(Endpoint.EMPLOYEE);
	}
	
	public void addPathParam(Object idString) {
		addPathParam(Param.ID, idString);
	}
}
