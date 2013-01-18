package Menu;

/**
 * This class is used to hold menu details.
 * 
 * @author	Harriet Willmott
 */
public class MenuClass {

	private int appetizerMeal_id, mainMeal_id, dessertMeal_id;
	private MealClass appetizer = null, mainMeal = null, dessert = null;
	private int menu_id;
	
	/**
	 * Constructs a menu object initialised with the given values for the
	 * appetiser's ID, main meal's ID, and dessert's ID.
	 * <p>
	 * This does not include menu's ID.
	 * 
	 * @param	app_id		appetiser's ID
	 * @param	main_id		main menu's ID
	 * @param	dess_id		dessert's ID
	 */
	public MenuClass( int app_id, int main_id, int dess_id) {
		setAppetizerMealId(app_id);
		setMainMealId(main_id);
		setDessertMealId(dess_id);
	}

	/**
	 * Constructs a menu object initialised with the given values for the
	 * appetiser's ID, main meal's ID, dessert's ID, and menu's ID.
	 * <p>
	 * This includes menu's ID.
	 * 
	 * @param	app_id		appetiser's ID
	 * @param	main_id		main menu's ID
	 * @param	dess_id		dessert's ID
	 * @param	menu_id		menu's ID
	 */
	public MenuClass( int app_id, int main_id, int dess_id, int menu_id) {
		setAppetizerMealId(app_id);
		setMainMealId(main_id);
		setDessertMealId(dess_id);
		this.menu_id = menu_id;
	}
	
	/**
	 * Gets the appetiser.
	 * 
	 * @return	appetiser
	 * @see		#setAppetizer(MealClass)
	 */
	public MealClass getAppetizer() {
		if(appetizer == null){
			appetizer = MealControllerClass.getInstance().getMeal(appetizerMeal_id);
		}
		return appetizer;
	}

	/**
	 * Sets the appetiser.
	 * 
	 * @param	appetizer	appetiser
	 * @see		MealClass
	 * @see		#getAppetizer()
	 */
	public void setAppetizer(MealClass appetizer) {
		this.appetizer = appetizer;
	}

	/**
	 * Gets the main menu.
	 * 
	 * @return	main menu
	 * @see		MealControllerClass
	 * @see		#setMainMeal(MealClass)
	 */
	public MealClass getMainMeal() {
		if(mainMeal == null){
			mainMeal = MealControllerClass.getInstance().getMeal(mainMeal_id);
		}
		return mainMeal;
	}

	/**
	 * Sets the main menu.
	 * 
	 * @param	mainMeal	main menu
	 * @see		#getMainMeal()
	 */
	public void setMainMeal(MealClass mainMeal) {
		this.mainMeal = mainMeal;
	}

	/**
	 * Gets the dessert.
	 * 
	 * @return	dessert
	 * @see		MealControllerClass
	 * @see		#setDessert(MealClass)
	 */
	public MealClass getDessert() {
		if(dessert == null){
			dessert = MealControllerClass.getInstance().getMeal(dessertMeal_id);
		}
		return dessert;
	}

	/**
	 * Sets the dessert.
	 * 
	 * @param	dessert	dessert
	 * @see		#getDessert()
	 */
	public void setDessert(MealClass dessert) {
		this.dessert = dessert;
	}

	/**
	 * Gets the menu's ID.
	 * 
	 * @return	menu's ID
	 * @see		#setMenuId(int)
	 */
	public int getMenuId() {
		return menu_id;
	}

	/**
	 * Sets the menu's ID.
	 * 
	 * @param	menu_id	menu's ID
	 * @see		#getMenuId()
	 */
	public void setMenuId(int menu_id) {
		this.menu_id = menu_id;
	}

	/**
	 * Gets the appetiser's ID.
	 * 
	 * @return	appetiser's ID
	 * @see		#setAppetizerMealId(int)
	 */
	public int getAppetizerMealId() {
		return appetizerMeal_id;
	}

	/**
	 * Sets the appetiser's ID.
	 * 
	 * @param	appetizerMeal_id	appetiser's ID
	 * @see		#getAppetizerMealId()
	 */
	public void setAppetizerMealId(int appetizerMeal_id) {
		this.appetizerMeal_id = appetizerMeal_id;
	}

	/**
	 * Gets the main meal's ID.
	 * 
	 * @return	main meal's ID
	 * @see		#setMainMealId(int)
	 */
	public int getMainMealId() {
		return mainMeal_id;
	}

	/**
	 * Sets the main meal's ID.
	 * 
	 * @param	mainMeal_id	main meal's ID
	 * @see		#getMainMealId()
	 */
	public void setMainMealId(int mainMeal_id) {
		this.mainMeal_id = mainMeal_id;
	}

	/**
	 * Gets the dessert's ID.
	 * 
	 * @return	dessert's ID
	 * @see		#setDessertMealId(int)
	 */
	public int getDessertMealId() {
		return dessertMeal_id;
	}

	/**
	 * Sets the dessert's ID.
	 * 
	 * @param	dessertMeal_id	dessert's ID
	 * @see		#getDessertMealId()
	 */
	public void setDessertMealId(int dessertMeal_id) {
		this.dessertMeal_id = dessertMeal_id;
	}
}
