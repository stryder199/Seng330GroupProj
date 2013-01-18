//package Client;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//
//import Login.UserClass;
//
//public class ClientViewClass {
//
//	public ClientViewClass() {}
//
//	/******************************************************************
//	 * This method displays the various options for clients
//	 * and receives input on option to complete
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	public void displayRoot(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option, iDNum;
//		boolean done = false;
//		boolean result = false;
//
//		while (!done) {
//			System.out.println("Please pick an option:");	//Prints options
//			System.out.println("1.Add Client");
//			System.out.println("2.Delete Client");
//			System.out.println("3.Modify Client");
//			System.out.println("4.List Clients");
//			System.out.println("5.Back");
//			option = input.nextLine();
//			switch (option) {
//			case "1":	//Add a client
//				ClientClass newClient = getAddInput();
//				result = ClientControllerClass.getInstance().addClient(newClient, currentUser);
//				if(!result){
//					System.out.println("Client didn't add correctly, please try again");
//				}
//				else{
//					System.out.println("Client successfully added!");
//				}
//				break;
//			case "2":	//Remove a client
//				iDNum = getIDInput("remove");
//				if (iDNum == null)
//					break;
//				if (confirmDelete(Integer.valueOf(iDNum))){
//					result = ClientControllerClass.getInstance().deleteClient(Integer.valueOf(iDNum));
//					if(!result){
//						System.out.println("Client was not deleted correctly, please try again");
//					} else{
//						System.out.println("Client successfully removed!");
//					}	
//				}
//				break;
//			case "3":	//Modify a client
//				iDNum = getIDInput("edit");
//				if (iDNum != null){
//					getModifyInput(iDNum);
//				}
//				break;
//			case "4":	//Prints all clients
//				printAllClients();
//				break;
//			case "5":	//Returns to previous screen
//				done = true;
//				break;
//			default:	//Error message if given incorrect input
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//	}
//
//	/******************************************************************
//	 * This method prints all clients and their details to the screen
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private void printAllClients() {
//		List<ClientClass> allClients = ClientControllerClass.getInstance().getAllClients();
//		if(allClients == null){
//			System.out.println("Error getting all clients");
//		} else {
//			Iterator<ClientClass> it; 
//		
//			//Sanity Check
//			assert(allClients != null);	
//			
//			it = allClients.iterator();
//			String fullName;
//			// Prints heading
//			System.out.printf("%-10s %-25s %-25s %-25s %-25s %-25s\n",
//							"ID #:", "Name:", "Company:", "Email:", 
//							"Phone Number:", "Address");
//			while(it.hasNext()){
//				ClientClass element = it.next();
//				fullName = element.getFirstName() + " " + element.getLastName();
//				// Prints client details
//				System.out.printf("%-10d %-25s %-25s %-25s %-25s %-25s\n", 
//								element.getClientId(), fullName, element.getCompany(), 
//								element.getEmail(), element.getPhone(), element.getAddress());
//			}
//		}
//	}
//	
//	/******************************************************************
//	 * This method requests a confirmation for deleting the given
//	 * client, based off their ID Number, returns resulting boolean
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private boolean confirmDelete(int iDNum){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		ClientClass client;
//		client = ClientControllerClass.getInstance().getClient(iDNum);
//		// Prints confirmation request
//		System.out.printf("Are you sure you wish to delete %s %s from the system? (yes or no)\n", 
//				client.getFirstName(), client.getLastName());
//		if (input.nextLine().matches("yes")){ //confirms delete
//			return true;
//		}
//		return false; //cancel delete
//	}
//	
//	/******************************************************************
//	 * This method requests an ID number and checks if it is valid
//	 * and returns the resulting ID Number
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private String getIDInput(String condition){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String iDNum;
//		
//		while(true){
//			System.out.printf("Please input the ID of the client you wish to %s\n", condition);
//			System.out.println("For a list of all clients type 'list'. To go back type 'back'");			
//			System.out.print("Client ID Number: ");
//			iDNum = input.nextLine();
//			if (iDNum.matches("list")){	//Prints full client list
//				printAllClients();
//			}
//			else if (iDNum.matches("back")){	//Returns to previous screen
//				return null;
//			}
//			else if (iDNum.matches("(\\d{1,7})")) {	//Checks if number given is between 0-9999999
//				if (ClientControllerClass.getInstance().verifyClientID(iDNum)){
//					return iDNum;
//				} else {	//Error message if given invalid client ID number
//					System.out.println("Not a valid client ID, Please try again...");
//				}
//			}
//			else {	//Error message if given invalid number 
//				System.out.println("Not a valid client ID, Please try again...");
//			}
//		}		
//	}
//
//	/******************************************************************
//	 * This method gets the information of a client that the user wishes
//	 * to change, and then modifies it
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private void getModifyInput(String iDNum){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option, clientData;
//		boolean done = false, modified;
//		
//		while(!done){	
//			// Prints the available modifying options
//			System.out.println("Please pick which information you would like to modify for the client:");
//			System.out.println("1.Client Email");
//			System.out.println("2.Client Company");
//			System.out.println("3.Client Phone Number");
//			System.out.println("4.Client Address");
//			System.out.println("5.Back");	
//			option = input.nextLine();
//			modified = false;
//			switch (option){
//			case "1":	//Changes clients email
//				System.out.println("What is the clients new email address?");
//				clientData = input.nextLine();
//				modified = ClientControllerClass.getInstance().modifyClient(iDNum, "EMAIL", clientData);
//				break;
//			case "2":	//Changes clients company
//				System.out.println("What is the clients new company name?");
//				clientData = input.nextLine();
//				modified = ClientControllerClass.getInstance().modifyClient(iDNum, "COMPANY", clientData);
//				break;
//			case "3":	//Changes clients phone number
//				System.out.println("What is the clients new phone number (format 000-000-0000)?");
//				while(true){
//					clientData = input.nextLine();
//					if (!verifyInput(clientData, "number")){	//Checks correct input format
//						System.out.println("Invalid input, Please try again...");	
//					} else {
//						modified = ClientControllerClass.getInstance().modifyClient(iDNum, "PHONE", clientData);
//						break;
//					}
//				}
//				break;
//			case "4":	//Changes clients address
//				System.out.println("What is the clients new address?");
//				clientData = input.nextLine();
//				modified = ClientControllerClass.getInstance().modifyClient(iDNum, "ADDRESS", clientData);
//				break;
//			case "5":	//Goes back to previous screen
//				done = true;
//				continue;
//			default:	//Error message if given incorrect input
//				System.out.println("Wrong input, Please try again...");
//				modified = true;
//				break;
//			}
//			if(!modified){	//Checks if change has taken place, and prints result
//				System.out.println("Client's information was not updated, Please try again...");
//				modified = false;
//			} else {
//				System.out.println("Client's information was successfully updated");
//			}
//		}
//	}
//	
//	/******************************************************************
//	 * This method verifies the input format based off the given type of data
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private boolean verifyInput(String input, String type){
//		if (type.matches("name")){	//Verifies name format
//			if (input.matches("[a-zA-Z ]++")){
//				return true;
//			}
//		} 
//		if (type.matches("number")){	//Verifies phone number format
//			if (input.matches("(\\d{3})-(\\d{3})-(\\d{4})")){
//				return true;
//			}
//		}
//		if (type.matches("email")){		//Verifies email format
//			if (input.matches("(\\w*)@(\\w*).(\\w*)")){
//				return true;
//			}
//		}
//		return false;
//	}
//
//	/******************************************************************
//	 * This method gets the input required for adding a client and 
//	 * creates a new Client class with the details, which it then returns
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private ClientClass getAddInput(){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String fName = "", lName = "", email = "", company, phoneNum = "", address;
//		boolean correctInput;
//		ClientClass newClient;
//		
//		correctInput = false; //boolean for checking correct input format
//		System.out.println("Clients first name:"); //requests clients first name
//		while(!correctInput){
//			fName = input.nextLine();
//			correctInput = verifyInput(fName, "name");
//			if (!correctInput){ 	//Prints error if incorrect format
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//
//		correctInput = false; //boolean for checking correct input format
//		System.out.println("Clients last name:");	//requests clients last name
//		while(!correctInput){
//			lName = input.nextLine();
//			correctInput = verifyInput(lName, "name");
//			if(!correctInput){		//Prints error if incorrect format
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//
//		System.out.println("Clients company:"); //requests clients company name
//		company = input.nextLine();
//
//		correctInput = false;	//boolean for checking correct input format
//		System.out.println("Clients email:");	//requests clients email
//		while(!correctInput){
//			email = input.nextLine();
//			correctInput = verifyInput(email, "email");
//			if(!correctInput){		//Prints error if incorrect format
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//	
//		correctInput = false;	//boolean for checking correct input format
//		System.out.println("Clients phone number (format 000-000-0000):");	//requests clients phone number
//		while(!correctInput){
//			phoneNum = input.nextLine();
//			correctInput = verifyInput(phoneNum, "number");
//			if(!correctInput){		//Prints error if incorrect format
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//
//		System.out.println("Clients address:");		//requests clients address
//		address = input.nextLine();
//		// Creates new client with given details
//		newClient = new ClientClass(fName, lName, email, company, phoneNum, address);
//
//		return newClient;
//	}
//}
