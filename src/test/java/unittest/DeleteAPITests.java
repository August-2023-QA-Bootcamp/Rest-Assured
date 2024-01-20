package unittest;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import dto.Employee;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteAPITests {

	@TestFactory
	@DisplayName("Chaining Delete Call and Validation")
	public List<DynamicNode> deleteTest() {
		
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees";
		
		// GET Call
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint);
		requestSpecification.log().all();
		
		Response response = requestSpecification.get();
		response.then().log().all();
		
		Employee[] employees = response.as(Employee[].class);
		
		// DELETE Call
		
		RestAssured.reset();
		
		String endpoint2 = "/employees/{id}";
		
		requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint2);
		
		int id = employees[employees.length - 1].getId();
		requestSpecification.pathParam("id", id);
		
		requestSpecification.log().all();
		
		Response response2 = requestSpecification.delete();
		response2.then().log().all();
		
		tests.add(dynamicTest("Status Code Validation. Expected: [" + 200 + "] and Actual: ["
				+ response.statusCode() +"]", ()-> {
					Assertions.assertEquals(200, response2.statusCode());
				}));
		
		String errorMsg = response2.then().extract().jsonPath().getString("msg");
		String actualMsg = "employee: "+ id +", has been deleted";
				
		tests.add(dynamicTest("Status Message Validation. Expected: ["+errorMsg+"] and Actual: ["
				+ actualMsg +"]", ()-> {
					Assertions.assertEquals(errorMsg, actualMsg);
				}));
		
		// Second GET Call
		
		RestAssured.reset();
		
		requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint);
		requestSpecification.log().all();
		
		Response response3 = requestSpecification.get();
		response.then().log().all();
		
		Employee[] employees2 = response3.as(Employee[].class);
		
		int expectedLength = employees.length -1;
		tests.add(dynamicTest("Employees Length Validation. Expected: ["+expectedLength+"] and Actual: ["
				+ employees2.length +"]", ()-> {
					Assertions.assertEquals(expectedLength, employees2.length);
				}));
		
		return tests;
	}
}
