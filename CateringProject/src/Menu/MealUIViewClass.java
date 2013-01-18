package Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Login.UserClass;
import UserInterface.ButtonListener;
import UserInterface.ComponentGeneratorClass;
import UserInterface.ComponentGeneratorClass;
import UserInterface.TableModel;
import UserInterface.UIControllerClass;

/**
 * This class is used for GUI display of meal view. 
 * 
 * @author	Mark Lessard 
 */
@SuppressWarnings("unused")
public class MealUIViewClass{
	//Used to reference the different panels
	String rootViewName = "Main Menu View";
	String mainViewName = "Meal Main View";
	String addViewName = "Meal Add View";
	String modifyViewName = "Meal Modify View";
	String deleteViewName = "Meal Delete View";
	String listViewName = "Meal List View";

	UserClass currentUser;
	ComponentGeneratorClass generator;
	JPanel cardHolder, mainPanel, addPanel, deletePanel, modifyPanel, listPanel;
	int TableWidth = 600;
	
	TableModel allMealsModel;
	
	/**
	 * Constructs an MealUIViewClass object initialised to add 
	 * the panels to the cardholder, as well as initialise the generator.
	 */
	public MealUIViewClass(JPanel cardHolder) {
		this.cardHolder = cardHolder;
		
		//Model with all the data
		allMealsModel = new TableModel();
		
		generator = new ComponentGeneratorClass(cardHolder);

		//Init all the Panels
		mainPanelInit();
		addPanelInit();
		deletePanelInit();
		modifyPanelInit();
		listPanelInit();
		
		cardHolder.add(mainPanel, mainViewName);
		cardHolder.add(addPanel, addViewName);
		cardHolder.add(modifyPanel, modifyViewName);
		cardHolder.add(deletePanel, deleteViewName);
		cardHolder.add(listPanel, listViewName);
		
	}

