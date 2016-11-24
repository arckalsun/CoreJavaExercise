package ballTest;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Ball
{
	public static int r = 50;
	private static final int XSIZE = r;
	private static final int YSIZE = r;
	public  double x = 0;
	public  double y = 0;
	public double dx = 1;
	public double dy = 1;
	public int steps = 1000;
	public Color color;
	private  static Random random = new Random();
	
	public Ball()
	{
		/*float speed = random.nextFloat()*10;
		if (speed < 0.1) speed += 1;
		dy = dx = speed;*/
		//BounceFrame.label.setText("y= "+y);
		float cr = random.nextFloat();
		float cg = random.nextFloat();
		float cb = random.nextFloat();
		this.color = new Color(cr,cg,cb);
		System.out.println(  "cr=" +cr+"; cg=" +cg+"; cb=" +cb);
	}
	public void move(Rectangle2D bounds)
	   {
	      x += dx;
	      y += dy;
	      //
	      //¼ì²â´úÂë
          //BallDetector.detect(this,);
          
	      if (x < bounds.getMinX())
	      { 
	         x = bounds.getMinX();
	         dx = -dx;
	      }
	      if (x + XSIZE >= bounds.getMaxX())
	      {
	         x = bounds.getMaxX() - XSIZE; 
	         dx = -dx; 
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

	   /**
	      Gets the shape of the ball at its current position.
	   */
	   public Ellipse2D getShape()
	   {
	      return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	   }
	

}
