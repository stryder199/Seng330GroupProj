package Employee;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Login.UserClass;
import UserInterface.ComponentGeneratorClass;
import UserInterface.TableModel;
import UserInterface.UIControllerClass;

/**
 * This class is used for GUI display of employee view. 
 * 
 * @author	Mark Lessard 
 */
@SuppressWarnings("unused")
public class EmployeeUIViewClass {
	//cardholder string names
	String rootViewName = "Main Menu View";
	String mainViewName = "Employee Main View";
	String listViewName = "Employee List View";
	String addViewName = "Employee Add View";
	String modifyViewName = "Employee Modify View";
	String deleteViewName = "Employee Delete View";
	String availabilityViewName = "Employee Availability View";
	String unavailabilityViewName = "Employee Unavailability View";
				
	//all panels and table models for the client menus
	JPanel cardHolder, mainPanel, listPanel, addPanel, modifyPanel, deletePanel, availPanel, unavailPanel;
	TableModel allEmployeesModel, availEmployeesModel;
	int TableWidth = 800;

	ComponentGeneratorClass generator;		// component generator
	EmployeeClass currentEmployee;			// employee class currently being worked on
	
	/**
	 * Constructs an EmployeeUIViewClass object initialised to add the panels 
	 * to the cardholder, as well as initialise the component generator.
	 * 
	 * @param cardHolder
	 * @param availEmployees
	 */
	public EmployeeUIViewClass(JPanel cardHolder, TableModel availEmployees) {
		this.cardHolder = cardHolder;
		// creates new generator
		generator = new ComponentGeneratorClass(cardHolder);
		// creates table models
		allEmployeesModel = new TableModel();
		availEmployeesModel = availEmployees;
		// initialise all employee panels
		mainPanelInit();
		addPanelInit();
		deletePanelInit();
		modifyPanelInit();
		listPanelInit();
		availPanelInit();
		unavailPanelInit();
		
		// adds all panels to the cardholder
		cardHolder.add(mainPanel, mainViewName);
		cardHolder.add(listPanel, listViewName);
		cardHolder.add(addPanel, addViewName);
		cardHolder.add(modifyPanel, modifyViewName);
		cardHolder.add(deletePanel, deleteViewName);	
		cardHolder.add(availPanel, availabilityViewName);
		cardHolder.add(unavailPanel, unavailabilityViewName);
	}

	/**
	 * This method initialises a main menu panel to represent the employee Menu.
	 */
	private void mainPanelInit(){ 
		mainPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(mainPanel, 140);
		JLabel heading = generator.createLabel(mainPanel, "Employee Menu", "");
		heading.setFont(new Font("Dialog", 1, 20));
		JButton createButton = generator.createButton(mainPanel, "Add Employee", addViewName, 50);
		JButton modifyButton = generator.createButton(mainPanel, "Modify Employee", modifyViewName, 5);
		JButton removeButton = generator.createButton(mainPanel, "Remove Employee", deleteViewName, 5);
		JButton viewButton = generator.createButton(mainPanel, "View Employees", listViewName, 5);
		JButton viewAvailableEmployeeButton = generator.createButton(mainPanel, "View Availability", availabilityViewName, 5);
		JButton BookingButton = generator.createButton(mainPanel, "Set Unavailability", unavailabilityViewName, 5);
		JButton backButton = generator.createButton(mainPanel, "Back", rootViewName, 30);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * This method initialises an employee visiting panel accessible from the employee Menu.
	 */
	private void listPanelInit(){
		listPanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(listPanel, 70);
		JTable allEmployees = new JTable(allEmployeesModel.getTableModel(1));
		allEmployees.removeColumn(allEmployees.getColumn("ID Number"));
		allEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEmployees.setFillsViewportHeight(true);
		allEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEmployees);
		scroll.setMaximumSize(new Dimension(TableWidth, 500));
		listPanel.add(scroll);
		//sets table column sizes
		allEmployees.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEmployees.getColumnModel().getColumn(6).setPreferredWidth(10);
        
		JButton backButton = generator.createButton(listPanel, "Back", mainViewName, 50);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}
	
