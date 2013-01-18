package Login;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import DB.DBHandler;

/**
 * This class is used to handle the login database.
 * 
 * @author Mark Lessard 
 */
public class LoginDBHandler extends DBHandler {

	/**
	 * Constructs a new instance of login database handler.
	 */
	public LoginDBHandler() {

	}

	/**
	 * Checks whether user exists and password is correct.
	 * 
	 * @param	currentUser		current user
	 * @return	true if user exists and password is correct; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		UserClass
	 */
	public boolean checkUser(UserClass currentUser) {
	
		ResultSet result = null;
		int numUsers = 99999;
		
		// Get the MD5 version of the inputed password
		currentUser.hashCurrentPassword();
		
		try {
			result = query("Select COUNT(*) AS NUM_USER " + "From users "
					+ "Where username='" + currentUser.getUsername() + "' AND password='"
					+ currentUser.getPassword() + "';");
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}

		try {
			if (!result.next()) {
				return false;
			}
			numUsers = result.getInt("NUM_USER");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (numUsers == 0) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a new user to database.
	 * 
	 * @param	newUser		new user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		UserClass
	 */
	public boolean addUser(UserClass newUser) {
		int result = 9999;
		
		try {
			result = updateQuery("INSERT INTO users(username, password) " +
								"VALUES ('" + newUser.getUsername() + "', '" + newUser.getPassword() + "')");
		} catch (MySQLIntegrityConstraintViolationException e){
			System.out.println("Error! Another username with that name already exisits!!");
			return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;		
	}

	/**
	 * Modifies a user's password.
	 * 
	 * @param	currentUser		current user
	 * @return	true if modified successfully; false otherwise
	 * @throws	SQLException	Error in database
	 * @see		UserClass
	 */
	public boolean modifyPassword(UserClass currentUser) {
		int result = 9999;
		
		try {
			result = updateQuery("UPDATE users " +
								"SET password='" + currentUser.getPassword() + "' " + 
								"WHERE username='" + currentUser.getUsername() + "'");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}

	//Find the user_id of the currentUser and set it in the object
	/**
	 * Sets a user's ID in the object.
	 * 
	 * @param	currentUser		current user
	 * @throws	SQLException	Error in database
	 * @see		UserClass
	 */
	public void setUserId(UserClass currentUser) {
		ResultSet result = null;
		int user_id = 0;
		
		try {
			result = query("Select user_id " +
							"From users " + 
							"WHERE username='" + currentUser.getUsername() + "'");
			result.next();
			user_id = result.getInt("user_id");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		currentUser.setUserId(user_id);
	}

	/**
	 * Gets all users from database.
	 * 
	 * @return	ResultSet of all users
	 * @throws	SQLException	Error in database
	 */
	public ResultSet getAllUsers() {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From users");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}
}
