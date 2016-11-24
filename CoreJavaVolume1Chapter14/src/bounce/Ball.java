package bounce;
import java.awt.Color;
import java.awt.geom.*;
import java.util.Random;
/**
 * A ball that moves and bounces off the edges of a rectangle.
 * @author john
 *
 */
public class Ball
{
	private static final int XSIZE = 20;
	private static final int YSIZE = 20;
	//球的初始位置
	private double x = 0;
	private double y = 0;
	
	// x和y方向上的步长
	private double dx = 1;
	private double dy = 1;
	
	public Color color;
	private  static Random random = new Random();
	
	public Ball()
	{
		//y = random.nextFloat()*1000;
		float speed = random.nextFloat()*10;
		if (speed < 0.1) speed += 1;
		dy = dx = speed;
		//BounceFrame.label.setText("y= "+y);
		float cr = random.nextFloat();
		float cg = random.nextFloat();
		float cb = random.nextFloat();
		this.color = new Color(cr,cg,cb);
		System.out.println("speed=" + speed + "; cr=" +cr+"; cg=" +cg+"; cb=" +cb);
	}
	/**
	 * Moves the ball to the next position. reversing direction if it hits one of the edges
	 */
	public void move(Rectangle2D bounds)
	{
		x += dx;
		y += dy;
		if (x < bounds.getMinX())
		{
			x = bounds.getMinX();
			dx = -dx;
		}
		if (x + XSIZE >= bounds.getMaxX())
		{
			x = bounds.getMaxX() - XSIZE;
			dx = - dx;
			
		}
		if (y < bounds.getMinY())
		{
			y = bounds.getMinY();
			dy = -dy;
		}
		if (y + YSIZE >= bounds.getMaxY())
		{
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}
	}
	public Ellipse2D getShape()
	{
		return new Ellipse2D.Double(x,y,XSIZE,YSIZE);
	}

}
