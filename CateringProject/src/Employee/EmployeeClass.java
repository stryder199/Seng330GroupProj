package Employee;

/**
 * This class is used to hold informations of an employee.
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeClass {

	private String firstName;
	private String lastName;
	private String phone;
	private double wage;
	private String birthDate;
	private String address;
	private String position;
	private int employeeId = 9999999;

	/**
	 * Constructs an EmployeeClass object initialised with the given values for the
	 * first name, last name, phone number, address, birth date, position, and wage.
	 * <p>
	 * This is without employee's ID.
	 * 
	 * @param	fName		employee's first name
	 * @param	lName		employee's last name
	 * @param	phone		employee's phone number
	 * @param	address		employee's address
	 * @param	birthDate	employee's date of birth
	 * @param	position	employee's position in company
	 * @param	wage		employee's wage
	 */
	public EmployeeClass(String fName, String lName, String phone, String address, String birthDate, String position, double wage) {
		super();
		this.firstName = fName;
		this.lastName = lName;
		this.phone = phone;
		this.address = address;
		this.birthDate = birthDate;
		this.position = position;
		this.wage = wage;
	}
	
	/**
	 * Constructs an EmployeeClass object initialised with the given values for the
	 * first name, last name, phone number, address, birth date, position, wage, 
	 * employee's ID number.
	 * 
	 * @param	fName		employee's first name
	 * @param	lName		employee's last name
	 * @param	phone		employee's phone number
	 * @param	address		employee's address
	 * @param	birthDate	employee's date of birth
	 * @param	position	employee's position in company
	 * @param	wage		employee's wage
	 * @param	employeeID	employee's ID number
	 */
	public EmployeeClass(String fName, String lName, String phone, String address, String birthDate, String position, double wage, int employeeID) {
		super();
		this.firstName = fName;
		this.lastName = lName;
		this.phone = phone;
		this.address = address;
		this.birthDate = birthDate;
		this.position = position;
		this.wage = wage;
		this.employeeId = employeeID;
	}

	public boolean verifyInput(){
		//Sanitize input
		firstName = firstName.replace("'","");
		lastName = lastName.replace("'","");
		phone = phone.replace("'","");
		address = address.replace("'","");
		birthDate = birthDate.replace("'","");
		
		if (!firstName.matches("[a-zA-Z ]++") && !lastName.matches("[a-zA-Z ]++")){
			return false;
		}

		if (!phone.matches("(\\d{3})-(\\d{3})-(\\d{4})")){
			return false;
		}

		if(wage < 0.0f){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets employee's phone number.
	 * 
	 * @return	employee's phone number
	 * @see		#setPhone(String)
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets employee's phone number.
	 * 
	 * @param	phone	employee's phone number
	 * @see		#getPhone()
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets employee's address.
	 * 
	 * @return	employee's address
	 * @see		#setAddress(String)
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets employee's address.
	 * 
	 * @param	address	employee's address
	 * @see		#getAddress()
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets employee's ID number.
	 * 
	 * @return	employee's ID number
	 * @see		#setEmployeeId(int)
	 */
	public int getEmployeeId() {
		if(employeeId == 9999999){
			employeeId = EmployeeControllerClass.getInstance().getEmployeeID(this);
		}
		return employeeId;
	}

	/**
	 * Sets employee's ID number.
	 * 
	 * @param	employeeId	employee's ID number
	 * @see		#getEmployeeId()
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Gets employee's position in company.
	 * 
	 * @return	employee's position in company
	 * @see		#setPosition(String)
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets employee's position in company.
	 * 
	 * @param	position	employee's position in company
	 * @see		#getPosition()
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Gets employee's date of birth.
	 * 
	 * @return	employee's date of birth
	 * @see		#setBirthDate(String)
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets employee's date of birth.
	 * 
	 * @param	birthDate	employee's date of birth
	 * @see		#getBirthDate()
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets employee's last name.
	 * 
	 * @return	employee's last name
	 * @see		#setLastName(String)
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets employee's last name.
	 * 
	 * @param	lastName	employee's last name
	 * @see		#getLastName()
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets employee's first name.
	 * 
	 * @return	employee's first name
	 * @see		#setFirstName(String)
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets employee's first name.
	 * 
	 * @param	firstName	employee's first name
	 * @see		#getFirstName()
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets employee's wage.
	 * 
	 * @return	employee's wage
	 * @see		#setWage(double)
	 */
	public double getWage() {
		return wage;
	}

	/**
	 * Sets employee's wage.
	 * 
	 * @param	wage	employee's wage
	 * @see		#getWage()
	 */
	public void setWage(double wage) {
		this.wage = wage;
	}

}
