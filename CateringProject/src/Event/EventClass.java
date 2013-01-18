package Event;

import java.sql.Date;
import java.util.List;

import Client.ClientClass;
import Client.ClientControllerClass;
import Employee.EmployeeControllerClass;
import Equipment.EquipmentControllerClass;
import Menu.MenuClass;
import Menu.MenuControllerClass;

/**
 * This class is used to hold event details.
 * 
 * @author	Mark Lessard
 */
@SuppressWarnings("unused")
public class EventClass {

	private MenuClass mainMenu, vegMenu;
	private ClientClass client;
	private Date dateOfEvent;
	private String name, location, status;
	private double totalLengthOfEvent;
	private int numGuests, event_id, mainMenu_id, vegMenu_id, client_id;
	private List<Integer> scheduledEmployees_id, rentedEquipment_id;

	/**
	 * Constructs an EventClass object initialised with the given values for the
	 * main menu's ID, vegetable menu's ID, client's ID, name, date of event, 
	 * number guests, location, and length of event.
	 * <p>
	 * This is without event ID.
	 * 
	 * @param	mainMenu_id		main menu's ID
	 * @param	vegMenu_id		vegetable menu's ID
	 * @param	client_id		client's ID
	 * @param	name			event's name
	 * @param	dateOfEvent		date of event
	 * @param	numGuests		number of guests
	 * @param	location		event's location
	 * @param	lengthOfEvent	length of event
	 */
	public EventClass(int mainMenu_id, int vegMenu_id, int client_id, String name,
					Date dateOfEvent, int numGuests, String location, double lengthOfEvent) {
		//Set to null at the start, will get initialized upon first use
		mainMenu = null;
		vegMenu = null;
		client = null;
		status = "pending";
		event_id = 9999999;
		
		this.name = name;
		this.dateOfEvent = dateOfEvent;
		this.numGuests = numGuests;
		this.location = location;
		this.mainMenu_id = mainMenu_id;
		this.vegMenu_id = vegMenu_id;
		this.client_id = client_id;
		this.totalLengthOfEvent = lengthOfEvent;
	}

	/**
	 * Constructs an EventClass object initialised with the given values for the
	 * main menu's ID, vegetable menu's ID, client's ID, event's ID, name, date 
	 * of event, number guests, location, and length of event.
	 * <p>
	 * This is with event ID.
	 * 
	 * @param	mainMenu_id		main menu's ID
	 * @param	vegMenu_id		vegetable menu's ID
	 * @param	client_id		client's ID
	 * @param	event_id		event's ID
	 * @param	name			event's name
	 * @param	dateOfEvent		date of event
	 * @param	numGuests		number of guests
	 * @param	location		event's location
	 * @param	lengthOfEvent	length of event
	 */
	public EventClass(int mainMenu_id, int vegMenu_id, int client_id, int event_id, String name,
			Date dateOfEvent, int numGuests, String location, double lengthOfEvent, String status) {
		//Set to null at the start, will get initialized upon first use
		mainMenu = null;
		vegMenu = null;
		client = null;
		
		this.event_id = event_id;
		this.name = name;
		this.status = status;
		this.dateOfEvent = dateOfEvent;
		this.numGuests = numGuests;
		this.location = location;
		this.mainMenu_id = mainMenu_id;
		this.vegMenu_id = vegMenu_id;
		this.client_id = client_id;
		this.totalLengthOfEvent = lengthOfEvent;
	}
	
	/**
	 * Constructs an EventClass object initialised with the given values for the
	 * client's ID, name, date of event, number guests, location, and length of 
	 * event.
	 * <p>
	 * This is without event's ID, main menu's ID, and vegetarian menu's ID.
	 * 
	 * @param	client_id		client's ID
	 * @param	name			event's name
	 * @param	dateOfEvent		date of event
	 * @param	numGuests		number of guests
	 * @param	location		event's location
	 * @param	lengthOfEvent	length of event
	 */
	public EventClass(int client_id, String name,
					Date dateOfEvent, int numGuests, String location, double lengthOfEvent) {
		//Set to null at the start, will get initialized upon first use
		mainMenu = null;
		vegMenu = null;
		client = null;
		status = "pending";
		event_id = 9999999;
		
		this.name = name;
		this.dateOfEvent = dateOfEvent;
		this.numGuests = numGuests;
		this.location = location;
		this.client_id = client_id;
		this.totalLengthOfEvent = lengthOfEvent;
	}	

