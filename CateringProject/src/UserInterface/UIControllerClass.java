package UserInterface;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Client.ClientUIViewClass;
import Employee.EmployeeUIViewClass;
import Equipment.EquipmentUIViewClass;
import Event.EventUIViewClass;
import Login.LoginUIViewClass;
import Login.UserClass;
import Menu.MealUIViewClass;
import UserInterface.TableModel;

/**
 * This class is used for GUI controller.
 * 
 * @author Mark Lessard
 */
public class UIControllerClass {

	public static UserClass currentUser;

	ClientUIViewClass clientUIView;
	EmployeeUIViewClass employeeUIView;
	EquipmentUIViewClass equipmentUIView;
	EventUIViewClass eventUIView;
	LoginUIViewClass loginUIView;
	MealUIViewClass mealUIView;
	MainMenuUIViewClass mainMenuUIView;
	
	//for GUI
	JPanel cardHolder;
	CardLayout cards;
	JFrame myWindow;
	
	TableModel allPendingEventsModel, allClients, allEquipment, availEmployees;

	/**
	 * Constructs a new instance of GUI controller.
	 */
	public UIControllerClass() {
		myWindow = new JFrame("mainGUIView");
		UIControllerClass.currentUser = null;
		
		
		
		cardHolder = new JPanel();
		cards = new CardLayout();
		cardHolder.setLayout(cards);
		
		allPendingEventsModel = new TableModel();
		allClients = new TableModel();
		allEquipment = new TableModel();
		availEmployees = new TableModel();
		
		loginUIView = new LoginUIViewClass(cardHolder);	
		clientUIView = new ClientUIViewClass(cardHolder, allClients);
		employeeUIView = new EmployeeUIViewClass(cardHolder, availEmployees);
		equipmentUIView = new EquipmentUIViewClass(cardHolder, allEquipment);
		eventUIView = new EventUIViewClass(cardHolder, allPendingEventsModel, allClients, allEquipment, availEmployees);
		mealUIView = new MealUIViewClass(cardHolder);
		mainMenuUIView = new MainMenuUIViewClass(cardHolder, allPendingEventsModel);
		
		myWindow.add(cardHolder);
		//Window config stuff
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setPreferredSize(new Dimension(800, 600));
		myWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
		myWindow.pack();
		myWindow.setLocationByPlatform(true);
		myWindow.setVisible(true);
	}

	
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public void go() {
//		boolean done = false;
//		String option;
//
//		currentUser = loginView.displayLogin();
//
//		while (!done) {
//			option = mainMenu.displayRoot(currentUser, eventView);
//
//			switch (option) {
//			case "1":
//				eventView.displayRoot(currentUser);
//				break;
//			case "2":
//				equipmentView.displayRoot(currentUser);
//				break;
//			case "3":
//				employeeView.displayRoot(currentUser);
//				break;
//			case "4":
//				clientView.displayRoot(currentUser);
//				break;
//			case "5":
//				menuView.displayMealOptions(currentUser);
//				break;
//			case "6":
//				loginView.displaySystemOptions(currentUser);
//				break;
//			case "7":
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
	}

