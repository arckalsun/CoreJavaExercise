package BookManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExceedTime extends JPanel implements ActionListener
{
	private JPanel panel = new JPanel();
	
	private JLabel [] labels = { new JLabel("����������ѧ��"), new JLabel("��������Ҫ���Ŀ��")};
	private JTextField[] textFields = { new JTextField(), new JTextField()};
	private JButton [] buttons = { new JButton("��ѯǷ��"), new JButton("����")};
	
	
	public ExceedTime()
	{
		this.setLayout(new GridLayout(1,1));
		panel.setLayout(null);
		// TODO Auto-generated constructor stub
		for (int i=0; i<2; i++)
		{
			panel.add(labels[i]);
			panel.add(textFields[i]);
			panel.add(buttons[i]);
			labels[i].setBounds(10, 40+i*40, 120, 20);
			textFields[i].setBounds(130, 40+i*40, 200, 20);
			buttons[i].setBounds(20+i*200,150, 100, 20);
			
			buttons[i].addActionListener(this);
		}
		
		this.add(panel);
		this.setBounds(3,10,700,400);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ExceedTime();
	}

}
