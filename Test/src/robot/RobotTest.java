package robot;

import java.awt.*;
import java.awt.event.InputEvent;

public class RobotTest
{
	public static void main(String[] args)
	{
		// circle();
		//line_circle();
		draw_heart();
		System.out.println("画图完毕。");
	}

	public static void circle()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try
		{
			Robot robot = new Robot(screen);
			robot.delay(3000);
			PointerInfo pi = MouseInfo.getPointerInfo();
			Point p = pi.getLocation();

			int x = (int) p.getX();
			int y = (int) p.getY();

			double a, b;
			int r; // 半径
			for (r = 200; r > 0; r = r - 5)
			{
				robot.mouseMove(x + r, y);
				System.out.println("r = " + r);
				for (double i = 0; i <= 2 * Math.PI; i = i + Math.PI / 4) // 0 -
																			// 2*
																			// PI
				{
					a = r * Math.cos(i);
					b = r * Math.sin(i);
					if (i == 0)
						robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseMove((int) (a + (x)), (int) (b + (y)));

					robot.delay(50);
				}
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				x = x - 10;
			}
		} catch (Exception e)
		{
			System.out.println("error: " + e.toString());
			e.printStackTrace(System.out);
		}
	}

	/**
	 * 画线
	 */
	public static void line()
	{
		final int line = 400;

		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try
		{
			Robot robot = new Robot(screen);
			robot.delay(3000);
			PointerInfo pi = MouseInfo.getPointerInfo();
			Point p = pi.getLocation();

			int x = (int) p.getX();
			int y = (int) p.getY();
			int unit = 10;

			// 左下角 x, y
			for (int i = 0; i <= line / unit; i++)
			{
				robot.mouseMove(x, y - line + i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + i * unit, y);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
			}

			// 右上角 x+line, y-line
			for (int i = 0; i <= line / unit; i++)
			{

				robot.mouseMove(x + line, y - line + i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + i * unit, y - line);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
			}
			// 左上角 x, y - line
			for (int i = 0; i <= line / unit; i++)
			{

				robot.mouseMove(x, y - i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + i * unit, y - line);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
			}
			// 右下角 x+line , y
			for (int i = 0; i <= line / unit; i++)
			{

				robot.mouseMove(x + i * unit, y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + line, y - i * unit);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
			}
			line2(x + line / 2, y - line / 2);
		} catch (Exception e)
		{
			System.out.println("error: " + e.toString());
			e.printStackTrace(System.out);
		}
	}

	public static void line2(int centerX, int centerY)
	{
		final int line = 200;

		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try
		{
			Robot robot = new Robot(screen);
			robot.delay(3000);

			int x = centerX;
			int y = centerY;
			int unit = 10;

			// 象限1
			for (int i = 0; i <= line / unit; i++)
			{
				robot.mouseMove(x, y - line + i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + i * unit, y);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);

				// 象限2

				robot.mouseMove(x, y - line + i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x - i * unit, y);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);

				// 象限3

				robot.mouseMove(x - line + i * unit, y);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x, y + i * unit);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);

				// 象限4

				robot.mouseMove(x, y + line - i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + i * unit, y);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
			}

		} catch (Exception e)
		{
			System.out.println("error: " + e.toString());
			e.printStackTrace(System.out);
		}
	}

	public static void line_circle()

	{
		final int line = 200;

		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try
		{
			Robot robot = new Robot(screen);
			robot.delay(3000);
			PointerInfo pi = MouseInfo.getPointerInfo();
			Point p = pi.getLocation();

			int x = (int) p.getX();
			int y = (int) p.getY();
			int unit = 10;

			// 象限1
			for (int i = 0; i <= line / unit; i++)
			{
				robot.mouseMove(x + line, y - i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + line - i * unit, y - line);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(50);

				// 象限2

				robot.mouseMove(x - line, y - i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x - line + i * unit, y - line);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(50);

				// 象限3

				robot.mouseMove(x - i * unit, y + line);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x - line, y + line - i * unit);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(50);

				// 象限4

				robot.mouseMove(x + line, y + i * unit);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseMove(x + line - i * unit, y + line);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(50);
			}
			line2(x, y);

		} catch (Exception e)
		{
			System.out.println("error: " + e.toString());
			e.printStackTrace(System.out);
		}
	}
	
	// Draw heart
	public static void draw_heart()
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = environment.getDefaultScreenDevice();
		try
		{
			Robot robot = new Robot(screen);
			robot.delay(3000);
			PointerInfo pi = MouseInfo.getPointerInfo();
			Point p = pi.getLocation();

			int x = (int) p.getX();
			int y = (int) p.getY();
			
			int n = 200;
			for ( float a = 1.5f; a >= -1.5f; a -= 0.1f)
			{
				for ( float b = -1.5f; b < 1.5f; b += 0.05f)
				{
					
					if ((Math.pow((b*b + a*a - 1), 3) - b * b* a*a*a)<=0.0f && (Math.pow((b*b + a*a - 1), 3) - b * b* a*a*a) >= -1f)
					{
						System.out.println( (Math.pow((b*b + a*a - 1), 3) - b * b* a*a*a) );
						robot.mouseMove(x -(int)( b* 200), y - (int)(a * 200));
						robot.mousePress(InputEvent.BUTTON1_MASK);
						
						robot.delay(5);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
						
						
					}
					//System.out.println("a = " + a + "; b = " + b);
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}
