package Event;

import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import DB.DBHandler;
import Login.UserClass;


/**
 * This class is used for handling event database.
 * 
 * @author	Mark Lessard
 */
public class EventDBHandler extends DBHandler{

	/**
	 * Constructs an instance of event database handler.
	 */
	public EventDBHandler() {
	}

	/**
	 * Gets all events from database.
	 * 
	 * @return	ResultSet of all events
	 * @throws	SQLException	Error in database
	 */
	public ResultSet getAllEvents() {
		ResultSet result = null;

		try {
			result = query("SELECT * " +
							"FROM events");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}
	
	/**
	 * Deletes an event from database.
	 * 
	 * @param	event_id	event's ID
	 * @return	true if deleted successfully; false otherwise
	 * @throws	SQLException	Error in database
	 */
	public boolean removeEvent( int event_id ){
		int result =  0;
		
		//Sanity check, make sure that the event id is actually set!
		assert(event_id != 9999999);
		
		try {
			result = updateQuery("DELETE FROM events " + 
								"WHERE event_id=" + event_id );
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}
	
	/**
	 * Adds an event to database.
	 * 
	 * @param	newEvent	new event
	 * @param	currentUser	current	user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		EventClass
	 * @see		UserClass
	 */
	public boolean addEvent( EventClass newEvent, UserClass currentUser ){
		int result = 0;
		try {
			result = updateQuery("INSERT INTO events (client_id, name, date, numGuests, main_menu_id, veg_menu_id, location, creationUser_id, totalEventLength, status) " + 
								"VALUES('" 
								+ newEvent.getClientId() + "','" 
								+ newEvent.getName() + "', '" 
								+ newEvent.getDateOfEvent() + "', '"
								+ newEvent.getNumGuests() + "', '" 
								+ newEvent.getMainMenuId() + "', '" 
								+ newEvent.getVegMenuId() + "', '" 
								+ newEvent.getLocation() + "', '" 
								+ currentUser.getUserId() + "', '" 
								+ newEvent.getTotalLengthOfEvent() + "', '" 
								+ "pending')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;

		return true;
	}

	/**
	 * Modifies an event in database.
	 * 
	 * @param	modifiedEvent	event being modified
	 * @return	true if modified successfully; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		EventClass
	 */
	public boolean modifyEvent(EventClass modifiedEvent){
		int result = 0;

		try {
			result = updateQuery("UPDATE events " +
								"SET client_id='" + modifiedEvent.getClientId() +
								"', name='" + modifiedEvent.getName() + 
								"', date='" + modifiedEvent.getDateOfEvent() + 
								"', numGuests=" + modifiedEvent.getNumGuests() + 
								", main_menu_id='" + modifiedEvent.getMainMenuId() + 
								"', veg_menu_id='" + modifiedEvent.getVegMenuId() +
								"', totalEventLength='" + modifiedEvent.getTotalLengthOfEvent() +
								"', location='" + modifiedEvent.getLocation() + "' " +
								"WHERE event_id ="+ modifiedEvent.getEventId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(result == 0) 
			return false;

		return true;
	}
	
	/**
	 * Confirms an event in database.
	 * 
	 * @param	event_id	event's ID
	 * @return	true if confirmed successfully; false otherwise
	 * @throws	SQLException	Error in database
	 */
	public boolean confirmEvent(int event_id){
		int result = 0;

		try {
			result = updateQuery("UPDATE events " +
								"SET status='confirmed' " +
								"WHERE event_id ="+ event_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(result == 0) 
			return false;

		return true;
	}

	/**
	 * Gets an event from database.
	 * 
	 * @param	event_id	event's ID
	 * @return	ResultSet of requested event
	 * @throws	SQLException	Error in database
	 */
	public ResultSet getEvent(int event_id) {
		ResultSet result = null;

		try {
			result = query("SELECT * " +
							"FROM events " +
							"WHERE event_id=" + event_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Gets all pending events.
	 * 
	 * @param	currentUser	current user
	 * @return	ResultSet of all pending events
	 * @throws	SQLException	Error in database
	 * @see		UserClass
	 */
	public ResultSet getAllPendingEvents(UserClass currentUser) {
		ResultSet result = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();

		try {
			result = query("SELECT * " +
							"FROM events " +
							"WHERE creationUser_id !=" + currentUser.getUserId() + " AND status='pending' AND date >= '" + dateFormat.format(cal.getTime()) + "'");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Gets event's ID.
	 * 
	 * @param	newEvent	requested event
	 * @return	ResultSet of required ID
	 * @throws	SQLException	Error in database
	 * @see		EventClass
	 */
	public ResultSet getEventID(EventClass newEvent) {
		ResultSet result = null;

		try {
			result = query("SELECT * " +
							"FROM events " +
							"WHERE client_id =" + newEvent.getClientId() + " AND " +
							"name ='" + newEvent.getName() + "' AND " +
							"date ='" + newEvent.getDateOfEvent() + "' AND " +
							"numGuests =" + newEvent.getNumGuests() + " AND " +
							"main_menu_id =" + newEvent.getMainMenuId() + " AND " +
							"veg_menu_id =" + newEvent.getVegMenuId() + " AND " +
							"location ='" + newEvent.getLocation() + "'");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}
	
	/**
	 * Get events that are on a particular date.
	 * 
	 * @param	dateOfEvent	requested date
	 * @return	ResultSet of required events
	 * @throws	SQLException	Error in database
	 */
	public ResultSet getEventsFromDate(Date dateOfEvent) {
		ResultSet result = null;
		try {
			result = query("SELECT * " +
							"FROM events " +
							"WHERE date='" + dateOfEvent + "'"); 
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
