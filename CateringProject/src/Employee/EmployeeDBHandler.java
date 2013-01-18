package Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DBHandler;
import Login.UserClass;

/**
 * This class handles the employee database.
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeDBHandler extends DBHandler{

	/**
	 * Constructs a new instance of employee database handler.
	 */
	public EmployeeDBHandler() {}

	/**
	 * Queries the DB for all the employees in the catering service.
	 * 
	 * @return		ResultSet of query
	 * @throws		SQLException	Error in employee database
	 */
	public ResultSet getAllEmployees() {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From employees");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	/**
	 * Queries the DB for the employee associated with the given employee ID.
	 * 
	 * @param	employeeID		ID of requested employee
	 * @return	requested employee
	 * @throws	SQLException	Error in employee database
	 */
	public ResultSet getEmployee(int employeeID) {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From employees " + 
							"WHERE employee_id=" + employeeID);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	/**
	 * Deletes the employee record from the DB associated with the given employee ID.
	 * 
	 * @param	employeeID		employee's ID
	 * @return	true if deleted successfully; false otherwise
	 * @throws	SQLException	Error in employee database
	 */
	public boolean removeEmployee(double employeeID){
		int result =  0;
		
		//Sanity check, make sure that the employee id is actually set!
		assert(employeeID != 9999999);
		
		try {
			result = updateQuery("DELETE FROM employees " + 
								"WHERE employee_id=" + employeeID);
		} catch (SQLException e) {
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}
	

	/**
	 * Adds a new employee record to the DB.
	 * 
	 * @param	newEmployee		new employee
	 * @param	currentUser		current user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error in employee database
	 */
	public boolean addEmployee( EmployeeClass newEmployee, UserClass currentUser ){
		int result = 0;
		try {
			result = updateQuery("INSERT INTO employees (firstName, lastName, phoneNum, wage, address, birthDate, position, creationUser_id) " + 
								"VALUES('" + newEmployee.getFirstName() + "','" + newEmployee.getLastName() + "', '" 
								+ newEmployee.getPhone() + "', '" +	newEmployee.getWage() + "', '" + newEmployee.getAddress() + "', '" 
								+ newEmployee.getBirthDate() +"', '" + newEmployee.getPosition() + "', '" + currentUser.getUserId() +"')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}

	/**
	 * Modifies an employee's record in the DB.
	 * 
	 * @param	changedEmployee	employee whose record is being modified
	 * @return	true if modified successfully; false otherwise
	 * @throws	SQLException	Error in employee database
	 */
	public boolean modifyEmployee(EmployeeClass changedEmployee) {
		int result = 0;
		try {
			result = updateQuery("UPDATE employees " +
								"SET firstName='" + changedEmployee.getFirstName() + 
								"', lastName='" + changedEmployee.getLastName() + 
								"', phoneNum='" + changedEmployee.getPhone() + 
								"', wage=" + changedEmployee.getWage() + 
								", address='" + changedEmployee.getAddress() + 
								"', birthDate='" + changedEmployee.getBirthDate() +
								"', position='" + changedEmployee.getPosition() + "' " +
								"WHERE employee_id ="+ changedEmployee.getEmployeeId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 0) return false;
		return true;
	}

	/**
	 * Gets an employee's ID from the DB.
	 * 
	 * @param	employeeClass	requested employee
	 * @return	requested employee's ID
	 * @throws	SQLException	Error in employee database
	 * @see		EmployeeClass
	 */
	public ResultSet getEmployee(EmployeeClass employeeClass) {
		ResultSet result = null;
		try {
			result = query("SELECT * " +
							"FROM employees " +
							"WHERE firstName='" + employeeClass.getFirstName() + 
							"' AND lastName='" + employeeClass.getLastName() + 
							"' AND phoneNum='" + employeeClass.getPhone() + 
							"' AND address='" + employeeClass.getAddress() + 
							"' AND birthDate='" + employeeClass.getBirthDate() +
							"' AND position='" + employeeClass.getPosition() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		
		return result;
	}
}
