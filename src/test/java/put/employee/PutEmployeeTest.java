package put.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;

import dto.Employee;
import endpoint.GetEmployees;
import endpoint.PutEmployee;

@Tag("REGRESSION")
public class PutEmployeeTest {

	static PutEmployee putEmployee;
	
	@BeforeAll
	public static void init() {
		putEmployee = new PutEmployee();
	}
	
	@TestFactory
	@DisplayName("TC_564 : PUTEmployee Happy Path")
	public List<DynamicNode> putEmployeeTest() {
		List<DynamicNode> tests = new LinkedList<DynamicNode>();
		
		GetEmployees getEmployees = new GetEmployees();
		getEmployees.getAPICall();
		
		tests.add(dynamicTest("1st Get Employees Status. Expected [200]. Actual [" 
				+ getEmployees.getResponse().getStatusCode(), 
				() -> assertEquals(200, getEmployees.getResponse().getStatusCode())));
		
		Employee[] beforeEmployees = getEmployees.getResponse().as(Employee[].class);
		
		Employee employee = new Employee();
		employee.setFirstName("Ali");
		employee.setLastName("Baba");
		employee.setDob("1999-12-02");
		
		int randomId = new Random().nextInt(beforeEmployees.length);
		
		putEmployee.addPathParam(randomId);
		putEmployee.addBody(employee);
		putEmployee.putAPICall();
		
		tests.add(dynamicTest("Status Code Validation", 
				() -> Assertions.assertEquals(200, putEmployee.getResponse().getStatusCode())));
		
		GetEmployees getEmployees2 = new GetEmployees();
		getEmployees2.getAPICall();
		
		tests.add(dynamicTest("1st Get Employees Status. Expected [200]. Actual [" 
				+ getEmployees2.getResponse().getStatusCode(), 
				() -> assertEquals(200, getEmployees2.getResponse().getStatusCode())));
		
		Employee[] afterEmployees = getEmployees2.getResponse().as(Employee[].class);
		Employee afterEmployee = afterEmployees[randomId];
		
		tests.add(dynamicTest("Comparing Full Name", ()-> assertEquals(employee.getFullName(), afterEmployee.getFullName())));
		tests.add(dynamicTest("Comparing First Name", ()-> assertEquals(employee.getFirstName(), afterEmployee.getFirstName())));
		tests.add(dynamicTest("Comparing Last Name", ()-> assertEquals(employee.getLastName(), afterEmployee.getLastName())));
		tests.add(dynamicTest("Comparing DOB", ()-> assertEquals(employee.getDob(), afterEmployee.getDob())));
		tests.add(dynamicTest("Comparing Age", ()-> assertEquals(employee.getAge(), afterEmployee.getAge())));
		
		return tests;
	}
}
