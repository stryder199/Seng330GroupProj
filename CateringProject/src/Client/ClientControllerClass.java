package Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Login.UserClass;


/**
 * This class is the controller for client related operations.
 * 
 * @author Cody Plante
 */
public class ClientControllerClass {

	private static ClientControllerClass controllerSinglton;
	
	private ClientDBHandler clientDB = new ClientDBHandler();

	/**
	 * Contructs an instance of client controller.
	 */
	private ClientControllerClass(){}
	
	/**
	 * This method is used to implement the singleton design for this controller class.
	 * If the controller hasn't been accessed before it initialises the object and 
	 * returns it, if it has been initialised then it returns the already initialised 
	 * object.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static ClientControllerClass getInstance()
	{
		if(controllerSinglton == null){
			ClientControllerClass instance = new ClientControllerClass();
			controllerSinglton = instance;
			return controllerSinglton;
		}
		else{
			return controllerSinglton;
		}
	}
	
	/**
	 * Verifies the given clients details, and initialises a request to clientDB 
	 * class to add the clients record.
	 * 
	 * @param	newClient	new client
	 * @param	currentUser	current user
	 * @return	true if client added successfully; false otherwise
	 * @see		ClientClass
	 * @see		ClientDBHandler
	 * @see		UserClass
	 */
	public boolean addClient(ClientClass newClient, UserClass currentUser) {
		boolean result = false;
		// verify the clients input
		result = newClient.verifyInput(newClient);
		if(!result)
			return false;
		// request client addition to database
		result = clientDB.addClient(newClient, currentUser);
		if(!result)
			return false;

		return true;
	}

	/**
	 * Remove a client record based off the given client ID.
	 * 
	 * @param	clientID	client's ID number
	 * @return	true if client removed successfully; false otherwise
	 * @see		ClientDBHandler
	 */
	public boolean deleteClient(int clientID) {
		boolean result = false;

		// request client removal from database
		result = clientDB.removeClient(clientID);
		if(!result)
			return false;

		return true;
	}

	/**
	 * Modifies a client's information in the database.
	 * 
	 * @param	modifiedClass		client to be modified
	 * @return	true if modified successfully; false otherwise
	 * @see		ClientDBHandler
	 */
	public boolean modifyClient(ClientClass modifiedClass) {
		boolean result = false;

		result = clientDB.modifyClient(modifiedClass);		
		if (!result){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Verifies whether the given ID Number has an associated client record in the
	 * database.
	 * 
	 * @param	iDNum		requested client's ID number
	 * @return	true if client exists; false otherwise
	 * @throws	SQLException	Client doesn't exist
	 * @throws	SQLException	Error with database
	 * @see		ClientDBHandler
	 */

	public boolean verifyClientID(int iDNum){
		ResultSet result = null;
		// Queries database for client
		result = clientDB.getClient(iDNum);

		try {	// Tries to access client information
			result.next();
			@SuppressWarnings("unused")
			String fName = result.getString("firstName");
		} catch (SQLException e) {
			// Client doesn't exist
			return false;
		}
		return true;	//returns client exists
	}

	/**
	 * Gets client based on client's ID.
	 * 
	 * @param	client_id	client's ID
	 * @return	client
	 * @throws	SQLException	Error with database
	 * @see		ClientClass
	 * @see		ClientDBHandler
	 */
	public ClientClass getClient(int client_id)
	{
		ResultSet result = null;
		ClientClass client = null;
		// Queries database for client
		result = clientDB.getClient(client_id);

		try {
			result.next();
			//Stores clients information
			String fName = result.getString("firstName");
			String lName = result.getString("lastName");
			String company = result.getString("company");
			String email = result.getString("email");
			String phoneNum = result.getString("phoneNum");
			String address = result.getString("address");
			// Creates new client to return
			client = new ClientClass(fName, lName, email, company, phoneNum, address);

			client.setClientId(client_id);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return client;
	}

	/**
	 * Gets a list of all client records.  
	 * 
	 * @return	list of all clients
	 * @throws	SQLException	Error with database
	 * @see		ClientClass
	 * @see		ClientDBHandler
	 */
	public List<ClientClass> getAllClients() {
		ResultSet result;
		boolean result2 = false;
		List<ClientClass> allClients = new ArrayList<ClientClass>();
		// Queries database for full client list
		result = clientDB.getAllClients();
		if(result == null)
			return null;

		try {
			while(result.next()){	//Continues to add clients to list until end
				// Stores all client information
				String fName = result.getString("firstName");
				String lName = result.getString("lastName");
				String company = result.getString("company");
				String email = result.getString("email");
				String phoneNum = result.getString("phoneNum");
				String address = result.getString("address");
				// Creates new client in list
				ClientClass newElement = new ClientClass(fName, lName, email, company, phoneNum, address);

				newElement.setClientId(result.getInt("client_id"));

				result2 = allClients.add(newElement);
				if(!result2)
					return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return allClients;
	}

	/**
	 * Gets the client's ID from database.
	 * 
	 * @param	clientClass		requested client
	 * @return	client's ID
	 * @throws	SQLException	Error in database
	 * @see		ClientClass
	 * @see		ClientDBHandler
	 */
	public int getClientId(ClientClass clientClass) {
		ResultSet result = null;
		int client_id = 0;
		// Queries database for client
		result = clientDB.getClientId(clientClass);

		try {
			result.next();
			//Stores clients information
			client_id = result.getInt("client_id");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return client_id;
	}

}
