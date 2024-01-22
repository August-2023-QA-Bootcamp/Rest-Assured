package base;

import constant.Endpoint;
import constant.FileName;
import constant.Key;
import constant.Param;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.Configure;

public abstract class RestSteps implements IAPIRequest{

	private RequestSpecification requestSpecification;
	private Response response;
	
	public void setup() {
		requestSpecification = RestAssured.given();
		setBaseUrl();
	}
	
	public void setEndpoint(Endpoint endpoint) {
		getRequestSpecification().basePath(endpoint.getEndpointValue());
	}
	
	private void setBaseUrl() {
		Configure configure = new Configure(FileName.CONFIG_PROPERTIES);
		getRequestSpecification().baseUri(configure.getPropertyValue(Key.BASE_URL));
	}
	
	@Override
	public void getAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().get());
		getResponse().then().log().all();
	}
	
	@Override
	public void postAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().post());
		getResponse().then().log().all();
	}
	
	@Override
	public void putAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().put());
		getResponse().then().log().all();
	}
	
	@Override
	public void deleteAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().delete());
		getResponse().then().log().all();
	}

	public void addPathParam(Param param, Object value) {
		getRequestSpecification().pathParam(param.getParamValue(), value);
	}
	
	public RequestSpecification getRequestSpecification() {
		return requestSpecification;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Response getResponse() {
		return response;
	}
	
	public void reset() {
		RestAssured.reset();
	}
	
}
