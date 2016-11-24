 package test;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;


public class MainFrame extends JFrame{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
                 MainFrame mai=new MainFrame();
                 mai.setLayout(new BorderLayout());
                 JToolBar jtoolbar=new JToolBar();
                 JLabel jl=new JLabel("state");
                 jtoolbar.add(jl);
                 JPanel jpanel1=new JPanel();
                 JButton jb1=new JButton("North");
                 
                 jpanel1.setPreferredSize(new Dimension(300, 150));//关键代码,设置JPanel的大小
                 jpanel1.add(jb1);
                  jpanel1.setBorder(BorderFactory.createEtchedBorder());
                 JButton jb2=new JButton("Center");
                 
                 
                 mai.add(jpanel1,BorderLayout.EAST);
                 mai.add(jb2,BorderLayout.CENTER);
                 mai.add(jtoolbar,BorderLayout.SOUTH);
                 mai.setSize(300, 400);
                 mai.setVisible(true);
                 mai.setDefaultCloseOperation(EXIT_ON_CLOSE);
                 
	}

}
