//package Employee;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.regex.Pattern;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.sql.Date;
//
//import Event.EventClass;
//import Login.UserClass;
//
///**
// * This class prompts the user for different actions involving employees:
// * Adding a new employee to the system.
// * Deleting an employee from the system.
// * Modifying an emplyee's record in the system.
// * Listing all the employees in the system.
// * Listing the employees available on a certain date.
// * Booking off time for the temporary serving staff.
// * 
// * @author	Harriet Willmott 
// */
//public class EmployeeViewClass {
//
//	/**
//	 * Constructs a new instance of the display UI.
//	 */
//	public EmployeeViewClass() {}
//
//	/**
//	 * Display the employee menu.
//	 * 
//	 * @param	currentUser	the current user
//	 * @see		UserClass
//	 */
//	public void displayRoot(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//		boolean done = false;
//
//		while (!done) {
//			System.out.println("Please pick an option:");
//			System.out.println("1.Add Employee");
//			System.out.println("2.Delete Employee");
//			System.out.println("3.Modify Employee");
//			System.out.println("4.List Employees");
//			System.out.println("5.List Available Employees on a Certain Date");
//			System.out.println("6.Book Off Date for Serving Staff");
//			System.out.println("7.Quit");
//			option = input.nextLine();
//			switch (option) {
//			case "1":
//				addEmployee(currentUser);
//				break;
//			case "2":
//				deleteEmployee();
//				break;
//			case "3":
//				modifyEmployee();
//				break;
//			case "4":
//				//listAllEmployees();
//				break;
//			case "5":
//				listAvailableEmployees();
//				break;
//			case "6":
//				bookOffDate(currentUser);
//				break;
//			case "7":
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
//	 * Prompts the user for a date and then prints a list of employees available on 
//	 * that date.
//	 * 
//	 * @throws	ParseException	invalid date
//	 * @see		EmployeeClass
//	 * @see		EmployeeControllerClass
//	 */
//	private void listAvailableEmployees() {
//
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		DateFormat DOB = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println("Enter the date yyyy-mm-dd you wish to inquire about");
//		List<EmployeeClass> list;
//		try {
//			Date date = new java.sql.Date(DOB.parse(input.next()).getTime());
//			list = EmployeeControllerClass.getInstance().listAvailableEmployees(date);
//			
//			Iterator<EmployeeClass> it = list.iterator();
//			
//			while(it.hasNext()){
//				EmployeeClass element = it.next();
//
//				System.out.println(element.getEmployeeId() + " " + element.getFirstName() + " " + element.getLastName() + ": " + element.getPosition());
//			}
//		} catch (ParseException e) {
//			System.out.println("Error: invalid date");
//		}
//		
//	}
//	
//	/**
//	 * Prints a list of all employees.
//	 * 
//	 * @see		EmployeeClass
//	 * @see		EmployeeControllerClass
//	 */
////	public void listAllEmployees() {
////		EmployeeClass[] employeeList = EmployeeControllerClass.getInstance().getAllEmployees();
////		
////		System.out.printf("%-25s %-5s %-30s %-25s %-25s %-25s %-5s\n",
////				"Name:", "ID#:", "Address:", "Phone Number:", 
////				"Birthdate:", "Position", "Wage");
////		for(int i = 0; i < employeeList.length; i++)
////		{
////			System.out.printf("%-25s %-5d %-30s %-25s %-25s %-25s %-5f\n",
////					employeeList[i].getFirstName() + " " + employeeList[i].getLastName(), employeeList[i].getEmployeeId(), employeeList[i].getAddress()
////					, employeeList[i].getPhone(), employeeList[i].getBirthDate(), employeeList[i].getPosition(), employeeList[i].getWage());
////		}
////
////	}
//
//	/**
//	 * Prompts the user for an employee ID and a date for which they would like to 
//	 * book off time. The employee must be one of the temporary serving staff.
//	 * 
//	 * @param	currentUser		current user
//	 * @throws	ParseException	invalid date
//	 * @see		EmployeeClass
//	 * @see		EmployeeControllerClass
//	 * @see		EmployeeUnavailDate
//	 */
//	private void bookOffDate(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		DateFormat DOB = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println("Enter the employee id of the employee for which you would like to book off time:");
//		int employeeID = input.nextInt();
//		EmployeeClass employee = EmployeeControllerClass.getInstance().getEmployee(employeeID);
//		if(employee == null) return;
//		if(!employee.getPosition().equals("server"))
//		{
//			System.out.println("Error: employee must be a server to book off time");
//			return;
//		}
//		
//
//		System.out.println("Enter the date (yyyy-mm-dd) the employee wishes to book off:");
//		try {
//			Date date = new java.sql.Date(DOB.parse(input.next()).getTime());
//			EmployeeUnavailDate empDate = new EmployeeUnavailDate(employee.getEmployeeId(), date);
//			EmployeeControllerClass.getInstance().addUnavailableDate(empDate, currentUser);
//		} catch (ParseException e) {
//			System.out.println("Error: invalid date");
//		}
//		
//		
//	}
//
//	/**
//	 * Prompts the user for the details of a new employee record to be inserted 
//	 * into the database.
//	 * 
//	 * @param	currentUser		current user
//	 * @see		EmployeeClass
//	 * @see		EmployeeControllerClass
//	 */
//	public void addEmployee(UserClass currentUser)
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String fName, lName, phone, address, birthDate, position;
//		double wage;
//		boolean valid;
//		
//		System.out.println("Please enter the new employee's first name:");
//		fName = input.nextLine();
//		System.out.println("Please enter the new employee's last name:");
//		lName = input.nextLine();
//		
//		/* some input validation */
//		System.out.println("Please enter the new employee's phone number (xxx-xxx-xxxx):");
//		phone = input.nextLine();
//		valid = Pattern.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}", phone);
//		if(!valid)
//		{
//			System.out.println("Invalid phone number");
//			return;
//		}
//		
//		System.out.println("Please enter the new employee's address:");
//		address = input.nextLine();
//		
//		/* some input validation */
//		System.out.println("Please enter the new employee's birth date (yyyy-mm-dd):");
//		birthDate = input.nextLine();
//		valid = Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", birthDate);
//		if(!valid)
//		{
//			System.out.println("Invalid date format");
//			return;
//		}
//		
//		System.out.println("Please enter the new employee's position");
//		position = input.nextLine();
//		
//		/* some input validation */
//		System.out.println("Please enter the new employee's wage:");
//		wage = input.nextFloat();
//		valid = (wage > 0);
//		if(!valid)
//		{
//			System.out.println("Invalid wage. Must be greater than 0");
//			return;
//		}
//		EmployeeClass newEmployee = new EmployeeClass(fName, lName, phone, address, birthDate, position, wage);
//		EmployeeControllerClass.getInstance().addEmployee(newEmployee, currentUser);
//	}
//	
//	
//	/**
//	 * Prompts the user for the details an employee which the user wished to
//	 * delete from the system.
//	 * 
//	 * @see		EmployeeControllerClass
//	 */
//	public void deleteEmployee()
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		float in;
//		
//		System.out.println("Enter the employee ID of the employee you wish to remove:");
//		in = input.nextFloat();
//		
//		EmployeeControllerClass.getInstance().deleteEmployee(in);
//	}
//	
//	/**
//	 * Prompts the user for the details of an employee record that the user
//	 * wishes to modify in the system and then updates the record in the 
//	 * database.
//	 * 
//	 * @see		EmployeeClass
//	 * @see		EmployeeControllerClass
//	 */
//	public void modifyEmployee()
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		int employeeID;
//		EmployeeClass employee;
//		boolean cont = true;
//		boolean valid;
//		
//		System.out.println("Enter the employee ID of the employee you wish to modify:");
//		employeeID = input.nextInt();
//		
//		employee = EmployeeControllerClass.getInstance().getEmployee(employeeID);
//		if(employee == null) return;
//		
//		while(cont)
//		{
//			System.out.println(employee.getFirstName() + " " + employee.getLastName());
//			System.out.println("address: " + employee.getAddress());
//			System.out.println("phone: " + employee.getPhone());
//			System.out.println("birthdate: " + employee.getBirthDate());
//			System.out.println("position: " + employee.getPosition());
//			System.out.println("wage: " + employee.getWage());
//			
//			System.out.println("\nChoose an option:");
//			System.out.println("1. Change first name");
//			System.out.println("2. Change last name");
//			System.out.println("3. Change address");
//			System.out.println("4. Change phone number");
//			System.out.println("5. Change birth date");
//			System.out.println("6. Change position");
//			System.out.println("7. Change wage");
//			System.out.println("8. Done");
//			
//			String choice = input.nextLine();
//			String temp;
//			
//			switch(choice){
//				case "1":
//					System.out.println("Enter a new first name: ");
//					temp = input.nextLine();
//					employee.setFirstName(temp);
//					break;
//				case "2":
//					System.out.println("Enter a new last name: ");
//					temp = input.nextLine();
//					employee.setLastName(temp);
//					break;
//				case "3":
//					System.out.println("Enter a new address: ");
//					temp = input.nextLine();
//					employee.setAddress(temp);
//					break;
//				case "4":
//					System.out.println("Enter a new phone number (xxx-xxx-xxxx): ");
//					temp = input.nextLine();
//					/* some input validation */
//					valid = Pattern.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}", temp);
//					if(!valid)
//					{
//						System.out.println("Invalid phone number");
//						break;
//					}
//					employee.setPhone(temp);
//					break;
//				case "5":
//					System.out.println("Enter a new birth date (yyyy-mm-dd): ");
//					temp = input.nextLine();
//					/* some input validation */
//					valid = Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", temp);
//					if(!valid)
//					{
//						System.out.println("Invalid date format");
//						break;
//					}
//					employee.setBirthDate(temp);
//					break;
//				case "6":
//					System.out.println("Enter a new position: ");
//					temp = input.nextLine();
//					employee.setPosition(temp);
//					break;
//				case "7":
//					System.out.println("Enter a new wage: ");
//					float tempf = input.nextFloat();
//					/* some input validation */
//					valid = (tempf > 0);
//					if(!valid)
//					{
//						System.out.println("Invalid wage. Must be greater than 0");
//						return;
//					}
//					employee.setWage(tempf);
//					break;
//				case "8":
//					cont = false;
//					break;
//				default:
//					System.out.println("Wrong input, Please try again...");
//					break;
//			}
//			
//		}
//		EmployeeControllerClass.getInstance().modifyEmployee(employee);
//	}
//}
