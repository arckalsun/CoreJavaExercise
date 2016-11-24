package bounce;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Shows an animated bouncing ball.
 * @author john
 *
 */
public class Bounce
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new BounceFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class BallRunnable implements Runnable
{
	private Ball ball;
	private Component component;
	
	public static final int STEPS = 2000;
	public static final int DELAY = 10;
	/**
	 * Constructs the runnable
	 * @param ball the ball to bounce
	 * @param aComponent the component in which the ball bounces
	 */
	public BallRunnable(Ball aBall, Component aComponent)
	{
		ball = aBall;
		component = aComponent;
		
	}
	public void run()
	{
		try
		{
			for (int i = 1; i <= STEPS; i++)
			{
				ball.move(component.getBounds());
				component.repaint();
				Thread.sleep(DELAY);
			}
		}
		catch (InterruptedException e)
		{
			
		}
	}
}
/**
 * The frame with ball component and buttons.
 * @author john
 *
 */
class BounceFrame extends JFrame
{
	private BallComponent comp;
	private JLabel label;
	
	private int count = 0;
	/**
	 * Constructs the frame with the component for showing the bouncing ball and Start and Close buttons
	 */
	public BounceFrame()
	{
		setTitle("Bounce");
		comp = new BallComponent();
		//label.setText("label");
		add(comp,BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		//add(label);
		
		addButton(buttonPanel, "Add", new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						addBall();
						
					}
				});
		addButton(buttonPanel,"Close", new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						System.exit(0);
					}
				});
		
		add(buttonPanel, BorderLayout.SOUTH);
		//add(label);
		pack();
	}
	/**
	 * Adds a button to a container.
	 * @param c the container
	 * @param title the button title
	 * @param listener the action listener for the button
	 */
	public void addButton(Container c, String title, ActionListener listener)
	{
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}
	/**
	 * Adds a bouncing ball to the canvas and starts a thread to make it bounce.
	 */
	public void addBall()
	{
		for (int i = 0; i<100;i++)
		{
			try
			{
			count++;
			//label.setText("Ð¡ÇòÊý£º"+count);
			System.out.print("Ð¡Çò£º"+count+"\t");
			Ball b = new Ball();
			comp.add(b);
			Runnable r =new BallRunnable(b, comp);
			Thread t = new Thread(r);
			t.start();
			Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				
			}
		}
	}
}