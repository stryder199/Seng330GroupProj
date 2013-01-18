//package Equipment;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//
//import Login.UserClass;
//
///**
// * This class is the display UI for the equipment menu.
// * 
// * @author Chuan Yun Loe
// */
//public class EquipmentViewClass {
//
//	/**
//	 * Constructs a new instance of the display UI.
//	 */
//	public EquipmentViewClass() {}
//
//	/**
//	 * Display the equipment menu.
//	 * 
//	 * @param	currentUser	the current user
//	 * @see		EquipmentClass
//	 * @see		EquipmentControllerClass
//	 * @see		UserClass
//	 */
//	public void displayRoot(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//		boolean done = false;
//		boolean result = false;
//		
//		// Equipment Menu
//		while (!done) {
//			System.out.println("Please pick an option:");
//			System.out.println("1.Add Equipment");
//			System.out.println("2.Delete Equipment");
//			System.out.println("3.Modify Equipment");
//			System.out.println("4.List Equipment");
//			System.out.println("5.Back");
//			option = input.nextLine();
//			switch (option) {
//			case "1": // Add Equipment
//				EquipmentClass newEquipment = getAddInput();
//				result = EquipmentControllerClass.getInstance().addEquipment(newEquipment, currentUser);
//				if(!result){
//					System.out.println("Equipment didn't add correctly, please try again");
//				}
//				else{
//					System.out.println("Equipment added correctly!");
//				}
//				break;
//			case "2": // Delete Equipment
//				String deleteTarget = getDeleteInput();
//				result = EquipmentControllerClass.getInstance().deleteEquipment(deleteTarget);
//				if(!result){
//					System.out.println("Equipment didn't delete correctly, please try again");
//				}
//				else{
//					System.out.println("Equipment deleted correctly!");
//				}
//				break;
//			case "3": // Modify Equipment
//				EquipmentClass updateEquipment = getUpdateInput();
//				result = EquipmentControllerClass.getInstance().modifyEquipment(updateEquipment);
//				if(!result){
//					System.out.println("Equipment didn't modify correctly, please try again");
//				}
//				else{
//					System.out.println("Equipment modified correctly!");
//				}
//				break;
//			case "4": // List Equipment
//				// Grab all data from DB
//				List<EquipmentClass> allEquipment = EquipmentControllerClass.getInstance().listAllEquipment();
//				printAllEquipment(allEquipment);
//				break;
//			case "5": // Back to main menu
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//	}
//	
//	/**
//	 * Display all equipments in the equipment database.
//	 * 
//	 * @param	allEquipment	all equipments
//	 */
//	private void printAllEquipment(List<EquipmentClass> allEquipment) {
//		Iterator<EquipmentClass> it; 
//
//		//Sanity Check
//		assert(allEquipment != null);	
//
//		it = allEquipment.iterator();
//
//		while(it.hasNext()){
//			EquipmentClass element = it.next();
//
//			System.out.println("Description: " + element.getName() + "\tQuantity: " + 
//							   element.getQuantity() + "\tCost per hour: " + 
//							   element.getRentalCost());
//		}
//	}
//	
//	/**
//	 * Display form to get information about the new equipment.
//	 * 
//	 * @return	new equipment
//	 */
//	private EquipmentClass getAddInput() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String equipmentName, quantity, rentalcost;
//		
//		System.out.println("Equipment's name:");
//		equipmentName = input.nextLine();
//		
//		do { // Make sure input is integer
//			System.out.println("Quantity:");
//			quantity = input.nextLine();
//		} while(!checkInteger(quantity));
//		
//		do {
//			System.out.println("Rental cost per hour:");
//			rentalcost = input.nextLine();
//		} while(!checkFloat(rentalcost));
//		
//		EquipmentClass newEquipment = new EquipmentClass(equipmentName.toLowerCase(), 
//														Integer.parseInt(quantity), 
//														Float.parseFloat(rentalcost));
//		return newEquipment;
//	}
//	
//	/**
//	 * Display form to get information about the equipment to be deleted.
//	 * 
//	 * @return	equipment name
//	 */
//	private String getDeleteInput() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String equipmentName;
//		
//		System.out.println("Equipment's name:");
//		equipmentName = input.nextLine();
//		
//		return equipmentName.toLowerCase();
//	}
//	
//	/**
//	 * Display form to get information about the equipment to be updated.
//	 * 
//	 * @return	updated equipment
//	 */
//	private EquipmentClass getUpdateInput() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String equipmentName, quantity, rentalcost;
//		
//		System.out.println("Equipment's name:");
//		equipmentName = input.nextLine();
//		
//		do { // Make sure input is integer
//			System.out.println("Equipment's new quantity:");
//			quantity = input.nextLine();
//		} while(!checkInteger(quantity));
//		
//		do {
//			System.out.println("Equipment's new rental cost per hour:");
//			rentalcost = input.nextLine();
//		} while(!checkFloat(rentalcost));
//		
//		EquipmentClass updateEquipment = new EquipmentClass(equipmentName.toLowerCase(), 
//															Integer.parseInt(quantity), 
//															Float.parseFloat(rentalcost));
//		return updateEquipment;
//	}
//
//	/**
//	 * Checks for integer input.
//	 * 
//	 * @param	input					input from stream
//	 * @return	true if input is positive integer; false otherwise
//	 * @throws	NumberFormatException	if the string does not contain a parsable integer
//	 */
//	private boolean checkInteger(String input) {
//		try {
//			Integer.parseInt(input);
//		} catch(Exception e) {
//			System.out.println("Input integer only!");
//			return false;
//		}
//		
//		if(Integer.parseInt(input) < 0) {
//			System.out.println("Input non-negative integer only!");
//			return false;
//		}
//		
//		return true;
//	}
//	
//	/**
//	 * Checks for float input.
//	 * 
//	 * @param	input					input from stream
//	 * @return	true if input is positive float; false otherwise
//	 * @throws	NumberFormatException	if the string does not contain a parsable float
//	 */
//	private boolean checkFloat(String input) {
//		try {
//			Float.parseFloat(input);
//		} catch(Exception e) {
//			System.out.println("Input real numbers only!");
//			return false;
//		}
//		
//		if(Float.parseFloat(input) < 0) {
//			System.out.println("Input non-negative real numbers only!");
//			return false;
//		}
//		
//		return true;
//	}
//}
