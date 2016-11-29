package BookManager;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.*;

public class BorrowBook extends JPanel implements ActionListener
{
	String sql;
	DataBase db;
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);

	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	// private String[] str = {"书号", "书名", "作者", "出版社", "是否借阅", "是否预约"};
	private JRadioButton borrowRadioButton = new JRadioButton("借阅图书", true);
	private JRadioButton orderRadioButton = new JRadioButton("预约图书");
	private JButton confirmButton = new JButton("确定");
	private JLabel[] jlArray =
	{ new JLabel("您要借阅或预约的书号"), new JLabel("请输入您的学号") };
	private JTextField[] jtxtArray =
	{ new JTextField(), new JTextField() };
	private ButtonGroup btngroup = new ButtonGroup();

	Vector<String> head = new Vector<String>();// 创建表头向量
	{
		head.add("书号");
		head.add("书名");
		head.add("作者");
		head.add("出版社");
		head.add("是否借阅");
		head.add("是否预约");
	};// 静态代码块
	Vector<Vector> data = new Vector<Vector>();// 存放检索出的书的基本信息
	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable jtable = new JTable(dtm);
	JScrollPane jscrollpane = new JScrollPane(jtable);

	public BorrowBook()
	{
		this.setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);
		jpb.setLayout(null);

		btngroup.add(borrowRadioButton);
		btngroup.add(orderRadioButton);

		jpt.add(borrowRadioButton);
		borrowRadioButton.setBounds(20, 20, 100, 20);
		borrowRadioButton.addActionListener(this);
		jpt.add(orderRadioButton);
		orderRadioButton.setBounds(200, 20, 100, 20);
		orderRadioButton.addActionListener(this);

		jpt.add(confirmButton);
		confirmButton.setBounds(500, 20, 100, 20);
		confirmButton.addActionListener(this);
		
		jpt.add(jlArray[0]);
		jlArray[0].setBounds(20 , 60, 140, 20);
		jpt.add(jtxtArray[0]);
		jtxtArray[0].setBounds(160, 60, 140, 20);
		jpt.add(jlArray[1]);
		jlArray[1].setBounds(20 +300, 60, 140, 20);
		jpt.add(jtxtArray[1]);
		jtxtArray[1].setBounds(120 +300, 60, 140, 20);

		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jscrollpane);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(100);
		this.setBounds(3, 10, 700, 400);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == confirmButton)//提交按钮
		{
			if (jtxtArray[1].getText().equals(""))//学号为空
			{
				JOptionPane.showMessageDialog(this, "输入不能为空!!!", "消息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql = "select * from STUDENT where StuNO=" + Integer.parseInt(jtxtArray[1].getText().trim());
			db = new DataBase();
			db.selectDB(sql);
			Vector<Vector> vtemp = new Vector<>();
			try
			{
				if (!(db.rs.next()))
				{
					JOptionPane.showMessageDialog(this, "输入的学号错误!!!","消息",JOptionPane.INFORMATION_MESSAGE);
					
				}
				else
				{
					String stuName = db.rs.getString(2).trim();
					String classes = db.rs.getString(5).trim();
					if(db.rs.getString(8).trim().equals("no"))//没有权限
					{
						JOptionPane.showMessageDialog(this, "您没有权限","消息",JOptionPane.INFORMATION_MESSAGE);
					}
					else//有权限
					{
						sql = "select * from BOOK where BookNO=" + Integer.parseInt(jtxtArray[0].getText().trim());
						db.selectDB(sql);
						do
						{
							String str6=null;
							String str7=null;
							String bookName=null;
							String author=null;
							if(!(db.rs.next()))
							{
								JOptionPane.showMessageDialog(this,"没有您要查询的书号","消息",JOptionPane.INFORMATION_MESSAGE);
							}
							Vector<String >v = new Vector<>();
							for (int i = 1; i<=7;i++)//顺序得到查找结果中的记录
							{
								if (i==5)//得到数据表第6列
								{
									str6 = db.rs.getString(i+1);
									v.add(str6);
								}
								if (i==6)//得到数据表第7列
								{
									str7 = db.rs.getString(i+1);
									v.add(str7);
								}
								if (i==2)
								{
									bookName = db.rs.getString(i).trim();
									v.addElement(bookName);
								}
								if (i==3)
								{
									author = db.rs.getString(i).trim();
									v.addElement(author);
								}
								if (i==1)
								{
									String str = db.rs.getString(i).trim();
									v.addElement(str);
								}
								if (i==4)
								{
									String str = db.rs.getString(i).trim();
									v.addElement(str);
								}
							}
							vtemp.add(v);
							dtm.setDataVector(vtemp, head);
							jtable.updateUI();
							jtable.repaint();
							
							if (borrowRadioButton.isSelected())//选择了 借 
							{
								if (str6.trim().equals("是"))
									JOptionPane.showMessageDialog(this, "此书已被借走!!!","消息",JOptionPane.INFORMATION_MESSAGE);
								else if(str7.trim().equals("是"))
									JOptionPane.showMessageDialog(this, "此书已被预约,不能借!!!","消息",JOptionPane.INFORMATION_MESSAGE);
								else
								{
									Date now = new Date();
									sql = "update book set Borrowed='是' where BookNO="+Integer.parseInt(jtxtArray[0].getText().trim());
									db.updateDB(sql);
									JOptionPane.showMessageDialog(this, "借书成功","消息",JOptionPane.INFORMATION_MESSAGE);
									sql="insert into RECORD values(" + Integer.parseInt(jtxtArray[0].getText().trim()) +"," + Integer.parseInt(jtxtArray[1].getText().trim()) +",'"+
									(now.getYear()+1900) +"." +(now.getMonth() +1)+"." + now.getDate()+"', '" +(now.getYear()+1900) +"." +(now.getMonth() +2)+"." + now.getDate()+"', '否','否')";
									db.updateDB(sql);
								}
							}
							if(orderRadioButton.isSelected())//选择 预约
							{
								if (str7.trim().equals("是"))
								{
									JOptionPane.showMessageDialog(this, "此书已经被预约","消息",JOptionPane.INFORMATION_MESSAGE);
								}
								else
								{
									sql = "update BOOK set Ordered='是' where BookNO=" + Integer.parseInt(jtxtArray[0].getText().trim());
									db.updateDB(sql);
									JOptionPane.showMessageDialog(this,"预约成功","消息",JOptionPane.INFORMATION_MESSAGE);
									
									sql = "insert into ORDERREPORT values("  + Integer.parseInt(jtxtArray[0].getText().trim()) +",'"+stuName+"','"+
									classes+"','" + bookName+"',"+Integer.parseInt(jtxtArray[1].getText().trim()) + ",'" + author+"')";
									db.updateDB(sql);
								}
							}
						}
						while(db.rs.next());
					}
				}
			
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			db.dbClose();
		}
	}

	public static void main(String[] args)
	{
		new BorrowBook();
	}
}