	/**
	 * This method initialises an employee add panel accessible from the employee Menu.
	 */
	private void addPanelInit () {
		addPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(addPanel, 40);
		JLabel fNameLabel = generator.createLabel(addPanel, "First Name");	
		final JTextField fNameInput = generator.createTextField(addPanel, "");
		JLabel lNameLabel = generator.createLabel(addPanel, "Last Name");	
		final JTextField lNameInput = generator.createTextField(addPanel, "");
		JLabel phoneLabel = generator.createLabel(addPanel, "Phone (xxx-xxx-xxxx)");
		final JTextField phoneInput = generator.createTextField(addPanel, "");
		JLabel addressLabel = generator.createLabel(addPanel, "Address");
		final JTextField addressInput = generator.createTextField(addPanel, "");
		JLabel birthDateLabel = generator.createLabel(addPanel, "Birthdate (yyyy-mm-dd)");
		final JTextField birthDateInput = generator.createTextField(addPanel, "");
		JLabel positionLabel = generator.createLabel(addPanel, "Position");
		final JTextField positionInput = generator.createTextField(addPanel, "");
		JLabel wageLabel = generator.createLabel(addPanel, "Wage");
		final JTextField wageInput = generator.createTextField(addPanel, "");
		
		generator.createRigidSpace(addPanel, 10);	
		final JLabel resultLabel = generator.createLabel(addPanel, " ", "");
		JButton doneButton = generator.createButton(addPanel, "Add Employee", 5);
		JButton backButton = generator.createButton(addPanel, "Back", mainViewName, 5);
		
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		// conditions for doneButton - attempts to add the employee to DB
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				EmployeeClass newEmployee;
				boolean result;
				double wage = 0;
				//Quick Input test
				if(fNameInput.getText().matches("") || lNameInput.getText().matches("") || phoneInput.getText().matches("")
						|| addressInput.getText().matches("") || birthDateInput.getText().matches("")
						|| positionInput.getText().matches("") || wageInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				
				try {
					wage = Double.parseDouble(wageInput.getText());
				} catch (NumberFormatException e1) {
					resultLabel.setText("Error wage MUST a number");
					return;
				}
				//End of Input tests
				
				newEmployee = new EmployeeClass(fNameInput.getText(), lNameInput.getText(), phoneInput.getText(), 
											addressInput.getText(), birthDateInput.getText(), positionInput.getText(), wage);
				result = newEmployee.verifyInput();
				if(!result){
					resultLabel.setText("Invalid Input!");
					return;
				}	// attempts adding employee to database
				result = EmployeeControllerClass.getInstance().addEmployee(newEmployee,UIControllerClass.currentUser);
				
				if(!result){
					resultLabel.setText("Error adding the Employee");
				}
				else{	// creates newrow data of employee to add to table
					Object[] newRow = {newEmployee.getEmployeeId(), newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getAddress(), newEmployee.getPhone(),
										newEmployee.getBirthDate(), newEmployee.getPosition(), newEmployee.getWage()};
					allEmployeesModel.addRow(newRow);
					resultLabel.setText("Successfully added the Employee!");
					fNameInput.setText("");
					lNameInput.setText(""); 
					phoneInput.setText("");
					addressInput.setText(""); 
					birthDateInput.setText(""); 
					positionInput.setText("");
					wageInput.setText("");
					}
			}			
		});
	}
	
	/**
	 * This method initialises an employee modify panel accessible from the employee Menu.
	 */
	private void modifyPanelInit() {
		modifyPanel = new JPanel();
		modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
		//generates panels formatted layout of components
		generator.createRigidSpace(modifyPanel, 5);
		final JTable allEmployees = new JTable(allEmployeesModel.getTableModel(1));
		allEmployees.removeColumn(allEmployees.getColumn("ID Number"));
		allEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEmployees.setFillsViewportHeight(true);
		allEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEmployees);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		modifyPanel.add(scroll); 
		//resizes tables columns
		allEmployees.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEmployees.getColumnModel().getColumn(6).setPreferredWidth(10);
		//generates more of panels formatted layout of components	
		JLabel idLabel = generator.createLabel(modifyPanel, "Please choose a row", "");
		JLabel fNameLabel = generator.createLabel(modifyPanel, "First Name");
		final JTextField fNameInput = generator.createTextField(modifyPanel, "");
		JLabel lNameLabel = generator.createLabel(modifyPanel, "Last Name");	
		final JTextField lNameInput = generator.createTextField(modifyPanel, "");
		JLabel phoneLabel = generator.createLabel(modifyPanel, "Phone Number (xxx-xxx-xxxx)");	
		final JTextField phoneInput = generator.createTextField(modifyPanel, "");
		JLabel wageLabel = generator.createLabel(modifyPanel, "Wage");
		final JTextField wageInput = generator.createTextField(modifyPanel, "");
		JLabel addressLabel = generator.createLabel(modifyPanel, "Address");
		final JTextField addressInput = generator.createTextField(modifyPanel, "");
		JLabel positionLabel = generator.createLabel(modifyPanel, "Position");
		final JTextField positionInput = generator.createTextField(modifyPanel, "");
		JLabel dateOfBirthLabel = generator.createLabel(modifyPanel, "Date of Birth (yyyy-mm-dd)");	
		final JTextField dateofBirthInput = generator.createTextField(modifyPanel, "");
		final JLabel resultLabel = generator.createLabel(modifyPanel, "");	
		
		JButton updateButton = generator.createButton(modifyPanel, "Update", 10);
		JButton backButton = generator.createButton(modifyPanel, "Back", mainViewName, 5);
		// conditions for when updateButton is pressed - updates employees info to DB
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){				
				boolean result = false;
				EmployeeClass modifiedEmployee;
				double wage = 0;
				//Quick Input test
				if(fNameInput.getText().matches("") || lNameInput.getText().matches("") || phoneInput.getText().matches("")
						|| addressInput.getText().matches("") || dateofBirthInput.getText().matches("")
						|| positionInput.getText().matches("") || wageInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				if(allEmployees.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }
				try {
					wage = Double.parseDouble(wageInput.getText());
				} catch (NumberFormatException e1) {
					resultLabel.setText("Error wage MUST a number");
					return;
				}
				//End of Input tests
				// creates new employee class with given data
				modifiedEmployee = new EmployeeClass(fNameInput.getText(), lNameInput.getText(), 
											phoneInput.getText(), addressInput.getText(), dateofBirthInput.getText(), 
											positionInput.getText(), wage);
				result = modifiedEmployee.verifyInput();	//verifies new employee object data
				if(!result){
					resultLabel.setText("Employee didn't update correctly, please try again");
					return;
				}
				//attempts to update the employees data in database
	            result = EmployeeControllerClass.getInstance().modifyEmployee(modifiedEmployee);
	            if(!result){
	            	resultLabel.setText("Employee didn't update correctly, please try again");
	            } else{
	            	resultLabel.setText("Employee successfully updated!");
	            	// sets data for employee to be added to the table
	            	Object[] modifiedRow = {modifiedEmployee.getEmployeeId(), modifiedEmployee.getFirstName(), modifiedEmployee.getLastName(), modifiedEmployee.getAddress(), modifiedEmployee.getPhone(),
										modifiedEmployee.getBirthDate(), modifiedEmployee.getPosition(), modifiedEmployee.getWage()};
	            	allEmployeesModel.modifyData(modifiedRow, allEmployees.getSelectedRow());
	            	fNameInput.setText("");
					lNameInput.setText("");
					phoneInput.setText("");
					wageInput.setText("");
					addressInput.setText("");
					positionInput.setText("");
					dateofBirthInput.setText("");
	            }
			}
		});
		//sets model for table to only allow row selection
		ListSelectionModel rowSM = allEmployees.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] data = allEmployeesModel.getData(allEmployees.getSelectedRow());
				//sets the input fields data to that of the given selected row
				fNameInput.setText((String)data[1]);
				lNameInput.setText((String)data[2]);
				addressInput.setText((String)data[3]);
				phoneInput.setText((String)data[4]);
				dateofBirthInput.setText((String)data[5]);
				positionInput.setText((String)data[6]);
				wageInput.setText(String.valueOf((double)data[7]));				
			}
		});
	}

	/**
	 * This method initialises an employee delete panel accessible from the employee Menu.
	 */
	private void deletePanelInit(){
		deletePanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(deletePanel, 90);
		final JTable allEmployees = new JTable(allEmployeesModel.getTableModel(1));
		allEmployees.removeColumn(allEmployees.getColumn("ID Number"));
		allEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEmployees.setFillsViewportHeight(true);
		allEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEmployees);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		deletePanel.add(scroll); 
		// resizes column's of table
		allEmployees.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEmployees.getColumnModel().getColumn(6).setPreferredWidth(10);
	    
		JLabel idLabel = generator.createLabel(deletePanel, "Please select an Employee from the table to remove", "");
		generator.createRigidSpace(deletePanel, 10);
		final JLabel resultLabel = generator.createLabel(deletePanel, " ", "");
		JButton doneButton = generator.createButton(deletePanel, "Remove", 10);
		JButton backButton = generator.createButton(deletePanel, "Back", mainViewName, 5);
		
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));	
		// conditions for doneButton pressed - attempts to delete the employee
		doneButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
		    	boolean result = false;
		    	//Quick Input checks
				if(allEmployees.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
				}
				//End of Input Checks
				
				if(allEmployees.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }
				//attempts to delete the employee from the database
				result = EmployeeControllerClass.getInstance().deleteEmployee(allEmployeesModel.getIDFromRow(allEmployees.getSelectedRow()));
				if(!result){
					resultLabel.setText("Employee was not removed correctly, please try again");
				} else{
					resultLabel.setText("Employee successfully removed!");
					allEmployeesModel.removeRow(allEmployees.getSelectedRow());	//removes the employee from the table row
				}
	    	}			
		});
	}

	/**
	 * This method initialises an employee panel to show available employees accessible 
	 * from the employee Menu.
	 */
	private void availPanelInit (){
		availPanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(availPanel, 100);
		final JScrollPane scroll = new JScrollPane();
		scroll.setMaximumSize(new Dimension(300, 300));
		availPanel.add(scroll);
		
		generator.createRigidSpace(availPanel, 30);
		JLabel dateLabel = generator.createLabel(availPanel, "Enter the date yyyy-mm-dd you wish to inquire about", "");
		final JTextField dateInput = generator.createTextField(availPanel, "");
		generator.createRigidSpace(availPanel, 10);
		final JLabel resultLabel = generator.createLabel(availPanel, " ", "");			
		JButton doneButton = generator.createButton(availPanel, "Check Date", 10);
		JButton backButton = generator.createButton(availPanel, "Back", mainViewName, 5);	
		
		availPanel.setLayout(new BoxLayout(availPanel, BoxLayout.Y_AXIS));	
		// conditions for doneButton pressed = attempts to 
		//	populate table with data from date give
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = new java.sql.Date(dateFormat.parse(dateInput.getText()).getTime());
				} catch (ParseException e1) {
					resultLabel.setText("Error date must be of the format (yyyy-mm-dd)!");
					return;
				}
				// resets Model data and repopulates with data from date given
				availEmployeesModel.resetModelData();
				JTable availEmployees = new JTable(availEmployeesModel.getAvailTable(date));
				availEmployees.removeColumn(availEmployees.getColumn("ID Number"));
				availEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
				availEmployees.setFillsViewportHeight(true);
				availEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scroll.setViewportView(availEmployees);
				scroll.setMaximumSize(new Dimension(TableWidth, 300));	    		
	    	}			
		});		
	}

	/**
	 * This method initialises an employee panel to show unavailable employees
	 * accessible from the employee Menu.
	 */
	private void unavailPanelInit() {
		unavailPanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(unavailPanel, 100);
		final JTable allEmployees = new JTable(allEmployeesModel.getTableModel(1));
		allEmployees.removeColumn(allEmployees.getColumn("ID Number"));
		allEmployees.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEmployees.setFillsViewportHeight(true);
		allEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEmployees);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		unavailPanel.add(scroll);
		// resizes column of table
		allEmployees.getColumnModel().getColumn(4).setPreferredWidth(10);
		allEmployees.getColumnModel().getColumn(6).setPreferredWidth(10);
		
		JLabel idLabel = generator.createLabel(unavailPanel, "Please select a employee to book off time for:", "");		
		generator.createRigidSpace(unavailPanel, 10);
		final JLabel dateLabel = generator.createLabel(unavailPanel, "Enter the date (yyyy-mm-dd) the employee wishes to book off", "");
		final JTextField dateInput = generator.createTextField(unavailPanel, "");
		generator.createRigidSpace(unavailPanel, 10);
		final JLabel dateResultLabel = generator.createLabel(unavailPanel, " ");	
		JButton bookButton = generator.createButton(unavailPanel, "Enter", 10);		
		JButton backButton = generator.createButton(unavailPanel, "Back", mainViewName, 5);	
		final JLabel resultLabel = generator.createLabel(unavailPanel, " ", "");	
		
		unavailPanel.setLayout(new BoxLayout(unavailPanel, BoxLayout.Y_AXIS));	
		// conditions for BookButton - attempts to book an employee and set them unavailable
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				boolean result = false;
				DateFormat DOB = new SimpleDateFormat("yyyy-MM-dd");	
				dateResultLabel.setText("");
				Date date = null;
				//Quick Input tests
				if(allEmployees.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
				}
				try {
					date = new java.sql.Date(DOB.parse(dateInput.getText()).getTime());
				} catch (ParseException e1) {
					resultLabel.setText("Error date must be of the format (yyyy-mm-dd)!");
					return;
				}
				
				//End of Input Tests
				EmployeeUnavailDate empDate = new EmployeeUnavailDate(allEmployeesModel.getIDFromRow(allEmployees.getSelectedRow()), date);
				// attempts to change employees status in DB 
				result = EmployeeControllerClass.getInstance().addUnavailableDate(empDate, UIControllerClass.currentUser);
				if(!result){
					resultLabel.setText("Unavailibility didn't add correctly!");
				}else{
					resultLabel.setText("Unavailibility successfully updated!");
					dateLabel.setText("");
					dateResultLabel.setText("");
				}

			}			
		});				
	}
}