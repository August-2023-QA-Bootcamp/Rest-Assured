package suites;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import delete.employee.DeleteEmployeeTest;
import get.employees.GetEmployeesTest;
import post.employee.PostEmployeeTest;
import put.employee.PutEmployeeTest;

@Suite
@SelectClasses({ DeleteEmployeeTest.class, GetEmployeesTest.class, PostEmployeeTest.class, PutEmployeeTest.class })
@SelectPackages("get.employees")
@IncludeTags("SMOKE")
public class TestSuites {

}
