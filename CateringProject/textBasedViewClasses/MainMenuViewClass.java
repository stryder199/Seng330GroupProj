package UserInterface;
//package UserInterface;
//
//import java.util.Scanner;
//
//import Event.EventViewClass;
//import Login.UserClass;
//
//public class MainMenuClass {
//
//	public MainMenuClass() {
//	}
//
//	/******************************************************************
//	 * Written by Mark Lessard 
//	 *******************************************************************/
//	public String displayRoot(UserClass currentUser, EventViewClass eventView) {
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String option;
//
//		System.out.println("\nWelcome to the Catering System " + currentUser.getUsername() + "!\n");
//		System.out.println("Your pending events:");
//		eventView.printAllPendingEvents(currentUser);
//		
//		System.out.println("\nPlease pick an option:");
//		System.out.println("1.Event");
//		System.out.println("2.Equipment");
//		System.out.println("3.Staff");
//		System.out.println("4.Client");
//		System.out.println("5.Meals");
//		System.out.println("6.System Admin");
//		System.out.println("7.Quit");
//		option = input.nextLine();
//
//		return option;
//	}
//
//}