	/**
	 * Verifies the input from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verifyInput(){
		//Sanitize input
		name = name.replace("'","");
		location = location.replace("'","");
		
		if(mainMenu_id < 0 || vegMenu_id < 0 || client_id < 0){
			return false;
		}
		
		if(numGuests < 0 || totalLengthOfEvent < 0){
			return false;
		}
		
		if(!status.matches("pending") && !status.matches("confirmed")){
			return false;
		}
		
		return true;
	}

	/**
	 * Verifies the initial input from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verifyInputInit(){
		if(name.length() <= 0){
			return false;
		}
		if(dateOfEvent.toString().length() <= 0){
			return false;
		}
		if(client_id < 0){
			return false;
		}
		if(location.length() <= 0){
			return false;
		}	
		if(numGuests < 0 || totalLengthOfEvent < 0){
			return false;
		}
		
		if(!status.matches("pending") && !status.matches("confirmed")){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets the main menu.
	 * 
	 * @return	main menu of event
	 * @see		#setMainMenu(MenuClass)
	 * @see		MenuControllerClass
	 */
	public MenuClass getMainMenu() {
		//Initialized on first use
		if(mainMenu == null){
			mainMenu = MenuControllerClass.getInstance().getMenu(mainMenu_id);
		}
		
		return mainMenu;
	}

	/**
	 * Sets the main menu.
	 * 
	 * @param	menu	main menu of event
	 * @see		#getMainMenu()
	 */
	public void setMainMenu(MenuClass menu) {
		this.mainMenu = menu;
	}

	/**
	 * Gets the client.
	 * 
	 * @return	event's client
	 * @see		#setClient(ClientClass)
	 * @see		MenuControllerClass
	 */
	public ClientClass getClient() {
		//Initialized on first use
		if(client == null){
			client = ClientControllerClass.getInstance().getClient(client_id);
		}
				
		return client;
	}

	/**
	 * Sets the client.
	 * 
	 * @param	client	event's client
	 * @see		#getClient()
	 */
	public void setClient(ClientClass client) {
		this.client = client;
	}

	/**
	 * Gets the event's name.
	 * 
	 * @return	event's name
	 * @see		#setName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the event's name.
	 * 
	 * @param	name	event's name
	 * @see		#getName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date of event.
	 * 
	 * @return	date of event
	 * @see		#setDateOfEvent(Date)
	 */
	public Date getDateOfEvent() {
		return dateOfEvent;
	}

	/**
	 * Sets the date of event.
	 * 
	 * @param	dateOfEvent	date of event
	 * @see		#getDateOfEvent()
	 */
	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	/**
	 * Gets the number of guests.
	 * 
	 * @return	number of guests
	 * @see		#setNumGuests(int)
	 */
	public int getNumGuests() {
		return numGuests;
	}

