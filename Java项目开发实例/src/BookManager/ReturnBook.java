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
			new JButton("查询")
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
		if(Root.isStudent)
		{
			textField.setText(Root.stuNo);
			textField.setEditable(false);
		}
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
		// 查询按钮事件
		if(e.getSource()==buttons[2])
		{
			if (textField.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "请输入学号","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else
			{
				String sql = "select * from RECORD where StuNo="+textField.getText().trim();
				db = new DataBase();
				db.selectDB(sql);
				Vector<Vector> vtemp = new Vector<>();
				try
				{
					int k=0;
					while (db.rs.next())
					{
						k++;
						Vector<String> v = new Vector<>();
						for (int i=1; i<=6;i++)
						{
							v.add(db.rs.getString(i).trim());
						}
						vtemp.add(v);
						table.clearSelection();
						dtm.setDataVector(vtemp, head);
						table.updateUI();
						table.repaint();
						
					}
					if(k==0)
					{
						JOptionPane.showMessageDialog(this, "未查询到记录","消息",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}catch (Exception ea)
				{
					ea.printStackTrace();
				}
			}
		}
		
		//归还 按钮事件
		if (e.getSource()==buttons[1])
		{
			int row = table.getSelectedRow();
			if (row<0)
			{
				JOptionPane.showMessageDialog(this, "请选择要归还的图书","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String bookNO = table.getValueAt(row, 0).toString();
			int stuNO = Integer.parseInt(table.getValueAt(row, 1).toString());
			int bookno = Integer.parseInt(bookNO);
			int flag = checkTime(stuNO,bookno);
			if (flag==-1)
			{
				sql = "update student set permitted='否' where StuNO="+stuNO;
				db = new DataBase();
				db.updateDB(sql);
				db.dbClose();
			}
			if (flag==0)
				return;
			sql = "Delete from record where BookNO="+bookNO;
			db = new DataBase();
			db.updateDB(sql);
			sql = "update book set borrowed='否' where BookNO=" +bookNO;
			
			db.updateDB(sql);
			db.dbClose();
			updateTable();
		}
		if (e.getSource() == buttons[0])//挂失 按钮
		{
			int row = table.getSelectedRow();
			if (row<0)
			{
				JOptionPane.showMessageDialog(this, "请选择要归还的图书","消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			loseBook(row);
			updateTable();
		}
		
	}
	public int checkTime(int sno, int bno)
	{
		int day=0;
		int flag=0;
		String bname="";
		Calendar calendar = Calendar.getInstance();
		Date now = new Date();
		calendar.setTime(now);
		String returnTime = "";
		sql = "select ReturnTime from record where StuNO="+sno+" and BookNO=" +bno;
		
		db = new DataBase();
		db.selectDB(sql);
		try
		{
			if (db.rs.next())
			{
				returnTime = db.rs.getString(1);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		String [] strday = returnTime.split("\\.");
		int ryear = Integer.parseInt(strday[0].trim());
		int rmonth = Integer.parseInt(strday[1].trim());
		int rday = Integer.parseInt(strday[2].trim());
		day = ( calendar.get(Calendar.YEAR)  - ryear)*365+(calendar.get(Calendar.MONTH) +1 - rmonth) * 30+ (calendar.get(Calendar.DAY_OF_MONTH) -rday);
		if (day==-30)
		{
			JOptionPane.showMessageDialog(this, "今天借的书不能还!!!","消息",JOptionPane.INFORMATION_MESSAGE);
			flag = 0;
		}
		else if (day>0)
		{
			int i = JOptionPane.showConfirmDialog(this, "该书已经过期，应该交纳罚款为: "+day*0.1+"元, 是否交纳罚款?","消息",JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION)
			{
				JOptionPane.showMessageDialog(this, "您已经成功交费 "+day*0.1+"元","消息",JOptionPane.INFORMATION_MESSAGE);
				flag = -2;
			}
			else
			{
				flag = -1;
				sql = "select BookName from BOOK where BookNO="+bno;
				db.selectDB(sql);
				try
				{
					if (db.rs.next())
					{
						bname = db.rs.getString(1).trim();
					}
				}catch (Exception e)
				{
					e.printStackTrace();
				}
				sql = "insert into exceedtime ( StuNO, BookNO, BookName,DelayTime) values("+sno+"," +bno +",'" +bname+"',"+day+")";
				db.updateDB(sql);
			}
		}
		else
			flag = 1;
		return flag;
	}
	public void loseBook(int row)
	{
		String bname="";
		int lbno=0;
		int bno = Integer.parseInt(table.getValueAt(row, 0).toString());
		
		String sno = table.getValueAt(row, 1).toString();
		sql = "select BookName from BOOK where BookNO="+bno;
		db = new DataBase();
		db.selectDB(sql);
		try
		{
			if (db.rs.next())
			{
				bname=db.rs.getString(1).trim();
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//sql = "insert into LOSEBOOK values("+lbno+","+sno+",'"+bname+"',)";
		sql = "select MAX(LBNO) from losebook"; //找到最大的丢失记录号
		db.selectDB(sql);
		try
		{
			if (db.rs.next())
			{
				lbno = db.rs.getInt(1);
				lbno++;
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		sql = "insert into losebook values (" +lbno+","+sno+","+bno+",'"+bname+"')";
		db.updateDB(sql);
		sql = "select BookNO from orderreport where BookNO="+bno;
		db.selectDB(sql);
		try
		{
			while (db.rs.next())
			{
				sql = " delete from orderreport where BookNO=" +bno;
				db.updateDB(sql);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sql = "select BookNO from exceedtime where BookNO="+bno;//检查超期表中是否有该书，若有删除
		db.selectDB(sql);
		try{
			while (db.rs.next())
			{
				sql = "delete from exceedtime where BookNO="+bno;
				db.updateDB(sql);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		//从借书表中删除丢失图书的记录
		sql = "delete from record where BookNO="+bno;
		db.updateDB(sql);
		//从图书表中删除丢失的图书
		sql = "delete from book where BookNO="+bno;
		if (db.updateDB(sql)>0)
		{
			JOptionPane.showMessageDialog(this, "挂失成功!!!","消息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "挂失失败!!!","消息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
	}
	public void updateTable()
	{
		sql = "select * from record where StuNO="+textField.getText().trim();
		db = new DataBase();
		db.selectDB(sql);
		Vector<Vector> vtemp = new Vector<>();
		try
		{
			while (db.rs.next())
			{
				Vector<String> v = new Vector<>();
				for (int i=1; i<=6;i++)
				{
					String str = db.rs.getString(i);
					v.add(str);
				}
				vtemp.add(v);
			}
			db.dbClose();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		table.clearSelection();
		dtm.setDataVector(vtemp, head);
		table.updateUI();
		table.repaint();
		
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ReturnBook();
	}

}
