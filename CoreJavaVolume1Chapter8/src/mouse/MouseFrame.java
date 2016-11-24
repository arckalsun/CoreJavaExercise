package mouse;

import javax.swing.*;

/**
 * A Frame containing a panel for testing mouse operations
 *
 */
public class MouseFrame extends JFrame
{

	
	public MouseFrame()
	{
		setSize(500, 400);
		
		add(new MouseComponent());
		pack();
	}
}
