package delete.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import static org.junit.jupiter.api.DynamicTest.*;
import org.junit.jupiter.api.TestFactory;

import dto.Employee;
import endpoint.DeleteEmployee;
import endpoint.GetEmployees;

public class DeleteEmployeeTest {

	static DeleteEmployee deleteEmployee;
	
	@BeforeAll
	public static void setup() {
		deleteEmployee = new DeleteEmployee();
	}
	
	@TestFactory
	@DisplayName("TC_324 : Delete Employee Validation")
	public List<DynamicNode> deleteEmployeeTest() {
		List<DynamicNode> tests = new ArrayList<DynamicNode>();
		
		GetEmployees getEmployees = new GetEmployees();
		getEmployees.getAPICall();
		
		tests.add(dynamicTest("1st Get Employees Staus. Actual [200]. Expected [" 
		+ getEmployees.getResponse().getStatusCode(), 
		() -> assertEquals(200, getEmployees.getResponse().getStatusCode())));
		
		Employee[] beforeEmployees = getEmployees.getResponse().as(Employee[].class);
		
		int randomNumber = new Random().nextInt(beforeEmployees.length);
		Employee expecptedEmployee = beforeEmployees[randomNumber];
		
		deleteEmployee.addPathParam(expecptedEmployee.getId());
		deleteEmployee.deleteAPICall();
		
		tests.add(dynamicTest("Delete Employee Staus. Actual [200]. Expected [" 
		+ deleteEmployee.getResponse().getStatusCode(), 
		() -> assertEquals(200, deleteEmployee.getResponse().getStatusCode())));
		
		GetEmployees getEmployees2 = new GetEmployees();
		getEmployees2.getAPICall();
		
		tests.add(dynamicTest("2nd Get Employees Staus. Actual [200]. Expected [" 
		+ getEmployees2.getResponse().getStatusCode(), 
		() -> assertEquals(200, getEmployees2.getResponse().getStatusCode())));
		
		Employee[] afterEmployees = getEmployees2.getResponse().as(Employee[].class);
		
		int expectedEmployeeSize = beforeEmployees.length - 1;
		
		tests.add(dynamicTest("Length of Employess reduced. Expected [" + expectedEmployeeSize +
				"]. Actual [" + afterEmployees.length + "]", 
				() -> assertEquals(expectedEmployeeSize, afterEmployees.length)));
		
		for(Employee employee : afterEmployees) {
			if(employee.getId() == expecptedEmployee.getId()) {
				fail();
			}
		}
		
		return tests;
	}
	
	@AfterAll
	public static void cleanUp() {
		deleteEmployee.reset();
	}
}
