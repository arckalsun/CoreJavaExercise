package BookManager;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReturnBook extends JPanel implements ActionListener
{
	DataBase db;
	String sql;
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	private JLabel label = new JLabel("请输入您的学号");
	private JTextField textField = new JTextField();
	private JButton [] buttons = {
			new JButton("挂失"),
			new JButton("归还"),
			new JButton("确定")
	};
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	
	Vector<String> head = new Vector<>();
	{
		head.add("书号");
		head.add("学号");
		head.add("借阅时间");
		head.add("还书时间");
		head.add("是否过期");
		head.add("是否预约");
	};
	Vector<Vector> data = new Vector<>();
	DefaultTableModel dtm = new DefaultTableModel(data,head);
	JTable table = new JTable(dtm);
	JScrollPane jspn = new JScrollPane(table);
	
	public ReturnBook()
	{
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		jpt.add(label);
		label.setBounds(2, 20, 100, 20);
		jpt.add(textField);
		textField.setBounds(100, 20, 300, 20);
		jpt.add(buttons[2]);
		buttons[2].setBounds(500, 20, 60, 20);
		buttons[2].addActionListener(this);
		for(int i=0; i<2; i++)
		{
			jpt.add(buttons[i]);
			buttons[i].setBounds(2+i*100, 60, 60, 20);
			buttons[i].addActionListener(this);
		}
		
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(100);
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
		new ReturnBook();
	}

}
