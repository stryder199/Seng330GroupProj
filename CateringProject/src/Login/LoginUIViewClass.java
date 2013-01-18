package Login;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import UserInterface.ComponentGeneratorClass;
import UserInterface.ButtonListener;
import UserInterface.UIControllerClass;

/**
 * This class is used for GUI display of login view.
 * 
 * @author	Mark Lessard
 */
@SuppressWarnings("unused")
public class LoginUIViewClass{
	private LoginControllerClass loginController;
	//cardholder STring names
	String rootViewName = "Main Menu View";
	String loginViewName = "Login View";
	String adminMainViewName = "Admin Main View";
	String adminRegisterViewName = "Admin Register View";
	String adminPasswordConfirmViewName = "Admin Password Confirm View";
	String adminPasswordChangeViewName = "Admin Password Change View";
	JPanel cardHolder, loginPanel, adminMainPanel;
	JPanel adminRegPanel, adminPassConfirmPanel, adminPassChangePanel;
	// component generator for panels
	ComponentGeneratorClass generator;
      
	/**
	 * Constructs an LoginUIViewClass object initialised to add 
	 * the panels to the cardholder, as well as initialise the generator.
	 * 
	 * @param cardHolder
	 */
	public LoginUIViewClass(JPanel cardHolder) {
		loginController = new LoginControllerClass();
		this.cardHolder = cardHolder;
		generator = new ComponentGeneratorClass(cardHolder);
		//initializes the panels
		initViewPanel();
		adminMainPaneInit();
		adminRegisterPaneInit();
		adminPasswordConfirmPaneInit();
		adminPasswordChangePaneInit();
		//adds panels to cardholder 
		cardHolder.add(loginPanel, loginViewName);
		cardHolder.add(adminMainPanel, adminMainViewName);
		cardHolder.add(adminRegPanel, adminRegisterViewName);
		cardHolder.add(adminPassConfirmPanel, adminPasswordConfirmViewName);
		cardHolder.add(adminPassChangePanel, adminPasswordChangeViewName);
	}
	
