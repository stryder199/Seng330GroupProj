//package Login;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Scanner;
//import UserInterface.Frame;
//import javax.swing.JFrame;
//import java.awt.Color;
//
//public class LoginViewClass {
//
//	private LoginControllerClass loginController;
//
//	public LoginViewClass() {
//		loginController = new LoginControllerClass();
//	}
//
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public UserClass displayLogin() {
//		boolean done = false;
//		boolean result = false;
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String username;
//		String password;
//		UserClass currentUser = null;
//
//		JFrame frame = new JFrame("test frame");
//
//		System.out.println("Welcome to the Catering System!");
//
//		while (!done) {
//			System.out.println("Please enter username:");
//
//			//username = input.nextLine();
//			username = "root";
//
//			System.out.println("Please enter password:");
//
//			//password = input.nextLine();
//			password = "admin";
//
//			currentUser = new UserClass(username, password);
//
//			// Return true if login info correct, false if wrong
//			result = loginController.checkLoginInfo(currentUser);
//
//			if (!result) {
//				System.out.println("Wrong username/password combination, " +
//						"please try again...");
//			} else {
//				done = true;
//			}
//		}
//
//		// input.close();
//
//		// You logged in successfully
//		System.out.println("Successful login");
//
//		return currentUser;
//	}
//
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public void displaySystemOptions(UserClass currentUser) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//		boolean done = false;
//
//		while (!done) {
//			System.out.println("Please pick an option:");
//			System.out.println("1.Register new User");
//			System.out.println("2.Change Password");
//			System.out.println("3.Back");
//			option = input.nextLine();
//			switch (option) {
//			case "1":
//				addNewUser();
//				break;
//			case "2":
//				changePassword(currentUser);
//				break;
//			case "3":
//				done = true;
//				break;
//			default:
//				System.out.println("Wrong input, Please try again...");
//				break;
//			}
//		}
//	}
//
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public void addNewUser(){
//		boolean done = false;
//		boolean result = false;
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String username;
//		String password;
//		String password2;
//		UserClass newUser;	
//
//		while (!done) {
//			System.out.println("Please enter new username:");
//			username = input.nextLine();
//
//			System.out.println("Please enter new password:");
//			password = input.nextLine();
//
//			System.out.println("Please re-enter new password:");
//			password2 = input.nextLine();
//
//			if(password.compareTo(password2) != 0){
//				System.out.println("Passwords dont match, please re-enter!");
//				continue;
//			}
//
//			newUser = new UserClass(username, password);
//
//			// Return true if login info correct, false if wrong
//			result = loginController.checkUserInfo(newUser);
//			if(!result){
//				System.out.println("Wrong username/password format, no spaces, password must be of length <= 5");
//				continue;
//			}
//
//			//MD5 the password and add it to the DB
//			result = loginController.addNewUser(newUser);
//			if(!result){
//				System.out.println("Error adding user, please try again");
//			}
//			else{
//				System.out.println("User added Successfully!!");
//				done = true;
//			}
//		}
//	}
//
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public void changePassword(UserClass currentUser){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		boolean done = false;
//		String option, oldPassword, newPassword, newPassword2;
//		
//		while (!done) {
//			System.out.println("Are you sure that you would like " +
//					"to change your username? (y/n)");
//			option = input.nextLine();
//			if (option.compareTo("n") == 0) {
//				return;
//			} 
//			else if (option.compareTo("y") == 0){
//				done = true;
//			}
//			else {
//				System.out.println("Wrong input, please try again.");
//			}
//		}
//		
//		done = false;
//		
//		while (!done) {
//			
//			System.out.println("Please enter old password");
//			oldPassword = input.nextLine();
//			
//			System.out.println("Please enter new password");
//			newPassword = input.nextLine();
//			
//			System.out.println("Please re-enter new password");
//			newPassword2 = input.nextLine();
//			
//			if(newPassword.compareTo(newPassword2) != 0)
//			{
//				System.out.println("New passwords dont match! Please try again.");
//				continue;
//			}
//			
//			if(currentUser.getPassword().compareTo(hashPassword(oldPassword)) != 0){
//				System.out.println("Wrong current password!");
//				return;
//			}
//						
//			loginController.changePassword(newPassword, currentUser);
//			done = true;
//		}
//
//	}
//	
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public String hashPassword(String password) {
//		String hashword = null;
//
//		try {
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			md5.update(password.getBytes());
//			BigInteger hash = new BigInteger(1, md5.digest());
//			hashword = hash.toString(16);
//		} catch (NoSuchAlgorithmException nsae) {
//			// ignore
//		}
//
//		return hashword;
//	}
//}
