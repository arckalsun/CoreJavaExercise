package action;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * A frame with a panel that demonstrates color change action.
 *
 */
public class ActionFrame extends JFrame
{
	private JPanel buttonPanel;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public ActionFrame()
	{
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		buttonPanel = new JPanel();
		
		// define actions
		Action yellowAction = new  ColorAction("Yellow", new ImageIcon("yellow.gif"), Color.YELLOW);
		Action blueAction = new  ColorAction("Blue", new ImageIcon("blue.gif"), Color.BLUE);
		Action redAction = new  ColorAction("Red", new ImageIcon("red.gif"), Color.RED);
		
		// create JButton
		JButton yellowButton = new JButton(yellowAction);
		JButton blueButton = new JButton(blueAction);
		JButton redButton = new JButton(redAction);
		
		yellowButton.setSize(64,32);
		blueButton.setSize(64,32);
		redButton.setSize(64,32);
		
		// add buttons for these actions
		buttonPanel.add(yellowButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(redButton);
		
		// add panel to frame
		add(buttonPanel);
		
		// associate the Y, B and R keys with names
		InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
		imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
		imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");
		
		// associate the names with actions
		ActionMap amap = buttonPanel.getActionMap();
		amap.put("panel.yellow", yellowAction);
		amap.put("panel.blue", blueAction);
		amap.put("panel.red", redAction);
		
	}
	
	public class ColorAction extends AbstractAction
	{
		/**
		 * Constructs a color action
		 * @param name the name to show on the button
		 * @param icon the icon to display on the button
		 * @param c the background color
		 */
		public ColorAction(String name, Icon icon, Color c)
		{
			putValue(Action.NAME, name);
			putValue(Action.SMALL_ICON, icon);
			putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
			putValue("color", c);
		}
		
		public void actionPerformed(ActionEvent event)
		{
			Color c = (Color) getValue("color");
			buttonPanel.setBackground(c);
		}
	}
}
