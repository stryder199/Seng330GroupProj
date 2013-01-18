package Event;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import UserInterface.ButtonListener;
import UserInterface.TableModel;
import UserInterface.ComponentGeneratorClass;
import UserInterface.UIControllerClass;
import Client.ClientClass;
import Client.ClientControllerClass;
import Employee.EmployeeBookedDate;
import Employee.EmployeeClass;
import Employee.EmployeeControllerClass;
import Equipment.EquipmentClass;
import Equipment.EquipmentControllerClass;
import Login.UserClass;
import Menu.MealControllerClass;
import Menu.MenuClass;
import Menu.MenuControllerClass;

/**
 * This class is used for GUI display of event view.
 * 
 * @author	Cody Plante
 */
@SuppressWarnings("unused")
public class EventUIViewClass {
	// String names for cardholder
	String rootViewName = "Main Menu View";
	String mainViewName = "Event Main View";
	String listViewName = "Event List View";
	String addViewName = "Event Add View";
	String addMenuViewName = "Event Add Menu View";
	String addConfirmViewName = "Event Add Confirm Menu View";
	String addEquipmentViewName = "Event Equipment View";
	String addEmployeeBookingViewName = "Event Employee Booking Menu View";
	String modifyViewName = "Event Modify View";
	String deleteViewName = "Event Delete View";
	String confirmViewName = "Event Confirm View";
	String invoiceViewName = "Event Invoice View";
	//all panels associated with event menu
	JPanel cardHolder, mainPanel, addPanel, addMenuPanel, addConfirmPanel, addEmployeeBookingPanel;
	JPanel deletePanel, modifyPanel, listPanel, confirmPanel, invoicePanel, addEquipmentPanel;
	//all tables and tablemodels used within event menu
	TableModel allEventsModel, allClientsModel, allEquipmentModel, allMainAppModel, availEmployeesModel, 
				allMainMainModel, allMainDessModel, allVegAppModel, allVegMainModel, allVegDessModel;
	JTable allPendingEvents, availEmployees;
	int TableWidth = 800;
	
	private boolean eventCreationComplete = true;	//condition to check if event is being created
	private boolean eventMenuChanged = false;	//condition to check if menus have been edited
	//component generator
	ComponentGeneratorClass generator;
	int mainMenuPanelMenuId = 99999, vegMenuPanelMenuId = 99999;
	EventClass currentEvent;
	
	/**
	 * This constructs an object which will initialise all panels and add them
	 * to the given cardholder for transitioning.
	 * 
	 * @param cardHolder
	 * @param allPendingEventsModel
	 * @param clientsModel
	 * @param equipmentModel
	 * @param availEmployees
	 */
	public EventUIViewClass(JPanel cardHolder, TableModel allPendingEventsModel, TableModel clientsModel, TableModel equipmentModel, TableModel availEmployees) {
		this.cardHolder = cardHolder; 	//sets cardholder to use
		// initializes table models

		allEventsModel = new TableModel();
		allClientsModel = clientsModel;
		allEquipmentModel = equipmentModel;
		availEmployeesModel = availEmployees;
		allMainAppModel = new TableModel();
		allMainMainModel = new TableModel();
		allMainDessModel = new TableModel();
		allVegAppModel = new TableModel();
		allVegMainModel = new TableModel();
		allVegDessModel = new TableModel();
		
		currentEvent = null;
		generator = new ComponentGeneratorClass(cardHolder);	//creates component generator
		//initalize all panels
		mainPanelInit();
		listPanelInit();
		addPanelInit();
		addMenuPanelInit();
		addConfirmPanelInit();
		addEquipmentPanelInit();
		addEmployeeBookingPanelInit();
		modifyPanelInit();
		deletePanelInit();
		confirmPanelInit(allPendingEventsModel);
		invoicePanelInit();
		//add all panels to the cardholder
		cardHolder.add(mainPanel, mainViewName);
		cardHolder.add(addPanel, addViewName);
		cardHolder.add(addMenuPanel, addMenuViewName);
		cardHolder.add(addConfirmPanel, addConfirmViewName);
		cardHolder.add(addEmployeeBookingPanel, addEmployeeBookingViewName);
		cardHolder.add(addEquipmentPanel, addEquipmentViewName);
		cardHolder.add(modifyPanel, modifyViewName);
		cardHolder.add(deletePanel, deleteViewName);
		cardHolder.add(listPanel, listViewName);
		cardHolder.add(confirmPanel, confirmViewName);
		cardHolder.add(invoicePanel, invoiceViewName);
	}

	/**
	 * This method initialises a main menu panel to represent the event Menu.
	 */
	private void mainPanelInit(){
		mainPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(mainPanel, 110);
		
		JLabel heading = generator.createLabel(mainPanel, "Events Menu", "");

		heading.setFont(new Font("Dialog", 1, 20));	//sets menu font size
		JButton createButton = generator.createButton(mainPanel, "Add Event", addViewName, 50);
		JButton equipmentButton = generator.createButton(mainPanel, "Add Event Equipment", addEquipmentViewName, 5);
		JButton employeeButton = generator.createButton(mainPanel, "Add Staff to Event", addEmployeeBookingViewName, 5);
		JButton modifyButton = generator.createButton(mainPanel, "Modify Event", modifyViewName, 5);
		JButton removeButton = generator.createButton(mainPanel, "Remove Event", deleteViewName, 5);
		JButton viewButton = generator.createButton(mainPanel, "View Events", listViewName, 5);
		JButton confirmButton = generator.createButton(mainPanel, "Confirm Events", confirmViewName, 5);
		JButton invoiceButton = generator.createButton(mainPanel, "View Invoices", invoiceViewName, 5);
		JButton backButton = generator.createButton(mainPanel, "Back", rootViewName, 30);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * This method initialises an event viewing panel accessible from the event Menu.
	 */
	private void listPanelInit(){
		listPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(listPanel, 70);
		JTable allEvents = new JTable(allEventsModel.getTableModel(3));
		allEvents.removeColumn(allEvents.getColumn("ID Number"));
		allEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEvents.setFillsViewportHeight(true);
		allEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEvents);
		scroll.setMaximumSize(new Dimension(TableWidth, 500));
		listPanel.add(scroll);
		// resizes tables columns
		allEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
		//creates backbutton that returns to the main event menu
		JButton backButton = generator.createButton(listPanel, "Back", mainViewName, 50);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	
	}
	
