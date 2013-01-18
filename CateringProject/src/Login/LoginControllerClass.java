package Login;

/**
 * This class is used for login controller.
 * 
 * @author Mark Lessard 
 */
public class LoginControllerClass {

	private LoginDBHandler loginDB = new LoginDBHandler();

	/**
	 * Constructs an instance of login controller.
	 */
	public LoginControllerClass() {
	}

	/**
	 * Checks login input from user.
	 * 
	 * @param	currentUser		current user
	 * @return	true if input is valid and user exists; false otherwise
	 * @see		UserClass
	 * @see		LoginDBHandler
	 */
	public boolean checkLoginInfo(UserClass currentUser) {
		boolean result = false;
		
		result = currentUser.verify();		
		if(!result)
			return false;
		
		//Check that the login info is correct
		result = loginDB.checkUser(currentUser);
		if(!result)
			return false;
		
		//Hackish thing to get other parts to work well
		loginDB.setUserId(currentUser);		
		
		return result;
	}

	/**
	 * Checks user's info.
	 * 
	 * @param	newUser		current user
	 * @return	true if input is valid; false otherwise
	 * @see		UserClass
	 */
	public boolean checkUserInfo(UserClass newUser) {
		boolean result;
		
		result = newUser.verify();
		if(!result)
			return false;
			
		
		return result;
	}

	/**
	 * Adds a new user.
	 * 
	 * @param	newUser		new user
	 * @return	true if new user added successfully; false otherwise
	 * @see		UserClass
	 * @see		LoginDBHandler
	 */
	public boolean addNewUser(UserClass newUser) {
		boolean result;
		
		newUser.hashCurrentPassword();
		
		result = loginDB.addUser(newUser);
		
		return result;
	}

	/**
	 * Changes a user's password.
	 * 
	 * @param	newPassword	new password
	 * @param	currentUser	current user
	 * @see		UserClass
	 * @see		LoginDBHandler
	 */
	public void changePassword(String newPassword, UserClass currentUser) {
		boolean result = false;
		
		//Set the new password in the object and md5 it!
		currentUser.setPassword(newPassword);
		currentUser.hashCurrentPassword();
		
		result = loginDB.modifyPassword(currentUser);	
		if(!result)
			System.out.println("DB update error!");
	}
}
