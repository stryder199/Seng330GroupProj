package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBHandler;
import Login.UserClass;

/**
 * This class is used to handle the menu database.
 * 
 * @author	Harriet Willmott
 */
public class MenuDBHandler extends DBHandler {

	/**
	 * Constructs a new instance of menu database handler.
	 */
	public MenuDBHandler(){}

	/**
	 * Adds the given menu instance to the database.
	 * 
	 * @param	menu		new menu
	 * @param	currentUser	current user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		MenuClass
	 * @see		UserClass
	 */
	public boolean addMenu(MenuClass menu, UserClass currentUser) {
		int result = 0;
		try {
			result = updateQuery("INSERT INTO menus (meal_id1, meal_id2, meal_id3, creationUser_id) " + 
								"VALUES(" + menu.getAppetizer().getID() + ", " + menu.getMainMeal().getID() + ", " 
								+ menu.getDessert().getID() + ", " + currentUser.getUserId() + ")");
		} catch (SQLException e) {
			System.out.println("Error inserting menu into DB");
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}

	/**
	 * Queries all the menus in the database.
	 * 
	 * @return	ResultSet of all menus
	 * @throws	SQLException	Error with database
	 */
	public ResultSet queryMenus() {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From menus");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Query for a menu based on menu's ID.
	 * 
	 * @param	menu_id		requested menu's ID
	 * @return	ResultSet of request menu
	 * @throws	SQLException	Error with database
	 */
	public ResultSet queryMenu(int menu_id)
	{
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From menus " + 
							"WHERE menu_id = " + menu_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	/**
	 * Updates the record in the DB for the given menu.
	 * 
	 * @param	menu		menu to be updated
	 * @param	currentUser	current user
	 * @return	true if updated sucessfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		MenuClass
	 * @see		UserClass
	 */
	public boolean modifyMenu(MenuClass menu, UserClass currentUser) {
		int result = 0;
		try {
			result = updateQuery("UPDATE menus " +
								"SET meal_id1=" + menu.getAppetizer().getID() + 
								", meal_id2=" + menu.getMainMeal().getID() + 
								", meal_id3=" + menu.getDessert().getID() + 
								", creationUser_id=" + currentUser.getUserId() + 
								"WHERE menu_id ="+ menu.getMenuId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 0) return false;
		return true;
		
		
	}

	/**
	 * Gets a menu's ID.
	 * 
	 * @param	menu		requested menu
	 * @return	requested menu's ID
	 * @throws	SQLException	Error with database
	 * @see		MenuClass
	 */
	public int getMenuID(MenuClass menu) {
		ResultSet result = null;
		int menu_id = 0;

		try {
			result = query("Select menu_id " +
							"From menus " + 
							"WHERE meal_id1 = " + menu.getAppetizer().getID() + " AND meal_id2=" + menu.getMainMeal().getID() + " AND meal_id3=" + menu.getDessert().getID());
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		try {
			result.next();
			menu_id = result.getInt("menu_id");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return menu_id;	
	}
}
