package base;

import io.restassured.response.Response;

public interface IAPIRequest {

	Response getAPICall();
	
	Response postAPICall();
	
	Response putAPICall();
	
	Response deleteAPICall();
}
