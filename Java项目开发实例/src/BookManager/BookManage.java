package BookManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;

public class BookManage extends JPanel implements ActionListener
{
	private DataBase db;

	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt = new JPanel();
	// private JPanel jpb = new JPanel();
	private JLabel[] labels =
	{ new JLabel("书号"), new JLabel("书名"), new JLabel("作者"), new JLabel("出版社"), new JLabel("购买日期"), new JLabel("已借阅"),
			new JLabel("已预约") };
	private JTextField[] texts =
	{ new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField() };
	private JButton cleanButton = new JButton("清空");
	private JButton[] buttons =
	{ new JButton("添加图书"), new JButton("删除图书"), new JButton("修改图书"), new JButton("查找图书") };
	private String[] str =
	{ "否", "是" };
	private JComboBox combo[] =
	{ new JComboBox(str), new JComboBox(str) };

	Vector<String> head = new Vector<>();
	{
		head.add("书号");
		head.add("书名");
		head.add("作者");
		head.add("出版社");
		head.add("购买日期");
		head.add("是否借阅");
		head.add("是否预约");
	};
	Vector<Vector> data = new Vector<>();
	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable table = new JTable(dtm);
	JScrollPane scrollPane = new JScrollPane(table);

	public BookManage()
	{
		setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);
		// jpb.setLayout(null);

		for (int i = 0; i < 3; i++)
		{
			jpt.add(labels[i]);
			jpt.add(texts[i]);
			labels[i].setBounds(20, 10 + i * 30, 40, 20);
			texts[i].setBounds(65, 10 + i * 30, 100, 20);

		}
		for (int i = 0; i < 2; i++)
		{
			jpt.add(labels[i + 3]);
			jpt.add(texts[i + 3]);
			labels[i + 3].setBounds(180, 10 + i * 30, 60, 20);
			texts[i + 3].setBounds(245, 10 + i * 30, 100, 20);
			jpt.add(labels[i + 5]);
			labels[i + 5].setBounds(380, 10 + i * 30, 40, 20);
			jpt.add(combo[i]);
			combo[i].setBounds(425, 10 + i * 30, 60, 20);
		}
		jpt.add(cleanButton);
		cleanButton.setBounds(180, 70, 60, 20);
		cleanButton.addActionListener(this);

		for (int i = 0; i < 4; i++)
		{
			jpt.add(buttons[i]);
			buttons[i].setBounds(80 + i * 120, 100, 100, 20);
			buttons[i].addActionListener(this);
		}
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(scrollPane);
		jsp.setDividerSize(5);
		this.add(jsp);
		jsp.setDividerLocation(120);
		this.setBounds(3, 10, 700, 400);
		// this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// 文本框事件监听
		for (int i = 0; i < 4; i++)
		{
			if (e.getSource() == texts[i])
				texts[i + 1].requestFocus();
		}
		if (e.getSource() == buttons[0])
			this.insertBook();
		if (e.getSource() == buttons[1])
			this.deleteBook();
		;
		if (e.getSource() == buttons[2])
			this.updateBook();
		if (e.getSource() == buttons[3])
			this.searchBook();
		if (e.getSource() == cleanButton)
			this.cleanTextField();
	}

	public void insertBook()
	{
		// 清空表格内容
		dtm.setRowCount(0);
		table.updateUI();
		table.repaint();

		String[] str = new String[7];
		String sql;
		// 得到文本框内容
		for (int i = 0; i < 5; i++)
		{
			str[i] = texts[i].getText().trim();
		}
		if (str[0].equals("") || str[1].equals("") || str[2].equals("") || str[3].equals("") || str[4].equals(""))
		{
			JOptionPane.showMessageDialog(this, "图书信息必须填写完整!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals("") && !str[3].equals("") && !str[4].equals(""))
		{
			str[5] = combo[0].getSelectedItem().toString();
			str[6] = combo[1].getSelectedItem().toString();

			sql = "insert into book values (" + str[0] + ",'" + str[1] + "','" + str[2] + "','" + str[3] + "','"
					+ str[4] + "','" + str[5] + "','" + str[6] + "')";
			db = new DataBase();
			db.updateDB(sql);
			Vector<String> v = new Vector<>();
			for (int i = 1; i <= 7; i++)
			{
				v.add(str[i - 1]);
			}
			data.add(v);
			dtm.setDataVector(data, head);
			table.updateUI();
			table.repaint();
			return;
		}
	}

	public void deleteBook()
	{
		String sql;

		int row = table.getSelectedRow();
		if (row >= 0)
		{

			sql = "delete from book where BookNO=" + Integer.parseInt(table.getValueAt(row, 0).toString().trim());
			db = new DataBase();
			if (db.updateDB(sql) == 0)
			{
				JOptionPane.showMessageDialog(this, "执行操作未成功!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "删除" + table.getValueAt(row, 0).toString().trim() + "成功", "消息",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "请先选中所修改的行", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void searchBook()
	{
		// 清空表格内容
		dtm.setRowCount(0);
		table.updateUI();
		table.repaint();

		String[] str = new String[7];
		String sql = "select * from book where 1=1 ";
		// 得到文本框内容
		for (int i = 0; i < 5; i++)
		{
			str[i] = texts[i].getText().trim();
		}
		str[5] = combo[0].getSelectedItem().toString();
		str[6] = combo[1].getSelectedItem().toString();
		String [] var = {
				"BookNO="+str[0],
				"BookName='"+str[1]+"'",
				"Author='"+str[2]+"'",
				"Publishment='"+str[3]+"'",
				"BuyTime='"+str[4]+"'",
				"Borrowed='"+str[5]+"'",
				"Ordered='"+str[6]+"'"
		};
		for (int i = 0; i < 7; i++)
		{
			if (!str[i].equals(""))
			{
				sql = sql + " and " + var[i];
			}
		}
		db = new DataBase();
		db.selectDB(sql);
		try
		{
			int k = 0;
			Vector<Vector> vtemp = new Vector<Vector>();
			while (db.rs.next())
			{
				k++;
				Vector<String> v = new Vector<>();
				for (int i = 1; i <= 7; i++)
				{
					// String str = db.rs.getString(i).trim();
					v.add(db.rs.getString(i).trim());
				}
				vtemp.add(v);
			}
			if (k == 0)
				JOptionPane.showMessageDialog(this, "没有您要查找的内容", "消息", JOptionPane.INFORMATION_MESSAGE);
			dtm.setDataVector(vtemp, head);
			table.updateUI();
			table.repaint();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateBook()
	{
		String sql;
		String strs[] = new String[7];
		
		int row = table.getSelectedRow();
		if (row >= 0)
		{
			for (int i = 0; i < 7; i++)
			{
				strs[i] = table.getValueAt(row, i).toString();
			}

			sql = "update book set BookName='"+strs[1]+"', Author='"+strs[2]+"',Publishment='"+strs[3]+
					"',BuyTime='"+strs[4]+"',Borrowed='"+strs[5]+"',Ordered='"+strs[6]+"' where BookNO="+strs[0];
			db = new DataBase();
			if (db.updateDB(sql) == 0)
			{
				JOptionPane.showMessageDialog(this, "执行操作未成功!!!", "消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "修改" + table.getValueAt(row, 0).toString().trim() + "成功", "消息",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "请先选中所修改的行", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void cleanTextField()
	{
		for (int i = 0; i < texts.length; i++)
			texts[i].setText("");
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new BookManage();
	}

}
