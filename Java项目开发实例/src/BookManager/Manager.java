package BookManager;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Manager extends JPanel implements ActionListener
{
	private String mgNo;
	private String sql;
	private DataBase db;
	private boolean isSuper = false;
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	private JLabel[] labels =
	{ new JLabel("管理员名"), new JLabel("权限"), new JLabel("密码") };
	private JComboBox combo = new JComboBox();
	private JTextField[] texts =
	{ new JTextField(), new JTextField() };
	private JButton[] buttons =
	{ new JButton("添加管理员"), new JButton("删除管理员"), new JButton("修改管理员"), new JButton("查找管理员"), new JButton("查找所有") };
	Vector<String> head = new Vector<>();
	{
		combo.addItem("超级");
		combo.addItem("普通");
		head.add("管理员名");
		head.add("权限");
		head.add("密码");
	};
	Vector<Vector> data = new Vector<>();

	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable table = new JTable(dtm);
	JScrollPane scrollPane = new JScrollPane(table);

	public Manager(String No)
	{
		this.mgNo = No;

		this.setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);
		jpb.setLayout(null);

		jpt.add(labels[0]);
		jpt.add(texts[0]);
		labels[0].setBounds(20, 20, 60, 20);
		texts[0].setBounds(80, 20, 100, 20);
		jpt.add(labels[1]);
		jpt.add(combo);
		jpt.add(texts[1]);
		labels[1].setBounds(20 + 1 * 180, 20, 30, 20);
		combo.setBounds(50 + 1 * 180, 20, 60, 20);
		jpt.add(labels[2]);
		jpt.add(texts[1]);
		labels[2].setBounds(20 + 2 * 180, 20, 30, 20);
		texts[1].setBounds(50 + 2 * 180, 20, 100, 20);

		for (int i = 0; i < 5; i++)
		{
			jpt.add(buttons[i]);
			buttons[i].setBounds(60 + i * 120, 60, 100, 20);
			buttons[i].addActionListener(this);
		}

		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(scrollPane);
		jsp.setDividerSize(5);
		this.add(jsp);
		jsp.setDividerLocation(100);
		this.setBounds(3, 10, 700, 400);
		this.setVisible(true);
	}

	public void setFlag(boolean b)
	{
		this.isSuper = b;
		buttons[0].setEnabled(b);
		buttons[1].setEnabled(b);
		buttons[4].setEnabled(b);
		if (!b)
		{
			buttons[3].setText("查看自己");
			texts[0].setEditable(false);
			texts[0].setEnabled(false);
			table.setEnabled(false);
			buttons[2].setText("修改密码");
		}

	}

	public void manager1()
	{
		sql = "select * from manager where mgNo="+ this.mgNo;
		db = new DataBase();
		db.selectDB(sql);
		try
		{
			int k = 0;
			Vector<Vector> vtemp = new Vector<>();
			while (db.rs.next())
			{
				k++;
				Vector<String> v = new Vector<String>();
				for (int i = 1; i <= 3; i++)
				{
					String str = db.rs.getString(i).trim();
					v.add(str);
				}
				vtemp.add(v);
			}
			if (k == 0)
			{
				JOptionPane.showMessageDialog(this, "没有您要查找的内容!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
			}
			dtm.setDataVector(vtemp, head);
			table.updateUI();
			table.repaint();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void insertManager()
	{
		String[] str = new String[3];
		// 得到文本框值
		for (int i = 0; i < 2; i++)
		{
			str[i] = texts[i].getText().trim();
		}
		str[2] = combo.getSelectedItem().toString();

		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals(""))// 都不为空
		{
			if (!str[0].matches("^\\d+$"))
			{
				JOptionPane.showMessageDialog(this, "管理员只能为数字", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int tmp = Integer.parseInt(str[0]);
			sql = "insert into manager (mgNo,permitted,password) values(" + tmp + ",'" + str[1] + "','" + str[2] + "')";
			db = new DataBase();
			int j = db.updateDB(sql);
			if (j == 0)
			{
				JOptionPane.showMessageDialog(this, "插入失败", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Vector<String> v = new Vector<>();
			for (int i = 0; i <= 2; i++)
			{
				v.add(str[i]);
				if (i != 1)
				{
					texts[i].setText("");
				}
			}
			data.add(v);
			dtm.setDataVector(data, head);
			table.updateUI();
			table.repaint();
			return;

		} else
		{
			JOptionPane.showMessageDialog(this, "请输入完整信息!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void deleteManager()
	{
		String mg = texts[0].getText().trim();
		if (mg.equals(""))
		{
			JOptionPane.showMessageDialog(this, "账号不能为空", "消息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		sql = "select permitted from manager where mgNo=" + mg;
		db = new DataBase();
		db.updateDB(sql);
		String str = "";
		try
		{
			while (db.rs.next())
			{
				str = db.rs.getString(1).trim();
			}
			if (str.equals("超级"))
			{
				JOptionPane.showMessageDialog(this, "不能删除超级管理员", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;

			} else
			{
				sql = "delete from manager where mgNo=" + mg;
				db = new DataBase();
				if (db.updateDB(sql) != 0)
					JOptionPane.showMessageDialog(this, "成功删除!!!", "信息", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "删除失败!!!", "信息", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateManager()
	{
		String[] str = new String[3];
		int row = table.getSelectedRow();
		if (row >= 0)
		{
			for (int i = 0; i < 3; i++)
			{
				str[i] = table.getValueAt(row, i).toString();
			}
			sql = "update manager set mgNo=" + str[0] + ", permitted='" + str[1] + "', password='" + str[2]
					+ "' where mgNo=" + str[0];
			db = new DataBase();
			if (db.updateDB(sql) != 0)
				JOptionPane.showMessageDialog(this, "成功修改!!!", "信息", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "修改失败!!!", "信息", JOptionPane.INFORMATION_MESSAGE);

		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "请选中表格中的一行!!!", "信息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void searchManager()
	{
		// int q = Integer.parseInt(texts[0].getText().trim());
		sql = "select * from manager where mgNo=" + texts[0].getText().trim();

		if (texts[0].getText().trim().equals("") && texts[1].getText().trim().equals(""))
		{
			sql = "select * from manager where permitted='" + combo.getSelectedItem().toString() + "'";
		}

		db = new DataBase();
		db.selectDB(sql);
		try
		{
			int k = 0;
			Vector<Vector> vtemp = new Vector<>();
			while (db.rs.next())
			{
				k++;
				Vector<String> v = new Vector<>();
				for (int i = 0; i < 3; i++)
				{
					v.add(db.rs.getString(i + 1).trim());
				}
				vtemp.add(v);
			}
			if (k == 0)
			{
				JOptionPane.showMessageDialog(this, "没有您要查找的内容!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			dtm.setDataVector(vtemp, head);
			table.updateUI();
			table.repaint();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void searchAll()
	{
		// int q = Integer.parseInt(texts[0].getText().trim());
		sql = "select * from manager";

		db = new DataBase();
		db.selectDB(sql);
		try
		{
			int k = 0;
			Vector<Vector> vtemp = new Vector<>();
			while (db.rs.next())
			{
				k++;
				Vector<String> v = new Vector<>();
				for (int i = 0; i < 3; i++)
				{
					v.add(db.rs.getString(i + 1).trim());
				}
				vtemp.add(v);
			}
			if (k == 0)
			{
				JOptionPane.showMessageDialog(this, "没有您要查找的内容!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			dtm.setDataVector(vtemp, head);
			table.updateUI();
			table.repaint();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{

		/*
		 * sql = "select permitted from manager where mgNo=" + mgNo; db = new
		 * DataBase(); db.selectDB(sql);
		 */
		// String str = "";
		try
		{

			int p = 0;
			if (this.isSuper)
			{
				p++;
				String txt = texts[0].getText().trim();
				/*
				 * if (txt.equals("")) { JOptionPane.showMessageDialog(this,
				 * "请输入管理员!!!", "消息", JOptionPane.INFORMATION_MESSAGE); return;
				 * }
				 */
				if (arg0.getSource() == buttons[0])// 添加
				{
					this.insertManager();
				}
				if (arg0.getSource() == buttons[1])// 删除
				{
					this.deleteManager();
				}
				if (arg0.getSource() == buttons[2])// 修改
				{
					this.updateManager();
				}
				if (arg0.getSource() == buttons[3])// 查询
				{
					this.searchManager();
				}
				if (arg0.getSource() == buttons[4])// 查询所有
				{
					this.searchAll();
				}
			}
			if (p == 0)// 普通管理员
			{
				/*
				 * texts[0].requestFocus(); String txt =
				 * texts[0].getText().trim(); if (txt.equals("")) {
				 * JOptionPane.showMessageDialog(this, "请输入管理员名!!!", "消息",
				 * JOptionPane.INFORMATION_MESSAGE); return; } else if
				 * (txt.equals(mgNo)) {
				 */
				if (arg0.getSource() == buttons[3])// 查询
				{
					this.manager1();
				}
				if (arg0.getSource() == buttons[2])// 修改密码
				{
					
						sql = "update manager set password='" + texts[1].getText().trim() + "' where mgNo=" + this.mgNo;
						db = new DataBase();
						int i = db.updateDB(sql);
						if (i == 1)
						{
							JOptionPane.showMessageDialog(this, "修改成功", "消息!!!", JOptionPane.INFORMATION_MESSAGE);
							return;
						} else
						{
							JOptionPane.showMessageDialog(this, "修改失败", "Warning!!!", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					
				}
			} else
			{
				JOptionPane.showMessageDialog(this, "你不能查看别人的信息", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		db.dbClose();
	}

	public static void main(String[] args)
	{
		// new Manager();

	}

}
