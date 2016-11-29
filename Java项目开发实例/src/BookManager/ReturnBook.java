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
	
	private JLabel label = new JLabel("����������ѧ��");
	private JTextField textField = new JTextField();
	private JButton [] buttons = {
			new JButton("��ʧ"),
			new JButton("�黹"),
			new JButton("��ѯ")
	};
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	
	Vector<String> head = new Vector<>();
	{
		head.add("���");
		head.add("ѧ��");
		head.add("����ʱ��");
		head.add("����ʱ��");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
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
		// ��ѯ��ť�¼�
		if(e.getSource()==buttons[2])
		{
			if (textField.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "������ѧ��","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
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
						JOptionPane.showMessageDialog(this, "δ��ѯ����¼","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}catch (Exception ea)
				{
					ea.printStackTrace();
				}
			}
		}
		
		//�黹 ��ť�¼�
		if (e.getSource()==buttons[1])
		{
			int row = table.getSelectedRow();
			if (row<0)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�黹��ͼ��","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String bookNO = table.getValueAt(row, 0).toString();
			int stuNO = Integer.parseInt(table.getValueAt(row, 1).toString());
			int bookno = Integer.parseInt(bookNO);
			int flag = checkTime(stuNO,bookno);
			if (flag==-1)
			{
				sql = "update student set permitted='��' where StuNO="+stuNO;
				db = new DataBase();
				db.updateDB(sql);
				db.dbClose();
			}
			if (flag==0)
				return;
			sql = "Delete from record where BookNO="+bookNO;
			db = new DataBase();
			db.updateDB(sql);
			sql = "update book set borrowed='��' where BookNO=" +bookNO;
			
			db.updateDB(sql);
			db.dbClose();
			updateTable();
		}
		if (e.getSource() == buttons[0])//��ʧ ��ť
		{
			int row = table.getSelectedRow();
			if (row<0)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�黹��ͼ��","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "�������鲻�ܻ�!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			flag = 0;
		}
		else if (day>0)
		{
			int i = JOptionPane.showConfirmDialog(this, "�����Ѿ����ڣ�Ӧ�ý��ɷ���Ϊ: "+day*0.1+"Ԫ, �Ƿ��ɷ���?","��Ϣ",JOptionPane.YES_NO_OPTION);
			if (i == JOptionPane.YES_OPTION)
			{
				JOptionPane.showMessageDialog(this, "���Ѿ��ɹ����� "+day*0.1+"Ԫ","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
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
		sql = "select MAX(LBNO) from losebook"; //�ҵ����Ķ�ʧ��¼��
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
		sql = "select BookNO from exceedtime where BookNO="+bno;//��鳬�ڱ����Ƿ��и��飬����ɾ��
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
		//�ӽ������ɾ����ʧͼ��ļ�¼
		sql = "delete from record where BookNO="+bno;
		db.updateDB(sql);
		//��ͼ�����ɾ����ʧ��ͼ��
		sql = "delete from book where BookNO="+bno;
		if (db.updateDB(sql)>0)
		{
			JOptionPane.showMessageDialog(this, "��ʧ�ɹ�!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "��ʧʧ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
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
