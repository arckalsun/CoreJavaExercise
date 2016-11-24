package mouse;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * A component with mouse operations for adding and removing squares
 * @author john
 *
 */
public class MouseComponent extends JComponent
{
	private static final int SIDELENGTH = 30;
	private ArrayList<Rectangle2D> squares;
	private Rectangle2D current;
	
	//setSize(500, 400);
	
	public MouseComponent()
	{
		setSize(500, 400);
		
		squares = new ArrayList<>();
		current = null;
		
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		// draw all squares
		for ( Rectangle2D r : squares)
			g2.draw(r);
		
	}
	
	/**
	 * Finds the first squares containing a point.
	 * @param p a point
	 * @return the first square that contains p
	 */
	public Rectangle2D find(Point2D p)
	{
		for (Rectangle2D r : squares)
		{
			if (r.contains(p))
				return r;
		}
		return null;
	}
	
	/**
	 * Adds a square to the collection.
	 * @param p the center of the square
	 */
	public void add(Point2D p)
	{
		double x = p.getX();
		double y = p.getY();
		
		current = new Rectangle2D.Double(x - SIDELENGTH/2, y - SIDELENGTH/2, SIDELENGTH, SIDELENGTH);
		squares.add(current);
		repaint();
	}
	
	/**
	 * Removes a square from the collection.
	 * @param s the square to remove
	 */
	public void remove(Rectangle2D s)
	{
		if (s == null)
			return;
		if (s == current)
			current = null;
		squares.remove(s);
		repaint();
	}
	// the square containing the mouse cursor
	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			// add a new square if the cursor isn't inside a square
			current = find(event.getPoint());
			if (current == null) add(event.getPoint());
		}
		public void mouseClicked(MouseEvent event)
		{
			// remove the current square if double clicked
			current = find(event.getPoint());
			if (current != null && event.getClickCount() >= 2)
				remove(current);
				
		}
	}
	
	private class MouseMotionHandler implements MouseMotionListener
	{
		public void mouseMoved(MouseEvent event)
		{
			// set the mouse cursor to cross hairs if it is inside a rectangle
			
			if (find(event.getPoint()) == null)
				setCursor(Cursor.getDefaultCursor());
			else 
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			
		}
		
		public void mouseDragged(MouseEvent event)
		{
			if (current != null)
			{
				int x = event.getX();
				int y = event.getY();
				
				// drag the current rectangle to center it at (x, y)
				current.setFrame(x - SIDELENGTH / 2, y - SIDELENGTH / 2, SIDELENGTH, SIDELENGTH);
				repaint();
			}
		}
	}

}