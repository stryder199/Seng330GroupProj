package Client;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
 
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Login.UserClass;
import UserInterface.ButtonListener;
import UserInterface.ComponentGeneratorClass;
import UserInterface.TableModel;
import UserInterface.UIControllerClass;

/**
 * This class is used to display the GUI interface of client view.
 * 
 * @author Mark Lessard 
 */
@SuppressWarnings("unused")
public class ClientUIViewClass {
	//cardholder string names
	String rootViewName = "Main Menu View";
	String mainViewName = "Client Main View";
	String listViewName = "Client List View";
	String addViewName = "Client Add View";
	String modifyViewName = "Client Modify View";
	String deleteViewName = "Client Delete View";
	
	//all panels and table models for the client menus
	private JPanel cardHolder, mainPanel, addPanel, deletePanel, listPanel, modifyPanel;
	private TableModel allClientsModel;
	int TableWidth = 800;
	
	//component generator
	ComponentGeneratorClass generator;
	
	/**
	 * Constructs a ClientUIViewClass object initialised to add 
	 * the panels to the cardholder, as well as initialise the component generator
	 * 
	 * @param cardHolder
	 * @param clientsModel
	 */
	public ClientUIViewClass(JPanel cardHolder, TableModel clientsModel) {
		this.cardHolder = cardHolder;

		allClientsModel = clientsModel;
		
		generator = new ComponentGeneratorClass(cardHolder);
		//init all panels
		mainPanelInit();
		addPanelInit();
		deletePanelInit();
		listPanelInit();
		modifyPanelInit();
		
		//add all panels to cardholder
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
		//generates panels formatted layout of components
		generator.createRigidSpace(mainPanel, 175);
		JLabel heading = generator.createLabel(mainPanel, "Clients Menu", "");
		heading.setFont(new Font("Dialog", 1, 20));
		JButton createButton = generator.createButton(mainPanel, "Add Client", addViewName, 50);
		JButton modifyButton = generator.createButton(mainPanel, "Modify Client", modifyViewName, 5);
		JButton removeButton = generator.createButton(mainPanel, "Remove Client", deleteViewName, 5);
		JButton viewButton = generator.createButton(mainPanel, "View Clients", listViewName, 5);
		JButton backButton = generator.createButton(mainPanel, "Back", rootViewName, 30);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * This method initialises a client viewing panel accessible from the client Menu.
	 */
	private void listPanelInit(){
		listPanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(listPanel, 70);
		JTable allClients = new JTable(allClientsModel.getTableModel(2));
		allClients.removeColumn(allClients.getColumn("ID Number"));
		allClients.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allClients.setFillsViewportHeight(true);
		allClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allClients);
		scroll.setMaximumSize(new Dimension(TableWidth, 500));
		listPanel.add(scroll);
        
		JButton backButton = generator.createButton(listPanel, "Back", mainViewName, 50);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}
	
