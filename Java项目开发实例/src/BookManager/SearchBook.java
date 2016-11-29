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
	{ "���", "����", "����", "������", "����ʱ��" };
	private JComboBox jcb = new JComboBox(str);
	private JButton jb = new JButton("�ύ");
	private JLabel[] jlArray = new JLabel[]
	{ new JLabel("����"), new JLabel("����"), new JLabel("������") };
	private JTextField[] jtxtArray = new JTextField[]
	{ new JTextField(), new JTextField(), new JTextField(), new JTextField() };
	private JRadioButton[] jrbArray =
	{ new JRadioButton("�򵥲�ѯ", true), new JRadioButton("�߼���ѯ", true) };
	private ButtonGroup bg = new ButtonGroup();
	Vector<String> head = new Vector<String>();
	// ��̬�����
	{
		head.add("���");
		head.add("����");
		head.add("����");
		head.add("������");
		head.add("����ʱ��");
		head.add("�Ƿ����");
		head.add("�Ƿ�ԤԼ");
	};
	Vector<Vector> data = new Vector<Vector>();
	DefaultTableModel dtm = new DefaultTableModel(data, head);
	JTable jt = new JTable(dtm);
	JScrollPane jspn = new JScrollPane(jt);

	public void actionPerformed(ActionEvent e)
	{
		if (jrbArray[0].isSelected())// ѡ��"�򵥲�ѯ"��ѡ��ť
		{
			jtxtArray[3].setEditable(true);
			for (int i = 0; i < jtxtArray.length - 1; i++)
				jtxtArray[i].setEditable(false);
			if (e.getSource() == jb)// �������ύ����ť
			{
				jtxtArray[3].requestFocus();
				if (e.getSource() == jb)
				{
					String stra = jtxtArray[3].getText().trim();
					if (stra.equals("")) // ��ѯ����Ϊ��
					{
						JOptionPane.showMessageDialog(this, "�������Ҫ����Ϣ!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (jcb.getSelectedIndex() == 0)// ������Ž��в�ѯ
					{
						sql = "select * from BOOK where BookNO='" + stra + "'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 1)// �����������в�ѯ
					{
						sql = "select * from BOOK where BookName like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 3)// ���ݳ�������в�ѯ
					{
						sql = "select * from BOOK where Publishment like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else if (jcb.getSelectedIndex() == 2)// �������߽��в�ѯ
					{
						sql = "select * from BOOK where Author like '%" + stra + "%'";
						jtxtArray[3].setText("");
					} else // ���ݹ���ʱ����в�ѯ
					{
						sql = "select * from BOOK where BuyTime like '%" + stra + "%'";
						jtxtArray[3].setText("");
					}
					db = new DataBase(); // �������ݿ������
					try
					{
						// sql = new String(sql.getBytes(), "utf-8");//��SQLת��
					} catch (Exception ae)
					{
						ae.printStackTrace();
					}
					db.selectDB(sql);

				}

				Vector<Vector> vtemp = new Vector<Vector>();// �������������ڴ�ű������
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
						JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����!!!\n" , "��Ϣ",
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
		if (jrbArray[1].isSelected()) // "�߼���ѯ"��ѡ��ť��ѡ��
		{
			jtxtArray[0].requestFocus();// ������뽹��
			jtxtArray[3].setEditable(false);
			for (int i = 0; i < jtxtArray.length - 1; i++)
			{
				jtxtArray[i].setEditable(true);
			}
			if (e.getSource() == jb)// �������ύ����ť
			{
				int bz = this.seniorSearch();// �õ���־λ
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
						JOptionPane.showMessageDialog(this, "û����Ҫ���ҵ�����!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
			JOptionPane.showMessageDialog(this, "�������Ҫ����Ϣ!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if (((!str0.equals("")) && (str1.equals("")) && (str2.equals("")))
				|| ((str0.equals("")) && (!str1.equals("")) && (str2.equals("")))
				|| ((str0.equals("")) && (str1.equals("")) && (!str2.equals(""))))
		{
			JOptionPane.showMessageDialog(this, "��ʹ�ü򵥲�ѯ!!!", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if ((!str0.equals("")) && (!str1.equals("")) && (str2.equals("")))// �������������
		{
			sql = "select * from BOOK where BookName like '%" + str0 + "%' and Author like'%" + str1 + "%'";
			jtxtArray[0].setText("");
			jtxtArray[1].setText("");
		}
		if ((!str0.equals("")) && (str1.equals("")) && (!str2.equals("")))// �����ͳ�����
		{
			sql = "select * from Book where BookName like '%" + str0 + "%' and Publishment like '%" + str2 + "%'";
			jtxtArray[0].setText("");
			jtxtArray[2].setText("");
		}
		if ((str0.equals("")) && (!str1.equals("")) && (!str2.equals("")))// ���ߺͳ�����
		{
			sql = "select * from Book where Author like '%" + str1 + "%' and Publishment like '%" + str2 + "%'";
			jtxtArray[1].setText("");
			jtxtArray[2].setText("");
		}
		if ((!str0.equals("")) && (str1.equals("")) && (!str2.equals("")))// �������
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
