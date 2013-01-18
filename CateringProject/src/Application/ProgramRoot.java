package Application;

import UserInterface.UIControllerClass;

/**
 * Starting point of the program
 * 
 * @author Mark Lessard
 */
public class ProgramRoot {

	/**
	 * Constructs an instance of the program.
	 */
	public ProgramRoot(){}
	
	/**
	 * Starts program.
	 */
	@SuppressWarnings("unused")
	public void go(){
		UIControllerClass UI = new UIControllerClass();
	}
	
	/**
	 * Creates a program root object and then gives control to program root.
	 * <p>
	 * Written by Mark Lessard
	 */
	public static void main(String[] args) {
		ProgramRoot system = new ProgramRoot();
		system.go();
	}
}
