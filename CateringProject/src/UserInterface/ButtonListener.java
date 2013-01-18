package UserInterface;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * This class is used for GUI display's button listener.
 * 
 * @author Mark Lessard 
 */
public class ButtonListener implements ActionListener{
	private JPanel currentPanel;
	private String panelToShow;
	
	/**
	 * Constructs a new instance of button listener.
	 */
	public ButtonListener (JPanel currentPanel, String panelToShow){
		this.currentPanel = currentPanel;
		this.panelToShow = panelToShow;
	}
	
	/**
	 * Performs an action when button is pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		CardLayout cLayout = (CardLayout) currentPanel.getLayout();
		cLayout.show(currentPanel, panelToShow);
	}
}