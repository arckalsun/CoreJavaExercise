package BookManager;

import java.awt.Dimension;
import java.awt.Font.*;
import java.awt.*;
import java.awt.Graphics;

import javax.swing.*;

public class errorPanel extends JPanel
{
	private Font f = new Font("Serif",Font.BOLD,36);
	
	
	public errorPanel()
	{
		this.setBounds(3, 10, 700, 400);
		this.setVisible(true);
		
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.gray);
		String str = "您没有权限查看本模块!";
		g2.setFont(f);
		g2.drawString(str, 100, 200);
		
	}


	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
				
		JPanel p = new errorPanel();
		frame.add(p);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
