package Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Login.UserClass;

/**
 * This class is used for meal controller.
 * 
 * @author	Harriet Willmott
 */
public class MealControllerClass {

	private static MealControllerClass controllerSinglton = null;
	private MealDBHandler mealDB;
	
	/**
	 * Constructs a new instance of meal controller.
	 */
	public MealControllerClass() {	
		mealDB = new MealDBHandler();
	}

	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initialises the object and
	 * returns it, if it has been initialised then it returns the already initialised 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static MealControllerClass getInstance()
	{
		if(controllerSinglton == null){
			MealControllerClass instance = new MealControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}
	
	/**
	 * Deletes a meal record from the DB.
	 * 
	 * @param	meal_id		meal's ID
	 * @return 	true if meal is deleted successfully; false otherwise
	 */
	public boolean deleteMeal(int meal_id)
	{
		boolean result;
		
		result = mealDB.deleteMeal(meal_id);
		
		return result;
	}


	/**
	 * Updates a meal record in the DB.
	 * 
	 * @param	meal	meal to be updated
	 * @return 	true if updated successfully; false otherwise
	 */
	public boolean updateMeal(MealClass meal) {
		boolean result;
		result = mealDB.updateMeal(meal);
		
		return result;
	}
	
	/**
	 * Returns a list of all the meals in the database.
	 * 
	 * @return	list of meals
	 * @throws	SQLException	Error querying meals
	 */
	public List<MealClass> getAllMeals()
	{
		ResultSet result = mealDB.queryMeals();
		
		List<MealClass> list = new ArrayList<MealClass>();
		
		try {
			while(result.next())
			{
				int meal_id = result.getInt("meal_id");
				String description = result.getString("description");
				String vegFriendly = result.getString("vegFriendly");
				String mealCourse = result.getString("course");
				
				MealClass meal = new MealClass(description, meal_id, vegFriendly, mealCourse);
				list.add(meal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;		
	}
	
	/**
	 * Gets all the meal records that match the args.
	 * 
	 * @param	vegFriendly		vegetarian friendliness
	 * @param	course			meal's course
	 * @return	list of meals
	 * @throws	SQLException	Error querying meals
	 */
	public List<MealClass> getSpecificMeals(boolean vegFriendly, String course){
		ResultSet result;
		List<MealClass> allMeals;
		
		allMeals = new ArrayList<MealClass>();
		
		result = mealDB.querySpecificMeals(vegFriendly, course);
		
		try {
			while(result.next())
			{
				int meal_id = result.getInt("meal_id");
				String description = result.getString("description");
				String vegeFriendly = result.getString("vegFriendly");
				String mealCourse = result.getString("course");
				
				MealClass meal = new MealClass(description, meal_id, vegeFriendly, mealCourse);
				allMeals.add(meal);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allMeals;
	}

	/**
	 * Gets a specific meal based on meal's ID.
	 * 
	 * @param	meal_id			meal's ID
	 * @return	meal
	 * @throws	SQLException	Error querying meals
	 */
	public MealClass getMeal(int meal_id) {
		ResultSet result = mealDB.queryMeal(meal_id);
		MealClass meal = null;
		try {
			if(!result.next()) return null;
			String description = result.getString("description");
			String vegFriendly = result.getString("vegFriendly");
			String mealCourse = result.getString("course");
			
			meal = new MealClass(description, meal_id, vegFriendly, mealCourse);
		} catch (SQLException e) {
			System.out.println("Error finding meal in DB. Meal ID: " + meal_id);
			e.printStackTrace();
		}
		
		return meal;
	}

	/**
	 * Adds a new meal record to the database.
	 * 
	 * @param	meal		new meal
	 * @param	currentUser	current user
	 * @return 	true if added successfully; false otherwise
	 * @see		MealClass
	 * @see		UserClass
	 * @see		MealDBHandler
	 */
	public boolean newMeal(MealClass meal, UserClass currentUser) {
		boolean result = false;
		
		result = mealDB.newMeal(meal, currentUser);
		
		return result;
	}

	/**
	 * Gets a meal's ID from the database.
	 * 
	 * @param	mealClass		requested meal
	 * @return 	meal's ID
	 * @see		MealClass
	 * @see		MealDBHandler
	 */
	public int getMealID(MealClass mealClass) {
		ResultSet result = mealDB.getMealID(mealClass);
		int meal_id = 0;
		try {
			if(!result.next())
				return 0;
			meal_id = result.getInt("meal_id");
	
		} catch (SQLException e) {
			System.out.println("Error finding meal in DB. Meal ID: " + meal_id);
			e.printStackTrace();
		}
		
		return meal_id;
	}
}
