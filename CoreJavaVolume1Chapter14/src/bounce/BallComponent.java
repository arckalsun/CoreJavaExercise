package bounce;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * The component that draws the balls.
 * @author john
 *
 */
public class BallComponent extends JPanel
{
	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;
	
	private java.util.List<Ball> balls = new ArrayList<>();
	/**
	 * Add a ball to the component.
	 * @param b the ball to add
	 */
	
	public void add(Ball b)
	{
		balls.add(b);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		for (Ball b : balls)
		{
			
			g2.setColor(b.color);
			g2.fill(b.getShape());
		}
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
