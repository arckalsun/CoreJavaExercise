package BookManager;

import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchBook extends JPanel implements ActionListener
{
	int flag;
	String sql;
	DataBase db;
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	private String[] str =
	{ "书号", "书名", "作者", "出版社", "购买时间" };
	private JComboBox jcb = new JComboBox(str);
	private JButton jb = new JButton("提交");
	private JLabel[] jlArray = new JLabel[]
	{ new JLabel("书名"), new JLabel("作者"), new JLabel("出版社") };
	private JTextField[] jtxtArray = new JTextField[]
	{ new JTextField(), new JTextField(), new JTextField(), new JTextField() };
	private JRadioButton[] jrbArray =
	{ new JRadioButton("简单查询", true), new JRadioButton("高级查询", true) };
	private ButtonGroup bg = new ButtonGroup();
	Vector<String> head = new Vector<String>();
	// 静态代码块
	{
		head.add("书号");
		head.add("书名");
		head.add("作者");
		head.add("出版社");
		head.add("购进时间");
		head.add("是否借阅");
		head.add("是否预约");
	};
	Vector<Vector> data = new Vector<Vector>();
	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable jt = new JTable(dtm);
	JScrollPane jspn = new JScrollPane(jt);

	public void actionPerformed(ActionEvent e)
	{
		if (jrbArray[0].isSelected())// 选中"简单查询"单选按钮
		{
			jtxtArray[3].setEditable(true);
			for (int i = 0; i < jtxtArray.length - 1; i++)
				jtxtArray[i].setEditable(false);
			if (e.getSource() == jb)// 单击“提交”按钮
			{
				jtxtArray[3].requestFocus();
				if (e.getSource() == jb)
				{
					String stra = jtxtArray[3].getText().trim();
					if (stra.equals("")) // 查询条件为空
					{
						JOptionPane.showMessageDialog(this, "请输入必要的信息!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (jcb.getSelectedIndex() == 0)// 根据书号进行查询
					{
						sql = "select * from BOOK where BookNO='" + stra + "'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 1)// 根据书名进行查询
					{
						sql = "select * from BOOK where BookName like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 3)// 根据出版社进行查询
					{
						sql = "select * from BOOK where Publishment like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 2)// 根据作者进行查询
					{
						sql = "select * from BOOK where Author like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else // 根据购进时间进行查询
					{
						sql = "select * from BOOK where BuyTime like '%" + stra + "%'";
						jtxtArray[3].setText("");
					}
					db = new DataBase(); // 创建数据库类对象
					try
					{
						// sql = new String(sql.getBytes(), "utf-8");//将SQL转码
					} catch (Exception ae)
					{
						ae.printStackTrace();
					}
					db.selectDB(sql);

				}

				Vector<Vector> vtemp = new Vector<Vector>();// 创建向量，用于存放表格数据
				try
				{
					int flag = 0;
					while (db.rs.next())
					{
						flag++;
						Vector<String> v = new Vector<String>();

						for (int i = 1; i <= 7; i++)
						{
							String str1 = db.rs.getString(i);
							// str1 = new String(str1.getBytes(),"utf-8");
							v.add(str1);
						}
						vtemp.add(v);
					}
					if (flag == 0)
					{
						JOptionPane.showMessageDialog(this, "没有您要查找的内容!!!\n" , "消息",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception ea)
				{
					ea.printStackTrace();
				}
				dtm.setDataVector(vtemp, head);
				jt.updateUI();
				jt.repaint();
				db.dbClose();
			}
		}
		if (jrbArray[1].isSelected()) // "高级查询"单选按钮被选中
		{
			jtxtArray[0].requestFocus();// 获得输入焦点
			jtxtArray[3].setEditable(false);
			for (int i = 0; i < jtxtArray.length - 1; i++)
			{
				jtxtArray[i].setEditable(true);
			}
			if (e.getSource() == jb)// 单击“提交”按钮
			{
				int bz = this.seniorSearch();// 得到标志位
				if (bz != 0)
					return;
				db = new DataBase();
				db.selectDB(sql);
				Vector<Vector> vtemp = new Vector<Vector>();
				try
				{
					int flag = 0;
					while (db.rs.next())
					{
						flag++;
						Vector<String> v = new Vector<String>();
						for (int i = 1; i <= 7; i++)
						{
							String str = db.rs.getString(i);
							// str = new String(str.getBytes(),"utf-8");
							v.add(str);
						}
						vtemp.add(v);
					}
					if (flag == 0)
					{
						JOptionPane.showMessageDialog(this, "没有您要查找的内容!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception ea)
				{
					ea.printStackTrace();
				}
				dtm.setDataVector(vtemp, head);
				jt.updateUI();
				jt.repaint();
				db.dbClose();
			}
		}

	}

	public SearchBook()
	{
		this.setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);
		jpb.setLayout(null);
		jpt.add(jcb);
		jcb.setBounds(160, 20, 150, 20);
		jcb.addActionListener(this);
		jpt.add(jb);
		jb.setBounds(560, 20, 100, 20);
		jb.addActionListener(this);

		for (int i = 0; i < 2; i++)
		{
			jrbArray[i].setBounds(20, 20 + i * 40, 100, 20);
			bg.add(jrbArray[i]);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
		}
		for (int i = 0; i < 3; i++)
		{
			jlArray[i].setBounds(120 + i * 200, 60, 80, 20);
			jtxtArray[i].setBounds(200 + i * 180, 60, 120, 20);
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
		}
		for (int i = 0; i < 3; i++)
		{
			jtxtArray[i].setEditable(false);
		}
		jtxtArray[3].setBounds(350, 20, 120, 20);
		jpt.add(jtxtArray[3]);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(100);
		this.setBounds(3, 10, 710, 400);
		this.setVisible(true);
	}

	public int seniorSearch()
	{
		int flag = 0;
		String str0 = jtxtArray[0].getText().trim();
		String str1 = jtxtArray[1].getText().trim();
		String str2 = jtxtArray[2].getText().trim();
		if (str0.equals("") && str1.equals("") && str2.equals(""))
		{
			JOptionPane.showMessageDialog(this, "请输入必要的信息!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if (((!str0.equals("")) && (str1.equals("")) && (str2.equals("")))
				|| ((str0.equals("")) && (!str1.equals("")) && (str2.equals("")))
				|| ((str0.equals("")) && (str1.equals("")) && (!str2.equals(""))))
		{
			JOptionPane.showMessageDialog(this, "请使用简单查询!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if ((!str0.equals("")) && (!str1.equals("")) && (str2.equals("")))// 书名和作者组合
		{
			sql = "select * from BOOK where BookName like '%" + str0 + "%' and Author like'%" + str1 + "%'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
		}
		if ((!str0.equals("")) && (str1.equals("")) && (!str2.equals("")))// 书名和出版社
		{
			sql = "select * from Book where BookName like '%" + str0 + "%' and Publishment like '%" + str2 + "%'";
			jtxtArray[0].setText("");
			jtxtArray[2].setText("");
		}
		if ((str0.equals("")) && (!str1.equals("")) && (!str2.equals("")))// 作者和出版社
		{
			sql = "select * from Book where Author like '%" + str1 + "%' and Publishment like '%" + str2 + "%'";
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		if ((!str0.equals("")) && (str1.equals("")) && (!str2.equals("")))// 三者组合
		{
			sql = "select * from Book where BookName like '%" + str0 + "%' and Publishment like '%" + str2
					+ "%' and Author like '%" + str1 + "%'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		return flag;
	}

	public static void main(String[] args)
	{
		new SearchBook();
	}
}
