package UserInterface;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Client.ClientClass;
import Client.ClientControllerClass;
import Employee.EmployeeClass;
import Employee.EmployeeControllerClass;
import Equipment.EquipmentClass;
import Equipment.EquipmentControllerClass;
import Event.EventClass;
import Event.EventControllerClass;
import Menu.MealClass;
import Menu.MealControllerClass;
import Menu.MenuControllerClass;

/**
 * This class is used for GUI tables.
 * 
 * @author Mark Lessard
 */
public class TableModel {
	private DefaultTableModel model = null;
	
	private int type = 9999;
	
	//Table column names
	private final String[] employeeCol = {"ID Number", "First Name", "Last Name", "Address",
			"Phone Number", "Birthdate", "Position", "Wage"};
	private final String[] clientCol = {"ID Number", "First Name", "Last Name", "Company",
			"Email", "Phone Number", "Address"};
	private final String[] eventCol = {"ID Number", "Client", "Name", "Date", "Location", "# Guests", "Duration"};
	private final String[] mealCol = {"ID Number", "Name", "Course Type", "Vegeterian Friendly?"};
	private final String[] mainVegMealCol = {"ID Number", "Name"};
	private final String[] availEmployCol = {"ID Number", "First Name", "Last Name", "Position"};
	private final String[] equipCol = {"ID Number", "Description", "Quantity", "Cost Per Hour"};
	
	/**
	 * Constructs a new instance of table modeling.
	 */
	public TableModel(){}
	
