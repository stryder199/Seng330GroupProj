package UserInterface;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import Client.ClientClass;
import Client.ClientControllerClass;

/**
 * This class is used for GUI display of main menu.
 * 
 * @author Mark Lessard 
 */
@SuppressWarnings("unused")
public class MainMenuUIViewClass {
	private String loginView = "Login View";
	private String rootView = "Main Menu View";
	private JPanel cardHolder;
	private JPanel mainMenuPanel;
	ComponentGeneratorClass generator;
	
	/**
	 * Constructs an MainMenuUIViewClass object initialised to add 
	 * the panels to the cardholder, as well as initialise the generator.
	 * 
	 * @param cardHolder
	 * @param allPendingEventsModel
	 */
	public MainMenuUIViewClass(JPanel cardHolder, TableModel allPendingEventsModel) {
		this.cardHolder = cardHolder;
		generator = new ComponentGeneratorClass(cardHolder);		
		mainMenuPanelInit(allPendingEventsModel);
		
		cardHolder.add(mainMenuPanel, rootView);
	}
	
	/**
	 * This method initialises a main menu panel allowing the user to
	 * choose which menu they would like to visit.
	 */
	public void mainMenuPanelInit (final TableModel allPendingEventsModel){
		mainMenuPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(mainMenuPanel, 90);
		JLabel pendingLabel = generator.createLabel(mainMenuPanel, "Your Pending Events", "");
		pendingLabel.setFont(new Font("Dialog", 1, 16));
		
		final JScrollPane scroll = new JScrollPane();
		scroll.setMaximumSize(new Dimension(350, 150));
		mainMenuPanel.add(scroll);
		
		JButton eventButton = generator.createButton(mainMenuPanel, "Events", "Event Main View", 30);
		JButton inventoryButton = generator.createButton(mainMenuPanel, "Inventory", "Equipment Main View", 5);
		JButton employeeButton = generator.createButton(mainMenuPanel, "Employees", "Employee Main View", 5);
		JButton clientButton = generator.createButton(mainMenuPanel, "Clients", "Client Main View", 5);
		JButton mealButton = generator.createButton(mainMenuPanel, "Meals", "Meal Main View", 5);
		JButton adminButton = generator.createButton(mainMenuPanel, "System Admin", "Admin Main View", 5);
		JButton logOutButton = generator.createButton(mainMenuPanel, "Log Out", 30);
		
		mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
		// when mainMenuPanel is shown, populates pending events table
		mainMenuPanel.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				allPendingEventsModel.resetModelData();
				JTable allPendingEvents = new JTable(allPendingEventsModel.getPendingEventTable());
				allPendingEvents.setPreferredScrollableViewportSize(new Dimension(450, 70));
				allPendingEvents.setFillsViewportHeight(true);
				allPendingEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				allPendingEvents.removeColumn(allPendingEvents.getColumn("ID Number"));
				allPendingEvents.removeColumn(allPendingEvents.getColumn("Client"));
				allPendingEvents.removeColumn(allPendingEvents.getColumn("Location"));
				allPendingEvents.removeColumn(allPendingEvents.getColumn("# Guests"));
				allPendingEvents.removeColumn(allPendingEvents.getColumn("Duration"));
				allPendingEvents.getColumnModel().getColumn(1).setPreferredWidth(20);
				scroll.setViewportView(allPendingEvents);
			}
		});
		// when logout button pressed, logs user out and goes to login screen
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				UIControllerClass.currentUser = null;
				CardLayout cLayout = (CardLayout) cardHolder.getLayout();
				cLayout.show(cardHolder, "Login View");
			}			
		});
	}
	
	/**
	 * Gets the main menu panel of display.
	 */
	public JPanel getMainMenuPanel(){
		return mainMenuPanel;
	}
}