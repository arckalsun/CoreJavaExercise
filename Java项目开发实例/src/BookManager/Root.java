package BookManager;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Root extends JFrame
{
	DefaultMutableTreeNode[] dmtn =
	{ new DefaultMutableTreeNode(new NodeValue("ͼ��ݹ���ϵͳ")), new DefaultMutableTreeNode(new NodeValue("ѧ���û�����")),
			new DefaultMutableTreeNode(new NodeValue("ͼ�����")), new DefaultMutableTreeNode(new NodeValue("��ѯͼ��")),
			new DefaultMutableTreeNode(new NodeValue("����ԤԼͼ��")), new DefaultMutableTreeNode(new NodeValue("�黹��ʧͼ��")),
			new DefaultMutableTreeNode(new NodeValue("���ɷ���")), new DefaultMutableTreeNode(new NodeValue("����Ա����")),
			new DefaultMutableTreeNode(new NodeValue("�˳�"))

	};
	DefaultTreeModel dtm = new DefaultTreeModel(dmtn[0]);
	JTree jt = new JTree(dtm);
	JScrollPane jsp = new JScrollPane(jt);

	boolean isStudent = false;
	String mgNo;
	String stuNo;
	private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JPanel jp = new JPanel();
	Image image ;// ����ͼƬImage����
	//ImageIcon ii = new ImageIcon(image);
	private JLabel jlRoot ;
	private Manager mg;

	CardLayout cl = new CardLayout();

	/**
	 * 
	 * @param No
	 *            ����
	 * @param b
	 *            true ������ѧ���û���false �������Ա�û�
	 */
	public Root(String No, boolean b)
	{
		this.isStudent = b;
		if (!b)
		{
			this.mgNo = No;
			mg = new Manager(mgNo);
			this.setManager();
		} else
		{
			this.stuNo = No;
			this.setStudent();
		}
	
		image = new ImageIcon("tsg1.jpg").getImage();
		
		ImageIcon ii = new ImageIcon(image);
		 jlRoot = new JLabel(ii);
		 
		this.initJp();

		this.addTreeListener();
		for (int i = 1; i < 9; i++)
			dtm.insertNodeInto(dmtn[i], dmtn[0], i - 1);
		jt.setEditable(false);
		this.add(jsplr);
		jsplr.setLeftComponent(jt);
		jsplr.setBounds(200, 50, 600, 500);
		jsplr.setRightComponent(jp);
		jsplr.setDividerLocation(150);
		jsplr.setDividerSize(4);
		jlRoot.setFont(new Font("Courier", Font.PLAIN, 30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image image = new ImageIcon("ico.gif").getImage();
	/*	Graphics g = image.getGraphics();
		g.setFont(new Font("Serif",Font.BOLD,36));
		g.drawString("ͼ��ݹ���ϵͳ", 100, 200);*/
		
		this.setIconImage(image);
		// this.setTitle("ͼ��ݹ���ϵͳ");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int centerX = screenSize.width / 2;
		int centerY = screenSize.height / 2;
		int w = 850;
		int h = 400;
		this.setBounds(centerX - w / 2, centerY - h / 2 - 100, w, h);
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		jt.setShowsRootHandles(true);
		jt.expandRow(0);

	}

	public void setStudent()
	{
		String sql = "select StuName from student where StuNO='" + stuNo + "'";

		DataBase db = new DataBase();
		db.selectDB(sql);
		try
		{
			db.rs.next();
			String str = db.rs.getString(1).trim();
			this.setTitle("ͼ��ݹ���ϵͳ" + "-- ѧ���û�: " + str);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void setManager()
	{
		String sql = "select permitted from manager where mgNo=" + mgNo ;

		DataBase db = new DataBase();
		db.selectDB(sql);
		try
		{
			db.rs.next();
			String str = db.rs.getString(1).trim();
			if (str.equals("��ͨ"))
			{
				mg.setFlag(false);
				this.setTitle("ͼ��ݹ���ϵͳ" + "-- ��ͨ����Ա: " + mgNo);
			} else if (str.equals("����"))
			{
					this.setTitle("ͼ��ݹ���ϵͳ" + "-- ��������Ա: " + mgNo);
					mg.setFlag(true);
			}
			
			
			else
			{
				this.setTitle("ͼ��ݹ���ϵͳ" + "-- δ֪Ȩ�޹���Ա: " + mgNo);
				mg.setFlag(false);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void initJp()
	{
		jp.setLayout(cl);
		jp.add(jlRoot, "root");
		jp.add(new Student(), "stu");
		jp.add(new BookManage(), "bm");
		jp.add(new SearchBook(), "sb");
		jp.add(new BorrowBook(), "bb");
		jp.add(new ReturnBook(), "rb");
		if (!isStudent)
			jp.add(this.mg, "manager");
		jp.add(new ExceedTime(), "et");
		jp.add(new errorPanel(), "errorp");
	}

	public void addTreeListener()
	{
		jt.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				DefaultMutableTreeNode cdmtn = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				NodeValue cnv = (NodeValue) cdmtn.getUserObject();
				if (cnv.value.equals("ͼ��ݹ���ϵͳ"))
				{
					cl.show(jp, "root");
				}
				if (cnv.value.equals("ѧ���û�����"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "stu");
				} else if (cnv.value.equals("ͼ�����"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "bm");
				}
				if (cnv.value.equals("��ѯͼ��"))
				{
					cl.show(jp, "sb");
				} else if (cnv.value.equals("����ԤԼͼ��"))
				{
					cl.show(jp, "bb");
				} else if (cnv.value.equals("�黹��ʧͼ��"))
				{
					cl.show(jp, "rb");
				} else if (cnv.value.equals("���ɷ���"))
				{
					cl.show(jp, "et");
				} else if (cnv.value.equals("����Ա����"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "manager");
				} else if (cnv.value.equals("�˳�"))
				{

					int i = JOptionPane.showConfirmDialog(Root.this, "�Ƿ��˳�ϵͳ��", "��Ϣ", JOptionPane.YES_NO_OPTION);
					if (i == JOptionPane.YES_OPTION)
					{
						System.exit(0);
					}
				}
			}
			
		});
		this.repaint();
	}

	public static void main(String[] args)
	{
		new Root("1001",false);
	}

}

class NodeValue
{
	String value;// �Զ���ڵ�����ַ�����

	public NodeValue(String value)// ������
	{
		this.value = value;
	}

	public String getValue()// �õ��ڵ��ֵ
	{
		return this.value;
	}

	public String toString()// ��дtoString()����
	{
		return value;
	}
}
