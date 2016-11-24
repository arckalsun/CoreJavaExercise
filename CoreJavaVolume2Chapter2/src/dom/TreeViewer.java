package dom;
import java.awt.EventQueue;


import javax.swing.JFrame;

public class TreeViewer
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame frame = new DOMTreeFrame();
						frame.setTitle("TreeViewer");
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
					}
				});
	}

}
