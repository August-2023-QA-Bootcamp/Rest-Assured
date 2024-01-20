package unittest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PutAPITests {

	//@TestFactory
	@DisplayName("TC_123 : PUT Call Happy Path Testing")
	public List<DynamicNode> putTest() throws IOException {
		List<DynamicNode> tests = new LinkedList<DynamicNode>();
		
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees";
		
		// GET Call
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint);
		
		//requestSpecification.auth().basic("user", "pass");
		//requestSpecification.header(new Header("key", "value"));
		
		requestSpecification.log().all();
		
		Response response = requestSpecification.get();
		
		response.then().log().all();
		
		Employee[] employees = response.as(Employee[].class);
		
		// Expected Employee object from file
		
		ObjectMapper mapper = new ObjectMapper();
		
		File file = new File("src/test/resources/sampleJson.json");
		
		System.err.println(file.exists());
		
		Employee expectedEmployee = mapper.readValue(file, Employee.class);
		System.err.println(expectedEmployee.getFullName());
		
		// PUT call
		
		RestAssured.reset();
		
		endpoint = "/employees/{id}";
		
		requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint);
		
		requestSpecification.pathParam("id", employees[employees.length - 1].getId());
		
		requestSpecification.contentType(ContentType.JSON);
		
		byte[] bytes = getClass().getClassLoader().getResourceAsStream("sampleJson.json").readAllBytes();
		
		requestSpecification.body(bytes);
		
		requestSpecification.log().all();
		
		Response response2 = requestSpecification.put();
		response2.then().log().all();
		
		Employee employee = response2.as(Employee.class);
		
		tests.add(dynamicTest("First Name Compare. Expected: ["+expectedEmployee.getFirstName()+"] and Actual: ["
				+ employee.getFirstName()+ "]", ()-> {
			Assertions.assertEquals(employee.getFirstName(), expectedEmployee.getFirstName());
		}));
		tests.add(dynamicTest("Last Name Compare. Expected: ["+expectedEmployee.getLastName()+"] and Actual: ["
				+ employee.getLastName()+ "]", ()-> {
			Assertions.assertEquals(employee.getLastName(), expectedEmployee.getLastName());
		}));
		tests.add(dynamicTest("Full Name Compare. Expected: ["+expectedEmployee.getFullName()+"] and Actual: ["
				+ employee.getFullName()+ "]", ()-> {
			Assertions.assertEquals(employee.getFullName(), expectedEmployee.getFirstName());
		}));
		tests.add(dynamicTest("DOB Compare. Expected: ["+expectedEmployee.getDob()+"] and Actual: ["
				+ employee.getDob()+ "]", ()-> {
			Assertions.assertEquals(employee.getDob(), expectedEmployee.getDob());
		}));
		
		return tests;
	}
	
	@TestFactory
	@DisplayName("TC_231 : Negative - Invalid Id")
	public List<DynamicNode> putTestInvalidId() throws IOException{
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		String baseUrl = "http://localhost:3000";
		String endpoint = "/employees/{employeeId}";
		
//		File file = new File("src/test/resources/sampleJson.json");
//		ObjectMapper mapper = new ObjectMapper();
//		Employee expectedEmployee = mapper.readValue(file, Employee.class);
		
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.baseUri(baseUrl);
		requestSpecification.basePath(endpoint);
		requestSpecification.pathParam("employeeId", 0);
		requestSpecification.contentType(ContentType.JSON);
		
		requestSpecification.body(getClass().getClassLoader().getResourceAsStream("sampleJson.json"));
		requestSpecification.log().all();
		
		Response response = requestSpecification.put();
		
		response.then().log().all();
		
		tests.add(dynamicTest("Status Code Validation. Expected: [" + 418 + "] and Actual: ["
				+ response.statusCode() +"]", ()-> {
					Assertions.assertEquals(418, response.statusCode());
				}));
		
		String actualMsg = response.then().extract().jsonPath().getString("errorMsg");
				
		tests.add(dynamicTest("Status Message Validation. Expected: [Employe Not Found] and Actual: ["
				+ actualMsg +"]", ()-> {
					Assertions.assertEquals("Employe Not Found", actualMsg);
				}));
		
		System.err.println(response.statusLine());
		
		return tests;
	}
}
