package get.employees;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;

import dto.Employee;
import endpoint.GetEmployees;

@TestMethodOrder(OrderAnnotation.class)
public class GetEmployeesTest {

	static GetEmployees getEmployees;
	
	@BeforeAll
	public static void init() {
		getEmployees = new GetEmployees();
	}
	
	@TestFactory
	@Order(2)
	@DisplayName("TC_122 : GetEmplyees - Happy Path")
	public List<DynamicNode> getEmployeesHappyPath() {
		
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		getEmployees.getAPICall();
		
		int actualStatusCode = getEmployees.getResponse().statusCode();
		
		tests.add(dynamicTest("Status Code Validation. Expected [200], Actual [" + actualStatusCode + "]",
				() -> assertEquals(200, actualStatusCode)));
		
		return tests;
	}
	
	@TestFactory
	@Order(1)
	@DisplayName("TC_122 : GetEmplyees - Happy Path JsonPath Body Validation")
	public List<DynamicNode> getEmployeesJsonResponsValidation() {
		
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		getEmployees.getAPICall();
		
		int actualStatusCode = getEmployees.getResponse().statusCode();
		
		tests.add(dynamicTest("Status Code Validation. Expected [200], Actual [" + actualStatusCode + "]",
				() -> assertEquals(200, actualStatusCode)));
		
		List<DynamicNode> test = new ArrayList<DynamicNode>();
		
		test.add(dynamicTest("first id validation", 
				() -> getEmployees.getResponse().then().body("id[0]", equalTo(1))));
		test.add(dynamicTest("second id validation", 
				() -> getEmployees.getResponse().then().body("id[1]", equalTo(2))));
		test.add(dynamicTest("third id validation", 
				() -> getEmployees.getResponse().then().body("id[2]", equalTo(3))));
		
		tests.add(dynamicContainer("Response ids validation", test));
		
		return tests;
	}
	
	@TestFactory
	@Order(3)
	@DisplayName("TC_122 : GetEmplyees - Happy Path DTO Body Validation")
	public List<DynamicNode> getEmployeesDTOValidation() {
		
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		getEmployees.getAPICall();
		
		int actualStatusCode = getEmployees.getResponse().statusCode();
		
		tests.add(dynamicTest("Status Code Validation. Expected [200], Actual [" + actualStatusCode + "]",
				() -> assertEquals(200, actualStatusCode)));
		
		Employee[] employees = getEmployees.getResponse().as(Employee[].class);
		
		List<DynamicNode> test = new ArrayList<DynamicNode>();
		
		for(int i = 0; i < employees.length; i++) {
			
			int actualId = employees[i].getId();
			int expectedId = i+1;
			
			test.add(dynamicTest("id validation. Actual [" + actualId + "]. Expected [" + expectedId +"]", 
					() -> assertEquals(actualId , expectedId)));
		}
		
		tests.add(dynamicContainer("Response ids validation", test));
		
		return tests;
	}
	
	@AfterAll
	public static void cleanUp() {
		getEmployees.reset();
	}
}
