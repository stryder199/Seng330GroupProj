package Employee;

import java.sql.Date;

/**
 * This class is used to hold informations of an unavailable employee.
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeUnavailDate {

	private int employeeID;
	private Date date;
	
	/**
	 * Constructs an EmployeeUnavailDate object initialised with the given values 
	 * for the employee's ID, and date unavailable.
	 * 
	 * @param	employeeID	employee's ID
	 * @param	date		date unavailable
	 */
	public EmployeeUnavailDate(int employeeID, Date date) {
		super();
		this.employeeID = employeeID;
		this.date = date;
	}
	
	/**
	 * Gets employee's ID.
	 * 
	 * @return	employee's ID
	 * @see		#setEmployeeID(int)
	 */
	public int getEmployeeID() {
		return employeeID;
	}
	
	/**
	 * Sets employee's ID.
	 * 
	 * @param	employeeID	employee's ID
	 * @see		#getEmployeeID()
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	/**
	 * Gets date unavailable.
	 * 
	 * @return	date unavailable
	 * @see		#setDate(Date)
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets date unavailable.
	 * 
	 * @param	date	date unavailable
	 * @see		#getDate()
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
