package Event;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Login.UserClass;
import UserInterface.UIControllerClass;

/**
 * This class is used to control event related operations.
 * 
 * @author	Mark Lessard
 */
public class EventControllerClass {

	private static EventControllerClass controllerSinglton;
	
	private EventDBHandler eventDB = new EventDBHandler();
	
	/**
	 * Constructs a new instance of event controller.
	 */
	private EventControllerClass(){}
	
	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initialises the object and 
	 * returns it, if it has been initialised then it returns the already initialised 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static EventControllerClass getInstance()
	{
		if(controllerSinglton == null){
			EventControllerClass instance = new EventControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}
	
	/**
	 * Adds a new event to the database.
	 * 
	 * @param	newEvent	new event
	 * @param	currentUser	current user
	 * @return	true if added successfully; false otherwise
	 * @see		EventClass
	 * @see		EventDBHandler
	 * @see		UserClass
	 */
	public boolean addEvent( EventClass newEvent, UserClass currentUser ) {
		boolean result = false;
		
		result = newEvent.verifyInput();
		if(!result)
			return false;
				
		result = eventDB.addEvent(newEvent, currentUser);
		
		newEvent.setEventId(getEventId(newEvent));
		
		return result;
	}


	/**
	 * Gets the event ID for a given event.
	 * <p>
	 * Used for event objects that do not have ID initialised.
	 * 
	 * @param	newEvent	given event
	 * @return	given event's ID
	 * @throws	SQLException	Error with database
	 * @see		EventClass
	 * @see		EventDBHandler
	 */
	public int getEventId(EventClass newEvent) {

		ResultSet result = null;
		int event_id = 0;
		
		result = eventDB.getEventID(newEvent);
		
		try {
			result.next();
			event_id = result.getInt("event_id");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return event_id;
	}

	/**
	 * Deletes an event from the database.
	 * 
	 * @param	event_id	ID of event being deleted
	 * @return	true if deleted successfully; false otherwise
	 * @see		EventDBHandler
	 */
	public boolean deleteEvent( int event_id ) {
		boolean result = false;
		
		result = eventDB.removeEvent(event_id);
		
		return result;

	}

	/**
	 * Modifies an event in the database.
	 * 
	 * @param	modifiedEvent	event being modified
	 * @return	true if modified successfully; false otherwise
	 * @see		EventClass
	 * @see		EventDBHandler
	 */
	public boolean modifyEvent( EventClass modifiedEvent ) {
		boolean result = false;
		
		result = eventDB.modifyEvent(modifiedEvent);
		
		return result;
	}


	/**
	 * Gets list of pending events.
	 * 
	 * @return	list of pending events
	 * @see		EventClass
	 * @see		EventDBHandler
	 * @see		UserClass
	 */
	public List<EventClass> getPendingEvents(){

		ResultSet result = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		List<EventClass> allPendingEvents = new ArrayList<EventClass>();
		
		result = eventDB.getAllPendingEvents(UIControllerClass.currentUser);
		
		try {
			while(result.next()){
				int event_id = result.getInt("event_id");
				String name = result.getString("name");
				String date = result.getString("date");
				int numGuests = result.getInt("numGuests");
				int menu_id = result.getInt("main_menu_id");
				int veg_menu_id = result.getInt("veg_menu_id");
				String location = result.getString("location");
				String status = result.getString("status");
				int client_id = result.getInt("client_id");
				double lengthOfEvent = result.getDouble("totalEventLength");
				
				try {
					myDate = new Date(dateFormat.parse(date).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
							
				EventClass myEvent = new EventClass(menu_id, veg_menu_id, client_id, event_id, name, myDate, numGuests, location, lengthOfEvent, status);
				
				allPendingEvents.add(myEvent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return allPendingEvents;
	}
	
	/**
	 * Gets list of all events.
	 * 
	 * @return	list of all events
	 * @throws	ParseException	Invalid date input
	 * @see		EventClass
	 * @see		EventDBHandler
	 */
	public List<EventClass> getAllEvents() {
		ResultSet result = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		List<EventClass> allEvents = new ArrayList<EventClass>();
		
		result = eventDB.getAllEvents();
		
		try {
			while(result.next()){
				int event_id = result.getInt("event_id");
				String name = result.getString("name");
				String date = result.getString("date");
				int numGuests = result.getInt("numGuests");
				int menu_id = result.getInt("main_menu_id");
				int veg_menu_id = result.getInt("veg_menu_id");
				String location = result.getString("location");
				String status = result.getString("status");
				int client_id = result.getInt("client_id");
				double lengthOfEvent = result.getDouble("totalEventLength");
				
				try {
					myDate = new Date(dateFormat.parse(date).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
							
				EventClass myEvent = new EventClass(menu_id, veg_menu_id, client_id, event_id, name, myDate, numGuests, location, lengthOfEvent, status);
				
				allEvents.add(myEvent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return allEvents;
	}

	/**
	 * Gets an event based on event's ID.
	 * 
	 * @param	event_id	event's ID
	 * @return	requested event
	 * @throws	SQLException	Error in querying event
	 * @see		EventClass
	 * @see		EventDBHandler
	 */
	public EventClass getEvent(int event_id)
	{
		ResultSet result = null;
		EventClass event = null;
		
		result = eventDB.getEvent(event_id);
		
		if(result == null)
		{
			System.out.println("Error: no such event");
			return null;
		}
		try {
			result.next();
			int client_id = result.getInt("client_id");
			String name = result.getString("name");
			Date date = result.getDate("date");
			int numGuests = result.getInt("numGuests");
			int menu_id = result.getInt("main_menu_id");
			int veg_menu_id = result.getInt("veg_menu_id");
			String location = result.getString("location");
			String status = result.getString("status");
			double lengthOfEvent = result.getDouble("totalEventLength");
			
			event = new EventClass(menu_id, veg_menu_id, client_id, event_id, name, date, numGuests, location, lengthOfEvent, status);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return event;
	}

	/**
	 * Confirms an event.
	 * 
	 * @param	event_id	event's ID
	 * @see		EventDBHandler
	 */
	public void confirmEvent(int event_id) {
		eventDB.confirmEvent(event_id);
	}
	
	/**
	 * Gets a list of events on a particular date.
	 * 
	 * @param	dateOfEvent	requested date
	 * @return	list of events' ID
	 * @throws	SQLException	Error in querying event
	 * @see		EventDBHandler
	 */
	public List<Integer> getListOfEventIDsOfDate(Date dateOfEvent) {
		List<Integer> events = new ArrayList<Integer>();
		ResultSet result = null;
		
		result = eventDB.getEventsFromDate(dateOfEvent);
		
		try {
			while(result.next()){
				int event_id = result.getInt("event_id");
				events.add(event_id);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return events;
	}

}