	/**
	 * This method initialises a login panel to be displayed when first starting the program.
	 */
	public void initViewPanel(){
		loginPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(loginPanel, 180);
		JLabel welcomeMessage = generator.createLabel(loginPanel, "Welcome to the Catering System!", "");
		welcomeMessage.setFont(new Font("Dialog", 1, 20));
		generator.createRigidSpace(loginPanel, 50);		
		final JLabel resultLabel = generator.createLabel(loginPanel, " ", "");		
		final JLabel resultLabel2 = generator.createLabel(loginPanel, " ", "");	
		generator.createRigidSpace(loginPanel, 15);
		final JLabel usernameLabel = generator.createLabel(loginPanel, "Username:", "");	
		final JTextField username = generator.createTextField(loginPanel, "");
		final JLabel passwordLabel = generator.createLabel(loginPanel, "Password:", "");
		final JPasswordField passwordInput = new JPasswordField();
		loginPanel.add(passwordInput);
		JButton loginButton = generator.createButton(loginPanel, "Login", 30);	
		//resizes the login button / username&password input box 
		loginButton.setMaximumSize(new Dimension(85, 30));
		username.setMaximumSize(new Dimension(120, 20));
		passwordInput.setMaximumSize(new Dimension(120, 20));
		
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		//sets the conditions for when the loginButton is pressed and attempts to login
		loginButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
				resultLabel.setText(" ");
				resultLabel2.setText(" ");
		    	boolean result = false;
		    	char[] password = passwordInput.getPassword();		
		    	UIControllerClass.currentUser = new UserClass(username.getText(), String.valueOf(password));
		    	//Zero out the possible password, for security.
		        Arrays.fill(password, '0');
				result = loginController.checkLoginInfo(UIControllerClass.currentUser);		//checks login info given
    			if (!result) {	//display error message if wrong login info
    				resultLabel.setText("Username or Password is incorrect");
    				resultLabel2.setText("Please Try Again");
    			} else {
    				//given correct login, transition to main menu
    				username.setText("");
    				passwordInput.setText(""); 
    				CardLayout cLayout = (CardLayout) cardHolder.getLayout();
    				cLayout.show(cardHolder, rootViewName);
    			}	
		    }
		});
	}

	/**
	 * This method initialises a main menu panel to represent the admin Menu.
	 */
	public void adminMainPaneInit (){
		adminMainPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(adminMainPanel, 225);
		JLabel heading = generator.createLabel(adminMainPanel, "Administration Menu", "");
		heading.setFont(new Font("Dialog", 1, 20));
		JButton registerButton = generator.createButton(adminMainPanel, "Register User", adminRegisterViewName, 50);
		JButton passwordButton = generator.createButton(adminMainPanel, "Change Password", adminPasswordConfirmViewName, 5);
		JButton backButton = generator.createButton(adminMainPanel, "Back", rootViewName, 30);
		
		adminMainPanel.setLayout(new BoxLayout(adminMainPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * This method initialises an administration panel accessible from the administration Menu
	 * that allows the user to register a new user.
	 */
	public void adminRegisterPaneInit(){
		adminRegPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(adminRegPanel, 150);
		JLabel usernameLabel = generator.createLabel(adminRegPanel, "Please enter new username");
		final JTextField usernameInput = generator.createTextField(adminRegPanel, " ");
		JLabel passwordLabel = generator.createLabel(adminRegPanel, "Please enter new password");
		final JTextField passwordInput = generator.createTextField(adminRegPanel, " ");
		JLabel passwordConfirmLabel = generator.createLabel(adminRegPanel, "Please re-enter new password");
		final JTextField passwordConfirmInput = generator.createTextField(adminRegPanel, " ");
		final JLabel resultLabel = generator.createLabel(adminRegPanel, "");
		JButton addUserButton = generator.createButton(adminRegPanel, "Add user", 10);		
		JButton backButton = generator.createButton(adminRegPanel, "Back", adminMainViewName, 5);	
		
		adminRegPanel.setLayout(new BoxLayout(adminRegPanel, BoxLayout.Y_AXIS));
		
		//sets the conditions for when the addUserButton is pressed and registers a new user
		addUserButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
				UserClass newUser;
		    	boolean result;
		    	resultLabel.setText("");
		    	//checks both passwords match
				if(passwordInput.getText().compareTo(passwordConfirmInput.getText()) != 0){
					resultLabel.setText("Passwords dont match, please re-enter!");
				} else {
					newUser = new UserClass(usernameInput.getText(), passwordInput.getText());	

					// Return true if login info correct, false if wrong
					result = loginController.checkUserInfo(newUser);
					if(!result){
						resultLabel.setText("Wrong username/password format, no spaces, password must be of length <= 5");
					} else {
						//MD5 the password and add it to the DB
						result = loginController.addNewUser(newUser);
						if(!result){
							resultLabel.setText("Error adding user, please try again");
						}
						else{
							resultLabel.setText("User added Successfully!!");
						}
					}
				}
		    }
		});
	}
	
	/**
	 * This method initialises an administration panel accessible from the administration Menu
	 * that confirms the user wishes to change there password.
	 */
	public void adminPasswordConfirmPaneInit(){
		adminPassConfirmPanel = new JPanel();
		//generates panels formatted layout of components
		generator.createRigidSpace(adminPassConfirmPanel, 225);
		final JLabel confirmLabel = generator.createLabel(adminPassConfirmPanel, "Are you sure that you would like to change your password?", "");
		final JButton yesButton = generator.createButton(adminPassConfirmPanel, "Yes", adminPasswordChangeViewName, 20);
		final JButton noButton = generator.createButton(adminPassConfirmPanel, "No", adminMainViewName, 5);	
		JButton backButton = generator.createButton(adminPassConfirmPanel, "Back", adminMainViewName, 30);

		adminPassConfirmPanel.setLayout(new BoxLayout(adminPassConfirmPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * This method initialises an administration panel accessible from the administration Menu
	 * changes a user's password.
	 */
	public void adminPasswordChangePaneInit(){
		adminPassChangePanel = new JPanel();	
		//generates panels formatted layout of components
		generator.createRigidSpace(adminPassChangePanel, 150);
		final JLabel oldPasswordLabel = generator.createLabel(adminPassChangePanel, "Please enter old password");
		final JTextField oldPasswordInput = generator.createTextField(adminPassChangePanel, " ");	
		final JLabel newPasswordLabel = generator.createLabel(adminPassChangePanel, "Please enter new password");
		final JTextField newPasswordInput = generator.createTextField(adminPassChangePanel, " ");
		final JLabel newPasswordConfirmLabel = generator.createLabel(adminPassChangePanel, "Please re-enter new password");
		final JTextField newPasswordConfirmInput = generator.createTextField(adminPassChangePanel, " ");
		generator.createRigidSpace(adminPassChangePanel, 10);
		final JLabel resultLabel = generator.createLabel(adminPassChangePanel, "");
		
		final JButton confirmChangeButton = generator.createButton(adminPassChangePanel, "Change Password", 20);
		JButton backButton = generator.createButton(adminPassChangePanel, "Back", adminMainViewName, 10);		

		adminPassChangePanel.setLayout(new BoxLayout(adminPassChangePanel, BoxLayout.Y_AXIS));		
		
		//sets the conditions for when the confirmchangebutton is pressed and changes the users password
		confirmChangeButton.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){	//checks if both passwords match
				if(newPasswordInput.getText().compareTo(newPasswordConfirmInput.getText()) != 0)
				{
					resultLabel.setText("New passwords dont match! Please try again.");
				} else {	//compare hash passwords
					if(UIControllerClass.currentUser.getPassword().compareTo(hashPassword(oldPasswordInput.getText())) != 0){
						resultLabel.setText("Wrong current password!");
					} else {	//change password
						loginController.changePassword(newPasswordInput.getText(), UIControllerClass.currentUser); 
					}
				}
		    }
		});
	}
	
	/**
	 * Hashes the password.
	 * 
	 * @param	password	password
	 * @return	the hashed string
	 * @throws	NoSuchAlgorithmException	No such hash algorithm
	 */
	public String hashPassword(String password) {
		String hashword = null;
	
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (NoSuchAlgorithmException nsae) {
			// ignore
		}
	
		return hashword;
	}
}