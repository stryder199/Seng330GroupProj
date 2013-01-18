package Client;

import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBHandler;
import Login.UserClass;

/**
 * This class is used for handling client database.
 * 
 * Written by Cody Plante
 */
public class ClientDBHandler extends DBHandler{

	public ClientDBHandler() {}

	/**
	 * Adds a client record based off the client information, and tracks which user has
	 * added the client.
	 * 
	 * @param	newClient		new client
	 * @param	currentUser		current user
	 * @return	true if added successfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		ClientClass
	 * @see		UserClass
	 */
	public boolean addClient( ClientClass newClient, UserClass currentUser){
		int result = 0;
		try {
			result = updateQuery("INSERT INTO clients (firstName, lastName, company, email, phoneNum, creationUser_id, address) " + 
								"VALUES('" + newClient.getFirstName() + "','" + newClient.getLastName() + "', '" 
								+ newClient.getCompany() + "', '" +	newClient.getEmail() + "', '" + newClient.getPhone() + "', '" 
								+ currentUser.getUserId() +"', '" + newClient.getAddress() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}
	
	/**
	 * Removes a client record associated with a given client ID.
	 * 
	 * @param	clientID		requested client's ID
	 * @return	true if removed successfully; false otherwise
	 * @throws	SQLException	Error with database
	 */	
	public boolean removeClient(int clientID){
		int result =  0;
		
		//Sanity check, make sure that the client id is actually set!
		assert(clientID != 9999999);
		
		try {
			result = updateQuery("DELETE FROM clients " + 
								"WHERE client_id=" + clientID);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		if(result == 0)
			return false;
		
		return true;
	}

	/**
	 * Updates a client's information.
	 * 
	 * @param	changedClient	requested client
	 * @return	true if modified successfully; false otherwise
	 * @throws	SQLException	Error with database
	 * @see		ClientClass
	 */
	public boolean modifyClient(ClientClass changedClient){
		int result = 0;

		try {
			result = updateQuery("UPDATE clients " +
				"SET firstName='" + changedClient.getFirstName() +
				"', lastName='" + changedClient.getLastName() +
				"', company='" + changedClient.getCompany() + 
				"', email='" + changedClient.getEmail() +
				"', phoneNum='" + changedClient.getPhone() + 
				"', address='" + changedClient.getAddress() + "' " +
				"WHERE client_id =" + changedClient.getClientId());
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
			
		if(result == 0)
			return false;
			
		return true;			
	}

	/**
	 * Gets all client records.
	 * 
	 * @return	ResultSet of all clients
	 * @throws	SQLException	Error with database
	 */
	public ResultSet getAllClients() {
		ResultSet result = null;

		try {
			result = query("Select * " +
							"From clients");
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	/**
	 * Gets a client's record.
	 * 
	 * @param	clientID		requested client's ID
	 * @return	ResultSet of requested client
	 * @throws	SQLException	Error with database
	 */	
	public ResultSet getClient(int clientID){
		ResultSet result = null;
		
		try{
			result = query("SELECT * FROM clients " +
							"WHERE client_id=" + clientID);	
		} catch (SQLException e){
			System.out.println("Error: client does not exist. Client ID: " + clientID);
			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	/**
	 * Gets a client's ID.
	 * 
	 * @param	clientClass		requested client's ID
	 * @return	ResultSet of requested client
	 * @throws	SQLException	Error with database
	 * @see		ClientClass
	 */	
	public ResultSet getClientId(ClientClass clientClass) {
		ResultSet result = null;

		try {
			result = query("SELECT * " +
							"From clients " +
							"WHERE firstName='" + clientClass.getFirstName() +
							"' AND lastName='" + clientClass.getLastName() +
							"' AND company='" + clientClass.getCompany() + 
							"' AND email='" + clientClass.getEmail() +
							"' AND phoneNum='" + clientClass.getPhone() + 
							"' AND address='" + clientClass.getAddress() + "'");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
			
		return result;
	}
}
