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
	{ new JLabel("����Ա��"), new JLabel("Ȩ��"), new JLabel("����") };
	private JComboBox combo = new JComboBox();
	private JTextField[] texts =
	{ new JTextField(), new JTextField() };
	private JButton[] buttons =
	{ new JButton("��ӹ���Ա"), new JButton("ɾ������Ա"), new JButton("�޸Ĺ���Ա"), new JButton("���ҹ���Ա"), new JButton("��������") };
	Vector<String> head = new Vector<>();
	{
		combo.addItem("����");
		combo.addItem("��ͨ");
		head.add("����Ա��");
		head.add("Ȩ��");
		head.add("����");
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
			buttons[3].setText("�鿴�Լ�");
			texts[0].setEditable(false);
			texts[0].setEnabled(false);
			table.setEnabled(false);
			buttons[2].setText("�޸�����");
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
				JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
		// �õ��ı���ֵ
		for (int i = 0; i < 2; i++)
		{
			str[i] = texts[i].getText().trim();
		}
		str[2] = combo.getSelectedItem().toString();

		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals(""))// ����Ϊ��
		{
			if (!str[0].matches("^\\d+$"))
			{
				JOptionPane.showMessageDialog(this, "����Աֻ��Ϊ����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int tmp = Integer.parseInt(str[0]);
			sql = "insert into manager (mgNo,permitted,password) values(" + tmp + ",'" + str[1] + "','" + str[2] + "')";
			db = new DataBase();
			int j = db.updateDB(sql);
			if (j == 0)
			{
				JOptionPane.showMessageDialog(this, "����ʧ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "������������Ϣ!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void deleteManager()
	{
		String mg = texts[0].getText().trim();
		if (mg.equals(""))
		{
			JOptionPane.showMessageDialog(this, "�˺Ų���Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
			if (str.equals("����"))
			{
				JOptionPane.showMessageDialog(this, "����ɾ����������Ա", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;

			} else
			{
				sql = "delete from manager where mgNo=" + mg;
				db = new DataBase();
				if (db.updateDB(sql) != 0)
					JOptionPane.showMessageDialog(this, "�ɹ�ɾ��!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, "ɾ��ʧ��!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "�ɹ��޸�!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "�޸�ʧ��!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);

		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "��ѡ�б���е�һ��!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
				 * "���������Ա!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE); return;
				 * }
				 */
				if (arg0.getSource() == buttons[0])// ���
				{
					this.insertManager();
				}
				if (arg0.getSource() == buttons[1])// ɾ��
				{
					this.deleteManager();
				}
				if (arg0.getSource() == buttons[2])// �޸�
				{
					this.updateManager();
				}
				if (arg0.getSource() == buttons[3])// ��ѯ
				{
					this.searchManager();
				}
				if (arg0.getSource() == buttons[4])// ��ѯ����
				{
					this.searchAll();
				}
			}
			if (p == 0)// ��ͨ����Ա
			{
				/*
				 * texts[0].requestFocus(); String txt =
				 * texts[0].getText().trim(); if (txt.equals("")) {
				 * JOptionPane.showMessageDialog(this, "���������Ա��!!!", "��Ϣ",
				 * JOptionPane.INFORMATION_MESSAGE); return; } else if
				 * (txt.equals(mgNo)) {
				 */
				if (arg0.getSource() == buttons[3])// ��ѯ
				{
					this.manager1();
				}
				if (arg0.getSource() == buttons[2])// �޸�����
				{
					
						sql = "update manager set password='" + texts[1].getText().trim() + "' where mgNo=" + this.mgNo;
						db = new DataBase();
						int i = db.updateDB(sql);
						if (i == 1)
						{
							JOptionPane.showMessageDialog(this, "�޸ĳɹ�", "��Ϣ!!!", JOptionPane.INFORMATION_MESSAGE);
							return;
						} else
						{
							JOptionPane.showMessageDialog(this, "�޸�ʧ��", "Warning!!!", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					
				}
			} else
			{
				JOptionPane.showMessageDialog(this, "�㲻�ܲ鿴���˵���Ϣ", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
