package Equipment;

import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Login.UserClass;
import UserInterface.ButtonListener;
import UserInterface.ComponentGeneratorClass;
import UserInterface.TableModel;
import UserInterface.UIControllerClass;

/**
 * This class is used for GUI display of equipment view. 
 * 
 * @author	Mark Lessard 
 */
@SuppressWarnings("unused")
public class EquipmentUIViewClass{
	String rootViewName = "Main Menu View";
	String mainViewName = "Equipment Main View";
	String listViewName = "Equipment List View";
	String addViewName = "Equipment Add View";
	String modifyViewName = "Equipment Modify View";
	String deleteViewName = "Equipment Delete View";
	
	UserClass currentUser;
	ComponentGeneratorClass generator;
	int TableWidth = 600;
	JPanel cardHolder, mainPanel, listPanel, addPanel, modifyPanel, deletePanel, bookingPanel;

	TableModel allEquipmentModel;
	
	/**
	 * Constructs the GUI display.
	 */
	public EquipmentUIViewClass(JPanel cardHolder, TableModel allEquipment) {
		this.cardHolder = cardHolder;
		generator = new ComponentGeneratorClass(cardHolder);
		
		//Get the shared model allEquipment
		allEquipmentModel = allEquipment;
		
		//Init all the panels
		mainPanelInit();
		listPanelInit();
		addPanelInit();
		modifyPanelInit();
		deletePanelInit();
		
		cardHolder.add(mainPanel, mainViewName);
		cardHolder.add(listPanel, listViewName);
		cardHolder.add(addPanel, addViewName);
		cardHolder.add(modifyPanel, modifyViewName);
		cardHolder.add(deletePanel, deleteViewName);
	}
    
	/**
	 * Initialise the main panel of display.
	 */
	private void mainPanelInit(){
		mainPanel = new JPanel();

		//Formatting
		generator.createRigidSpace(mainPanel, 175);
		JLabel heading = generator.createLabel(mainPanel, "Inventory Menu", "");
		heading.setFont(new Font("Dialog", 1, 20));
		
		//Add the buttons to main menu
		JButton createButton = generator.createButton(mainPanel, "Add Equipment", addViewName, 50);
		JButton modifyButton = generator.createButton(mainPanel, "Modify Equipment", modifyViewName, 5);
		JButton removeButton = generator.createButton(mainPanel, "Remove Equipment", deleteViewName, 5);
		JButton viewButton = generator.createButton(mainPanel, "View Equipment", listViewName, 5);
		JButton backButton = generator.createButton(mainPanel, "Back", rootViewName, 30);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * Initialise the panel displaying list.
	 */
	private void listPanelInit (){
		listPanel = new JPanel();
		
		generator.createRigidSpace(listPanel, 70);
		
		//Add a table of all the equipment
		JTable allEquipment = new JTable(allEquipmentModel.getTableModel(5));
		allEquipment.removeColumn(allEquipment.getColumn("ID Number"));
		allEquipment.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEquipment.setFillsViewportHeight(true);
		allEquipment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEquipment);
		scroll.setMaximumSize(new Dimension(TableWidth, 500));
		listPanel.add(scroll);
        
		JButton backButton = generator.createButton(listPanel, "Back", mainViewName, 50);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * Initialise the panel displaying add equipment.
	 */
	private void addPanelInit(){
		addPanel = new JPanel();

		generator.createRigidSpace(addPanel, 215);
		
		//Add all the text and text fields to the panel
		JLabel nameLabel = generator.createLabel(addPanel, "Equipment's Name");	
		final JTextField nameInput = generator.createTextField(addPanel, "");
		
		JLabel quantityLabel = generator.createLabel(addPanel, "Quantity");	
		final JTextField quantityInput = generator.createTextField(addPanel, "");
		
		JLabel rentalLabel = generator.createLabel(addPanel, "Rental Cost Per Hour");
		final JTextField rentalInput = generator.createTextField(addPanel, "");	
		
		final JLabel resultLabel = generator.createLabel(addPanel, " ");
		
		//Add and back button
		JButton doneButton = generator.createButton(addPanel, "Add", 5);
		JButton backButton = generator.createButton(addPanel, "Back", mainViewName, 5);
		
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));	
		
