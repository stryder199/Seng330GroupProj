package Employee;

/**
 * This class is used to hold which employee is booked for which event.
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeBookedDate {
	
	private int employeeID;
	private int eventID;

	/**
	 * Constructs an EmployeeBookedDate initialised with the given value
	 * for the employeeID and eventID.
	 * 
	 * @param	employeeID	the ID of the employee
	 * @param	eventID		the ID of the event
	 */
	public EmployeeBookedDate(int employeeID, int eventID) {
		super();
		this.employeeID = employeeID;
		this.eventID = eventID;
	}
	
	/**
	 * Gets the employee's ID
	 * 
	 * @return	employee's ID
	 * @see		#setEmployeeID(int)
	 */
	public int getEmployeeID() {
		return employeeID;
	}
	
	/**
	 * Sets the employee's ID
	 * 
	 * @param	employeeID	employee's ID
	 * @see		#getEmployeeID()
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	/**
	 * Gets the event's ID
	 * 
	 * @return	event's ID\
	 * @see		#setEventID(int)
	 */
	public int getEventID() {
		return eventID;
	}
	
	/**
	 * Sets the event's ID
	 * 
	 * @param	eventID	event's ID
	 * @see		#getEventID()
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
}
