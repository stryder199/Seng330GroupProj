//package Event;
//
//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.InputMismatchException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//
//import Client.ClientClass;
//import Client.ClientControllerClass;
//import Employee.EmployeeAvailabilityDBHandler;
//import Employee.EmployeeBookedDate;
//import Employee.EmployeeClass;
//import Employee.EmployeeControllerClass;
//import Employee.EmployeeUnavailDate;
//import Equipment.EquipmentClass;
//import Equipment.EquipmentControllerClass;
//import Login.UserClass;
//import Menu.MealClass;
//import Menu.MealControllerClass;
//import Menu.MenuClass;
//import Menu.MenuControllerClass;
//
//public class EventViewClass {
//
//	public EventViewClass() {}
//
//	public void displayRoot(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//		boolean done = false;
//		boolean result = false;
//
//		while (!done) {
//			System.out.println("\nUpcoming Events");
//			printAllEventsBasic();
//			
//			System.out.println("\nPlease pick an option:");
//			System.out.println("1.Add Event");
//			System.out.println("2.Delete Event");
//			System.out.println("3.Modify Event");
//			System.out.println("4.List Events");
//			System.out.println("5.Confirm Events");
//			System.out.println("6.Book employees for event");
//			System.out.println("7.Print Invoices");
//			System.out.println("8.Back");
//			option = input.nextLine();
//			switch (option) {
//			case "1":
//				EventClass newEvent = getAddInput(currentUser);
//				if(newEvent == null)
//				{
//					System.out.println("Aborting user creation");
//					break;
//				}
//				
//				//Add the event
//				result = EventControllerClass.getInstance().addEvent(newEvent, currentUser);
//				if(result == false)
//				{
//					System.out.println("Error adding event");
//					break;
//				}
//				
//				System.out.println("Would you like to add Employees to this event now? (y/n)");
//				boolean contResult = printContinue();
//				if(contResult == true)
//					bookEmployees(newEvent, currentUser);
//				
//				break;
//			case "2":
//				int event_id = getRemoveInput();
//				if(event_id == -1){
//					break;
//				}
//				
//				result = EventControllerClass.getInstance().deleteEvent(event_id);
//				break;
//			case "3":
//				EventClass modifiedEvent = getModifiedInput(currentUser);
//				if(modifiedEvent == null)
//					break;
//				
//				EventControllerClass.getInstance().modifyEvent(modifiedEvent);
//				break;
//			case "4":
//				printAllEventsDetailed();
//				break;
//			case "5":
//				int event_id1 = getConfirmEventInput(currentUser);
//				if(event_id1 == -1)
//					break;
//				
//				EventControllerClass.getInstance().confirmEvent(event_id1);
//				break;
//			case "6":
//				EventClass eventChoice = getEventChoice();
//				
//				bookEmployees(eventChoice, currentUser);
//				break;
//			case "7":
//				EventClass eventChoice1 = getEventChoice();
//				
//				printInvoice(eventChoice1);
//				break;
//			case "8":
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//	}
//	
//	private int getConfirmEventInput(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		int choice = 0;
//		boolean done = false;
//		
//		List<EventClass> pendingEvents = printAllPendingEvents(currentUser);
//		if(pendingEvents == null){
//			return -1;
//		}
//		
//		while (!done) {
//			try {
//				System.out.println("Event choice: (type \"-1\" to quit)");
//				choice = input.nextInt();
//				if (choice == -1) {
//					return -1;
//				}
//				
//				if(choice <= 0 || choice > pendingEvents.size()){
//					System.out.println("Invalid input, please try again...");
//				}
//				else{
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		
//		return pendingEvents.get(choice-1).getEventId();
//	}
//
//	private int getRemoveInput() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String result;
//		int choice = 0;
//		boolean done = false;
//		
//		List<EventClass> allEvents = printAllEventsDetailed();
//		while (!done) {
//			System.out.println("Please pick a event to delete from the list: (type \"back\" to quit)");
//			try {
//				choice = input.nextInt();
//				if (choice == -1) {
//					return -1;
//				}
//				
//				if(choice <= 0 || choice > allEvents.size()){
//					System.out.println("Invalid input, please try again...");
//				}
//				else{
//					done = true;
//				}
//				
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, please try again...");
//			}
//		}
//		done = false;
//		
//		while (!done) {
//			System.out.println("Are you sure that you want to delete " + allEvents.get(choice - 1).getName() + "? (y\n)");
//			result = input.nextLine();
//			
//			if(result.matches("y")){
//				done = true;
//			}
//			else if(result.matches("n")){
//				System.out.println("Aborting deletion");
//				return -1;
//			}
//			else{
//				System.out.println("Wrong input please pick (y/n)!");
//			}
//		}
//		return allEvents.get(choice-1).getEventId();
//	}
//
//	private EventClass getModifiedInput( UserClass currentUser ) {
//		Scanner input = new Scanner(System.in);
//		boolean doneCategory = false, done = false;
//		int option, choice = 0;
//		EventClass focusEvent;
//		
//		System.out.println("Please pick an event to modify:");
//		List<EventClass> allEvents = printAllEventsDetailed();
//		option = input.nextInt();
//		
//		focusEvent = allEvents.get(option-1);
//		
//		while (!doneCategory) {
//			System.out.println("Please pick an option:");
//			System.out.println("1.Modify name");
//			System.out.println("2.Modify date of event");
//			System.out.println("3.Modify number of guests");
//			System.out.println("4.Modify location");
//			System.out.println("5.Modify main menu");
//			System.out.println("6.Modify vegetarian menu");
//			System.out.println("7.Modify client");
//			System.out.println("8.Modify event length");
//			System.out.println("9.Back");
//			option = input.nextInt();
//			
//			//Modify Name
//			if(option == 1){
//				System.out.println("Please enter the event name\\description: (type \"back\" to quit)");
//				String name = input.nextLine();
//				if(name.matches("back") == true)
//				{
//					return null;
//				}
//				
//				focusEvent.setName(name);
//				doneCategory = true;
//			}
//			//Modify Date
//			else if(option == 2){
//				DateFormat dateFormat = new SimpleDateFormat("dd\\MM\\yyyy");
//				Date myDate = null;
//				
//				while (!done) {
//					System.out.println("Please enter the date(dd\\mm\\yyyy): (type \"back\" to quit)");
//					String date = input.nextLine();
//					if (date.matches("back") == true)
//					{
//						return null;
//					}
//					
//					try {
//						myDate = new Date(dateFormat.parse(date).getTime());
//						done = true;
//					} catch (ParseException e) {
//						System.out.println("Wrong Date format, please try again");
//					}
//				}
//				done = false;
//				
//				focusEvent.setDateOfEvent(myDate);
//				doneCategory = true;
//			}
//			//Modify number of guests
//			else if(option == 3){
//				int numGuests = 0;
//				
//				while (!done) {
//					try {
//						System.out.println("Please enter the total number of guests: (type \"-1\" to quit)");
//						numGuests = input.nextInt();
//						if(numGuests == -1)
//						{
//							return null;
//						}
//						
//						if(numGuests < 0){
//							System.out.println("Invalid input, Please try again...");
//						}
//						else{
//							done = true;
//						}
//					} catch (InputMismatchException e) {
//						System.out.println("Invalid input, Please try again...");
//					}
//				}
//				done = false;
//				
//				focusEvent.setNumGuests(numGuests);
//				doneCategory = true;
//			}
//			//Modify location
//			else if(option == 4){
//				System.out.println("Please enter the location: (type \"back\" to quit)");
//				String location = input.nextLine();
//				if(location.matches("back") == true)
//				{
//					return null;
//				}
//				
//				focusEvent.setLocation(location);
//				doneCategory = true;
//			}
////			//Modify main menu
////			else if(option == 5){
////				MenuClass mainMenu = createMenu(input, false, currentUser);
////				if(mainMenu == null){
////					return null;
////				}
////				
////				focusEvent.setMainMenu(mainMenu);
////				doneCategory = true;
////			}
////			//Modify veg menu
////			else if(option == 6){
////				MenuClass vegMenu = createMenu(input, true, currentUser);
////				if(vegMenu == null)
////					return null;
//				
////				focusEvent.setVegMenu(vegMenu);
////				doneCategory = true;
////			}
//			//Modify client
//			else if(option == 7){
//				List<ClientClass> allClients = printAllClients();
//				ClientClass client;
//				
//				while (!done) {
//					try {
//						System.out.println("Client choice: (type \"-1\" to quit)");
//						choice = input.nextInt();
//						if (choice == -1) {
//							return null;
//						}
//						
//						if(choice <= 0 || choice > allClients.size()){
//							System.out.println("Invalid input, please try again...");
//						}
//						else{
//							done = true;
//						}
//					} catch (InputMismatchException e) {
//						System.out.println("Invalid input, Please try again...");
//					}
//				}
//				done = false;
//				
//				client = allClients.get(choice-1);
//				
//				focusEvent.setClient(client);
//				doneCategory = true;
//			}
//			else if(option == 8){
//				float totalLength = 0;
//				
//				while (!done) {
//					try {
//						System.out.println("Please enter new length of event: (type \"-1\" to quit)");
//						totalLength = input.nextFloat();
//						if(totalLength == -1)
//						{
//							return null;
//						}
//						
//						if(totalLength < 0){
//							System.out.println("Invalid input, Please try again...");
//						}
//						else{
//							done = true;
//						}
//					} catch (InputMismatchException e) {
//						System.out.println("Invalid input, Please try again...");
//					}
//				}
//				done = false;
//				
//				focusEvent.setTotalLengthOfEvent(totalLength);
//				doneCategory = true;
//			}
//			//back
//			else if(option == 9){
//				doneCategory = true;
//			}
//			else{
//				System.out.println("Invalid input, please try again...");
//			}
//		}
//		return focusEvent;
//	}
//
//	@SuppressWarnings("resource")
//	private EventClass getAddInput( UserClass currentUser) {
//		Scanner input = new Scanner(System.in);
//		String name = "", date = "", location = "";
//		int numGuests = 0, choice = 0;
//		float lengthOfEvent = 0.0f;
//		ClientClass client;
//		DateFormat dateFormat = new SimpleDateFormat("dd\\MM\\yyyy");
//		Date myDate = null;
//		MenuClass newMainMenu, newVegMenu;
//		EventClass newEvent = null;
//		boolean done = false;
//		
//		System.out.println("Enter information for the new event");
//		
//		System.out.println("Please pick a client from the list:");
//		List<ClientClass> allClients = printAllClients();
//		
//		while (!done) {
//			try {
//				System.out.println("Client choice: (type \"-1\" to quit)");
//				choice = input.nextInt();
//				if (choice == -1) {
//					return null;
//				}
//				
//				if(choice <= 0 || choice > allClients.size()){
//					System.out.println("Invalid input, please try again...");
//				}
//				else{
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		done = false;
//		
//		client = allClients.get(choice-1);
//		
//		//Fixes a bug, no idea why....
//		input.nextLine();
//		
//		System.out.println("Please enter the event name\\description: (type \"back\" to quit)");
//		name = input.nextLine();
//		if(name.matches("back") == true)
//		{
//			return null;
//		}
//		
//		System.out.println("Please enter the location: (type \"back\" to quit)");
//		location = input.nextLine();
//		if(location.matches("back") == true)
//		{
//			return null;
//		}
//		
//		while (!done) {
//			System.out.println("Please enter the date(dd\\mm\\yyyy): (type \"back\" to quit)");
//			date = input.nextLine();
//			if (date.matches("back") == true)
//			{
//				return null;
//			}
//			
//			try {
//				myDate = new Date(dateFormat.parse(date).getTime());
//				done = true;
//			} catch (ParseException e) {
//				System.out.println("Wrong Date format, please try again");
//			}
//		}
//		done = false;
//		
//		while (!done) {
//			try {
//				System.out.println("Please enter the total number of guests: (type \"-1\" to quit)");
//				numGuests = input.nextInt();
//				if(numGuests == -1)
//				{
//					return null;
//				}
//				
//				if(numGuests < 0){
//					System.out.println("Invalid input, Please try again...");
//				}
//				else{
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		done = false;
//		
//		while (!done) {
//			try {
//				System.out.println("Length of Event(hours): (type \"-1\" to quit)");
//				lengthOfEvent = input.nextFloat();
//				if(numGuests == -1)
//				{
//					return null;
//				}
//				
//				if(numGuests <= 0){
//					System.out.println("Invalid input, Please try again...");
//				}
//				else{
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		done = false;
//		
////		newMainMenu = createMenu(input, false, currentUser);
////		if(newMainMenu == null){
////			return null;
////		}
////		
////		newVegMenu = createMenu(input, true, currentUser);
////		if(newVegMenu == null){
////			return null;
////		}
//		
////		newEvent = new EventClass(newMainMenu.getMenuId(), newVegMenu.getMenuId(), client.getClientId(), name, myDate, numGuests, location, lengthOfEvent);
//		
//		return newEvent;
//	}
//
//	@SuppressWarnings("resource")
//	private EventClass getEventChoice(){
//		boolean doneInput = false;
//		Scanner input = new Scanner(System.in);
//		int choice = -1;
//		List<EventClass> allEvents = printAllEventsDetailed();
//		EventClass chosenEvent;
//	
//		
//		while (!doneInput) {
//			try {
//				System.out.println("Event choice: (type \"-1\" to quit)");
//				choice = input.nextInt();
//				if (choice == -1) {
//					return null;
//				}
//				
//				if(choice <= 0 || choice > allEvents.size()){
//					System.out.println("Invalid input, please try again...");
//				}
//				else{
//					doneInput = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		
//		chosenEvent = allEvents.get(choice-1);
//		
//		return chosenEvent;
//	}
//
////	private MenuClass createMenu(Scanner input, boolean vegFriendly, UserClass currentUser){
////		MealClass[] allMeals = null;
////		MealClass opt1, opt2, opt3;
////		int choice = 0;
////		boolean done = false;
////		
////		//Your creating a main menu
////		if(!vegFriendly){
////			System.out.println("Please pick 3 main meals from the list:");
////		}
////		//Your creating a veg menu
////		else{
////			System.out.println("Please pick 3 vegetarian meals from the list:");
////		}
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("appetizer", vegFriendly);
////				System.out.println("Appetizer option: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt1 = allMeals[choice-1];
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("main", vegFriendly);
////				System.out.println("Main Course option: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt2 = allMeals[choice-1];
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("dessert", vegFriendly);
////				System.out.println("Dessert option 3: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt3 = allMeals[choice-1];
////		
////		MenuClass newMenu = new MenuClass(opt1, opt2, opt3);
////		MenuControllerClass.getInstance().addMenu(newMenu, currentUser);
////		
////		return newMenu;
////	}
//
//	private void bookEmployees(EventClass event, UserClass currentUser) {
//		List<EmployeeClass> availEmployees, availCooks, availServers;
//		boolean done = false, result = false;
//		boolean keepGoing = false;
//		
//		availEmployees = EmployeeControllerClass.getInstance().listAvailableEmployees(event.getDateOfEvent());
//				
//		availCooks = splitIntoPositions("cook", availEmployees);
//		
//		while (!done) {
//			try {
//				result = bookAnEmployee("cook", availCooks, event, currentUser);
//				if(!result)
//					return;
//				System.out.println("Would you like to add another cook? (y/n)");
//				keepGoing = printContinue();
//				if(keepGoing == false){
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		done = false;
//		
//		availServers = splitIntoPositions("server", availEmployees);
//		
//		while (!done) {
//			try {
//				result = bookAnEmployee("server", availServers, event, currentUser);
//				if(!result)
//					return;
//				System.out.println("Would you like to add another server? (y/n)");
//				keepGoing = printContinue();
//				if(keepGoing == false){
//					done = true;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		done = false;
//		
//	}
//	
//	@SuppressWarnings("resource")
//	private boolean bookAnEmployee(String position, List<EmployeeClass> availEmployees, EventClass event, UserClass currentUser){
//		boolean done = false;
//		Scanner input = new Scanner(System.in);
//		int choice = -1;
//		
//		System.out.println("Please pick a " + position + " from the list:");
//		printAllEmployees(availEmployees, position);
//		
//		while (!done) {
//			try {
//				System.out.println("Employee choice: (type \"-1\" to quit)");
//				choice = input.nextInt();
//				if (choice == -1) {
//					return false;
//				}
//				
//				if(choice <= 0 || choice > availEmployees.size()){
//					System.out.println("Invalid input, please try again...");
//				}
//				else{
//					done = true;
//				}
//	
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again...");
//			}
//		}
//		
//		EmployeeClass focusEmployee = availEmployees.get(choice -1);
//		EmployeeControllerClass.getInstance().addBookedDate(new EmployeeBookedDate(focusEmployee.getEmployeeId(), event.getEventId()), currentUser);		
//		
//		availEmployees.remove(choice-1);
//		
//		return true;
//	}
//
//	private List<EmployeeClass> splitIntoPositions(String position, List<EmployeeClass> availEmployees) {
//		
//		Iterator<EmployeeClass> it = availEmployees.iterator();
//		List<EmployeeClass> allOfPosition = new ArrayList<EmployeeClass>();		
//		
//		while(it.hasNext()){
//			EmployeeClass element = it.next();
//			
//			if(element.getPosition().matches(position) == true){
//				allOfPosition.add(element);
//			}
//		}
//		
//		return allOfPosition;
//	}
//
//	private void printInvoice(EventClass event) {
//		System.out.println("\n\nInvoice for " + event.getName() + " on " + event.getDateOfEvent().toString());
//		
//		System.out.printf("%-25s %-10s\n", "Booking Fee", "100");
//		int bookingFee = 100;
//		
//		int numGuests = event.getNumGuests();
//		String eventSize;
//		int sizeCost;
//		if(numGuests < 100){
//			eventSize = "small";
//			sizeCost = 1000;
//		}
//		else if(numGuests < 250){
//			eventSize = "medium";
//			sizeCost = 3000;
//		}
//		else{
//			eventSize = "large";
//			sizeCost = 10000;
//		}
//		System.out.printf("%-25s %-10d\n", eventSize, sizeCost);
//		
//		float employeeCost = costOfEmployees(event);
//		System.out.printf("%-25s %-10f\n", "Labour Cost", employeeCost);
//		
//		float equipmentCost = costOfEquipment(event);
//		System.out.printf("%-25s %-10f\n", "Equipment Rental Fee", equipmentCost);
//		
//		float subTotal = bookingFee + sizeCost + employeeCost + equipmentCost;
//		System.out.printf("%-25s %-10f\n", "SubTotal", subTotal);
//		
//		float tax = subTotal * 0.12f;
//		System.out.printf("%-25s %-10f\n", "Tax", tax);
//		
//		float grandTotal = subTotal + tax;
//		System.out.printf("%-25s %-10f\n", "GrandTotal", grandTotal);
//	}
//	
//	private float costOfEmployees(EventClass event){
//		float totalCost = 0;		
//		
//		List<Integer> allPayableEmployeesByID = event.getScheduledEmployeesId();
//		
//		Iterator<Integer> it = allPayableEmployeesByID.iterator();
//		
//		while(it.hasNext()){
//			Integer element = it.next();
//			
//			EmployeeClass employee = EmployeeControllerClass.getInstance().getEmployee(element.intValue());
//			
//			totalCost += employee.getWage()*event.getLengthByHour();
//		}
//		
//		return totalCost;
//	}
//	
//	private float costOfEquipment(EventClass event){
//		float totalCost = 0;		
//		
//		List<Integer> allRentedEquipmentByID = event.getRentedEquipmentId();
//		
//		Iterator<Integer> it = allRentedEquipmentByID.iterator();
//		
//		while(it.hasNext()){
//			Integer element = it.next();
//			
//			EquipmentClass equip = EquipmentControllerClass.getInstance().getEquipment(element.intValue());
//			
//			totalCost += equip.getRentalCost()*event.getLengthByHour();
//		}
//		
//		return totalCost;
//	}
//	
//	//On y/n input return true or false
//	@SuppressWarnings("resource")
//	private boolean printContinue(){
//		boolean done = false, result = false;
//		String choice = null;
//		Scanner input = new Scanner(System.in);
//		
//		while (!done) {
//			try {
//				choice = input.nextLine();
//				if(choice.matches("y") == true)
//				{
//					result = true;
//					done = true;
//				}
//				else if(choice.matches("n") == true){
//					result = false;
//					done = true;
//				}
//				else{
//					System.out.println("Invalid input, Please try again... Please type 'y' or 'n'");
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input, Please try again... Please type 'y' or 'n'");
//			}
//		}
//		
//		return result;
//	}
//
//	private List<EventClass> printAllEventsDetailed()
//	{
//		List<EventClass> allEvents = EventControllerClass.getInstance().getAllEvents();
//		int count = 1;
//		String status = "";
//		
//		
//		if(allEvents == null){
//			System.out.println("Error getting all events");
//		} else {
//			Iterator<EventClass> it; 
//		
//			//Sanity Check
//			assert(allEvents != null);	
//			
//			it = allEvents.iterator();
//		
//			//header
//			System.out.printf("%-5s %-25s %-25s %-25s %-25s\n",
//					"#:", "Name:", "Date:", "# Guests:", 
//					"Location:");
//			
//			while(it.hasNext()){
//				EventClass element = it.next();
//				
//				if(element.getStatus().matches("pending")){
//					status = "(PENDING)";
//				}
//				
//				// Prints client details
//				System.out.printf("%-5d %-25s %-25s %-25s %-25s\n", 
//								count, element.getName() + status, element.getDateOfEvent().toString(), 
//								element.getNumGuests(), element.getLocation());
//				count++;
//			}
//		}
//		return allEvents;
//	}
//	
//	private void printAllEventsBasic()
//	{
//		List<EventClass> allEvents = EventControllerClass.getInstance().getAllEvents();
//		
//		if(allEvents == null){
//			System.out.println("Error getting all events");
//		} else {
//			Iterator<EventClass> it; 
//		
//			//Sanity Check
//			assert(allEvents != null);	
//			
//			it = allEvents.iterator();
//		
//			//header
//			System.out.printf("%-25s %-25s\n",
//					"Name:", "Date:");
//			
//			while(it.hasNext()){
//				EventClass element = it.next();
//			
//				// Prints client details
//				System.out.printf("%-25s %-25s\n", 
//								element.getName(), element.getDateOfEvent().toString());
//			}
//		}
//	}
//	
//	public List<EventClass> printAllPendingEvents(UserClass currentUser)
//	{
//		List<EventClass> allPendingEvents = EventControllerClass.getInstance().getPendingEvents(currentUser);
//		int counter = 1;
//		
//		if(allPendingEvents == null){
//			System.out.println("Error getting all events");
//			return null;
//		} 
//		
//		if(allPendingEvents.size() == 0){
//			System.out.println("No Pending Events!!");
//			return null;
//		}
//		else {
//			Iterator<EventClass> it; 
//		
//			//Sanity Check
//			assert(allPendingEvents != null);	
//			
//			it = allPendingEvents.iterator();
//		
//			//header
//			System.out.printf("%-5s %-25s %-25s\n",
//								"#:","Name:", "Date:");
//			
//			while(it.hasNext()){
//				EventClass element = it.next();
//			
//				// Prints client details
//				System.out.printf("%-5d %-25s %-25s\n", 
//								counter ,element.getName(), element.getDateOfEvent().toString());
//				
//				counter++;
//			}
//		}
//		return allPendingEvents;
//	}
//	
////	private MenuClass createMenu(Scanner input, boolean vegFriendly, UserClass currentUser){
////		MealClass[] allMeals = null;
////		MealClass opt1, opt2, opt3;
////		int choice = 0;
////		boolean done = false;
////		
////		//Your creating a main menu
////		if(!vegFriendly){
////			System.out.println("Please pick 3 main meals from the list:");
////		}
////		//Your creating a veg menu
////		else{
////			System.out.println("Please pick 3 vegetarian meals from the list:");
////		}
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("appetizer", vegFriendly);
////				System.out.println("Appetizer option: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt1 = allMeals[choice-1];
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("main", vegFriendly);
////				System.out.println("Main Course option: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt2 = allMeals[choice-1];
////		
////		while (!done) {
////			try {
////				allMeals = printAllSpecificMeals("dessert", vegFriendly);
////				System.out.println("Dessert option 3: (type \"-1\" to quit)");
////				choice = input.nextInt();
////				if(choice == -1){
////					return null;
////				}
////				
////				if(choice <= 0 || choice > allMeals.length){
////					System.out.println("Invalid input, Please try again...");
////				}
////				else{
////					done = true;
////				}
////			} catch (InputMismatchException e) {
////				System.out.println("Invalid input, Please try again...");
////			}
////		}
////		done = false;
////		opt3 = allMeals[choice-1];
////		
////		MenuClass newMenu = new MenuClass(opt1, opt2, opt3);
////		MenuControllerClass.getInstance().addMenu(newMenu, currentUser);
////		
////		return newMenu;
////	}
//
//	/******************************************************************
//	 * This method prints all clients and their details to the screen
//	 * 
//	 * Written by Cody Plante
//	 *******************************************************************/
//	private List<ClientClass> printAllClients() {
//		List<ClientClass> allClients = ClientControllerClass.getInstance().getAllClients();
//		int count = 1;
//		
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
//			System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s\n",
//							"#:", "Name:", "Company:", "Email:", 
//							"Phone Number:", "Address");
//			while(it.hasNext()){
//				ClientClass element = it.next();
//				fullName = element.getFirstName() + " " + element.getLastName();
//				// Prints client details
//				System.out.printf("%-5d %-25s %-25s %-25s %-25s %-25s\n", 
//								count, fullName, element.getCompany(), 
//								element.getEmail(), element.getPhone(), element.getAddress());
//				
//				count++;
//			}
//		}
//		
//		return allClients;
//	}
//
//	/******************************************************************
//	 * This method prints all meal desc's
//	 * 
//	 * Written by Mark Lessard
//	 *******************************************************************/
////	private MealClass[] printAllSpecificMeals(String course, boolean vegFriendly) {
////		//Get the list of all meals
////		MealClass[] allMeals = MealControllerClass.getInstance().getSpecificMeals(vegFriendly, course);
////
////		if(allMeals == null){
////			System.out.println("Error getting all meals");
////		} 
////		else{	
////
////			for(int i=0; i < allMeals.length; i++){
////				// Prints Menu details
////				System.out.println(i+1 + ". " + allMeals[i].getName());
////			}
////		}
////		return allMeals;
////	}
//
//	private int printAllEmployees(List<EmployeeClass> allEmployees, String position){
//		int count = 1;
//		
//		if(allEmployees == null){
//			System.out.println("Error getting all meals");
//		} 
//		else{	
//			System.out.printf("%-5s %-25s\n", "#:", "Name:");
//			
//			Iterator<EmployeeClass> it = allEmployees.iterator();
//			
//			while(it.hasNext()){
//				EmployeeClass element = it.next();
//				// Prints Menu details
//				if(element.getPosition().matches(position) == true){
//					System.out.printf("%-5s %-25s\n",
//										count ,element.getFirstName() + " " + element.getLastName());
//					count++;
//				}
//			}
//		}
//		
//		return count-1;
//	}
//	
//}
