package Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DB.DBHandler;
import Login.UserClass;

/**
 * This class is used for handling equipment booking database.
 * 
 * @author	Mark Lessard
 */
public class EquipmentBookingDBHandler extends DBHandler{

	/**
	 * Constructs an instance of equipment booking database handler.
	 */
	public EquipmentBookingDBHandler(){}
	
	/**
	 * Adds a new booking of equipment into the equipment booking database.
	 * 
	 * @param	event_id		event's ID
	 * @param	equipment_id	equipment's ID
	 * @param	quantity		amount of equipment being booked
	 * @param	currentUser		current user
	 * @return	true is added successfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		UserClass
	 */
	public boolean addBooking(int event_id, int equipment_id, int quantity, UserClass currentUser){
		int result = 0;
		try {
			result = updateQuery("INSERT INTO equipmentBookings (event_id, equipment_id, creationUser_id, quantity) " + 
								"VALUES(" 
								+ event_id + ", " 
								+ equipment_id + ", " 
								+ currentUser.getUserId() + ", " 
								+ quantity + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;

		return true;
	}
		
	/**
	 * Returns all equipment booked for a particular event.
	 * 
	 * @param	event_id		event's ID
	 * @return	ResultSet of booked equipments
	 * @throws	SQLException	Error with database
	 */
	public ResultSet listBookingFromEvent(int event_id){
		ResultSet result = null;
		
		try {
			result = query("SELECT * " +
							"FROM equipmentBookings " +
							"WHERE event_id=" + event_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Deletes all equipment booked for a particular event.
	 * 
	 * @param	event_id		event's ID
	 * @return	true if deleted successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean removeBooking(int event_id) {
		int result = 0;
		try {
			result = updateQuery("DELETE FROM equipmentBookings " + 
								"WHERE event_id=" + event_id);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0) { // SQL returns 0 when target equipment is not present in DB
			System.out.println("Equipment does not exist in inventory");
			return false;
		}
		return true;
	}
	
	/**
	 * Returns all equipment booked for a particular event.
	 * 
	 * @param	event_id		event's ID
	 * @return	ResultSet of booked equipments
	 * @throws	SQLException	Error with database
	 */
	public ResultSet listBookingFromMultipleEvent(List<Integer> event_id){
		ResultSet result = null;
		String s = event_id.get(0).toString();
		
		for(int i = 1; i < event_id.size(); i++) {
			s.concat(" OR event_id=" + event_id.get(i).toString());
		}
		
		try {
			result = query("SELECT * " +
							"FROM equipmentBookings " +
							"WHERE event_id=" + s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