		//If add button is pressed
		doneButton.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				boolean result = false;
				EquipmentClass newEquipment;
				int quantity = 0;
				double rental = 0;
				//Quick Input test
				if(nameInput.getText().matches("") || quantityInput.getText().matches("") || rentalInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				try {
					quantity = Integer.parseInt(quantityInput.getText());
					rental = Double.parseDouble(rentalInput.getText());
				} catch (NumberFormatException e1) {
					resultLabel.setText("Error quantity/rental MUST be numbers");
					return;
				}
				//End of Input tests
				
				newEquipment = new EquipmentClass(nameInput.getText().toLowerCase(), quantity, rental);
				if(!newEquipment.verifyInput()){
					resultLabel.setText("Invalid Input");
					return;
				}
				
				//Add Equip to the DB
				result = EquipmentControllerClass.getInstance().addEquipment(newEquipment, UIControllerClass.currentUser);
				if(!result){
					resultLabel.setText("Equipment didn't add correctly, please try again");
				} else{
					//Report success and add to model
					resultLabel.setText("Equipment successfully added!");
					Object[] newRow = {newEquipment.getEquipmentId(), newEquipment.getName(), newEquipment.getQuantity(), newEquipment.getRentalCost()};
					allEquipmentModel.addRow(newRow);
					nameInput.setText("");
					quantityInput.setText("");
					rentalInput.setText("");
				}
			}			
		});
	}

	/**
	 * Initialise the panel displaying modify equipment.
	 */
	private void modifyPanelInit(){
		modifyPanel = new JPanel();
		
		generator.createRigidSpace(modifyPanel, 15);
		
		//Add a table of all the equipment to the panel
		final JTable allEquipment = new JTable(allEquipmentModel.getTableModel(5));
		allEquipment.removeColumn(allEquipment.getColumn("ID Number"));
		allEquipment.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEquipment.setFillsViewportHeight(true);
		allEquipment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEquipment);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		modifyPanel.add(scroll);   
		
		//Add the text and text fields to the panel
		JLabel employeeChoiceLabel = generator.createLabel(modifyPanel, "Please choose a piece of equipment to modify", "");
		
		generator.createRigidSpace(modifyPanel, 10);
		JLabel nameLabel = generator.createLabel(modifyPanel, "Name");
		final JTextField nameInput = generator.createTextField(modifyPanel, "");
		
		JLabel quantityLabel = generator.createLabel(modifyPanel, "Quantity");	
		final JTextField quantityInput = generator.createTextField(modifyPanel, "");
		
		
		JLabel rentalLabel = generator.createLabel(modifyPanel, "Rental Cost Per Hour");	
		final JTextField rentalInput = generator.createTextField(modifyPanel, "");
		
		final JLabel resultLabel = generator.createLabel(modifyPanel, " ");	
		
		//Add the modify and back button to the panel
		JButton updateButton = generator.createButton(modifyPanel, "Update", 5);
		JButton backButton = generator.createButton(modifyPanel, "Back", mainViewName, 5);

	    modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));	
		
	    //If the Modify button was pressed
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				boolean result = false;
				EquipmentClass modifiedEquipment;
				int quantity = 0;
				double rental = 0;
				//Quick Input test
				if(nameInput.getText().matches("") || quantityInput.getText().matches("") || rentalInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				if(allEquipment.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }
				try {
					quantity = Integer.parseInt(quantityInput.getText());
					rental = Double.parseDouble(rentalInput.getText());
				} catch (NumberFormatException e1) {
					resultLabel.setText("Error quantity/rental MUST be numbers");
					return;
				}
				//End of Input tests
				
				//Create a new equipmentclass with the modified data
	            modifiedEquipment = new EquipmentClass(nameInput.getText().toLowerCase(), quantity, rental);
	            result = modifiedEquipment.verifyInput();
	            if(!result){
	            	resultLabel.setText("Invalid Input");
	            	return;
	            }
	            
	            //Modify the equipment in the DB
	            result = EquipmentControllerClass.getInstance().modifyEquipment(modifiedEquipment);
	            if(!result){
	            	resultLabel.setText("Equipment didn't update correctly, please try again");
	            } else{
	            	//Report success and modify the table
	            	resultLabel.setText("Equipment successfully updated!");
	            	Object[] modifiedRow = {modifiedEquipment.getEquipmentId(), modifiedEquipment.getName(), modifiedEquipment.getQuantity(), modifiedEquipment.getRentalCost()};
	            	allEquipmentModel.modifyData(modifiedRow, allEquipment.getSelectedRow());
					nameInput.setText("");
					quantityInput.setText("");
					rentalInput.setText("");
	            }
			}
		});
		
		//Change text field data based on selected row in the table
		ListSelectionModel rowSM = allEquipment.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] data = allEquipmentModel.getData(allEquipment.getSelectedRow());
				
				nameInput.setText((String)data[1]);
				quantityInput.setText(String.valueOf((int)data[2]));
				rentalInput.setText(String.valueOf((double)data[3]));
			}
		});
	}
	
	/**
	 * Initialise the panel displaying delete equipment.
	 */
	private void deletePanelInit(){
		deletePanel = new JPanel();
		
		generator.createRigidSpace(deletePanel, 90);
		
		//Add a table of all the equipment to the panel
		final JTable allEquipment = new JTable(allEquipmentModel.getTableModel(5));
		allEquipment.removeColumn(allEquipment.getColumn("ID Number"));
		allEquipment.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allEquipment.setFillsViewportHeight(true);
		allEquipment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allEquipment);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		deletePanel.add(scroll);   

		//Add the text and text fields to the panel
		JLabel employeeChoiceLabel = generator.createLabel(deletePanel, "Please select equipment from the table to remove", "");
		generator.createRigidSpace(deletePanel, 10);
		final JLabel resultLabel = generator.createLabel(deletePanel, " ", "");
		
		//Add the remove and back buttons to the panel
		JButton doneButton = generator.createButton(deletePanel, "Remove", 10);
		JButton backButton = generator.createButton(deletePanel, "Back", mainViewName, 5);
		generator.createRigidSpace(deletePanel, 100);
	    
	    deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));			
		
	    //If you pressed the remove button
		doneButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
				boolean result = false;
				//Quick Input Check
				if(allEquipment.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }
				
				//Remove the selected equipment from the DB
				result = EquipmentControllerClass.getInstance().deleteEquipment(allEquipmentModel.getIDFromRow(allEquipment.getSelectedRow()));
				if(!result){
					resultLabel.setText("Equipment was not removed correctly, please try again");
				} else{
					//Report success and remove from the Model
					resultLabel.setText("Equipment successfully removed!");
					allEquipmentModel.removeRow(allEquipment.getSelectedRow());
				}
	    	}
		});
	}
}