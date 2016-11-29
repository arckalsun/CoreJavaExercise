package BookManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExceedTime extends JPanel implements ActionListener
{
	private JPanel panel = new JPanel();
	
	private JLabel [] labels = { new JLabel("请输入学号"), new JLabel("请输入款额")};
	private JTextField[] textFields = { new JTextField(), new JTextField()};
	private JButton [] buttons = { new JButton("查询欠费"), new JButton("交费")};
	
	
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
			if (Root.isStudent)
			{
				textFields[0].setText(Root.stuNo);
				textFields[0].setEditable(false);
			}
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
		int day=0;
		DataBase db = new DataBase();
		String sno= textFields[0].getText().trim();
		if (sno.equals(""))
		{
			JOptionPane.showMessageDialog(this, "学号不能为空!!!","消息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (sno.matches("\\D"))
		{
			JOptionPane.showMessageDialog(this, "学号只能为数字!!!","消息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sql = "select DelayTime from exceedtime where StuNO="+sno;
		db.selectDB(sql);
		try{
			int flag = 0;
			while (db.rs.next())
			{
				flag++;
				day+=db.rs.getInt(1);
			}
			if (flag ==0)
			{
				JOptionPane.showMessageDialog(this, "您所借的书还没有超期!!!","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		if (e.getSource()==buttons[0])//查询欠费
		{
			if (day>0)
			{
				JOptionPane.showMessageDialog(this, "该书已经过期，应该交纳罚款为: "+day*0.1+"元,","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(this, "您所借的书还没有超期!!!","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==buttons[1])//交费 按钮
		{
			if (textFields[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "请输入交款金额!!!","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int k=JOptionPane.showConfirmDialog(this, "应该交纳罚款为: "+day*0.1+"元, 是否交纳罚款?","消息",JOptionPane.YES_NO_OPTION);
			if (k==JOptionPane.YES_OPTION)
			{
				int ii = Integer.parseInt(textFields[1].getText().trim());
				if (ii<(day*0.1))
				{
					sql = "update exceedtime set delaytime=delaytime-"+ii/0.1+" where stuNO="+sno;
					db = new DataBase();
					int i = db.updateDB(sql);
					if (i==1)
					{
						JOptionPane.showMessageDialog(this, "您已成功交费  "+ii+"元，还需交纳"+(day*0.1-ii)+"元!!!","消息",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else
					{
						JOptionPane.showMessageDialog(this, "对不起，交费失败!!!","消息",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "您已成功交费"+day*0.1+"元!","消息",JOptionPane.INFORMATION_MESSAGE);
					textFields[1].setText("");
					sql = "delete from exceedtime where StuNO="+sno;
					db.updateDB(sql);
					sql = "update STUDENT set permitted='是' where StuNO="+sno;
					db.updateDB(sql);
					
					
				}
			}
			
		}
		db.dbClose();
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ExceedTime();
	}

}
