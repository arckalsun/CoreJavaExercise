package ballTest;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * The component that draws the balls.
 * 
 * @version 1.34 2012-01-26
 * @author Cay Horstmann
 */
public class BallComponent extends JComponent
{
	private static final int DEFAULT_WIDTH = 450;
	private static final int DEFAULT_HEIGHT = 350;

	private java.util.List<Ball> balls = new ArrayList<>();

	/**
	 * Add a ball to the panel.
	 * 
	 * @param b
	 *            the ball to add
	 */
	public void add(Ball b)
	{
		balls.add(b);
	}

	public void reaction()
	{
		// System.out.print("reaction:\t");
		if (balls.size() < 2)
			return;
		for (int i = 0; i < balls.size() - 1; i++)
		{
			for (int j = i + 1; j < balls.size(); j++)
			{
				if (detect(balls.get(i), balls.get(j)))
				{
					//System.out.println(" 相撞 ");
					// 速度方向改变
					balls.get(i).dx = -balls.get(i).dx;
					balls.get(i).dy = -balls.get(i).dy;
					balls.get(j).dx = -balls.get(j).dx;
					balls.get(j).dy = -balls.get(j).dy;
				}
			}

		}

	}

	private boolean detect(Ball b1, Ball b2)
	{
		double distance = Math.sqrt((b1.x - b2.x) * (b1.x - b2.x) +(b1.y - b2.y) * (b1.y - b2.y));
		if (distance < Ball.r && distance > Ball.r*0.9)
			return true;
		else
			return false;

	}

	public void paintComponent(Graphics g)
	{
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
