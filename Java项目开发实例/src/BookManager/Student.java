package BookManager;

import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class Student extends JPanel implements ActionListener
{
	String sql;
	DataBase db;
	private JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt = new JPanel();
	private JPanel jpb = new JPanel();
	private JLabel bp = new JLabel("����Ȩ��");
	private JLabel[][] labels =
	{
			{ new JLabel("ѧ��"), new JLabel("����"), new JLabel("�Ա�") },
			{ new JLabel("�༶"), new JLabel("Ժϵ"), new JLabel("����") } };
	private JTextField[][] texts =
	{
			{ new JTextField(), new JTextField(), new JTextField() },
			{ new JTextField(), new JTextField(), new JTextField() } };
	private String[] str = new String[7];
	private JComboBox<String> combo = new JComboBox<String>();
	private JButton[] buttons =
	{ new JButton("���"), new JButton("ɾ��"), new JButton("�޸�"), new JButton("����"), new JButton("���") };
	Vector<String> head = new Vector<>();
	{
		head.add("ѧ��");
		head.add("����");
		head.add("�Ա�");
		head.add("�༶");
		head.add("Ժϵ");
		head.add("����");
		head.add("����Ȩ��");
	};
	Vector<Vector> data = new Vector<>();

	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable table = new JTable(dtm);
	JScrollPane scrollPane = new JScrollPane(table);

	public Student()
	{
		this.setLayout(new GridLayout(1, 1));
		jpt.setLayout(null);
		jpb.setLayout(null);

		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				jpt.add(labels[i][j]);
				jpt.add(texts[i][j]);
				labels[i][j].setBounds(40 + j * 180, 10 + i * 40, 30, 20);
				texts[i][j].setBounds(75 + j * 180, 10 + i * 40, 120, 20);
			}

		}
		jpt.add(combo);
		combo.addItem("��");
		combo.addItem("��");
		combo.addActionListener(this);
		combo.setBounds(565 + 60, 10, 40, 20);
		jpt.add(bp);
		bp.setBounds(565, 10, 60, 20);
		// add buttons
		for (int i = 0; i < 5; i++)
		{
			jpt.add(buttons[i]);
			buttons[i].setBounds(180 + 70 * i, 80, 60, 20);
			buttons[i].addActionListener(this);
		}

		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(scrollPane);
		jsp.setDividerSize(4);
		this.add(jsp);
		jsp.setDividerLocation(110);
		this.setBounds(3, 10, 700, 400);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// �ı����¼�����
		if (e.getSource() == texts[0][0])
			texts[0][1].requestFocus();
		if (e.getSource() == texts[0][1])
			texts[0][2].requestFocus();
		if (e.getSource() == texts[0][2])
			texts[1][0].requestFocus();
		if (e.getSource() == texts[1][0])
			texts[1][1].requestFocus();
		if (e.getSource() == texts[1][1])
			texts[1][2].requestFocus();
		// ��ť����
		if (e.getSource() == buttons[0])// ��Ӱ�ť
			this.insertStudent();
		if (e.getSource() == buttons[1])// ɾ����ť
			this.deleteStudent();
		if (e.getSource() == buttons[2])// �޸İ�ť
			this.updateStudent();
		if (e.getSource() == buttons[3])// ���Ұ�ť
			this.searchStudent();
		if (e.getSource() == buttons[4])// ��հ�ť
			this.cleanTextField();

	}

	public void cleanTextField()
	{
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				texts[i][j].setText("");
			}

		}
	}

	public void insertStudent()
	{
		// System.out.println(table.getRowCount());

		dtm.setRowCount(0);
		table.updateUI();
		table.repaint();

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 3; j++)
			{
				str[3 * i + j] = texts[i][j].getText().trim();
			}
		if (str[0].equals("") && str[1].equals("") && str[2].equals("") && str[3].equals("") && str[4].equals("")
				&& str[5].equals(""))
		{
			JOptionPane.showMessageDialog(this, "ѧ����Ϣ����Ϊ��", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!str[0].equals("") && !str[1].equals("") && !str[2].equals("") && !str[3].equals("") && !str[4].equals("")
				&& !str[5].equals(""))
		{
			str[6] = combo.getSelectedItem().toString();
			sql = "insert into student(StuNO, StuName, StuSex, Class, Department, " + "Password, Permitted) Values('"
					+ str[0] + "','" + str[1] + "','" + str[2] + "','" + str[3] + "','" + str[4] + "','" + str[5]
					+ "','" + str[6] + "')";
			/*
			 * try { sql = new String(sql.getBytes(),"UTF-8"); } catch
			 * (UnsupportedEncodingException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */
			db = new DataBase();
			int count = db.updateDB(sql);
			if (count == 0)
			{
				JOptionPane.showMessageDialog(this, "ִ�в������δ�ɹ�!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Vector<String> v = new Vector<String>();
			for (int i = 0; i <= 6; i++)
			{
				v.add(str[i]);
			}
			data.add(v);
			dtm.setDataVector(data, head);

			table.updateUI();
			table.repaint();
			return;
		}
	}

	public void deleteStudent()
	{
		//String strs[] = new String[7];
		int row = table.getSelectedRow();
		if (row >= 0)
		{
			/*for (int i = 0; i < 7; i++)
			{
				strs[i] = table.getValueAt(row, i).toString();
			}*/
			/*sql = "update student set StuName='" + strs[1] + "', StuSex='" + strs[2] + "',Class='" + strs[3]
					+ "',Department='" + strs[4] + "',Permitted='" + strs[5] + "',Password='" + strs[6]
					+ "' where StuNO=" */
			sql="delete from student where StuNO="+ Integer.parseInt(table.getValueAt(row, 0).toString().trim());
			db = new DataBase();
			if (db.updateDB(sql) == 0)
			{
				JOptionPane.showMessageDialog(this, "ִ�в���δ�ɹ�!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "ɾ��"+table.getValueAt(row, 0).toString().trim()+"�ɹ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "����ѡ�����޸ĵ���", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void updateStudent()
	{
		
		String strs[] = new String[7];
		int row = table.getSelectedRow();
		if (row >= 0)
		{
			for (int i = 0; i < 7; i++)
			{
				strs[i] = table.getValueAt(row, i).toString();
			}
			sql = "update student set StuName='" + strs[1] + "', StuSex='" + strs[2] + "',Class='" + strs[3]
					+ "',Department='" + strs[4] + "',Permitted='" + strs[5] + "',Password='" + strs[6]
					+ "' where StuNO=" + Integer.parseInt(strs[0].trim());
			db = new DataBase();
			if (db.updateDB(sql) == 0)
			{
				JOptionPane.showMessageDialog(this, "ִ�в���δ�ɹ�!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "�޸ĳɹ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (row == -1)
		{
			JOptionPane.showMessageDialog(this, "����ѡ�����޸ĵ���", "Warning!!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	public void searchStudent()
	{
		dtm.setRowCount(0);
		table.updateUI();
		table.repaint();

		String[] str1 = new String[7];

		
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 3; j++)
			{
				str1[3 * i + j] = texts[i][j].getText().trim();
			}

		sql = "select * from STUDENT where 1=1 ";

	
		str1[6] = combo.getSelectedItem().toString();
		String[] vars =
		{ " StuNO=" + str1[0], " StuName='" + str1[1] + "' ", " StuSex='" + str1[2] + "' ", " Class='" + str1[3] + "' ",
				" Department='" + str1[4] + "' ", " Password='" + str1[5] + "'", " Permitted='" + str1[6] + "'" };
		for (int i = 0; i < 7; i++)
		{
			if (!str1[i].equals(""))
			{
				sql = sql + " and " + vars[i];
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
				JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			dtm.setDataVector(vtemp, head);
			table.updateUI();
			table.repaint();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new Student();
	}

}
