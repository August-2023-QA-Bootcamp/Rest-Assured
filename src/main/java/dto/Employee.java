package dto;

/**
 * This is POGO class for Employee object
 */
public class Employee {

	int id;
	String firstName;
	String lastName;
	String dob;
	String fullName;
	String age;
	
	public Employee() {}
	
	public Employee(int id, String firstName, String lastName, String dob, String fullName, String age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.fullName = fullName;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * getAge() will calculate age from DOB
	 * @return String age.
	 * e.g 39 years 2 months 12 days
	 */
	public String getAge() {
		return age;
	}

	/**
	 * 
	 * @param age
	 */
	public void setAge(String age) {
		this.age = age;
	}
	
	
}
