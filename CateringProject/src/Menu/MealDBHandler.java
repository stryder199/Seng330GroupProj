package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBHandler;
import Login.UserClass;

/**
 * This class is used for handling the meal database.
 * 
 * @author	Harriet Willmott
 */
public class MealDBHandler extends DBHandler{

	/**
	 * Constucts a new instance of meal handler.
	 */
	public MealDBHandler() {}

	/**
	 * Queries the DB for all the meal records.
	 * 
	 * @return	ResultSet of all meals
	 * @throws	SQLException	Error with database
	 */
	public ResultSet queryMeals() {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From meals");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	/**
	 * Queries the DB for all the meal records that match the args.
	 * 
	 * @param	vegFriendly		vegetarian friendliness
	 * @param	course			meal's course
	 * @return	ResultSet of all requested meals
	 * @throws	SQLException	Error with database
	 */
	public ResultSet querySpecificMeals(boolean vegFriendly, String course) {
		ResultSet result = null;
		String veg = "";
		
		if(vegFriendly == true){
			veg = " AND vegFriendly='yes'";
		}
		
		try {
			result = query("SELECT * " +
							"FROM meals " +
							"WHERE course='" + course + "'" + veg);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Queries the DB for the meal record associated with the given meal ID.
	 * 
	 * @param	meal_id		meal's ID
	 * @return	ResultSet of meal
	 * @throws	SQLException	Error with database
	 */
	public ResultSet queryMeal(int meal_id) {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From meals " +
							"WHERE meal_id=" + meal_id);
		}
		catch (SQLException e) {
			System.out.println("Error: meal does not exist. Meal ID: " + meal_id);
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Deletes a meal record from the database.
	 * 
	 * @param	meal_id			meal's ID
	 * @return	true if meal deleted successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean deleteMeal(int meal_id) {
		int result =  0;
		try {
			result = updateQuery("DELETE FROM meals " + 
								"WHERE meal_id=" + meal_id );
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
		
	}

	/**
	 * Updates a meal record in the datebase.
	 * 
	 * @param	meal		meal to be updated
	 * @return	true if updated successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean updateMeal(MealClass meal) {
		int result = 0;
		try {
			result = updateQuery("UPDATE meals " +
								"SET description='" + meal.getName() + 
								"', vegfriendly='" + meal.getVegFriendly() + 
								"', course='" + meal.getMealCourse() +
								"' WHERE meal_id ="+ meal.getID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 0) return false;
		return true;
		
	}

	/**
	 * Adds a new meal record in the database.
	 * 
	 * @param	meal		new meal
	 * @param	currentUser	current user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */
	public boolean newMeal(MealClass meal, UserClass currentUser) {
		int result =  0;
		
		try {
			result = updateQuery("INSERT INTO meals (description, vegfriendly, course, creationUser_id) " + 
					"VALUES('" + meal.getName() + "','" + meal.getVegFriendly() + "', '" 
					+ meal.getMealCourse() + "', " +	currentUser.getUserId() + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
		
	}

	/**
	 * Gets a meal's ID from the database.
	 * 
	 * @param	mealClass		requested meal
	 * @return	ResultSet of requested meal
	 * @throws	SQLException	Error with database
	 */
	public ResultSet getMealID(MealClass mealClass) {
		ResultSet result = null;
		try {
			result = query("SELECT * " +
							"FROM meals " +
							"WHERE description='" + mealClass.getName() + 
							"' AND vegfriendly='" + mealClass.getVegFriendly() + 
							"' AND course='" + mealClass.getMealCourse() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

}
