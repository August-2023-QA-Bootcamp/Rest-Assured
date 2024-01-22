package endpoint;

import base.RestSteps;
import constant.Endpoint;

public class GetEmployees extends RestSteps{

	public GetEmployees() {
		setup();
		setEndpoint(Endpoint.EMPLOYEES);
	}
}
