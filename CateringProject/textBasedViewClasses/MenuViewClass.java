//package Menu;
//
//import java.util.Scanner;
//
//import Event.EventClass;
//import Login.UserClass;
//
///******************************************************************
// * This class prompts the user for different actions involving 
// * menus for an event. This menu can only be accessed through
// * the EventView, when the user is either adding or modifying an event
// * 
// * Written by Harriet Willmott 
// *******************************************************************/
//public class MenuViewClass {
//
//	public MenuViewClass() {}
//	
//	/******************************************************************
//	 * This method prompts the user to add, modify, or delete meals
//	 * from the system
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	public void displayMealOptions(UserClass currentUser)
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//		boolean done = false;
//		
//		while(!done)
//		{
//			System.out.println("\nPlease pick an option:");
//			System.out.println("1.Add a new meal to the system");
//			System.out.println("2.Modify a meal in the system");
//			System.out.println("3.Delete a meal from the system");
//			System.out.println("4.Quit");
//			option = input.nextLine();
//
//			switch (option) {
//			case "1":
//				addMeal(currentUser);
//				break;
//			case "2":
//				modifyMeal();
//				break;
//			case "3":
//				deleteMeal();
//				break;
//			case "4":
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//		
//		
//	}
//	
//	
//	/******************************************************************
//	 * This method prompts the user for the meal they wish to 
//	 * delete from the DB
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	private void deleteMeal() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		int option;
//		
//		System.out.println("Choose the meal you would like to delete:");
//		printAllMeals();
//		option = input.nextInt();
//		MealClass meal = MenuControllerClass.getInstance().getMeal(option);
//		MealControllerClass.getInstance().deleteMeal(meal);
//	}
//
//	/******************************************************************
//	 * This method prompts the user for new attributes for an 
//	 * existing meal record in the DB to be updated
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	private void modifyMeal() {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		int option;
//		String choice;
//		String tmp;
//		boolean done = false;
//		
//		System.out.println("Choose the meal you would like to modify:");
//		printAllMeals();
//		option = input.nextInt();
//		MealClass meal = MenuControllerClass.getInstance().getMeal(option);
//		
//		while(!done)
//		{
//			System.out.println(meal.getName() + ": " + meal.getMealCourse() + ", vegeterian friendly: " + meal.getVegFriendly());
//			System.out.println("What would you like to change?");
//			System.out.println("1.Name of meal");
//			System.out.println("2.Course type");
//			System.out.println("3.Vegeterian Friendly?");
//			System.out.println("4.Done");
//			choice = input.nextLine();
//			switch (choice) {
//			case "1":
//				System.out.println("Enter new name for meal:");
//				tmp = input.nextLine();
//				meal.setName(tmp);
//				break;
//			case "2":
//				System.out.println("Enter new course type for meal ('appetizer', 'main', or 'dessert'):");
//				tmp = input.nextLine();
//				meal.setMealCourse(tmp);
//				break;
//			case "3":
//				System.out.println("Enter whether or not it is vegeterian friendly ('yes', or 'no'):");
//				tmp = input.nextLine();
//				meal.setVegFriendly(tmp);
//				break;
//			case "4":
//				done = true;
//				break;
//			}
//			
//		}
//		MealControllerClass.getInstance().updateMeal(meal);
//		
//	}
//
//	/******************************************************************
//	 * This method prompts the user for the details of a new meal
//	 * record to be inserted into the DB
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	private void addMeal(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String name, course, vegFriendly;
//		
//		System.out.println("Enter a name for the meal:");
//		name = input.nextLine();
//		System.out.println("Enter the course of the meal ('appetizer', 'main', or 'dessert')");
//		course = input.nextLine();
//		System.out.println("Is this meal vegeterian friendly? ('yes', 'no'):");
//		vegFriendly = input.nextLine();
//		
//		MealClass meal = new MealClass(name, vegFriendly, course);
//		
//		MealControllerClass.getInstance().newMeal(meal, currentUser);
//		
//	}
//
//	/******************************************************************
//	 * This method takes an event and a user as input and prompts the
//	 * user for the meals he or she wishes to add to the menu
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	public void addMenu(EventClass event, UserClass currentUser)
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		System.out.println("Please choose an appetizer for the menu (select the ID of the meal):");
//		printAllMeals();
//		int app_id = input.nextInt();
//		
//		System.out.println("Please choose an main course for the menu (select the ID of the meal):");
//		printAllMeals();
//		int main_id = input.nextInt();
//		
//		System.out.println("Please choose an dessert for the menu (select the ID of the meal):");
//		printAllMeals();
//		int dess_id = input.nextInt();
//		
//		MenuClass newMenu = new MenuClass(app_id, main_id, dess_id);
//		MenuControllerClass.getInstance().addMenu(newMenu, currentUser);
//	}
//
//	/******************************************************************
//	 * This method displays the current menu record in the DB and
//	 * prompts the user for different meals to replace the current ones
//	 * then updates the record in the DB
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	public void modifyMenu(MenuClass menu, UserClass currentUser)
//	{
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		boolean done = false;
//		String option;
//		while(!done)
//		{
//			System.out.println("Choose the meal you wish to change (select the number): ");
//			System.out.println("1. Appetizer: " + menu.getAppetizer().getName());
//			System.out.println("2. Main Course: " + menu.getMainMeal().getName());
//			System.out.println("3. Dessert: " + menu.getDessert().getName());
//			System.out.println("4. Done changing menu");
//			option = input.nextLine();
//			switch (option) {
//			case "1":
//				System.out.println("Please choose an appetizer for the menu (select the ID of the meal):");
//				printAllMeals();
//				int app_id = input.nextInt();
//				MealClass app = MenuControllerClass.getInstance().getMeal(app_id);
//				menu.setAppetizer(app);
//				break;
//			case "2":
//				System.out.println("Please choose an main course for the menu (select the ID of the meal):");
//				printAllMeals();
//				int main_id = input.nextInt();
//				MealClass main = MenuControllerClass.getInstance().getMeal(main_id);
//				menu.setMainMeal(main);
//				break;
//			case "3":
//				System.out.println("Please choose an dessert for the menu (select the ID of the meal):");
//				printAllMeals();
//				int dess_id = input.nextInt();
//				MealClass dess = MenuControllerClass.getInstance().getMeal(dess_id);
//				menu.setDessert(dess);
//				break;
//			case "4":
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//		
//		MenuControllerClass.getInstance().modifyMenu(menu, currentUser);
//	}
//	
//	/******************************************************************
//	 * This method prints all the menus in the DB
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	public void printAllMenus()
//	{
//		MenuClass[] menus = MenuControllerClass.getInstance().getAllMenus();
//		for(int i = 0; i < menus.length; i++)
//		{
//			System.out.println(menus[i].getMenuId() + ": " + menus[i].getAppetizer().getName() + ", " + menus[i].getMainMeal().getName() + ", " + menus[i].getDessert().getName());
//		}
//		
//	}
//	
//	/******************************************************************
//	 * This method prints all the meals available for the Catering service
//	 * 
//	 * Written by Harriet Willmott 
//	 *******************************************************************/
//	public void printAllMeals()
//	{
////		MealClass[] meals = MenuControllerClass.getInstance().getAllMeals();
////		for(int i = 0; i < meals.length; i++)
////		{
////			System.out.println(meals[i].getID() + ". " + meals[i].getName());
////		}
//	}
//	
//}
