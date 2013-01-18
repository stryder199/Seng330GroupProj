package Employee;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import DB.DBHandler;
import Login.UserClass;

/**
 * This class handles the following database tables:
 * EmployeeBookings (for keeping track of employees scheduled for events)
 * EmployeeUnavail (for keeping track of time booked off by the serving staff)
 * 
 * @author	Harriet Willmott 
 */
public class EmployeeAvailabilityDBHandler extends DBHandler{

	/**
	 * Constructs a new instance of employee availability database handler.
	 */
	public EmployeeAvailabilityDBHandler(){}
	
	
	/**
	 * This method queries both tables for employees that are free on the date 
	 * specified and returns the result of the query.
	 * 
	 * @param	date			date in which an employee is free
	 * @return	ResultSet of query
	 * @throws	SQLException	Error in employee database
	 */
	public ResultSet findAvailableEmployees(Date date)
	{
		ResultSet result = null;
		try {
			result = query("SELECT * " +
							"FROM (SELECT * " +
									"FROM employees " +
									"WHERE (employee_id) NOT IN " +
														"(SELECT employee_id " +
														"FROM employeeUnavail " +
														"WHERE dayUnavail  = '" + date.toString() + "')) AS X " +
							"INNER JOIN " +
								"(SELECT * " +
								"FROM employees " +
								"WHERE (employee_id) NOT IN " +
												"(SELECT employee_id " +
												"FROM employeeBookings " +
												"WHERE event_id IN (SELECT event_id " +
																	"FROM events " +
																	"WHERE date = '" + date.toString() + "'))) AS Y " +
							"USING (employee_id)");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Adds a new record to the table EmployeeUnavail indicating that a serving 
	 * staff has booked the given date off.
	 * 
	 * @param	empdate			employee who is unavailable on a later date
	 * @param	currentUser		the current user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error in employee database
	 * @see		EmployeeUnavailDate
	 * @see		UserClass
	 */
	public boolean addUnavailableDate(EmployeeUnavailDate empdate, UserClass currentUser)
	{
		int employeeID = empdate.getEmployeeID();
		Date uDate = empdate.getDate();
		String strDate = uDate.toString();
		int userID = currentUser.getUserId();

		try {
			updateQuery("INSERT INTO employeeUnavail(employee_id, dayUnavail, creationUser_id) " + 
							"VALUES(" + employeeID + ", '" + strDate + "', " + userID + ")");
		} catch (SQLException e) {
			System.out.println("Error inserting into EmployeeUnavailableDates");
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is used when employees are being scheduled for events.
	 * A record is created indicating that the employee is busy working
	 * that event on that date.
	 * 
	 * @param	empdate			employee with a new schedule
	 * @param	currentUser		the current user
	 * @throws	SQLException	Error in employee database
	 * @see		EmployeeBookedDate
	 * @see		UserClass
	 */
	public void addBookedDate(EmployeeBookedDate empdate, UserClass currentUser)
	{
		int employeeID = empdate.getEmployeeID();
		int eventID = empdate.getEventID();
		int userID = currentUser.getUserId();
	
		try {
			updateQuery("INSERT INTO employeeBookings(event_id, employee_id, creationUser_id) " + 
							"VALUES(" + eventID + ", " + employeeID + ", " + userID + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    /**
     * This method is used when employees are being scheduled for events.
     * A record is created indicating that the employee is busy working
     * that event on that date.
     *
     * @throws    SQLException    Error in employee database
     * @see        EmployeeBookedDate
     * @see        UserClass
     */
	public ResultSet allBookedEmployees(int event_id) {
		ResultSet result = null;

		try {
			result = query("SELECT * " +
							"FROM employeeBookings " +
							"WHERE event_id=" + event_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}
}