	/**
	 * Initialise the main panel of display.
	 */
	private void mainPanelInit() {
		mainPanel = new JPanel();

		//Formatting
		generator.createRigidSpace(mainPanel, 175);
		JLabel heading = generator.createLabel(mainPanel, "Meal's Menu", "");
		heading.setFont(new Font("Dialog", 1, 20));
		
		//Add the buttons to the screen
		JButton createButton = generator.createButton(mainPanel, "Create Meal", addViewName, 50);
		JButton modifyButton = generator.createButton(mainPanel, "Modify Meal", modifyViewName, 5);
		JButton removeButton = generator.createButton(mainPanel, "Remove Meal", deleteViewName, 5);
		JButton listButton = generator.createButton(mainPanel, "View Meals", listViewName, 5);
		JButton backButton = generator.createButton(mainPanel, "Back", rootViewName, 30);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Initialise the panel displaying list.
	 */
	private void listPanelInit (){
		listPanel = new JPanel();

		//Formatting
		generator.createRigidSpace(listPanel, 70);

		//Show the allMeals table
		JTable allMeals = new JTable(allMealsModel.getTableModel(4));
		allMeals.removeColumn(allMeals.getColumn("ID Number"));
		allMeals.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMeals.setFillsViewportHeight(true);
		allMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allMeals);
		scroll.setMaximumSize(new Dimension(TableWidth, 500));
		listPanel.add(scroll);

		//Add the back button
		JButton backButton = generator.createButton(listPanel, "Back", mainViewName, 50);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * Initialise the panel displaying add meal.
	 */
	private void addPanelInit() {
		addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		
		generator.createRigidSpace(addPanel, 215);
		
		//Add all the Text and text fields
		JLabel nameLabel = generator.createLabel(addPanel, "Meal Name");
		final JTextField nameInput = generator.createTextField(addPanel, "");		
		JLabel courseLabel = generator.createLabel(addPanel, "Pick the course of the meal", "");
		
		//Course Radio Buttons
		final JRadioButton appetizerButton = new JRadioButton("Appetizer");
		appetizerButton.setActionCommand("appetizer");
		final JRadioButton mainButton = new JRadioButton("Main");
		mainButton.setActionCommand("main");
		final JRadioButton dessertButton = new JRadioButton("Dessert");
		dessertButton.setActionCommand("dessert");
		final ButtonGroup courseGroup = new ButtonGroup();
		courseGroup.add(appetizerButton);
		courseGroup.add(mainButton);
		courseGroup.add(dessertButton);
		addPanel.add(appetizerButton);
		addPanel.add(mainButton);
		addPanel.add(dessertButton);
		
		//Veg Radio buttons
		JLabel vegLabel = generator.createLabel(addPanel, "Is this meal vegeterian friendly?", "");
		final JRadioButton vegYes = new JRadioButton("Yes");
		vegYes.setActionCommand("yes");
		final JRadioButton vegNo = new JRadioButton("No");
		vegNo.setActionCommand("no");
		final ButtonGroup vegGroup = new ButtonGroup();
		vegGroup.add(vegYes);
		vegGroup.add(vegNo);
		addPanel.add(vegYes);
		addPanel.add(vegNo);
		
		generator.createRigidSpace(addPanel, 10);
		final JLabel resultLabel = generator.createLabel(addPanel, " ", "");
		//Add Buttons to screen
		JButton doneButton = generator.createButton(addPanel, "Add", 10);
		JButton backButton = generator.createButton(addPanel, "Back", mainViewName, 5);
		
		//If add button pressed
		doneButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MealClass newMeal;
				boolean result = false;
				//Quick Input checks
				if(nameInput.getText().matches("") || (!appetizerButton.isSelected() && !mainButton.isSelected() && !dessertButton.isSelected())
						|| (!vegYes.isSelected() && !vegNo.isSelected())){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				//End of Input Checks
				
				//Create a new meal class
				newMeal = new MealClass(nameInput.getText(),
										vegGroup.getSelection().getActionCommand(), 
										courseGroup.getSelection().getActionCommand());
				result = newMeal.verifyInput();
				if(!result){
					resultLabel.setText("Error Invalid Input!");
					return;
				}
				
				//Add it to the DB
				result = MealControllerClass.getInstance().newMeal(newMeal, UIControllerClass.currentUser);
				
				if(!result){
					resultLabel.setText("Error Adding Meal!");
				}else{
					//Report success and add it to the all meals table
					resultLabel.setText("Meal Added Successful!");
					Object[] newRow = {newMeal.getID(), newMeal.getName(), newMeal.getMealCourse(), newMeal.getVegFriendly()};
					allMealsModel.addRow(newRow);
					nameInput.setText("");
					courseGroup.clearSelection();
					vegGroup.clearSelection();
				}
			}			
		});
	}

	/**
	 * Initialise the panel displaying modify meal.
	 */
	private void modifyPanelInit(){
		modifyPanel = new JPanel();
		
		generator.createRigidSpace(modifyPanel, 15);
		
		//Show all the meals in a table
		final JTable allMeals = new JTable(allMealsModel.getTableModel(4));
		allMeals.removeColumn(allMeals.getColumn("ID Number"));
		allMeals.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMeals.setFillsViewportHeight(true);
		allMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allMeals);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		modifyPanel.add(scroll);
		
		//Add the text and text fields to the panel
		JLabel idLabel = generator.createLabel(modifyPanel, "Please choose a row", "");
		JLabel nameLabel = generator.createLabel(modifyPanel, "Meal Name");
		final JTextField nameInput = generator.createTextField(modifyPanel, "\t");	
		JLabel courseLabel = generator.createLabel(modifyPanel, "Please pick the course of the meal:", "");
		
		//Course Radio Buttons
		final JRadioButton appetizerButton = new JRadioButton("Appetizer");
		appetizerButton.setActionCommand("appetizer");
		final JRadioButton mainButton = new JRadioButton("Main");
		mainButton.setActionCommand("main");
		final JRadioButton dessertButton = new JRadioButton("Dessert");
		dessertButton.setActionCommand("dessert");
		final ButtonGroup courseGroup = new ButtonGroup();
		courseGroup.add(appetizerButton);
		courseGroup.add(mainButton);
		courseGroup.add(dessertButton);
		modifyPanel.add(appetizerButton);
		modifyPanel.add(mainButton);
		modifyPanel.add(dessertButton);	
		
		//Veg Radio Buttons
		JLabel vegLabel = generator.createLabel(modifyPanel, "Is this meal vegeterian friendly?", "");
		final JRadioButton vegYes = new JRadioButton("Yes");
		vegYes.setActionCommand("yes");
		final JRadioButton vegNo = new JRadioButton("No");
		vegNo.setActionCommand("no");
		final ButtonGroup vegGroup = new ButtonGroup();
		vegGroup.add(vegYes);
		vegGroup.add(vegNo);
		modifyPanel.add(vegYes);
		modifyPanel.add(vegNo);	
		final JLabel resultLabel = generator.createLabel(modifyPanel, " ", "");
		
		//Add and back Buttons
		JButton updateButton = generator.createButton(modifyPanel, "Update Meal", 10);
		JButton backButton = generator.createButton(modifyPanel, "Back", mainViewName, 5);
		
		modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));
		
		//If Modify Button pressed
		updateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean result = false;
				MealClass meal;
				//Quick Input checks
				if(allMeals.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
				}
				if(nameInput.getText().matches("") || (!appetizerButton.isSelected() && !mainButton.isSelected() && !dessertButton.isSelected())
						|| (!vegYes.isSelected() && !vegNo.isSelected())){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				//End of Input Checks
				
				//Make a new meal class to hold modified data
				meal = new MealClass(nameInput.getText(), allMealsModel.getIDFromRow(allMeals.getSelectedRow()), vegGroup.getSelection().getActionCommand(), courseGroup.getSelection().getActionCommand());
				result = meal.verifyInput();
				if(!result){
					resultLabel.setText("Invalid Input");
					return;
				}
				
				//Send the mealclass to the DB to update
				result = MealControllerClass.getInstance().updateMeal(meal);
				if(!result){
					resultLabel.setText("Error modifying meal");
					
				}else{
					//Report success and modify menu entry
					resultLabel.setText("Meal Modified Successful!");
					Object[] modifiedRow = {meal.getID(), meal.getName(), meal.getMealCourse(), meal.getVegFriendly()};
					allMealsModel.modifyData(modifiedRow, allMeals.getSelectedRow());
					nameInput.setText("");
					courseGroup.clearSelection();
					vegGroup.clearSelection();
				}
			}
		});
		
		//Populate fields based on table selection
		ListSelectionModel rowSM = allMeals.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] data = allMealsModel.getData(allMeals.getSelectedRow());
				
				nameInput.setText((String)data[1]);
				String course = (String)data[2];
				String veg = (String)data[3];
				
				if(course.matches("appetizer") == true){
					appetizerButton.setSelected(true);
				}
				else if(course.matches("main") == true){
					mainButton.setSelected(true);
				}
				else if(course.matches("dessert") == true){
					dessertButton.setSelected(true);
				}
				
				if(veg.matches("yes")){
					vegYes.setSelected(true);
				}
				else if(veg.matches("no")){
					vegNo.setSelected(true);
				}
			}
		});
	}
	
	/**
	 * Initialise the panel displaying delete meal.
	 */
	private void deletePanelInit(){
		deletePanel = new JPanel();
		
		generator.createRigidSpace(deletePanel, 90);

		//Show all meals in a table
		final JTable allMeals = new JTable(allMealsModel.getTableModel(4));
		allMeals.removeColumn(allMeals.getColumn("ID Number"));
		allMeals.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allMeals.setFillsViewportHeight(true);
		allMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allMeals);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		deletePanel.add(scroll);
	
		//Add the text to the panel
		JLabel idLabel = generator.createLabel(deletePanel, "Please select a meal from the table to remove:", "");
		generator.createRigidSpace(deletePanel, 10);
		final JLabel resultLabel = generator.createLabel(deletePanel, " ", "");
		
		//Add a remove and back button
		JButton doneButton = generator.createButton(deletePanel, "Remove", 10);
		JButton backButton = generator.createButton(deletePanel, "Back", mainViewName, 5);
		
		deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));

		//If Remove button pressed
		doneButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
		    	boolean result = false;
		    	//Quick Input checks
				if(allMeals.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
				}
				//End of Input Checks
		    	
				//Remove from the DB
				result = MealControllerClass.getInstance().deleteMeal(allMealsModel.getIDFromRow(allMeals.getSelectedRow()));
				if(!result){
					resultLabel.setText("Error Deleting meal");
					return;
				}
				else{
					//Report success and remove from the table
					resultLabel.setText("Meal Deleted Successfully!");
					allMealsModel.removeRow(allMeals.getSelectedRow());
					return;
				}
	    	}		
		});  
	}
}
