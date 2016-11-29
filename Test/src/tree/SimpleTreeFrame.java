package tree;

import javax.swing.*;
import javax.swing.tree.*;
/**
 * This frame contains a simple tree that displays a manually constructed tree model.
 * @author john
 *
 */
public class SimpleTreeFrame extends JFrame
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 400;
	
	public SimpleTreeFrame()
	{
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// set up
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("world");
		DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
		root.add(country);
		DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
		country.add(state);
		DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
		state.add(city);
		city = new DefaultMutableTreeNode("Cupertino");
		state.add(city);
		state = new DefaultMutableTreeNode("Michigan");
		country.add(state);
		city = new DefaultMutableTreeNode("Ann Arbor");
		state.add(city);
		country = new DefaultMutableTreeNode("GerMany");
		root.add(country);
		state = new DefaultMutableTreeNode("Schleswig-Holstein");
		country.add(state);
		city = new DefaultMutableTreeNode("Kiel");
		state.add(city);
		
		// construct tree and put it in a scroll pane.
		
		JTree tree = new JTree(root);
		add(new JScrollPane(tree));
		
		setVisible(true);
		
	}
	public static void main(String[] args)
	{
		new SimpleTreeFrame();
	}
}
