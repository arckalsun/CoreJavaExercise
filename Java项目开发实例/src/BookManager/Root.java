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
	{ new DefaultMutableTreeNode(new NodeValue("图书馆管理系统")), new DefaultMutableTreeNode(new NodeValue("学生用户管理")),
			new DefaultMutableTreeNode(new NodeValue("图书管理")), new DefaultMutableTreeNode(new NodeValue("查询图书")),
			new DefaultMutableTreeNode(new NodeValue("借阅预约图书")), new DefaultMutableTreeNode(new NodeValue("归还挂失图书")),
			new DefaultMutableTreeNode(new NodeValue("交纳罚款")), new DefaultMutableTreeNode(new NodeValue("管理员管理")),
			new DefaultMutableTreeNode(new NodeValue("退出"))

	};
	DefaultTreeModel dtm = new DefaultTreeModel(dmtn[0]);
	JTree jt = new JTree(dtm);
	JScrollPane jsp = new JScrollPane(jt);

	boolean isStudent = false;
	String mgNo;
	String stuNo;
	private JSplitPane jsplr = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
	private JPanel jp = new JPanel();
	Image image ;// 加载图片Image对象
	//ImageIcon ii = new ImageIcon(image);
	private JLabel jlRoot ;
	private Manager mg;

	CardLayout cl = new CardLayout();

	/**
	 * 
	 * @param No
	 *            号码
	 * @param b
	 *            true 代表是学生用户，false 代表管理员用户
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
		g.drawString("图书馆管理系统", 100, 200);*/
		
		this.setIconImage(image);
		// this.setTitle("图书馆管理系统");
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
			this.setTitle("图书馆管理系统" + "-- 学生用户: " + str);

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
			if (str.equals("普通"))
			{
				mg.setFlag(false);
				this.setTitle("图书馆管理系统" + "-- 普通管理员: " + mgNo);
			} else if (str.equals("超级"))
			{
					this.setTitle("图书馆管理系统" + "-- 超级管理员: " + mgNo);
					mg.setFlag(true);
			}
			
			
			else
			{
				this.setTitle("图书馆管理系统" + "-- 未知权限管理员: " + mgNo);
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
				if (cnv.value.equals("图书馆管理系统"))
				{
					cl.show(jp, "root");
				}
				if (cnv.value.equals("学生用户管理"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "stu");
				} else if (cnv.value.equals("图书管理"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "bm");
				}
				if (cnv.value.equals("查询图书"))
				{
					cl.show(jp, "sb");
				} else if (cnv.value.equals("借阅预约图书"))
				{
					cl.show(jp, "bb");
				} else if (cnv.value.equals("归还挂失图书"))
				{
					cl.show(jp, "rb");
				} else if (cnv.value.equals("交纳罚款"))
				{
					cl.show(jp, "et");
				} else if (cnv.value.equals("管理员管理"))
				{
					if (isStudent)
					{
						cl.show(jp, "errorp");
						jp.repaint();
					} else
						cl.show(jp, "manager");
				} else if (cnv.value.equals("退出"))
				{

					int i = JOptionPane.showConfirmDialog(Root.this, "是否退出系统？", "消息", JOptionPane.YES_NO_OPTION);
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
	String value;// 自定义节点对象字符属性

	public NodeValue(String value)// 构造器
	{
		this.value = value;
	}

	public String getValue()// 得到节点的值
	{
		return this.value;
	}

	public String toString()// 重写toString()方法
	{
		return value;
	}
}
