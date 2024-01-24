package base;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import constant.Endpoint;
import constant.FileName;
import constant.Key;
import constant.Param;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.Configure;


/**
 * This abstract class have all the required steps
 * to build a HTTP request using RestAssured
 * and parsing response.
 * It has multiple helper method to create request
 * and have multiple helper methods to validate response
 */
public abstract class RestSteps implements IAPIRequest{

	private RequestSpecification requestSpecification;
	private Response response;
	
	
	/**
	 * Setting up RequestSpecification object
	 * and Base URL from setBaseUrl
	 */
	public void setup() {
		requestSpecification = RestAssured.given();
		setBaseUrl();
	}
	
	
	/**
	 * setEndpoint method will set the endpoint
	 * @param endpoint
	 */
	public void setEndpoint(Endpoint endpoint) {
		getRequestSpecification().basePath(endpoint.getEndpointValue());
	}
	
	
	/**
	 * setBaseUrl method will set the base url
	 * from config.properties
	 */
	private void setBaseUrl() {
		Configure configure = new Configure(FileName.CONFIG_PROPERTIES);
		getRequestSpecification().baseUri(configure.getPropertyValue(Key.BASE_URL));
	}
	
	
	/**
	 * Generic GET Call
	 * with pre-defined RequestSpecification Object
	 * If requestSpecification obj not set
	 * getAPICall will return null
	 */
	@Override
	public void getAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().get());
		getResponse().then().log().all();
	}
	
	
	/**
	 * Generic POST Call
	 * with pre-defined RequestSpecification Object
	 * If requestSpecification obj not set
	 * postAPICall will return null
	 */
	@Override
	public void postAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().post());
		getResponse().then().log().all();
	}
	
	
	/**
	 * Generic PUT Call
	 * with pre-defined RequestSpecification Object
	 * If requestSpecification obj not set
	 * putAPICall will return null
	 */
	@Override
	public void putAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().put());
		getResponse().then().log().all();
	}
	
	
	/**
	 * Generic DELETE Call
	 * with pre-defined RequestSpecification Object
	 * If requestSpecification obj not set
	 * deleteAPICall will return null
	 */
	@Override
	public void deleteAPICall() {
		getRequestSpecification().log().all();
		setResponse(getRequestSpecification().delete());
		getResponse().then().log().all();
	}

	
	/**
	 * Setting content type for POST/PUT request body
	 * @param contentType
	 */
	public void setContentType(ContentType contentType) {
		getRequestSpecification().contentType(contentType);
	}
	
	/**
	 * Setting up request body with
	 * String body object
	 * @param body
	 */
	public void addRequestBody(String body) {
		getRequestSpecification().body(body);
	}
	
	
	/**
	 * Setting up request body with
	 * File body object
	 * @param body
	 */
	public void addRequestBody(File body) {
		getRequestSpecification().body(body);
	}
	
	
	/**
	 * Setting up request body with
	 * Map<String,Object> body object
	 * @param body
	 */
	public void addRequestBody(Map<String, Object> body) {
		getRequestSpecification().body(body);
	}
	
	
	/**
	 * Setting up request body with
	 * Object body object
	 * @param body
	 */
	public void addRequestBody(Object body) {
		getRequestSpecification().body(body);
	}
	
	
	/**
	 * Setting up request body with
	 * ObjectMapper body object
	 * @param body
	 */
	public void addRequestBody(Object body, ObjectMapper mapper) {
		getRequestSpecification().body(body, mapper);
	}
	
	
	/**
	 * Setting up request body with
	 * InputStream body object
	 * @param body
	 */
	public void addRequestBody(InputStream body) {
		getRequestSpecification().body(body);
	}
	
	
	/**
	 * Adding path param
	 * @param param will define the path variable name from ENDPOINT
	 * @param value dynamic Object value
	 */
	public void addPathParam(Param param, Object value) {
		getRequestSpecification().pathParam(param.getParamValue(), value);
	}
	
	
	/**
	 * getRequestSpecification
	 * @return requestSpecification object
	 */
	public RequestSpecification getRequestSpecification() {
		return requestSpecification;
	}

	
	/**
	 * Setting response variable
	 * @param response
	 */
	public void setResponse(Response response) {
		this.response = response;
	}
	
	
	/**
	 * Response getter
	 * @return response
	 */
	public Response getResponse() {
		return response;
	}
	
	
	/**
	 * Resetting RestAssured object
	 */
	public void reset() {
		RestAssured.reset();
	}
	
}
