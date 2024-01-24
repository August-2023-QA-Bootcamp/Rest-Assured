package post.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;

import dto.Employee;
import endpoint.DeleteEmployee;
import endpoint.GetEmployees;
import endpoint.PostEmployee;

@Tag("REGRESSION")
public class PostEmployeeTest {

	static PostEmployee postEmployee;
	List<Integer> toBeDeletedIds = new ArrayList<Integer>();
	
	@BeforeAll
	public static void init() {
		postEmployee = new PostEmployee();
	}
	
	@TestFactory
	@Tag("SMOKE")
	@DisplayName("TC_342 : PostEmployee Happy Path")
	public List<DynamicNode> happyPath(){
		List<DynamicNode> tests = new LinkedList<DynamicNode>();
		
		GetEmployees getEmployees = new GetEmployees();
		getEmployees.getAPICall();
		
		tests.add(dynamicTest("1st Get Employees Status. Expected [200]. Actual [" 
				+ getEmployees.getResponse().getStatusCode(), 
				() -> assertEquals(200, getEmployees.getResponse().getStatusCode())));
		
		Employee[] beforeEmployees = getEmployees.getResponse().as(Employee[].class);
		
		Employee john = new Employee();
		john.setFirstName("John");
		john.setLastName("Cena");
		john.setDob("2000-02-13");
		
		postEmployee.addBody(john);
		postEmployee.postAPICall();
		
		tests.add(dynamicTest("POST Employees Status. Expected [201]. Actual [" 
				+ postEmployee.getResponse().getStatusCode(), 
				() -> assertEquals(201, postEmployee.getResponse().getStatusCode())));
		
		GetEmployees getEmployees2 = new GetEmployees();
		getEmployees2.getAPICall();
		
		tests.add(dynamicTest("1st Get Employees Status. Expected [200]. Actual [" 
				+ getEmployees2.getResponse().getStatusCode(), 
				() -> assertEquals(200, getEmployees2.getResponse().getStatusCode())));
		
		Employee[] afterEmployees = getEmployees2.getResponse().as(Employee[].class);
		
		int actualEmployeeSize = afterEmployees.length;
		int expectedEmployeeSize = beforeEmployees.length + 1;
		
		tests.add(dynamicTest(String.format("Size of the employee. Expected : %s , Actual : %s", 
				expectedEmployeeSize, actualEmployeeSize),
				() -> assertEquals(expectedEmployeeSize, actualEmployeeSize)));
		
		int actualId = afterEmployees[afterEmployees.length-1].getId();
		int expectedId = beforeEmployees[beforeEmployees.length-1].getId() + 1;
		
		tests.add(dynamicTest(String.format("New Employee Id. Expected id [%s], Actual id [%s]", expectedId, actualId), ()-> assertEquals(expectedId, actualId)));
		
		toBeDeletedIds.add(actualId);
		
		return tests;
	}
	
	@AfterEach
	public void cleanUp() {
		toBeDeletedIds.forEach(id -> {
			DeleteEmployee deleteEmployee = new DeleteEmployee();
			deleteEmployee.addPathParam(id);
			deleteEmployee.deleteAPICall();
		});
	}
}
