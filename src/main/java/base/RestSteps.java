package base;

import io.restassured.response.Response;

public abstract class RestSteps implements IAPIRequest{

	@Override
	public Response getAPICall() {
		return null;
	}
	
	@Override
	public Response postAPICall() {
		return null;
	}
	
	@Override
	public Response putAPICall() {
		return null;
	}
	
	@Override
	public Response deleteAPICall() {
		return null;
	}
	
}
