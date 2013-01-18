package Employee;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Login.UserClass;

/**
 * This class handles the logic for all classes in the Employee package.
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeControllerClass {

	private static EmployeeControllerClass controllerSinglton;

	private EmployeeDBHandler employeeDB = new EmployeeDBHandler();
	private EmployeeAvailabilityDBHandler availabilityDB = new EmployeeAvailabilityDBHandler();
	
	/**
	 * Constructs a new instance of employee controller.
	 */
	public EmployeeControllerClass() {}
	
	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initialises the object and
	 * returns it, if it has been initialised then it returns the already initialised 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static EmployeeControllerClass getInstance()
	{
		if(controllerSinglton == null){
			EmployeeControllerClass instance = new EmployeeControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}
	
	/**
	 * Returns a list of all available the employees in the database.
	 * 
	 * @return	list of all available employees
	 * @throws	SQLException	SQL error in querying employees
	 * @see		EmployeeClass
	 * @see		EmployeeDBHandler	
	 */
	public List<EmployeeClass> getAllEmployees(){
		ResultSet result = employeeDB.getAllEmployees();
		List<EmployeeClass> list = new ArrayList<EmployeeClass>();
		
		try {
			while(result.next())
			{
				String fName = result.getString("firstName");
				String lName = result.getString("lastName");
				String phoneNum = result.getString("phoneNum");
				double wage = result.getDouble("wage");
				String address = result.getString("address");
				String birthDate = result.getString("birthDate");
				String position = result.getString("position");
				
				EmployeeClass tempEmployee = new EmployeeClass(fName, lName, phoneNum, address, birthDate, position, wage);
				tempEmployee.setEmployeeId(result.getInt("employee_id"));
				list.add(tempEmployee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Returns the employee associated with the given employee id.
	 * 
	 * @param	employeeID		requested employee's ID
	 * @return	requested employee
	 * @see		EmployeeClass
	 * @see		EmployeeDBHandler
	 */
	public EmployeeClass getEmployee(int employeeID)
	{
		ResultSet result = employeeDB.getEmployee(employeeID);
		EmployeeClass employee = null;
		
		if(result == null)
		{
			System.out.println("Error: no such employee");
			return null;
		}
		try {
			result.next();
			String fName = result.getString("firstName");
			String lName = result.getString("lastName");
			String phoneNum = result.getString("phoneNum");
			double wage = result.getDouble("wage");
			String address = result.getString("address");
			Date birthDate = result.getDate("birthDate");
			String position = result.getString("position");
			
			employee = new EmployeeClass(fName, lName, phoneNum, address, birthDate.toString(), position, wage);
			employee.setEmployeeId(result.getInt("employee_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employee;
	}

	/**
	 * Adds a new employee record to the database.
	 * 
	 * @param	newEmployee		new employee
	 * @param	currentUser		current user
	 * @return	true if added successfully; false otherwise
	 * @see		EmployeeClass
	 * @see		EmployeeDBHandler
	 * @see		UserClass
	 */
	public boolean addEmployee(EmployeeClass newEmployee, UserClass currentUser) {
		boolean result = false;
		
		result = employeeDB.addEmployee(newEmployee, currentUser);
		
		return result;
	}


	/**
	 * Adds a record in the database of a date that a serving staff booked off.
	 * 
	 * @param	empdate		employee who is unavailable
	 * @param	currentUser	current user
	 * @return	true if added successfully; false otherwise
	 * @see		EmployeeAvailabilityDBHandler
	 * @see		EmployeeUnavailDate
	 * @see		UserClass
	 */
	public boolean addUnavailableDate(EmployeeUnavailDate empdate, UserClass currentUser)
	{
		boolean result = false;
		
		result = availabilityDB.addUnavailableDate(empdate, currentUser);
		
		return result;
	}

	/**
	 * Adds a record in the database of a date that an employee is working an event.
	 * 
	 * @param	empdate		booked employee
	 * @param	currentUser	current user
	 * @see		EmployeeAvailabilityDBHandler
	 * @see		EmployeeBookedDate
	 * @see		UserClass
	 */
	public void addBookedDate(EmployeeBookedDate empdate, UserClass currentUser)
	{
		availabilityDB.addBookedDate(empdate, currentUser);
	}

	/**
	 * This method deletes an employee record from the database
	 * 
	 * @param	employeeID	employee's ID
	 * @return	true if deleted successfully; false otherwise
	 * @see		EmployeeDBHandler
	 */
	public boolean deleteEmployee(int employeeID) {
		boolean result;
		result = employeeDB.removeEmployee(employeeID);
		
		return result;
	}

	/**
	 * Updates an employee record in the database.
	 * 
	 * @param	changedEmployee		employee whose details are being updated
	 * @see		EmployeeDBHandler
	 */
	public boolean modifyEmployee(EmployeeClass changedEmployee) {
		boolean result;
		result = employeeDB.modifyEmployee(changedEmployee);
		
		return result;
	}

	/**
	 * Returns a list of all available employees on a certain date.
	 * 
	 * @param	date			the date requested
	 * @return	list of available employees
	 * @throws	SQLException	SQL error in querying employees
	 * @see		EmployeeAvailabilityDBHandler
	 * @see		EmployeeClass
	 */
	public List<EmployeeClass> listAvailableEmployees(Date date)
	{
		ResultSet result = availabilityDB.findAvailableEmployees(date);
		
		List<EmployeeClass> list = new ArrayList<EmployeeClass>();
		
		try {
			while(result.next())
			{
				String fName = result.getString("firstName");
				String lName = result.getString("lastName");
				String phoneNum = result.getString("phoneNum");
				double wage = result.getDouble("wage");
				String address = result.getString("address");
				String birthDate = result.getString("birthDate");
				String position = result.getString("position");
				
				EmployeeClass tempEmployee = new EmployeeClass(fName, lName, phoneNum, address, birthDate, position, wage);
				tempEmployee.setEmployeeId(result.getInt("employee_id"));
				list.add(tempEmployee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * Returns a list of all employees working on a certain event.
	 * 
	 * @param	event_id		the event requested
	 * @return	list of employees working on a certain event
	 * @throws	SQLException	SQL error in querying employees
	 * @see		EmployeeAvailabilityDBHandler
	 * @see		EmployeeClass
	 */
	public List<Integer> listEmployeeIDForEvent(int event_id){
		ResultSet result = null;
		List<Integer> allEmployeeIds = new ArrayList<Integer>();
		
		result = availabilityDB.allBookedEmployees(event_id);
		
		try {
			while(result.next())
			{
				int currentEID = result.getInt("employee_id");
				allEmployeeIds.add(new Integer(currentEID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allEmployeeIds;
	}

	/**
	 * Gets employee's ID.
	 * 
	 * @param	employeeClass	requested employee
	 * @return	requested employee's ID
	 * @throws	SQLException	Error in database
	 * @see		EmployeeClass
	 * @see		EmployeeDBHandler
	 */
	public int getEmployeeID(EmployeeClass employeeClass) {
		int employee_id = 0;
		ResultSet result = employeeDB.getEmployee(employeeClass);
	
		try {
			result.next();
			employee_id = result.getInt("employee_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employee_id;
	}
}
