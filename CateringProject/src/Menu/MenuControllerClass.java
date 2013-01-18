package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import Login.UserClass;

/**
 * This class is used for the menu controller.
 * 
 * @author	Harriet Willmott
 */
public class MenuControllerClass {

	private static MenuControllerClass controllerSinglton = null;
	private MenuDBHandler menuDB;
	
	/**
	 * Constructs a new instance of menu controller.
	 */
	public MenuControllerClass() {
		menuDB = new MenuDBHandler();
	}

	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initialises the object and
	 * returns it, if it has been initialised then it returns the already initialised 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static MenuControllerClass getInstance()
	{
		if(controllerSinglton == null){
			MenuControllerClass instance = new MenuControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}

	/**
	 * Gets the menu from the database.
	 * 
	 * @param	menu_id	requested menu
	 * @return	requested menu
	 * @throws	SQLException	Error querying menu
	 * @see		MenuDBHandler
	 */
	public MenuClass getMenu(int menu_id) {
		ResultSet result = menuDB.queryMenu(menu_id);
		MenuClass menu = null;
		try {
			if(!result.next()) return null;
			int meal_id1 = result.getInt("meal_id1");
			int meal_id2 = result.getInt("meal_id2");
			int meal_id3 = result.getInt("meal_id3");;
			menu = new MenuClass(meal_id1, meal_id2, meal_id3, menu_id);
		} catch (SQLException e) {
			System.out.println("Error finding meal in DB. Meal ID: " + menu_id);
			e.printStackTrace();
		}
		
		return menu;
	}

	/**
	 * Adds a menu record in the DB with the attributes of the given menu.
	 * 
	 * @param	menu		new menu
	 * @param	currentUser	current user
	 * @see		MenuClass
	 * @see		MenuDBHandler
	 * @see		UserClass
	 */
	public void addMenu(MenuClass menu, UserClass currentUser)
	{
		menuDB.addMenu(menu, currentUser);
		
		menu.setMenuId(getMenuID(menu));
	}
	
	/**
	 * Gets all meals from the database.
	 * 
	 * @return	list of all meals
	 * @see		MealControllerClass
	 */
	public List<MealClass> getAllMeals() {
		return MealControllerClass.getInstance().getAllMeals();
	}
	
	/**
	 * Gets meal based on meal's ID.
	 * 
	 * @param	meal_id		requested meal's ID
	 * @return	requested meal
	 * @see		MealControllerClass
	 */
	public MealClass getMeal(int meal_id)
	{
		return MealControllerClass.getInstance().getMeal(meal_id);
	}

	/**
	 * Gets all menus from database.
	 * 
	 * @return	array of all menus
	 * @throws	SQLException	Error querying menu
	 * @see		MenuClass
	 * @see		MenuDBHandler
	 */
	public MenuClass[] getAllMenus() {
		ResultSet result = menuDB.queryMenus();
		
		Vector<MenuClass> list = new Vector<MenuClass>();
		
		try {
			while(result.next())
			{
				int menu_id = result.getInt("menu_id");
				int meal_id1 = result.getInt("meal_id1");
				int meal_id2 = result.getInt("meal_id2");
				int meal_id3 = result.getInt("meal_id3");
				
				MenuClass menu = new MenuClass(meal_id1, meal_id2, meal_id3, menu_id);
				list.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		MenuClass[] menuList = new MenuClass[list.size()];
		menuList = list.toArray(menuList);
		return menuList;	
	}

	/**
	 * Modifies the menu.
	 * 
	 * @param	menu		menu to be modified
	 * @param	currentUser	current user
	 * @see		MenuClass
	 * @see		MenuDBHandler
	 * @see		UserClass
	 */
	public void modifyMenu(MenuClass menu, UserClass currentUser) {
		menuDB.modifyMenu(menu, currentUser);
	}
	
	/**
	 * Gets the menu's ID.
	 * 
	 * @param	menu		requested menu
	 * @return	requested menu's ID
	 * @see		MenuClass
	 * @see		MenuDBHandler
	 */
	public int getMenuID(MenuClass menu){
		return menuDB.getMenuID(menu);
	}
}