	//Returns the initialized tableModel with the data loaded
	//1 for employ, 2 for client, 3 for event, 4 for meal, 5 for equip
	/**
	 * Gets the appropriate table model for the corresponding input.
	 * 
	 * @param	type	the type of table requested
	 * @return	requested table
	 */
	public DefaultTableModel getTableModel(int type){
		if (model == null) {
			String[] columnName = null;
			this.type = type;
			switch (type) {
			case 1:
				columnName = employeeCol;
				model = new DefaultTableModel(columnName, 0){

				    /**
					 * Makes the table uneditable
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				};
				initEmployeeData(model);
				break;
			case 2:
				columnName = clientCol;
				model = new DefaultTableModel(columnName, 0){

				    /**
					 * Makes the table uneditable
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				};
				initClientData(model);
				break;
			case 3:
				columnName = eventCol;
				model = new DefaultTableModel(columnName, 0){

				    /**
					 * Makes the table uneditable
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				};
				initEventData(model);
				break;
			case 4:
				columnName = mealCol;
				model = new DefaultTableModel(columnName, 0){

				    /**
					 * Makes the table uneditable
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				};
				initMealData(model);
				break;
			case 5:
				columnName = equipCol;
				model = new DefaultTableModel(columnName, 0){

				    /**
					 * Makes the table uneditable
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       //all cells false
				       return false;
				    }
				};
				initEquipmentData(model);
				break;
			}
			
		}

		return model;
	}
	
	/**
	 * Gets the pending event table.
	 * 
	 * @return	table for pending event
	 */
	public DefaultTableModel getPendingEventTable(){
		if(model == null){
			this.type = 15;
			model = new DefaultTableModel(eventCol, 0){

			    /**
				 * Makes the table uneditable
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			initPendingEventData(model);
		}
		return model;
	}
	
	/**
	 * Gets the specific meal's table.
	 * 
	 * @param	vegFriendly		vegetable menu or not
	 * @param	course			the course of meals
	 * @return	requested table
	 */
	public DefaultTableModel getSpecificMealTable(boolean vegFriendly, String course){

		if (model == null) {
			String[] columnName = null;
			
			columnName = mainVegMealCol;
			model = new DefaultTableModel(columnName, 0){

			    /**
				 * Makes the table uneditable
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			if(vegFriendly){
				initVegMealData(model, course);
				this.type = 10;
			}
			else{
				initMainMealData(model, course);
				this.type = 11;
			}
			
//			table = new JTable(model);
//			table.setPreferredScrollableViewportSize(new Dimension(450, 70));
//			table.setFillsViewportHeight(true);	
		}

		return model;
	}
	
	//type 20
	/**
	 * Gets the available date table.
	 * 
	 * @param	date	requested date
	 * @return	available date table
	 */
	public DefaultTableModel getAvailTable(Date date){
	
		if (model == null) {
			this.type = 20;
			model = new DefaultTableModel(availEmployCol, 0){

			    /**
				 * Makes the table uneditable
				 */
				private static final long serialVersionUID = 1L;

				@Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			initAvailEmployeeData(model, date);
		}
	
		return model;
	}
	
	/**
	 * Initialises the equipment data for table display.
	 */
	private void initEquipmentData(DefaultTableModel model) {
		//Get all the equipment data from the DB
		List<EquipmentClass> allEquipments = EquipmentControllerClass.getInstance().listAllEquipment();
		Iterator<EquipmentClass> it;
		if (allEquipments != null){
			it = allEquipments.iterator();
			
			//Load it into the table rows
			while(it.hasNext()){
				Object[] data = new Object[equipCol.length];
				EquipmentClass element = it.next();
				
				data[0] = element.getEquipmentId();
				data[1] = element.getName();
				data[2] = element.getQuantity();
				data[3] = element.getRentalCost();
				
				model.addRow(data);
			}
		} 
	}

	/**
	 * Initialises the employee data for table display.
	 */
	private void initEmployeeData(DefaultTableModel model){
		//Get all the employee data from the DB
		List<EmployeeClass> allEmployees = EmployeeControllerClass.getInstance().getAllEmployees();
		Iterator<EmployeeClass> it;
		
		if (allEmployees != null){
			it = allEmployees.iterator();
			
			//Load it into the table rows
			while(it.hasNext()){
				Object[] data = new Object[employeeCol.length];
				
				EmployeeClass element = it.next();
				data[0] = element.getEmployeeId();
				data[1] = element.getFirstName();
				data[2] = element.getLastName();
				data[3] = element.getAddress();
				data[4] = element.getPhone();
				data[5] = element.getBirthDate();
				data[6] = element.getPosition();
				data[7] = element.getWage();
				
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Initialises the client data for table display.
	 */
	private void initClientData(DefaultTableModel model){
		Iterator<ClientClass> it;
		//Get all the client data from the DB
		List<ClientClass> allClients = ClientControllerClass.getInstance().getAllClients();
		
		if (allClients != null){
			it = allClients.iterator();
			
			//Load it into the table rows
			while(it.hasNext()){
				Object[] data = new Object[clientCol.length];
				
				ClientClass element = it.next();
				data[0] = element.getClientId();
				data[1] = element.getFirstName();
				data[2] = element.getLastName();
				data[3] = element.getCompany();
				data[4] = element.getEmail();
				data[5] = element.getPhone();
				data[6] = element.getAddress();
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Initialises the event data for table display.
	 */
	private void initEventData(DefaultTableModel model){
		//Get all the event data from the DB
		List<EventClass> allEvents = EventControllerClass.getInstance().getAllEvents();
		String status = "";
		Iterator<EventClass> it;	
		
		if (allEvents != null){
			it = allEvents.iterator();
			
			//Load it into the rows
			while(it.hasNext()){
				Object[] data = new Object[eventCol.length];
				
				EventClass element = it.next();
				if(element.getStatus().matches("pending")){
					status = " (PENDING)";
				}
				data[0] = element.getEventId();
				data[1] = element.getClient().getFirstName() + " "+ element.getClient().getLastName();
				data[2] = element.getName() + status;
				data[3] = element.getDateOfEvent().toString();
				data[4] = element.getLocation();
				data[5] = element.getNumGuests();
				data[6] = element.getTotalLengthOfEvent();
				
				model.addRow(data);
				status = "";
			}
		}
	}
	
	/**
	 * Initialises the pending event data for table display.
	 */
	private void initPendingEventData(DefaultTableModel model){
		//Get all the pending events from the DB
		List<EventClass> allEvents = EventControllerClass.getInstance().getPendingEvents();
		String status = "";
		Iterator<EventClass> it;	
		
		if (allEvents != null){
			it = allEvents.iterator();
			
			//Load it into the rows
			while(it.hasNext()){
				Object[] data = new Object[eventCol.length];
				
				EventClass element = it.next();
				if(element.getStatus().matches("pending")){
					status = " (PENDING)";
				}
				data[0] = element.getEventId();
				data[1] = element.getClient().getFirstName() + " "+ element.getClient().getLastName();
				data[2] = element.getName() + status;
				data[3] = element.getDateOfEvent().toString();
				data[4] = element.getLocation();
				data[5] = element.getNumGuests();
				data[6] = element.getTotalLengthOfEvent();
				
				model.addRow(data);
				status = "";
			}
		}
	}
	
	/**
	 * Initialises the meal data for table display.
	 */
	private void initMealData(DefaultTableModel model){
		//Get all the meals from the DB
		List<MealClass> allMeals = MenuControllerClass.getInstance().getAllMeals();
		
		if (allMeals != null){
			Iterator<MealClass> it;
			it = allMeals.iterator();
			
			//Load it into the rows
			while(it.hasNext()){
				Object[] data = new Object[mealCol.length];	
				MealClass element = it.next();
				data[0] = element.getID();
				data[1] = element.getName();
				data[2] = element.getMealCourse();
				data[3] = element.getVegFriendly();
				
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Initialises the main menu data for table display.
	 */
	private void initMainMealData(DefaultTableModel model, String course){
		//Load all the data for a specific set of meals from the DB
		List<MealClass> allMeals = MealControllerClass.getInstance().getSpecificMeals(false, course);
		
		if (allMeals != null){
			Iterator<MealClass> it;
			it = allMeals.iterator();
			
			//Load the data into the table rows
			while(it.hasNext()){
				Object[] data = new Object[mainVegMealCol.length];	
				MealClass element = it.next();
				data[0] = element.getID();
				data[1] = element.getName();
				
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Initialises the vegetable meal data for table display.
	 */
	private void initVegMealData(DefaultTableModel model, String course){
		//Load all the data for a specific veg meal from the DB
		List<MealClass> allMeals = MealControllerClass.getInstance().getSpecificMeals(true, course);
		
		if (allMeals != null){
			Iterator<MealClass> it;
			it = allMeals.iterator();
			
			//Load the data into the table rows
			while(it.hasNext()){
				Object[] data = new Object[mainVegMealCol.length];	
				MealClass element = it.next();
				data[0] = element.getID();
				data[1] = element.getName();
				
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Initialises the available employee data for table display.
	 */
	private void initAvailEmployeeData(DefaultTableModel model, Date date){
		//Load all the data from availiable employees from the DB
		List<EmployeeClass> allAvailEmploy = EmployeeControllerClass.getInstance().listAvailableEmployees(date);
		
		if (allAvailEmploy != null){
			Iterator<EmployeeClass> it;
			it = allAvailEmploy.iterator();
			
			//Load the data into the table rows
			while(it.hasNext()){
				Object[] data = new Object[availEmployCol.length];	
				EmployeeClass element = it.next();
				data[0] = element.getEmployeeId();
				data[1] = element.getFirstName();
				data[2] = element.getLastName();
				data[3] = element.getPosition();
				
				model.addRow(data);
			}
		}
	}
	
	/**
	 * Adds a new row.
	 */
	public void addRow(Object[] newRow){
		model.addRow(newRow);
	}
	
	/**
	 * Removes a row.
	 */
	public void removeRow(int index){
		model.removeRow(index);
	}
	
	/**
	 * Gets ID from a row.
	 */
	public int getIDFromRow(int index){
		return (int)model.getValueAt(index, 0);
	}
	
	/**
	 * Gets a row from ID.
	 */
	public int getRowFromID(int id){
		for(int i=0; i<model.getRowCount(); i++){
			if((int)model.getValueAt(i, 0) == id){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Gets a row of data.
	 */
	public Object[] getData(int index){
		Object[] data = new Object[model.getColumnCount()];
		
		for(int i=0; i<model.getColumnCount(); i++){
			data[i] = model.getValueAt(index, i);
		}
		
		
		return data;
	}
	
	/**
	 * Modifies a data in a column.
	 */
	public boolean modifyData(Object[] modifiedData, int index){
		if(modifiedData.length != model.getColumnCount()){
			return false;
		}
		
		for(int i=0; i<modifiedData.length; i++){
			model.setValueAt(modifiedData[i], index, i);
		}

		return true;
	}

	/**
	 * Gets the type of table.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type of table.
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Gets the number of rows.
	 */
	public int getRowCount(){
		return model.getRowCount();
	}
	
	/**
	 * Resets model data.
	 */
	public void resetModelData(){
		model = null;
	}
}
