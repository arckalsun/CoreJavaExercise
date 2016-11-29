package BookManager;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener
{
	private JPanel jp = new JPanel();
	private JLabel [] jlArray = {
			new JLabel("用户IP"), new JLabel("端口号"), new JLabel("用户名"), new JLabel("密码"), new JLabel("")
	};
	private JButton[] jbArray = {
			new JButton("清空"), new JButton("学生登录"), new JButton("管理登录")
	};
	private JTextField[] jtextArray = {
			new JTextField("127.0.0.1"), new JTextField("3306"), new JTextField("1001")
	};
	private JPasswordField jpassword = new JPasswordField("1001");
	Image image = new ImageIcon("ico.png").getImage();
	String sql;
	DataBase db;

	public Login()
	{
		jp.setLayout(null);
		for ( int i = 0; i < 4; i++)
		{
			jlArray[i].setBounds(30,20+i*50,80,25);
			jp.add(jlArray[i]);
		}
		for (int i = 0; i < 3; i++)
		{
			jbArray[i].setBounds(10+i*120, 230, 100, 25);
			jp.add(jbArray[i]);
			jbArray[i].addActionListener(this);
		}
		for ( int i = 0; i < 3; i++)
		{
			jtextArray[i].setBounds(80,20+50*i, 180,25);
			jp.add(jtextArray[i]);
			jtextArray[i].addActionListener(this);
		}
		jpassword.setBounds(80,170,180,25);
		jp.add(jpassword);
		jpassword.addActionListener(this);
		jlArray[4].setBounds(10,280,300,25);
		jp.add(jlArray[4]);
		this.add(jp);
		this.setTitle("登录");
		this.setResizable(false);
		this.setBounds(100,100,400,350);
		this.setIconImage(image);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent event)
	{
		// TODO Auto-generated method stub
		String mgno = jtextArray[2].getText().trim();//用户名
		String mgIP = jtextArray[0].getText().trim();
		String port = jtextArray[1].getText().trim();
		String message = mgIP+":" + port;
		DataBase.log = this;
		DataBase.message = message;
		if (event.getSource() == jtextArray[0])//用户IP
		{
			jtextArray[1].requestFocus();
		}
		if (event.getSource() == jtextArray[1])//端口号
		{
			jtextArray[2].requestFocus();
		}
		if (event.getSource() == jtextArray[2])//用户名
		{
			jpassword.requestFocus();
		}
		else if (event.getSource() == jbArray[0])//清空按钮
		{
			jlArray[4].setText("");
			jtextArray[2].setText("");
			jpassword.setText("");
			jtextArray[2].requestFocus();
		}
		else if ( event.getSource() == jbArray[2])//管理员登录
		{
			if (!mgno.matches("\\d+"))//匹配数字
			{
				JOptionPane.showMessageDialog(this, "用户名格式错误!!!","信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[0].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "用户IP不为空!!!","信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "端口号不能为空!!!","信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			sql = "Select mgNo,password from manager where mgNo="+Integer.parseInt(mgno);
			
			db = new DataBase();
			db.selectDB(sql);
			try
			{
				String mgNo = "";
				String password = "";
				while (db.rs.next())
				{
					mgNo = db.rs.getString(1).trim();
					password = db.rs.getString(2).trim();
				}
				if (jtextArray[2].getText().trim().equals(mgNo)&& 
			String.valueOf(jpassword.getPassword()).equals(password))//登录成功
				{
					jlArray[4].setText("恭喜您，登录成功!");
					new Root(mgNo,false);
					this.dispose();
				}
				else
				{
					jlArray[4].setText("登录失败!!!");
				}}
			catch ( Exception e1)
			{
				e1.printStackTrace();
			}
			db.dbClose();
		
			
		}
		else if(event.getSource() == jbArray[1])//学生用户登录
		{
			if (!jtextArray[2].getText().trim().matches("\\d+"))
			{
				JOptionPane.showMessageDialog(this, "输入有误, 学号只能为数字!!!","消息", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[0].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "用户IP不为空!!!","信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "端口号不能为空!!!","信息",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//查询学号文本中所输入的学号是否存在于STUDENT表中
			sql = "select StuNO, Password from STUDENT where StuNo=" + Integer.parseInt(jtextArray[2].getText().trim());
			db = new DataBase();
			db.selectDB(sql);
			try
			{
				if (!(db.rs.next()))
					JOptionPane.showMessageDialog(this, "无此学号！","消息",JOptionPane.INFORMATION_MESSAGE);
				else
				{
					String stuNO = db.rs.getString(1).trim();
					String password = db.rs.getString(2).trim();
					if (jtextArray[2].getText().trim().equals(stuNO) && String.valueOf(jpassword.getPassword()).equals(password))
					{
						jlArray[4].setText("恭喜您，登录成功!!!");
						new Root(stuNO,true);
						this.dispose();
					}
					else//登录失败
					{
						jlArray[4].setText("对不起，登录失败!!!");
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			db.dbClose();
		}
		
	}
	
	public static void main(String[] args)
	{
		new Login();
	}

}
