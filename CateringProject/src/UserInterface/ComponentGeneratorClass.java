package UserInterface;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

/**
 * This class creates a generator that will allow easy adding of buttons
 * text fields and labels to a given panel and cardholder. Sets up some
 * upon initialisation.
 * 
 * @author Cody Plante
 */
public class ComponentGeneratorClass {
	JPanel cardHolder;  //main panel used as cardholder
	Dimension textFieldSize = new Dimension(250, 30);
	Dimension buttonSize = new Dimension(175, 40);
	
	/** Constructs a new generator object that sets the cardholder to 
	 *  set the components to
	 * 
	 * @param cardHolder
	 */
	public ComponentGeneratorClass(JPanel cardHolder){
		this.cardHolder = cardHolder;
		
	}	

	/**	
	 * This method returns a button that is created with a given panel to being 
	 * added to and the given name. It will also generate rigidspace above the 
	 * button.
	 * 
	 * @param panel
	 * @param name
	 * @param ViewName
	 * @param rigidLength
	 * @return	button
	 */
	public JButton createButton(JPanel panel, String name, String ViewName, int rigidLength){
		JButton tempButton = new JButton(name);
		
		if (rigidLength > 0){	//checks rigid length is valid, and addes it to panel
			panel.add(Box.createRigidArea(new Dimension(0, rigidLength)));
		}
		panel.add(tempButton);	// adds the button and sets to center alignment
		tempButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		if (ViewName != null){	//checks viewName is valid and sets a panel swap to given name when pressed
			tempButton.addActionListener(new ButtonListener(cardHolder, ViewName));
		}
		//sets the max size to given dimensions
		tempButton.setMaximumSize(buttonSize);
		return tempButton;
	}
	
	/**	
	 * This method returns a button that is created with a given panel to being 
	 * added to and the given name. It will also generate rigidspace above the 
	 * button but provides no action listeners.
	 * 
	 * @param panel
	 * @param name
	 * @param rigidLength
	 * @return	button
	 */
	public JButton createButton(JPanel panel, String name, int rigidLength){
		JButton tempButton = new JButton(name);
		
		if (rigidLength > 0){	//checks rigidlength is valid and adds to panel
			panel.add(Box.createRigidArea(new Dimension(0, rigidLength)));
		}
		panel.add(tempButton);	//adds button to panel and centers then sets max size
		tempButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		tempButton.setMaximumSize(buttonSize);
		
		return tempButton;
	}
	
	/**
	 * This method returns a textfield that is created with a given panel to being 
	 * added to and a given name. It will generate size 5 rigid space above the textfield.
	 * 
	 * @param panel
	 * @param name
	 * @return	text field
	 */
	public JTextField createTextField(JPanel panel, String name){
		JTextField tempTextField = new JTextField(name);
		//adds rigid space to panel
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		// adds text field to panel, sets alignment and max size
		panel.add(tempTextField);
		tempTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		tempTextField.setMaximumSize(textFieldSize);
		
		return tempTextField;
	}
	
	/**
	 * This method returns a label that is created with a given panel to being 
	 * added to and a given name. It will generate size 5 rigid space above the label.
	 * 
	 * @param panel
	 * @param label
	 * @return	label
	 */
	public JLabel createLabel(JPanel panel, String label){
		JLabel tempLabel = new JLabel(label);
		//adds rigid space to panel
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		//adds label to panel and sets alignment and max size
		panel.add(tempLabel);
		tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		tempLabel.setMaximumSize(textFieldSize);
		
		return tempLabel;
	}
	
	/** 
	 * This method returns a label that is created with a given panel to being 
	 * added to and a given name. It will generate size 5 rigid space above the label
	 * and has given a string parameter to flag that it does not what a max size set.
	 * 
	 * @param panel
	 * @param label
	 * @param center
	 * @return	label
	 */
	public JLabel createLabel(JPanel panel, String label, String center){
		JLabel tempLabel = new JLabel(label);
		// adds rigid space to panel 
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		// adds button to panel and sets alignment
		panel.add(tempLabel);
		tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		return tempLabel;
	}
	
	/**
	 * This method sets rigid space to a panel given the panel and size
	 * 
	 * @param panel
	 * @param rigidLength
	 */
	public void createRigidSpace(JPanel panel, int rigidLength){
		panel.add(Box.createRigidArea(new Dimension(0, rigidLength)));
	}
}
