package unittest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dto.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPITests {

	//@Test
	public void postTestInline() {
		RestAssured.given()
		.log().all()
		.body("{\"firstName\": \"Henrich\",\n"
				+ "\"lastName\": \"Kalaseen\",\n"
				+ "\"dob\": \"1998-02-28\"}")
		.contentType(ContentType.JSON)
		.post("http://localhost:3000/employees")
		.then().log().all()
		.statusCode(201);
	}
	
	//@Test
	public void postTestStandard_w_file() {
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees";
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		request.contentType(ContentType.JSON);
		request.body(new File("src/test/resources/sampleJson.json"));
		
		request.log().all();
		
		//Response Obj
		Response response = request.post();
		response.then().log().all();
		
		response.then().statusCode(201);
	}
	
	//@Test
	public void postTestStandard_w_inputstream_chaining_jsonpath() {
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees";
		
		//------ GET Call
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		
		request.log().all();
		
		//Response Obj
		Response response = request.get();
		response.then().log().all();
		
		response.then().statusCode(200);
		
		List<Integer> ids = response.andReturn().jsonPath().getList("id");
		
		int expectedlId = ids.get(ids.size()-1) + 1;
		
		//------- POST Call
		request.contentType(ContentType.JSON);
		request.body(getClass().getClassLoader().getResourceAsStream("sampleJson.json"));
				
		response = request.post();
		
		System.err.println("----- Second Response : \n" + response.asPrettyString());
		
		int actualId = response.andReturn().jsonPath().getInt("id");
		
		System.out.println("Actual Id is " + actualId +" .... Expected Id : "+ expectedlId);
		
		Assertions.assertEquals(expectedlId, actualId);
	}
	
	@Test
	public void postTest_w_map_DTO() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstName", "John");
		map.put("lastName", "Doe");
		map.put("dob", "1992-11-17");
		
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees";
		
		//Request Obj
		RequestSpecification request = RestAssured.given();
		request.baseUri(baseUrl);
		request.basePath(endpoint);
		
		request.contentType(ContentType.JSON);
		request.body(map);
		
		request.log().all();
		
		//Response Obj
		Response response = request.post();
		response.then().log().all();
		
		response.then().statusCode(201);
		
		Employee employee = response.as(Employee.class);
		System.out.println(employee.getAge());
	}
}
