package Equipment;

/**
 * This class is used to query the inventory, where the client is keeping track of a
 * varying number of items.
 * 
 * @author Chuan Yun Loe
 */
public class EquipmentClass {

	private String name;
	private int quantity;
	private int equipment_id = 9999999;
	private double rentalcost;

	/**
	 * Constructs an EquipmentClass object initialised with the given values for the
	 * equipment name, quantity, and rental cost per hour. 
	 * 
	 * @param	name		the name of the equipment
	 * @param	quantity	the quantity
	 * @param	rentalcost	the cost of renting equipment per unit per hour
	 */
	public EquipmentClass(String name, int quantity, double rentalcost) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.rentalcost = rentalcost;
	}
	
	/**
	 * Constructs an EquipmentClass object initialised with the given values for the
	 * equipment name, quantity, rental cost per hour, and equipment ID. 
	 * 
	 * @param	name			the name of the equipment
	 * @param	quantity		the quantity
	 * @param	rentalcost		the cost of renting equipment per unit per hour
	 * @param	equipment_id	the ID of the equipment
	 */
	public EquipmentClass(String name, int quantity, double rentalcost, int equipment_id) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.rentalcost = rentalcost;
		this.equipment_id = equipment_id;
	}

	/**
	 * Verifies the input from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verifyInput(){
		//Sanitize input
		name = name.replace("'","");
				
		if(quantity < 0 || rentalcost < 0 || equipment_id < 0){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets the name of the equipment.
	 * 
	 * @return	the name of the equipment
	 * @see		#setName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the equipment.
	 * 
	 * @param	name	the name of the equipment
	 * @see		#getName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the quantity of the equipment.
	 * 
	 * @return	the quantity of the equipment
	 * @see		#setQuantity(int)
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of the equipment.
	 * 
	 * @param	quantity	the quantity of the equipment
	 * @see		#getQuantity()
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the ID of the equipment.
	 * 
	 * @return	the ID of the equipment
	 * @see		#setEquipmentId(int)
	 */
	public int getEquipmentId() {
		if(equipment_id == 9999999){
			EquipmentControllerClass.getInstance().setEquipmentId(this);
		}
		return equipment_id;
	}

	/**
	 * Sets the ID of the equipment.
	 * 
	 * @param	equipment_id	the ID of the equipment
	 * @see		#getEquipmentId()
	 */
	public void setEquipmentId(int equipment_id) {
		this.equipment_id = equipment_id;
	}

	/**
	 * Gets the rental cost of the equipment.
	 * 
	 * @return	the rental cost of the equipment
	 * @see		#setRentalCost(double)
	 */
	public double getRentalCost() {
		return rentalcost;
	}

	/**
	 * Sets the rental cost of the equipment.
	 * 
	 * @param	rentalcost	the rental cost of the equipment
	 * @see		#getRentalCost()
	 */
	public void setRentalCost(double rentalcost) {
		this.rentalcost = rentalcost;
	}

}
