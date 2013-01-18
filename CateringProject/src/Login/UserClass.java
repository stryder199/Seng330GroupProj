package Login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used to hold user details.
 * 
 * @author	Mark Lessard
 */
public class UserClass {

	private String username;
	private String password;
	private int user_id;

	/**
	 * Constructs a user object initialised with the given values for the
	 * user name, and password.
	 * 
	 * @param	username	user name
	 * @param	password	password
	 */
	public UserClass(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return	user name
	 * @see		#setUsername(String)
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param	username	user name
	 * @see		#getUsername()
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return	password
	 * @see		#setPassword(String)
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param	password	password
	 * @see		#getPassword()
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Hashes the current password.
	 */
	public void hashCurrentPassword() {
		String hashword = null;

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (NoSuchAlgorithmException nsae) {
			// ignore
		}

		password = hashword;
	}

	/**
	 * Verifies the inputs from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verify() {
		boolean result;
		
		result = username.contains(" ");
		if(result)
			return false;
		
		result = password.contains(" ");
		if(result)
			return false;
		
		if(username.length() > 30)
			return false;
		
		if(password.length() < 5 || password.length() > 30)
			return false;
		
		return true;
	}

	/**
	 * Gets the user's ID.
	 * 
	 * @return	user's ID
	 * @see		#setUserId(int)
	 */
	public int getUserId() {
		return user_id;
	}

	/**
	 * Sets the user's ID.
	 * 
	 * @param	user_id	user's ID
	 * @see		#getUserId()
	 */
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

}