	/**
	 * This method initialises a client add panel accessible from the client Menu.
	 */
	private void addPanelInit () {
		addPanel = new JPanel();

		//generates panels formatted layout of components
		generator.createRigidSpace(addPanel, 40);
		final JLabel testLabel = generator.createLabel(addPanel, "");		
		JLabel fNameLabel = generator.createLabel(addPanel, "First Name");	
		final JTextField fNameInput = generator.createTextField(addPanel, "");
		JLabel lNameLabel = generator.createLabel(addPanel, "Last Name");	
		final JTextField lNameInput = generator.createTextField(addPanel, "");
		JLabel companyLabel = generator.createLabel(addPanel, "Company");
		final JTextField companyInput = generator.createTextField(addPanel, "");	
		JLabel emailLabel = generator.createLabel(addPanel, "Email");
		final JTextField emailInput = generator.createTextField(addPanel, "");
		JLabel phoneLabel = generator.createLabel(addPanel, "Phone (xxx-xxx-xxxx)");
		final JTextField phoneInput = generator.createTextField(addPanel, "");
		JLabel addressLabel = generator.createLabel(addPanel, "Address");
		final JTextField addressInput = generator.createTextField(addPanel, "");
		generator.createRigidSpace(addPanel, 10);	
		final JLabel resultLabel = generator.createLabel(addPanel, " ", "");
		JButton doneButton = generator.createButton(addPanel, "Add client", 15);
		JButton backButton = generator.createButton(addPanel, "Back", mainViewName, 5);
		
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));	
		
		//conditions for when doneButton is pressed - attempts to add the client to the DB
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				resultLabel.setText(" ");
				boolean result = false;
				ClientClass newClient;
				//Quick Input test
				if(fNameInput.getText().matches("") || lNameInput.getText().matches("") || emailInput.getText().matches("")
						|| companyInput.getText().matches("") || phoneInput.getText().matches("") || addressInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				//End of Input tests
				
				newClient = new ClientClass(fNameInput.getText(), lNameInput.getText(), emailInput.getText(), 
											companyInput.getText(), phoneInput.getText(), addressInput.getText());
				result = newClient.verifyInput();
				if (result == true){
					//attempts to add client to the Database
					result = ClientControllerClass.getInstance().addClient(newClient, UIControllerClass.currentUser);
					
					Object[] data = new Object[8];
					//sets all the given data to data for updating table
					data[0] = newClient.getClientId();
					data[1] = newClient.getFirstName();
					data[2] = newClient.getLastName();
					data[3] = newClient.getCompany();
					data[4] = newClient.getEmail();
					data[5] = newClient.getPhone();
					data[6] = newClient.getAddress();
					
					if (result == true){
						resultLabel.setText("Successfully added the client!");
						allClientsModel.addRow(data);
						fNameInput.setText("");
						lNameInput.setText(""); 
						emailInput.setText("");
						companyInput.setText("");
						phoneInput.setText(""); 
						addressInput.setText("");
					} else {
						resultLabel.setText("Error adding client to the database, Please try again...");

					}
				} else {
					resultLabel.setText("Invalid Input, Please try again...");
				}
			}			
		});
	}

	/**
	 * This method initialises a client modify panel accessible from the client Menu.
	 */
	private void modifyPanelInit() {
		modifyPanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(modifyPanel, 15);
		final JTable allClients = new JTable(allClientsModel.getTableModel(2));
		allClients.removeColumn(allClients.getColumn("ID Number"));
		allClients.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allClients.setFillsViewportHeight(true);
		allClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allClients);
		scroll.setMaximumSize(new Dimension(TableWidth, 300));
		modifyPanel.add(scroll);
		
		JLabel idLabel = generator.createLabel(modifyPanel, "Please choose a row", "");
		JLabel emailLabel = generator.createLabel(modifyPanel, "Email");
		final JTextField emailInput = generator.createTextField(modifyPanel, "");
		JLabel companyLabel = generator.createLabel(modifyPanel, "Company");	
		final JTextField companyInput = generator.createTextField(modifyPanel, "");
		JLabel phoneLabel = generator.createLabel(modifyPanel, "Phone Number (xxx-xxx-xxxx)");	
		final JTextField phoneInput = generator.createTextField(modifyPanel, "");
		JLabel addressLabel = generator.createLabel(modifyPanel, "Address");
		final JTextField addressInput = generator.createTextField(modifyPanel, "");
		
		final JLabel resultLabel = generator.createLabel(modifyPanel, " ");	
		JButton updateButton = generator.createButton(modifyPanel, "Update", 5);
		JButton backButton = generator.createButton(modifyPanel, "Back", mainViewName, 5);

	    modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));	
	    //conditions for when updateButton is pressed - attemps to update clients DB info
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				boolean result = false;
				int clientId = 0;
				ClientClass client;
				
				//Quick Input Check
				if(emailInput.getText().matches("")	|| companyInput.getText().matches("") || phoneInput.getText().matches("") || addressInput.getText().matches("")){
					resultLabel.setText("Please fill out the empty fields");
					return;
				}
				if(allClients.getSelectedRow() == -1){
					resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
				}
				//End Input Check
				
				clientId = allClientsModel.getIDFromRow(allClients.getSelectedRow());
				client = ClientControllerClass.getInstance().getClient(clientId);
				//setes clientclass to data from input fields
				client.setEmail(emailInput.getText());
				client.setCompany(companyInput.getText());
				client.setPhone(phoneInput.getText());
				client.setAddress(addressInput.getText());
				
				result = client.verifyInput();
				if(!result){
					resultLabel.setText("Invalid Client Information, Please try again...");
					return;
				}
				//attempts to modify the DB info for the clint
				result = ClientControllerClass.getInstance().modifyClient(client);
				if (result == true){
					resultLabel.setText("Client information successfully updated!");
					Object[] modifiedRow = {client.getClientId(), client.getFirstName(), client.getLastName(), client.getCompany(), client.getEmail(), client.getPhone(), client.getAddress()};
					allClientsModel.modifyData(modifiedRow, allClients.getSelectedRow());
					emailInput.setText("");
					companyInput.setText("");
					phoneInput.setText("");
					addressInput.setText("");
				} else {
					resultLabel.setText("Error modifying client in the database, Please try again...");
				}

			}
		});
		//sets the table to allow row selection only
		ListSelectionModel rowSM = allClients.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
		    @Override
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] data = allClientsModel.getData(allClients.getSelectedRow());
				//sets the input labels to given table data
				companyInput.setText((String)data[3]);
				emailInput.setText((String)data[4]);
				phoneInput.setText((String)data[5]);
				addressInput.setText((String)data[6]);				
			}
		});
	}

	/**
	 * This method initialises a client delete panel accessible from the client Menu.
	 */
	private void deletePanelInit(){
		deletePanel = new JPanel();
		
		//generates panels formatted layout of components
		generator.createRigidSpace(deletePanel, 90);
		final JTable allClients = new JTable(allClientsModel.getTableModel(2));
		allClients.removeColumn(allClients.getColumn("ID Number"));
		allClients.setPreferredScrollableViewportSize(new Dimension(450, 70));
		allClients.setFillsViewportHeight(true);
		allClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(allClients);
		scroll.setMaximumSize(new Dimension(TableWidth, 400));
		deletePanel.add(scroll);
	
		JLabel idLabel = generator.createLabel(deletePanel, "Please select a Client from the table to remove", "");	
		generator.createRigidSpace(deletePanel, 10);
		final JLabel resultLabel = generator.createLabel(deletePanel, " ", "");
		JButton doneButton = generator.createButton(deletePanel, "Remove", 10);
		JButton backButton = generator.createButton(deletePanel, "Back", mainViewName, 5);
		JLabel warningLabel = generator.createLabel(deletePanel, "****WARNING This will also remove all events associated with this client!!****", "");	
	    
	    deletePanel.setLayout(new BoxLayout(deletePanel, BoxLayout.Y_AXIS));	
		//conditions for when doneButton is pressed-attempts to remove the client from DB
		doneButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e){
		    	resultLabel.setText(" ");
				boolean result = false;
				//Quick input check
				if(allClients.getSelectedRow() == -1){
	            	resultLabel.setText("Please select a row, Please try again..."); 
	            	return;
	            }

				result = ClientControllerClass.getInstance().deleteClient(allClientsModel.getIDFromRow(allClients.getSelectedRow()));
				if(!result){
					resultLabel.setText("Client was not removed correctly, please try again");
				} else{
					resultLabel.setText("Client successfully removed!");
					allClientsModel.removeRow(allClients.getSelectedRow());
				}			
		    }
		});
	}
}