	/**
	 * This method initialises an event add panel accessible from the event Menu
	 * that will begin the process of adding an event by getting the basic details
	 * of the event from the user.
	 */
	public void addPanelInit() {
		addPanel = new JPanel();
		eventCreationComplete = true;	//resets creation status
		// generates the panel's formatted components
		generator.createRigidSpace(addPanel, 10);
		final JTable allClients = new JTable(allClientsModel.getTableModel(2));	//creates and displays a table of all clients
		allClients.removeColumn(allClients.getColumn("ID Number"));
		allClients.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allClients.setFillsViewportHeight(true);
		allClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allClients);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		addPanel.add(scroll);	//adds scroll to panel
		//generates label and input fields for event data
		JLabel clientChoiceLabel = generator.createLabel(addPanel, "Please choose the client associated with the event above", "");
		generator.createRigidSpace(addPanel, 30);
		JLabel nameLabel = generator.createLabel(addPanel, "Name");
		final JTextField nameInput = generator.createTextField(addPanel, "");		
		JLabel dateOfEventLabel = generator.createLabel(addPanel, "Date of Event (yyyy-mm-dd)");		
		final JTextField dateOfEventInput = generator.createTextField(addPanel, "");	
		JLabel locationLabel = generator.createLabel(addPanel, "Location");
		final JTextField locationInput = generator.createTextField(addPanel, "");
		JLabel numGuestsLabel = generator.createLabel(addPanel, "Number of Guests");
		final JTextField numGuestsInput = generator.createTextField(addPanel, "");	
		JLabel lengthOfEventLabel = generator.createLabel(addPanel, "Length Of Event (hours)");
		final JTextField lengthOfEventInput = generator.createTextField(addPanel, "");
		
		generator.createRigidSpace(addPanel, 10);
		final JLabel resultLabel = generator.createLabel(addPanel, " ", "");
		JButton continueButton = generator.createButton(addPanel, "Continue", 10);	
		JButton backButton = generator.createButton(addPanel, "Back", 5);
		
