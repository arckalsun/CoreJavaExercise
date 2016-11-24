package checkBox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * A frame with a panel containing check box.
 * @author john
 *
 */

public class CheckBoxTest
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame frame = new CheckBoxFrame();
						frame.setTitle("CheckBoxTest");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
					}
				});
	}
}

class CheckBoxFrame extends JFrame
{
	
	private JLabel label2;
	private JCheckBox bold;
	private JCheckBox italic;
	private ButtonGroup group;
	private JPanel buttonPanel;
	
	public static final int FONTSIZE = 12;
	
	// 构造函数没有返回值
	public CheckBoxFrame()
	{
		setLayout(new BorderLayout());
		
	
		// add label text
		label2 = new JLabel("The quick brown fox jumps over the lazy dog.");
		label2.setFont(new Font("Serif",Font.PLAIN, FONTSIZE));
		add(label2, BorderLayout.CENTER);
		
		
		ActionListener listener = new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						int mode = 0;
						int size = Integer.parseInt(group.getSelection().getActionCommand());
						if (bold.isSelected()) mode += Font.BOLD;
						if (italic.isSelected()) mode += Font.ITALIC;
						label2.setFont(new Font("Serif", mode, size));
						pack();
					}
				};
				
		// add RadioButtons
		buttonPanel = new JPanel();
		group = new ButtonGroup();
		Border etched = BorderFactory.createEtchedBorder();
		Border titled = BorderFactory.createTitledBorder(etched,"Radio BUtton");
		buttonPanel.setBorder(titled);
		
		addRadioButton("Small", 12);
		addRadioButton("Medium", 24);
		addRadioButton("Large", 36);
		
		add(buttonPanel, BorderLayout.NORTH);
				
		bold = new JCheckBox("Bold");
		italic = new JCheckBox("Italic");
		bold.addActionListener(listener);
		italic.addActionListener(listener);
		
		JPanel panel = new JPanel();
		panel.add(bold);
		panel.add(italic);
		
		add(panel, BorderLayout.SOUTH);
		pack();
		//setSize(new Dimension(400,300));
		
	}
	
	/**
	 * Adds a radio button that sets the font size of the sample text.
	 * @param name the string to appear on the button.
	 * @param size the font size that this button sets
	 */
	public void addRadioButton(String name, int size)
	{
		boolean selected = size == FONTSIZE;
		JRadioButton button = new JRadioButton(name, selected);
		button.setActionCommand("" + size);
		group.add(button);
		buttonPanel.add(button);
		
		// this listener sets the label font size
		ActionListener listener = new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						// size refers to the final parameter of the addRadioButton method.
						label2.setFont(new Font("Serif",Font.PLAIN, size));
						pack();
					}
				};
		button.addActionListener(listener);
	}
}

