package Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Login.UserClass;

/**
 * This class is the controller for equipment related operations.
 * 
 * @author Chuan Yun Loe
 */
public class EquipmentControllerClass {
	
	private static EquipmentControllerClass controllerSinglton;
	
	private EquipmentDBHandler equipDB = new EquipmentDBHandler();
	private EquipmentBookingDBHandler bookingDB = new EquipmentBookingDBHandler();
	
	/**
	 * Constructs a new instance of equipment controller.
	 */
	public EquipmentControllerClass(){}
	
	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initializes the object and
	 * returns it, if it has been initialized then it returns the already initialized 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static EquipmentControllerClass getInstance()
	{
		if(controllerSinglton == null){
			EquipmentControllerClass instance = new EquipmentControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}
	
	/**
	 * Adds an equipment to the equipment database. The name of user who added the 
	 * equipment will also be added into the database.
	 * <p>
	 * This function returns true if the entry has been added to the database
	 * successfully, else return false.
	 * 
	 * @param	newEquipment	the new equipment being added
	 * @param	currentUser		the user who is adding the new equipment
	 * @return	true if new equipment has been added to database successfully; false otherwise
	 * @see		EquipmentClass
	 * @see		EquipmentDBHandler
	 * @see		UserClass
	 */
	public boolean addEquipment(EquipmentClass newEquipment, UserClass currentUser) {
		boolean result = false;
		
		result = equipDB.addEquipment(newEquipment, currentUser);
		if(!result)
			return false;
		
		result = setEquipmentId(newEquipment);
		if(!result)
			return false;
				
		return true;
	}
	
	/**
	 * Adds an equipment to the equipment database. The name of user who added the 
	 * equipment will also be added into the database.
	 * <p>
	 * This function returns true if the entry has been added to the database
	 * successfully, else return false.
	 * 
	 * @param	currentUser		the user who is adding the new equipment
	 * @return	true if new equipment has been added to database successfully; false otherwise
	 * @see		EquipmentClass
	 * @see		EquipmentDBHandler
	 * @see		UserClass
	 */
	public boolean addEquipmentBooking(int event_id, int equipment_id, int quantity, UserClass currentUser) {
		boolean result = false;
		
		result = bookingDB.addBooking(event_id, equipment_id, quantity, currentUser);
		if(!result)
			return false;
		
		return true;
	}

	/**
	 * Deletes an equipment from the equipment database.
	 * <p>
	 * This function returns true if the equipment has been deleted from the database
	 * successfully, else return false.
	 * 
	 * @param	equipmentId		equipment's ID
	 * @return	true if equipment has been deleted from database successfully; false otherwise
	 * @see		EquipmentDBHandler
	 */
	public boolean deleteEquipment(int equipmentId) {
		boolean result = false;
						
		result = equipDB.removeEquipment(equipmentId);
		if(!result)
			return false;
		
		return true;
	}

	/**
	 * Modifies an equipment from the equipment database.
	 * <p>
	 * This function returns true if the equipment has been modified in the database
	 * successfully, else return false.
	 * 
	 * @param	updateEquipment	the equipment being modified
	 * @return	true if equipment has been modified in the database successfully; false otherwise
	 * @see		EquipmentClass
	 * @see		EquipmentDBHandler
	 */
	public boolean modifyEquipment(EquipmentClass updateEquipment) {
		boolean result = false;
		
		result = equipDB.modifyEquipment(updateEquipment);
		if(!result)
			return false;
		
		return true;
	}

	/**
	 * Lists all equipments in the equipment database.
	 * 
	 * @return	list of equipments
	 * @throws	SQLException	SQL error in querying inventory
	 * @see		EquipmentClass
	 * @see		EquipmentDBHandler
	 */
	public List<EquipmentClass> listAllEquipment() {
		ResultSet result;
		List<EquipmentClass> allEquipment = new ArrayList<EquipmentClass>();
		
		result = equipDB.getAllEquipment();
		
		try {
			while(result.next())
			{
				String name = result.getString("name");
				int quantity = result.getInt("quantity");

				double rentalcost = result.getDouble("rent_per_hour");
				
				EquipmentClass equip = new EquipmentClass(name, quantity, rentalcost);
				equip.setEquipmentId(result.getInt("equipment_id"));
				
				allEquipment.add(equip);
			}
		} catch (SQLException e) {
			System.out.println("SQL error in querying inventory");
			e.printStackTrace();
		}
		return allEquipment;
	}

	/**
	 * Lists all equipments' ID for a particular event.
	 * 
	 * @param	event_id		requested event's ID
	 * @return	list of equipment IDs
	 * @throws	SQLException	SQL error in querying inventory
	 * @see		EquipmentBookingDBHandler
	 */
	public List<Integer> listEquipmentIDForEvent(int event_id) {
		ResultSet result = null;
		List<Integer> allEquipmentIds = new ArrayList<Integer>();
		
		result = bookingDB.listBookingFromEvent(event_id);
		
		try {
			while(result.next())
			{
				int currentEID = result.getInt("equipment_id");
				allEquipmentIds.add(new Integer(currentEID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allEquipmentIds;
	}

	/**
	 * Gets an equipment from equipment database.
	 * 
	 * @param	equipment_id	requested equipment's ID
	 * @return	requested equipment
	 * @throws	SQLException	SQL error in querying inventory
	 * @see		EquipmentClass
	 * @see		EquipmentDBHandler
	 */
	public EquipmentClass getEquipment(int equipment_id) {
		ResultSet result = null;
		EquipmentClass equipment = null;
		// Queries database for client
		result = equipDB.getEquipment(equipment_id);

		try {
			result.next();
			//Stores clients information
			String name = result.getString("name");
			int quantity = result.getInt("quantity");
			double rentCost = result.getDouble("rent_per_hour");
			// Creates new client to return
			equipment = new EquipmentClass(name, quantity, rentCost, equipment_id);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return equipment;
	}

	/**
	 * Adds a new list of equipment into the equipment booking database.
	 * 
	 * @param	event_id	requested equipment's ID
	 * @param	bookequip	equipment being booked, initialise equipment_id and quantity
	 * @param	currentUser	current user
	 * @return	true if added successfully; false otherwise
	 * @see		EquipmentClass
	 * @see		EquipmentBookingDBHandler
	 * @see		UserClass
	 */
	public boolean bookNewEventEquipments(int event_id, List<EquipmentClass> bookequip, UserClass currentUser) {
		boolean result = false;
		Iterator<EquipmentClass> it; 
		
		//Sanity Check
		assert(bookequip != null);	
				
		it = bookequip.iterator();
		
		while(it.hasNext()){
			EquipmentClass element = it.next();

			result = bookingDB.addBooking(event_id, element.getEquipmentId(), element.getQuantity(), currentUser);
		}
		if(!result)
				return false;
			
		return true;
	}
	
	/**
	 * Deleted all equipment booked for a particular event.
	 * 
	 * @param	event_id		event's ID
	 * @return	true if deleted successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean removeEventEquipments(int event_id) {
		boolean result = false;
		
		result = bookingDB.removeBooking(event_id);
		if(!result)
			return false;
		
		return true;
	}
	
	/**
	 * Returns all equipment available for a particular date.
	 * 
	 * @param	event_id		events' ID
	 * @return	list of equipments available
	 * @throws	SQLException	Error with database
	 * @see		EquipmentBookingDBHandler
	 * @see		EquipmentDBHandler
	 */
	public List<EquipmentClass> listAllEquipmentAvailability(List<Integer> event_id) {
		ResultSet result, unavail;
		List<EquipmentClass> allEquipment = new ArrayList<EquipmentClass>();
		
		result = equipDB.getAllEquipment();
		unavail = bookingDB.listBookingFromMultipleEvent(event_id);
		try {
			while(result.next())
			{
				String name = result.getString("name");
				int quantity = result.getInt("quantity");
				float rentalcost = result.getFloat("rent_per_hour");
				EquipmentClass equip = new EquipmentClass(name, quantity, rentalcost);
				equip.setEquipmentId(result.getInt("equipment_id"));
				
				while(unavail.next()){
					if(unavail.getInt("equipment_id") == equip.getEquipmentId())
						quantity = quantity - unavail.getInt("quantity");
				}
				unavail.beforeFirst();
				equip.setQuantity(quantity);
				
				allEquipment.add(equip);
			}
		} catch (SQLException e) {
			System.out.println("SQL error in querying inventory");
			e.printStackTrace();
		}
		return allEquipment;
	}

	/**
	 * Sets the equipment's ID from the database.
	 * 
	 * @param	equipment	requested equipment
	 * @return	true if ID has been set; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		EquipmentClass
	 */
	public boolean setEquipmentId(EquipmentClass equipment) {
		ResultSet result = null;
		// Queries database for client
		result = equipDB.getEquipmentID(equipment);
		int id = 0;
		try {
			result.next();
			//Stores clients information
			id = result.getInt("equipment_id");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		equipment.setEquipmentId(id);
		
		return true;

	}

}
