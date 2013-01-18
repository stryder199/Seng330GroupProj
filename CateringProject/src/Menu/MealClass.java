package Menu;

/**
 * This class is used to hold user details.
 * 
 * @author	Harriet Willmott
 */
public class MealClass {

	private String name, vegFriendly, mealCourse;
	private int ID = 9999999;

	/**
	 * Constructs a meal object initialised with the given values for the
	 * name, meal's ID, vegetarian menu or not, meal course.
	 * <p>
	 * This includes meal's ID.
	 * 
	 * @param	name		meal's name
	 * @param	iD			meal's ID
	 * @param	vegFriendly	vegetarian menu or not
	 * @param	mealCourse	meal course
	 */
	public MealClass(String name, int iD, String vegFriendly, String mealCourse) {
		this.name = name;
		this.ID = iD;
		this.vegFriendly = vegFriendly;
		this.mealCourse = mealCourse;
	}
	
	/**
	 * Constructs a meal object initialised with the given values for the
	 * name, vegetarian menu or not, meal course.
	 * <p>
	 * This does not include meal's ID.
	 * 
	 * @param	name		meal's name
	 * @param	vegFriendly	vegetarian menu or not
	 * @param	mealCourse	meal course
	 */
	public MealClass(String name, String vegFriendly, String mealCourse) {
		this.name = name;
		this.vegFriendly = vegFriendly;
		this.mealCourse = mealCourse;
	}

	/**
	 * Verifies the input from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verifyInput(){
		//Sanitize input
		name = name.replace("'","");
		
		
		if(!mealCourse.matches("appetizer") && !mealCourse.matches("main") && !mealCourse.matches("dessert")){
			return false;
		}
		
		if(!vegFriendly.matches("yes") && !vegFriendly.matches("no")){
			return false;
		}
		
		return true;
	}

	/**
	 * Gets the meal's name.
	 * 
	 * @return	meal's name
	 * @see		#setName(String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the meal's name.
	 * 
	 * @param	name	meal's name
	 * @see		#getName()
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the meal's ID.
	 * 
	 * @return	meal's ID
	 * @see		#setID(int)
	 * @see		MealControllerClass
	 */
	public int getID() {
		if(ID == 9999999){
			ID = MealControllerClass.getInstance().getMealID(this);
		}
		return ID;
	}

	/**
	 * Sets the meal's ID.
	 * 
	 * @param	iD		meal's ID
	 * @see		#getID()
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Gets the meal's status of vegetarian friendliness.
	 * 
	 * @return	meal's status of vegetarian friendliness
	 * @see		#setVegFriendly(String)
	 */
	public String getVegFriendly() {
		return vegFriendly;
	}

	/**
	 * Sets the meal's status of vegetarian friendliness.
	 * 
	 * @param	vegFriendly	meal's status of vegetarian friendliness
	 * @see		#getVegFriendly()
	 */
	public void setVegFriendly(String vegFriendly) {
		this.vegFriendly = vegFriendly;
	}

	/**
	 * Gets the meal's course.
	 * 
	 * @return	meal's course
	 * @see		#setMealCourse(String)
	 */
	public String getMealCourse() {
		return mealCourse;
	}

	/**
	 * Sets the meal's course.
	 * 
	 * @param	mealCourse	meal's course
	 * @see		#getMealCourse()
	 */
	public void setMealCourse(String mealCourse) {
		this.mealCourse = mealCourse;
	}

}
