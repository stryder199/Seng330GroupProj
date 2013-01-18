package Equipment;

import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBHandler;
import Login.UserClass;

/**
 * This class is the handler for equipment database.
 * 
 * @author Chuan Yun Loe
 */
public class EquipmentDBHandler extends DBHandler{

	/**
	 * Constructs a new instance of equipment database handler.
	 */
	public EquipmentDBHandler(){}
	
	/**
	 * Gets all the equipments from the database.
	 * 
	 * @return	ResultSet object
	 * @throws	SQLException	SQL error in querying equipment database
	 */
	public ResultSet getAllEquipment()
	{
		ResultSet result = null;
		
		try {
			result = query("SELECT * " +
							"FROM equipment ");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Adds a new equipment to the equipment database.
	 * 
	 * @param	newEquip		new equipment to be added into database
	 * @param	currentUser		user who added the new equipment
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Equipment already exists in inventory
	 * @throws	SQLException	Error with database
	 * @see		EquipmentClass
	 * @see		UserClass
	 */
	public boolean addEquipment( EquipmentClass newEquip, UserClass currentUser ){
		int result = 0;
		try {
			result = updateQuery("INSERT INTO equipment (name, quantity, creationUser_id, rent_per_hour) " + 
								"VALUES('" + newEquip.getName() + "'," + newEquip.getQuantity() + ", " + currentUser.getUserId() + ", " + newEquip.getRentalCost() +")");
		} catch (SQLException e) {
			System.out.println("Equipment already exists in inventory");
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}
	
	/**
	 * Deletes an equipment from the equipment database.
	 * 
	 * @param	equipmentId		Equipment's ID
	 * @return	true if deleted successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean removeEquipment( int equipmentId ){
		int result =  0;
		
		try {
			result = updateQuery("DELETE FROM equipment " + 
								"WHERE equipment_id=" + equipmentId);
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
	 * Modifies an equipment in the equipment database.
	 * 
	 * @param	equipment	the equipment to be modified in the database
	 * @return	true if modified successfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		EquipmentClass
	 */
	public boolean modifyEquipment( EquipmentClass equipment){
		int result = 0;
		
		try {
			result = updateQuery("UPDATE equipment " +
								"SET quantity=" + equipment.getQuantity() +
								", rent_per_hour=" + equipment.getRentalCost() +
								" WHERE name='" + equipment.getName() + "'");
		} catch (SQLException e) {
			System.out.println("SQL error in querying inventory");
			e.printStackTrace();
		}
		
		if(result == 0) { // SQL returns 0 when target equipment is not present in DB
			System.out.println("Equipment does not exist in inventory");
			return false;
		}
		return true;
	}

	/**
	 * Gets an equipment from the equipment database.
	 * 
	 * @param	equipment_id	requested equipment's ID
	 * @return	ResultSet of requested equipment
	 * @throws	SQLException	Error with database
	 */
	public ResultSet getEquipment(int equipment_id) {
		ResultSet result = null;
		
		try{
			result = query("SELECT * " +
							"FROM equipment " +
							"WHERE equipment_id=" + equipment_id);	
		} catch (SQLException e){
			System.out.println("Error: client does not exist. Client ID: " + equipment_id);
			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	/**
	 * Gets an equipment's ID from the equipment database.
	 * 
	 * @param	equipment	requested equipment
	 * @return	ResultSet of requested equipment
	 * @throws	SQLException	Error with database
	 * @see		EquipmentClass
	 */
	public ResultSet getEquipmentID(EquipmentClass equipment) {
		ResultSet result = null;
		
		try{
			result = query("SELECT * " +
							"FROM equipment " +
							"WHERE name='" + equipment.getName() + "'");	
		} catch (SQLException e){
			System.out.println("Error: equipment does not exist");
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
}