	/**
	 * Sets the number of guests.
	 * 
	 * @param	numGuests	number of guests
	 * @see		#getNumGuests()
	 */
	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}

	/**
	 * Gets the event's ID.
	 * 
	 * @return	event's ID
	 * @see		#setEventId(int)
	 */
	public int getEventId() {
		if(event_id == 9999999){
			event_id = EventControllerClass.getInstance().getEventId(this);
		}
		return event_id;
	}

	/**
	 * Sets the event's ID.
	 * 
	 * @param	event_id	event's ID
	 * @see		#getEventId()
	 */
	public void setEventId(int event_id) {
		this.event_id = event_id;
	}

	/**
	 * Gets the event's location.
	 * 
	 * @return	event's location
	 * @see		#setLocation(String)
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the event's location.
	 * 
	 * @param	location	event's location
	 * @see		#getLocation()
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the vegetable menu.
	 * 
	 * @return	vegetable menu
	 * @see		#setVegMenu(MenuClass)
	 * @see		MenuControllerClass
	 */
	public MenuClass getVegMenu() {
		//Initialized on first use
		if(vegMenu == null){
			vegMenu = MenuControllerClass.getInstance().getMenu(vegMenu_id);
		}
				
		return vegMenu;
	}

	/**
	 * Sets the vegetable menu.
	 * 
	 * @param	veg_menu	vegetable menu
	 * @see		#getVegMenu()
	 */
	public void setVegMenu(MenuClass veg_menu) {
		this.vegMenu = veg_menu;
	}

	/**
	 * Gets the main menu's ID.
	 * 
	 * @return	main menu's ID
	 * @see		#setMainMenuId(int)
	 */
	public int getMainMenuId() {
		return mainMenu_id;
	}

	/**
	 * Sets the main menu's ID.
	 * 
	 * @param	mainMenu_id	main menu's ID
	 * @see		#getMainMenuId()
	 */
	public void setMainMenuId(int mainMenu_id) {
		this.mainMenu_id = mainMenu_id;
	}

	/**
	 * Gets the vegetable menu's ID.
	 * 
	 * @return	vegetable menu's ID
	 * @see		#setVegMenuId(int)
	 */
	public int getVegMenuId() {
		return vegMenu_id;
	}

	/**
	 * Sets the vegetable menu's ID.
	 * 
	 * @param	vegMenu_id	vegetable menu's ID
	 * @see		#getVegMenuId()
	 */
	public void setVegMenuId(int vegMenu_id) {
		this.vegMenu_id = vegMenu_id;
	}

	/**
	 * Gets the client's ID.
	 * 
	 * @return	client's ID
	 * @see		#setClientId(int)
	 */
	public int getClientId() {
		return client_id;
	}

	/**
	 * Sets the client's ID.
	 * 
	 * @param	client_id	client's ID
	 * @see		#getClientId()
	 */
	public void setClientId(int client_id) {
		this.client_id = client_id;
	}

	/**
	 * Gets the event's status.
	 * 
	 * @return	event's status
	 * @see		#setStatus(String)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the event's status.
	 * 
	 * @param	status	event's status
	 * @see		#getStatus()
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the list of scheduled employees' ID.
	 * 
	 * @return	list of scheduled employees' ID
	 * @see		#setScheduledEmployeesId(List)
	 * @see		EmployeeControllerClass
	 */
	public List<Integer> getScheduledEmployeesId() {
		return EmployeeControllerClass.getInstance().listEmployeeIDForEvent(event_id);
	}

	/**
	 * Sets the list of scheduled employees' ID.
	 * 
	 * @param	scheduledEmployees_id	list of scheduled employees' ID
	 * @see		#getScheduledEmployeesId()
	 */
	public void setScheduledEmployeesId(List<Integer> scheduledEmployees_id) {
		this.scheduledEmployees_id = scheduledEmployees_id;
	}


	/**
	 * Gets the list of rented equipments' ID.
	 * 
	 * @return	list of rented equipments' ID
	 * @see		#setRentedEquipmentId(List)
	 * @see		EmployeeControllerClass
	 */
	public List<Integer> getRentedEquipmentId() {
		return EquipmentControllerClass.getInstance().listEquipmentIDForEvent(event_id);
	}

	/**
	 * Sets the list of rented equipments' ID.
	 * 
	 * @param	rentedEquipment_id	list of rented equipments' ID
	 * @see		#getRentedEquipmentId()
	 */
	public void setRentedEquipmentId(List<Integer> rentedEquipment_id) {
		this.rentedEquipment_id = rentedEquipment_id;
	}


	/**
	 * Gets the total duration of the event.
	 * 
	 * @return	total duration of the event
	 * @see		#setTotalLengthOfEvent(double)
	 */
	public double getTotalLengthOfEvent() {
		return totalLengthOfEvent;
	}

	/**
	 * Sets the total duration of the event.
	 * 
	 * @param	totalLengthOfEvent	total duration of the event
	 * @see		#getTotalLengthOfEvent()
	 */
	public void setTotalLengthOfEvent(double totalLengthOfEvent) {
		this.totalLengthOfEvent = totalLengthOfEvent;
	}
}
