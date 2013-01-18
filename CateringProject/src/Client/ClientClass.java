package Client;

/**
 * This class is used to hold client details.
 * 
 * @author Cody Plante
 */
public class ClientClass {

	private String firstName;
	private String lastName;
	private String email;
	private String company;
	private String phone;
	private String address;
	private int client_id = 9999999;

	/**
	 * Constructs a ClientClass object initialised with the given values for the
	 * first name, last name, email, company, phone, and address.
	 * 
	 * @param	fName		client's first name
	 * @param	lName		client's last name
	 * @param	email		client's email
	 * @param	company		client's company
	 * @param	phone		client's phone number
	 * @param	address		client's address
	 */
	public ClientClass(String fName, String lName, String email, String company, String phone, String address) {
		super();
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.company = company;
		this.phone = phone;
		this.address = address;
	}
	
	/**
	 * Verifies input from user.
	 * 
	 * @return	true if all inputs are valid; false otherwise
	 */
	public boolean verifyInput(){
		if (!firstName.matches("[a-zA-Z ]++") && !lastName.matches("[a-zA-Z ]++")){
			return false;
		}

		if (!phone.matches("(\\d{3})-(\\d{3})-(\\d{4})")){
			return false;
		}


		if (!email.matches("(\\w*)@(\\w*).(\\w*)")){
			return false;
		}
		
		return true;
	}

	/**
	 * Gets client's first name.
	 * 
	 * @return	client's first name
	 * @see		#setFirstName(String)
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets client's first name.
	 * 
	 * @param	firstName	client's first name
	 * @see		#getFirstName()
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets client's last name.
	 * 
	 * @return	client's last name
	 * @see		#setLastName(String)
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets client's last name.
	 * 
	 * @param	lastName	client's last name
	 * @see		#getLastName()
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets client's email.
	 * 
	 * @return	client's email
	 * @see		#setEmail(String)
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets client's email.
	 * 
	 * @param	email	client's email
	 * @see		#getEmail()
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets client's company.
	 * 
	 * @return	client's company
	 * @see		#setCompany(String)
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Sets client's company.
	 * 
	 * @param	company	client's company
	 * @see		#getCompany()
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gets client's phone number.
	 * 
	 * @return	client's phone number
	 * @see		#setPhone(String)
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets client's phone number.
	 * 
	 * @param	phone	client's phone number
	 * @see		#getPhone()
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets client's address.
	 * 
	 * @return	client's address
	 * @see		#setAddress(String)
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets client's address.
	 * 
	 * @param	address	client's address
	 * @see		#getAddress()
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Verifies that the clients input details are in the correct format.
	 * 
	 * @param	newClient		client
	 * @return	true if all input is valid; false otherwise
	 * @see		ClientClass
	 */
	public boolean verifyInput(ClientClass newClient) {
		boolean verified = true;
		//Sanitize input
		firstName = firstName.replace("'","");
		lastName = lastName.replace("'","");
		email = email.replace("'","");
		company = company.replace("'","");
		phone = phone.replace("'","");
		address = address.replace("'","");
		
		if (!newClient.firstName.matches("[a-zA-Z ]++")){
			verified = false;
		}
		if (!newClient.lastName.matches("[a-zA-Z ]++")){
			verified = false;
		}
		if (!newClient.phone.matches("(\\d{3})-(\\d{3})-(\\d{4})")){
			verified = false;
		}
		if (!newClient.email.matches("(\\w*)@(\\w*).(\\w*)")){
			verified = false;
		}
		return verified;
	}

	/**
	 * Gets client's ID number.
	 * 
	 * @return	client's ID number
	 * @see		#setClientId(int)
	 */
	public int getClientId() {
		if(client_id == 9999999){
			client_id = ClientControllerClass.getInstance().getClientId(this);
		}
		
		return client_id;
	}

	/**
	 * Sets client's ID number.
	 * 
	 * @param	client_id	client's ID number
	 * @see		#getClientId()
	 */
	public void setClientId(int client_id) {
		this.client_id = client_id;
	}

}