		final JLabel backWarningLabel = generator.createLabel(addPanel, " ", "");
		final JButton confirmBack = generator.createButton(addPanel, "Confirm", 5);
		//hides confirm button
		confirmBack.setVisible(false);
		
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		
		addPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				if (eventCreationComplete == true){
					allClients.clearSelection();
					nameInput.setText("");
					dateOfEventInput.setText("");
					locationInput.setText("");
					numGuestsInput.setText("");
					lengthOfEventInput.setText("");
				}
			}
		});
	
		//conditions for back button being pressed - tries to return to event menu
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				if (eventCreationComplete == false){	//checks if a new event is in the process of being created
					// enables a warning message to return to event menu
					backWarningLabel.setText("This events creation is not complete, going back will delete it. Confirm?");
					confirmBack.setVisible(true);
				} else {	//clears all event data and sets event menu to display
					allClients.clearSelection();
					nameInput.setText("");
					dateOfEventInput.setText("");
					locationInput.setText("");
					numGuestsInput.setText("");
					lengthOfEventInput.setText("");
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, mainViewName);
				}
			}
		});
		
		// confirm button that allows the user to return to event menu 
		// after acknowledging that they will lose the event data
		confirmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				backWarningLabel.setText(" ");
				confirmBack.setVisible(false);	//resets the warning label and confirm button to hidden
				eventCreationComplete = true;
				currentEvent = null;	//resets current working event
				allClients.clearSelection();
				nameInput.setText("");	//resets all input fields
				dateOfEventInput.setText("");
				locationInput.setText("");
				numGuestsInput.setText("");
				lengthOfEventInput.setText("");
				CardLayout cLayout = (CardLayout) cardHolder.getLayout();	//changes display to event menu
				cLayout.show(cardHolder, mainViewName);
			}
		});		
		// continue button that checks input is valid then goes to 
		// event menu selection
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				if (eventCreationComplete == false){	//resets warning label and confirm button
					backWarningLabel.setText(" ");
					confirmBack.setVisible(false);
				}
				resultLabel.setText("");
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				boolean result = true;
				int clientID = -1;
				
				try {		//attempts to get client id from selected row
					clientID = allClientsModel.getIDFromRow(allClients.getSelectedRow());
					if(clientID == -1){
						result = false;
					}				
				} catch (ArrayIndexOutOfBoundsException e1){
					resultLabel.setText("Please choose a client from the list");
					result = false;	//prevents from event creation if no client is selected
				}
				if (result){	
					if (!nameInput.getText().matches("") && !numGuestsInput.getText().matches("") && 
						!dateOfEventInput.getText().matches("") && !locationInput.getText().matches("") 
						&& !lengthOfEventInput.getText().matches("")){	//checks textfields arent empty

						if (nameInput.getText().matches(".+") && numGuestsInput.getText().matches("\\d+") && 
								dateOfEventInput.getText().matches("\\d{4}-\\d{2}-\\d{2}") && locationInput.getText().matches(".+") 
								&& lengthOfEventInput.getText().matches("\\d*\\.?\\d*")){		//checks textfields are in correct format
							
							Date myDate = null;
							try {	//sets date to date object
								myDate = new Date(dateFormat.parse(dateOfEventInput.getText()).getTime());
							} catch (ParseException e1) {
								resultLabel.setText("Date format incorrect, Please Try Again...");
								result = false;
							}
							// sets current event to a new event based off given input
							currentEvent = new EventClass(clientID, nameInput.getText(), 
														myDate, Integer.parseInt(numGuestsInput.getText()), locationInput.getText(), 
														Double.parseDouble(lengthOfEventInput.getText()));
							result = currentEvent.verifyInputInit();	//verifies input
							
							if (result){
								eventCreationComplete = false;	//goes to event menu and sets creation to incomplete
			    				CardLayout cLayout = (CardLayout) cardHolder.getLayout();
			    				cLayout.show(cardHolder, addMenuViewName);
							} else {
								resultLabel.setText("Invalid input, Please Try Again...");
								currentEvent = null;
							}
						} else {
							resultLabel.setText("Invalid input, Please Try Again...");
						}
					} else {
						resultLabel.setText("Please fill in all criteria");
					}
				}
			}
		});
	}

	/**
	 * This method initialises an event menu panel that allows a user to choose
	 * the meal items they wish to be associated with the given event.
	 */
	public void addMenuPanelInit() {
		addMenuPanel = new JPanel();
		// generate panels formmated compononents
		JLabel menuInstructionLabel = generator.createLabel(addMenuPanel, "Please Choose an Item from each Menu Below", "");
		menuInstructionLabel.setFont(new Font("Dialog", 1, 20));
		
		JLabel mainMenuAppChoiceLabel = generator.createLabel(addMenuPanel, "Main Menu Appetizer Choice", "");
		//table for main menu appetizers 
		final JTable allMainApp = new JTable(allMainAppModel.getSpecificMealTable(false, "appetizer"));
		allMainApp.removeColumn(allMainApp.getColumn("ID Number"));
		allMainApp.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMainApp.setFillsViewportHeight(true);
		allMainApp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollMainApp = new JScrollPane(allMainApp);
		scrollMainApp.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollMainApp);
		
		JLabel mainMenuMainChoiceLabel = generator.createLabel(addMenuPanel, "Main Menu Main Choice", "");
		// table for main menu main meals
		final JTable allMainMain = new JTable(allMainMainModel.getSpecificMealTable(false, "main"));
		allMainMain.removeColumn(allMainMain.getColumn("ID Number"));
		allMainMain.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMainMain.setFillsViewportHeight(true);
		allMainMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollMainMain = new JScrollPane(allMainMain);
		scrollMainMain.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollMainMain);
		
		JLabel mainMenuDessChoiceLabel = generator.createLabel(addMenuPanel, "Main Menu Dessert Choice", "");
		//table for main menu desserts
		final JTable allMainDess = new JTable(allMainDessModel.getSpecificMealTable(false, "dessert"));
		allMainDess.removeColumn(allMainDess.getColumn("ID Number"));
		allMainDess.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMainDess.setFillsViewportHeight(true);
		allMainDess.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollMainDess = new JScrollPane(allMainDess);
		scrollMainDess.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollMainDess);
		
		JLabel vegMenuAppChoiceLabel = generator.createLabel(addMenuPanel, "Vegetarian Menu Appetizer Choice", "");
		// table for vegetarian appetizers
		final JTable allVegApp = new JTable(allVegAppModel.getSpecificMealTable(true, "appetizer"));
		allVegApp.removeColumn(allVegApp.getColumn("ID Number"));
		allVegApp.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allVegApp.setFillsViewportHeight(true);
		allVegApp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollVegApp = new JScrollPane(allVegApp);
		scrollVegApp.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollVegApp);
		
		JLabel vegMenuMainChoiceLabel = generator.createLabel(addMenuPanel, "Vegetarian Menu Main Choice", "");
		// table for vegetarian main meals
		final JTable allVegMain = new JTable(allVegMainModel.getSpecificMealTable(true, "main"));
		allVegMain.removeColumn(allVegMain.getColumn("ID Number"));
		allVegMain.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allVegMain.setFillsViewportHeight(true);
		allVegMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollVegMain = new JScrollPane(allVegMain);
		scrollVegMain.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollVegMain);
		
		JLabel vegMenuDessChoiceLabel = generator.createLabel(addMenuPanel, "Vegetarian Menu Dessert Choice", "");
		// table for vegetarian desserts
		final JTable allVegDess = new JTable(allVegDessModel.getSpecificMealTable(true, "dessert"));
		allVegDess.removeColumn(allVegDess.getColumn("ID Number"));
		allVegDess.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allVegDess.setFillsViewportHeight(true);
		allVegDess.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollVegDess = new JScrollPane(allVegDess);
		scrollVegDess.setMaximumSize(new Dimension(200, 300));
		addMenuPanel.add(scrollVegDess);
		
		final JLabel resultLabel = generator.createLabel(addMenuPanel, " ", "");
		//creates buttons for panel
		final JButton continueButton = generator.createButton(addMenuPanel, "Continue", 10);	
		JButton backButton = generator.createButton(addMenuPanel, "Back", 5);
		
		addMenuPanel.setLayout(new BoxLayout(addMenuPanel, BoxLayout.Y_AXIS));
		//sets menu row selections 
		addMenuPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				if (eventCreationComplete == true){
					continueButton.setText("Update");
					//sets the tables selected rows based off currentevents menus
					if(vegMenuPanelMenuId != 99999 && mainMenuPanelMenuId != 99999){
						MenuClass vegMenu = MenuControllerClass.getInstance().getMenu(vegMenuPanelMenuId);
						MenuClass mainMenu = MenuControllerClass.getInstance().getMenu(mainMenuPanelMenuId);
						int mainApp = allMainAppModel.getRowFromID(mainMenu.getAppetizerMealId());
						int mainMain = allMainMainModel.getRowFromID(mainMenu.getMainMealId());
						int mainDess = allMainDessModel.getRowFromID(mainMenu.getDessertMealId());
						int vegApp = allVegAppModel.getRowFromID(vegMenu.getAppetizerMealId());
						int vegMain = allVegMainModel.getRowFromID(vegMenu.getMainMealId());
						int vegDess = allVegDessModel.getRowFromID(vegMenu.getDessertMealId());
						allMainApp.setRowSelectionInterval(mainApp, mainApp);
						allMainMain.setRowSelectionInterval(mainMain, mainMain);
						allMainDess.setRowSelectionInterval(mainDess, mainDess);
						allVegApp.setRowSelectionInterval(vegApp, vegApp);
						allVegMain.setRowSelectionInterval(vegMain, vegMain);
						allVegDess.setRowSelectionInterval(vegDess, vegDess);
					}else{
						allMainApp.setRowSelectionInterval(0, 0);
						allMainMain.setRowSelectionInterval(0, 0);
						allMainDess.setRowSelectionInterval(0, 0);
						allVegApp.setRowSelectionInterval(0, 0);
						allVegMain.setRowSelectionInterval(0, 0);
						allVegDess.setRowSelectionInterval(0, 0);
					}
				}
			}
		});
		//backbutton that changes panels based on eventcreation
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				if (eventCreationComplete == false) {	//returns to event add menu
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, addViewName);
				} else {			//returns to event modify menu
					continueButton.setText("Continue");
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, modifyViewName);	
				}				
			}
		});
		// when continue button is pressed, will generate new menus and add to current event
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				boolean result = true;
				int menuIDs[] = new int[6];
				
				try {	//grabs all selected meal ids and stores them into array
					menuIDs[0] = allMainAppModel.getIDFromRow(allMainApp.getSelectedRow());
					menuIDs[1] = allMainMainModel.getIDFromRow(allMainMain.getSelectedRow());
					menuIDs[2] = allMainDessModel.getIDFromRow(allMainDess.getSelectedRow());
					menuIDs[3] = allVegAppModel.getIDFromRow(allVegApp.getSelectedRow());
					menuIDs[4] = allVegMainModel.getIDFromRow(allVegMain.getSelectedRow());
					menuIDs[5] = allVegDessModel.getIDFromRow(allVegDess.getSelectedRow());
				} catch (ArrayIndexOutOfBoundsException e1){
					resultLabel.setText("Please choose an option from each menu listed");
					result = false;
				}
				
				if (result == true){
					//creates a new mainmenu
					MenuClass newMainMenu = new MenuClass(menuIDs[0], menuIDs[1], menuIDs[2]);
					//adds menu to database
					MenuControllerClass.getInstance().addMenu(newMainMenu, UIControllerClass.currentUser);
					//creates new vegetarian menu
					MenuClass newVegMenu = new MenuClass(menuIDs[3], menuIDs[4], menuIDs[5]);
					//adds to databse
					MenuControllerClass.getInstance().addMenu(newVegMenu, UIControllerClass.currentUser);
					//adds new menus to event
					currentEvent.setMainMenu(newMainMenu);
					currentEvent.setVegMenu(newVegMenu);
					currentEvent.setMainMenuId(newMainMenu.getMenuId());
					currentEvent.setVegMenuId(newVegMenu.getMenuId());
					
					if (eventCreationComplete == false) {	//if still in event creation process, go to confirming menu
						CardLayout cLayout = (CardLayout) cardHolder.getLayout();
						cLayout.show(cardHolder, addConfirmViewName);
					} else { // else go back to the update event menu
						result = EventControllerClass.getInstance().modifyEvent(currentEvent);
						if (result == true){
							eventMenuChanged = true;	//sets that the events menus were changed
						}
						continueButton.setText("Continue");	//rests continue button
						CardLayout cLayout = (CardLayout) cardHolder.getLayout();	
						cLayout.show(cardHolder, modifyViewName);
					}
				}
			}
		});		
	}

	/**
	 * 	This method initialises an event confirming panel that allows a user
	 * 	to view all options they have selected for the event and can then confirm
	 *  the event to add to the database.
	 */
	private void addConfirmPanelInit() {
		addConfirmPanel = new JPanel();
		// generates the panels formatted components
		generator.createRigidSpace(addConfirmPanel, 140);
		//displays events information
		final JLabel clientLabel = generator.createLabel(addConfirmPanel, " ", "");	
		final JLabel nameLabel = generator.createLabel(addConfirmPanel, " ", "");	
		final JLabel dateOfEventLabel = generator.createLabel(addConfirmPanel, " ", "");
		final JLabel locationLabel = generator.createLabel(addConfirmPanel, " ", "");
		final JLabel numGuestsLabel = generator.createLabel(addConfirmPanel, " ", "");
		final JLabel lengthOfEventLabel = generator.createLabel(addConfirmPanel, " ", "");
		//displays events main menu
		generator.createRigidSpace(addConfirmPanel, 20);
		JLabel mainMenuLabel = generator.createLabel(addConfirmPanel, "Main Menu:", "");	
		final JLabel mainMenuAppLabel = generator.createLabel(addConfirmPanel, " ", "");	
		final JLabel mainMenuMainLabel = generator.createLabel(addConfirmPanel, " ", "");	
		final JLabel mainMenuDessLabel = generator.createLabel(addConfirmPanel, " ", "");	
		//displays events vegetarian menu
		generator.createRigidSpace(addConfirmPanel, 10);
		JLabel vegMenuLabel = generator.createLabel(addConfirmPanel, "Vegetarian Menu:", "");
		final JLabel vegMenuAppLabel = generator.createLabel(addConfirmPanel, " ", "");
		final JLabel vegMenuMainLabel = generator.createLabel(addConfirmPanel, " ", "");
		final JLabel vegMenuDessLabel = generator.createLabel(addConfirmPanel, " ", "");
		
		final JLabel resultLabel = generator.createLabel(addConfirmPanel, " ", "");

		final JButton continueButton = generator.createButton(addConfirmPanel, "Add Event", 20);	
		JButton backButton = generator.createButton(addConfirmPanel, "Back", 5);	
		
		addConfirmPanel.setLayout(new BoxLayout(addConfirmPanel, BoxLayout.Y_AXIS));
		//when backbutton is pressed, will go to main event window if creation is complete 
		// or back to adding menu panel
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				if (eventCreationComplete == true){	//goes to event main menu
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, mainViewName);
				} else {	//goes to adding menu  panel
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, addMenuViewName);			
				}
			}
		});	
		//when the panel is shown will populate the labels with the events details
		addConfirmPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				resultLabel.setText(" ");
				continueButton.setVisible(true);
				try {	//tries to grab all event data
					clientLabel.setText("Client: " + currentEvent.getClient().getFirstName() + " " + currentEvent.getClient().getLastName());
					nameLabel.setText("Name: " + currentEvent.getName());
					//sets all basic event data
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String eventDate = df.format(currentEvent.getDateOfEvent());
					dateOfEventLabel.setText("Date of Event: " + eventDate);
					locationLabel.setText("Location: " + currentEvent.getLocation());
					numGuestsLabel.setText("Number of Guests: " + String.valueOf(currentEvent.getNumGuests()));
					lengthOfEventLabel.setText("Duration of Event (hours): " + String.valueOf(currentEvent.getTotalLengthOfEvent()));
					//sets all menu details
					mainMenuAppLabel.setText("1. " + currentEvent.getMainMenu().getAppetizer().getName());
					mainMenuMainLabel.setText("2. " + currentEvent.getMainMenu().getMainMeal().getName());
					mainMenuDessLabel.setText("3. " + currentEvent.getMainMenu().getDessert().getName());
					vegMenuAppLabel.setText("1. " + currentEvent.getVegMenu().getAppetizer().getName());
					vegMenuMainLabel.setText("2. " + currentEvent.getVegMenu().getMainMeal().getName());
					vegMenuDessLabel.setText("3. " + currentEvent.getVegMenu().getDessert().getName());
					
				} catch (NullPointerException e1){
					resultLabel.setText("Error Getting Event Data");	//error when grabbing data
				}	
			}
		});
		//when continue button is pressed will attempt to add to the database and display a message of result
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				boolean result = true;
				//attempts to add to database
				result = EventControllerClass.getInstance().addEvent(currentEvent, UIControllerClass.currentUser);

				if (result == true){	// if success, display result and builds data to add to table
					resultLabel.setText("Successfully Added Event");
					//grabs client name to add to table data
					ClientClass client = ClientControllerClass.getInstance().getClient(currentEvent.getClientId());
					String clientName = client.getFirstName() + " " + client.getLastName();
					String status = "";
					if(currentEvent.getStatus().matches("pending")){	//checks status
						status = " (PENDING)";
					}	//creats new table data
					Object[] newRow = {currentEvent.getEventId(), clientName, currentEvent.getName() + status, currentEvent.getDateOfEvent().toString(),
										currentEvent.getLocation(), currentEvent.getNumGuests(), currentEvent.getTotalLengthOfEvent()};
					allEventsModel.addRow(newRow);	//add data to table
				} else {
					resultLabel.setText("Error Adding Event To System, Please try again...");
				}
				eventCreationComplete = true;	//sets that creation is complete
				continueButton.setVisible(false);	//hides continue button
			}
		});	
	}
	
	/**
	 * Initialise the panel displaying add equipment.
	 */
	private void addEquipmentPanelInit(){
		addEquipmentPanel = new JPanel();
		
		generator.createRigidSpace(addEquipmentPanel, 10);
		
		//Add a table of all events to the Panel
		final JTable allEventEquipment = new JTable(allEventsModel.getTableModel(3));
		allEventEquipment.removeColumn(allEventEquipment.getColumn("ID Number"));
		allEventEquipment.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEventEquipment.setFillsViewportHeight(true);
		allEventEquipment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll2 = new JScrollPane(allEventEquipment);
		scroll2.setMaximumSize(new Dimension(TableWidth, 250));
		addEquipmentPanel.add(scroll2);
		
		//Formatting
		allEventEquipment.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEventEquipment.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEventEquipment.getColumnModel().getColumn(5).setPreferredWidth(10);
		generator.createRigidSpace(addEquipmentPanel, 10);
		
		final int equipID = -1;
		
		//Add a table of all equipment to the panel
		final JTable allEquipment = new JTable(allEquipmentModel.getTableModel(2));
		allEquipment.removeColumn(allEquipment.getColumn("ID Number"));
		allEquipment.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEquipment.setFillsViewportHeight(true);
		allEquipment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEquipment);
		scroll.setMaximumSize(new Dimension(400, 250));
		addEquipmentPanel.add(scroll);
		
		//Add text/text fields to the Panel
		final JLabel menuLabel = generator.createLabel(addEquipmentPanel, "Please select an event and an item to add to the event", "");
		final JLabel quantityLabel = generator.createLabel(addEquipmentPanel, "Please input the required quantity", "");
		final JTextField quantityTextField = generator.createTextField(addEquipmentPanel, "");
		final JLabel resultLabel = generator.createLabel(addEquipmentPanel, " ", "");
		
		final JButton addEquipmentButton = generator.createButton(addEquipmentPanel, "Add Equipment", 10);
		JButton backButton = generator.createButton(addEquipmentPanel, "Back", mainViewName, 5);
		
		addEquipmentPanel.setLayout(new BoxLayout(addEquipmentPanel, BoxLayout.Y_AXIS));
		
		//If you pressed add Equipment
		addEquipmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				//Quick Input Checks
				if(allEventEquipment.getSelectedRow() == -1 || allEquipment.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
					return;
				}
				//Add the new equipment booking to the DB
				EquipmentControllerClass.getInstance().addEquipmentBooking(allEventsModel.getIDFromRow(allEventEquipment.getSelectedRow()), 
																			allEquipmentModel.getIDFromRow(allEquipment.getSelectedRow()), 
																			Integer.parseInt(quantityTextField.getText()), UIControllerClass.currentUser);
			}
		});
	}

	/**
	 * Initialise the panel displaying add employee booking. 
	 */
	private void addEmployeeBookingPanelInit() {
		addEmployeeBookingPanel = new JPanel();
				
		generator.createRigidSpace(addEmployeeBookingPanel, 25);
		
		//Add a table of all events to the panel
		final JTable allEvents = new JTable(allEventsModel.getTableModel(3));
		allEvents.removeColumn(allEvents.getColumn("ID Number"));
		allEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEvents.setFillsViewportHeight(true);
		allEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll2 = new JScrollPane(allEvents);
		scroll2.setMaximumSize(new Dimension(TableWidth, 250));
		addEmployeeBookingPanel.add(scroll2);
		
		allEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
		
		generator.createRigidSpace(addEmployeeBookingPanel, 10);

		final JScrollPane scroll = new JScrollPane();
		scroll.setMaximumSize(new Dimension(300, 250));
		addEmployeeBookingPanel.add(scroll);
		
		final JLabel menuLabel = generator.createLabel(addEmployeeBookingPanel, "Please select an event then an employee to add to the event", "");	
		final JLabel resultLabel = generator.createLabel(addEmployeeBookingPanel, " ", "");
		final JButton addEmployeeButton = generator.createButton(addEmployeeBookingPanel, "Book Staff Member", 10);
		JButton backButton = generator.createButton(addEmployeeBookingPanel, "Back", 5);
		
		addEmployeeBookingPanel.setLayout(new BoxLayout(addEmployeeBookingPanel, BoxLayout.Y_AXIS));
		
		//If you pressed the back button
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				currentEvent = null;
				CardLayout cLayout = (CardLayout) cardHolder.getLayout();
				cLayout.show(cardHolder, mainViewName);
			}
		});
		
		//If you pressed the Book staff member button
		addEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				//Quick Input Checks
				if(allEvents.getSelectedRow() == -1 || availEmployees.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
					return;
				}
				
				try {
					//Add the booking to the DB
					EmployeeControllerClass.getInstance().addBookedDate(new EmployeeBookedDate(availEmployeesModel.getIDFromRow(availEmployees.getSelectedRow()), 
												allEventsModel.getIDFromRow(allEvents.getSelectedRow())), UIControllerClass.currentUser);	
				
					availEmployeesModel.removeRow(availEmployees.getSelectedRow());
				} catch (NullPointerException e1){
					resultLabel.setText("Please choose an event from the list");
				}
			}
		});
		
		//Set the text fields to the table selection
		ListSelectionModel rowSM = allEvents.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
		    	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				currentEvent = EventControllerClass.getInstance().getEvent(allEventsModel.getIDFromRow(allEvents.getSelectedRow()));
				date = currentEvent.getDateOfEvent();
				availEmployeesModel.resetModelData();
				availEmployees = new JTable(availEmployeesModel.getAvailTable(date));
				availEmployees.removeColumn(availEmployees.getColumn("ID Number"));
				availEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
				availEmployees.setFillsViewportHeight(true);
				availEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scroll.setViewportView(availEmployees);
				scroll.setMaximumSize(new Dimension(300, 250));	
			}
		});
	}

	/**
	 * Initialise the panel displaying modify event.
	 */
	private void modifyPanelInit() {
		
		modifyPanel = new JPanel();
		generator.createRigidSpace(modifyPanel, 10);
		
		//Add a table of all the events to the panel
		final JTable allEvents = new JTable(allEventsModel.getTableModel(3));
		allEvents.removeColumn(allEvents.getColumn("ID Number"));
		allEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEvents.setFillsViewportHeight(true);
		allEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEvents);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		modifyPanel.add(scroll);
		
		allEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
		
		//Add all the text/text fields to the panel
		JLabel nameLabel = generator.createLabel(modifyPanel, "Name");
		final JTextField nameInput = generator.createTextField(modifyPanel, " ");
		
		JLabel dateOfEventLabel = generator.createLabel(modifyPanel, "Date of Event (yyyy-mm-dd)");
		final JTextField dateOfEventInput = generator.createTextField(modifyPanel, " ");
		
		JLabel locationLabel = generator.createLabel(modifyPanel, "Location");
		final JTextField locationInput = generator.createTextField(modifyPanel, " ");
		
		JLabel numGuestsLabel = generator.createLabel(modifyPanel, "Number of Guests");
		final JTextField numGuestsInput = generator.createTextField(modifyPanel, " ");

		JLabel lengthOfEventLabel = generator.createLabel(modifyPanel, "Length Of Event (hours)");
		final JTextField lengthOfEventInput = generator.createTextField(modifyPanel, " ");
		
		final JLabel resultLabel = generator.createLabel(modifyPanel, " ", "");
		
		//Add buttons to the panel
		JButton updateMealsButton = generator.createButton(modifyPanel, "Update Event Menu's", 5);	
		JButton updateButton = generator.createButton(modifyPanel, "Update Event", 5);
		JButton backButton = generator.createButton(modifyPanel, "Back", 5);
		
		modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
		
		//If the menu got set in another panel
		modifyPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				if (eventMenuChanged == true){
					resultLabel.setText("Events Menu's successfully updated!");
				}
			}
		});
		
		//If you want to quit
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentEvent = null;
				eventMenuChanged = false;
				resultLabel.setText(" ");
				CardLayout cLayout = (CardLayout) cardHolder.getLayout();
				cLayout.show(cardHolder, mainViewName);	
			}
		});
		
		//If you want to modify a meal
		updateMealsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result;
				resultLabel.setText(" ");
				
				try {
					currentEvent = EventControllerClass.getInstance().getEvent(allEventsModel.getIDFromRow(allEvents.getSelectedRow()));
					result = true;
				} catch (ArrayIndexOutOfBoundsException e1){
					resultLabel.setText("Please choose an event from the list");
					result = false;
				}
				if (result == true){
					//Data needed to modify a menu
					vegMenuPanelMenuId = currentEvent.getVegMenuId();
					mainMenuPanelMenuId = currentEvent.getMainMenuId();
					
					//Goto the add menu panel
					CardLayout cLayout = (CardLayout) cardHolder.getLayout();
					cLayout.show(cardHolder, addMenuViewName);	
				}
			}
		});
		
		//If you pressed the update event button
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean result;
				Date myDate = null;
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				resultLabel.setText("");

				
				try {
					currentEvent = EventControllerClass.getInstance().getEvent(allEventsModel.getIDFromRow(allEvents.getSelectedRow()));
					result = true;		
				} catch (ArrayIndexOutOfBoundsException e1){
					resultLabel.setText("Please choose an event from the list");
					result = false;
				}
				
				if (result){
					
					//Input checks
					if (!nameInput.getText().matches("") && !numGuestsInput.getText().matches("") && 
						!dateOfEventInput.getText().matches("") && !locationInput.getText().matches("") 
						&& !lengthOfEventInput.getText().matches("")){

						if (nameInput.getText().matches(".+") && numGuestsInput.getText().matches("\\d+") && 
								dateOfEventInput.getText().matches("\\d{4}-\\d{2}-\\d{2}") && locationInput.getText().matches(".+") 
								&& lengthOfEventInput.getText().matches("\\d*\\.?\\d*")){	

							try {
								myDate = new Date(dateFormat.parse(dateOfEventInput.getText()).getTime());
							} catch (ParseException e1) {
								resultLabel.setText("Date format incorrect, Please Try Again...");
								result = false;
							}
											
							if (result){
								
								//Set the changed fields in the eventclass
								currentEvent.setName(nameInput.getText());
								currentEvent.setDateOfEvent(myDate);
								currentEvent.setNumGuests(Integer.parseInt(numGuestsInput.getText()));
								currentEvent.setLocation(locationInput.getText());
								currentEvent.setTotalLengthOfEvent(Double.parseDouble(lengthOfEventInput.getText()));				
								result = currentEvent.verifyInput();
								if(!result){
									resultLabel.setText("Invalid Input, Please Try Again...");
								} else {
									//Modify the event in the DB
									result = EventControllerClass.getInstance().modifyEvent(currentEvent);
									eventMenuChanged = false;
									if(!result){
										resultLabel.setText("Error Updating Event!");
									}else{
										resultLabel.setText("Successfully Updated Event!");
										String status = "";
										if(currentEvent.getStatus().matches("pending")){
											status = " (PENDING)";
										}
										//Modify the event in the model
										Object[] modifiedRow = {currentEvent.getEventId(), currentEvent.getClient().getFirstName() + " "+ 
																currentEvent.getClient().getLastName(),  currentEvent.getName() + status, 
																currentEvent.getDateOfEvent().toString(), currentEvent.getLocation(), currentEvent.getNumGuests(), 
																currentEvent.getTotalLengthOfEvent()};
										allEventsModel.modifyData(modifiedRow, allEvents.getSelectedRow());
									}
								}

							} else {
								resultLabel.setText("Invalid input, Please Try Again...");
								currentEvent = null;
							}
						} else {
							resultLabel.setText("Invalid input, Please Try Again...");
						}
					} else {
						resultLabel.setText("Please fill in all criteria");
					}
				}
			}
		});
		
		//Change the text fields based on table selection
		ListSelectionModel rowSM = allEvents.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] data = allEventsModel.getData(allEvents.getSelectedRow());
				if (String.valueOf(data[2]).matches(".* \\(PENDING\\)")){
					nameInput.setText(String.valueOf(data[2]).substring(0, String.valueOf(data[2]).length()-10));
		    	} else {
		    		nameInput.setText((String)data[2]);
		    	}
				dateOfEventInput.setText((String)data[3]);
				locationInput.setText((String)data[4]);
				numGuestsInput.setText(String.valueOf((int)data[5]));
				lengthOfEventInput.setText(String.valueOf(data[6]));
			}
		});
	}

	/**
	 * Initialise the panel displaying delete event.
	 */
	private void deletePanelInit(){	    
		deletePanel = new JPanel();
		
		generator.createRigidSpace(deletePanel, 90);
		
		//Add a table of all events to the panel
		final JTable allEvents = new JTable(allEventsModel.getTableModel(3));
		allEvents.removeColumn(allEvents.getColumn("ID Number"));
		allEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEvents.setFillsViewportHeight(true);
		allEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEvents);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		deletePanel.add(scroll);
		
		allEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
		
		//Add text/textfields to the panel
		JLabel idLabel = generator.createLabel(deletePanel, "Please select an Event from the table to remove", "");
		generator.createRigidSpace(deletePanel, 10);
		final JLabel resultLabel = generator.createLabel(deletePanel, " ");
		
		//Add remove and back buttons to the panel
		JButton removeButton = generator.createButton(deletePanel, "Remove", 10);
		JButton backButton = generator.createButton(deletePanel, "Back", mainViewName, 5);

		generator.createRigidSpace(deletePanel, 5);

	    deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));	
			
	    //If you pressed the remove button
		removeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
		    	boolean result = false;
		    	//Quick input checks
				if(allEvents.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }
				
				//Remove the event from the DB
				result = EventControllerClass.getInstance().deleteEvent(allEventsModel.getIDFromRow(allEvents.getSelectedRow()));
				if(!result){
					resultLabel.setText("Event was not removed correctly, please try again");
				} else{
					//Remove the event from the model
					resultLabel.setText("Event successfully removed!");
					allEventsModel.removeRow(allEvents.getSelectedRow());
				}
	    	}		
		});
	}

	/**
	 * Initialise the panel displaying confirm pending.
	 */
	private void confirmPanelInit(final TableModel allPendingEventsModel){
		confirmPanel = new JPanel();
		
		//Add a blank table to the panel
		generator.createRigidSpace(confirmPanel, 90);
		final JScrollPane scroll = new JScrollPane();
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		confirmPanel.add(scroll);
		
		//Add text/text fields to the panel
		JLabel idLabel = generator.createLabel(confirmPanel, "Please pick a pending event to confirm from table", "");
		generator.createRigidSpace(confirmPanel, 30);
		final JLabel resultLabel = generator.createLabel(confirmPanel, " ", "");
		
		//Add a confirm and back button to the panel
		JButton confirmButton = generator.createButton(confirmPanel, "Confirm", 10);
		JButton backButton = generator.createButton(confirmPanel, "Back", mainViewName, 5);

		confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.Y_AXIS));
		
		//When the panel is opened
		confirmPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				//Show the pending events for the user
				allPendingEventsModel.resetModelData();
				allPendingEvents = new JTable(allPendingEventsModel.getPendingEventTable());
				allPendingEvents.removeColumn(allPendingEvents.getColumn("ID Number"));
				allPendingEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
				allPendingEvents.setFillsViewportHeight(true);
				allPendingEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scroll.setViewportView(allPendingEvents);
				
				allPendingEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
				allPendingEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
				allPendingEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
			}
		});
		
		
		confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Quick input check
				if(allPendingEvents.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
					return;
				}
				
				//Change event status of the selected row
				EventControllerClass.getInstance().confirmEvent(allPendingEventsModel.getIDFromRow(allPendingEvents.getSelectedRow()));
				allPendingEventsModel.removeRow(allPendingEvents.getSelectedRow());
			}				
		});
	}
	
	/**
	 * Initialise the panel displaying invoice.
	 */	
	private void invoicePanelInit(){
		invoicePanel = new JPanel();
		generator.createRigidSpace(invoicePanel, 30);
		
		//Add a table of all event to the panel
		final JTable allEvents = new JTable(allEventsModel.getTableModel(3));
		allEvents.removeColumn(allEvents.getColumn("ID Number"));
		allEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEvents.setFillsViewportHeight(true);
		allEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEvents);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		invoicePanel.add(scroll);
		
		allEvents.getColumnModel().getColumn(2).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEvents.getColumnModel().getColumn(5).setPreferredWidth(10);
		
		
		//Add text/text fields to the panel
		final JLabel instructionLabel = generator.createLabel(invoicePanel, "Please choose an event from above to generate an invoice", "");
		generator.createRigidSpace(invoicePanel, 20);
		final JLabel nameLabel = generator.createLabel(invoicePanel, " ", "");
		final JLabel bookingFeeLabel = generator.createLabel(invoicePanel, " ");
		final JLabel sizeLabel = generator.createLabel(invoicePanel, " ");
		final JLabel labourLabel = generator.createLabel(invoicePanel, " ");
		final JLabel equipmentLabel = generator.createLabel(invoicePanel, " ");
		final JLabel subTotalLabel = generator.createLabel(invoicePanel, " ");
		final JLabel taxLabel = generator.createLabel(invoicePanel, " ");
		final JLabel finalTotalLabel = generator.createLabel(invoicePanel, " ");
		
		
		//Add a back button
		JButton backButton = generator.createButton(invoicePanel, "Back", mainViewName, 10);

		invoicePanel.setLayout(new BoxLayout(invoicePanel, BoxLayout.Y_AXIS));
		
		ListSelectionModel rowSM = allEvents.getSelectionModel();
		//If the selection changed
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
		    	//Get the selected event
				currentEvent = EventControllerClass.getInstance().getEvent(allEventsModel.getIDFromRow(allEvents.getSelectedRow()));
				nameLabel.setText("Invoice for " + currentEvent.getName() + " on " + currentEvent.getDateOfEvent().toString());
				bookingFeeLabel.setText(String.format("%-25s %37s\n", "Booking Fee", "100"));
				
				//Charge a base price based on guest count
				int numGuests = currentEvent.getNumGuests();
				String eventSize;
				int sizeCost;
				if(numGuests < 100){
					eventSize = "Sml Event Cost";
					sizeCost = 1000;
				}
				else if(numGuests < 250){
					eventSize = "Med Event Cost";
					sizeCost = 3000;
				}
				else{
					eventSize = "Lrg Event Cost";
					sizeCost = 10000;
				}
				
				sizeLabel.setText(String.format("%-25s %33d\n", eventSize, sizeCost));
				
				//Get the labour cost for the event
				float employeeCost = costOfEmployees(currentEvent);
				labourLabel.setText(String.format("%-25s %36.2f\n", "Labour Cost", employeeCost));
				
				//Get the cost of all the rented equipment
				float equipmentCost = costOfEquipment(currentEvent);
				equipmentLabel.setText(String.format("%-25s %28.2f\n", "Equipment Rental Fee", equipmentCost));
				
				float subTotal = 100 + sizeCost + employeeCost + equipmentCost;
				subTotalLabel.setText(String.format("%-25s %37.2f\n", "SubTotal", subTotal));
				
				//calculate the tax
				float tax = subTotal * 0.12f;
				taxLabel.setText(String.format("%-25s %43.2f\n", "Tax", tax));
				
				//Print the grand total
				float grandTotal = subTotal + tax;
				finalTotalLabel.setText(String.format("%-25s %35.2f\n", "GrandTotal", grandTotal));
				
			}
		});
	}	
	
	/**
	 * Calculates cost of employees.
	 * 
	 * @param	event	requested event
	 * @return	cost of employees
	 * @see		EventClass
	 * @see		EquipmentControllerClass
	 */
	private float costOfEmployees(EventClass event){
		float totalCost = 0;		
		
		List<Integer> allPayableEmployeesByID = event.getScheduledEmployeesId();
		
		Iterator<Integer> it = allPayableEmployeesByID.iterator();
		
		while(it.hasNext()){
			Integer element = it.next();
			
			EmployeeClass employee = EmployeeControllerClass.getInstance().getEmployee(element.intValue());
			
			totalCost += employee.getWage()*event.getTotalLengthOfEvent();
		}
		
		return totalCost;
	}
	
	/**
	 * Calculates cost of equipment.
	 * 
	 * @param	event	requested event
	 * @return	cost of equipment
	 * @see		EventClass
	 * @see		EquipmentControllerClass
	 */
	private float costOfEquipment(EventClass event){
		float totalCost = 0;		
		
		List<Integer> allRentedEquipmentByID = event.getRentedEquipmentId();
		
		Iterator<Integer> it = allRentedEquipmentByID.iterator();
		
		while(it.hasNext()){
			Integer element = it.next();
			
			EquipmentClass equip = EquipmentControllerClass.getInstance().getEquipment(element.intValue());
			
			totalCost += equip.getRentalCost()*event.getTotalLengthOfEvent();
		}
		
		return totalCost;
	}
}

