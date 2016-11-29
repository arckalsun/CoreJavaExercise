package BookManager;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener
{
	private JPanel jp = new JPanel();
	private JLabel [] jlArray = {
			new JLabel("�û�IP"), new JLabel("�˿ں�"), new JLabel("�û���"), new JLabel("����"), new JLabel("")
	};
	private JButton[] jbArray = {
			new JButton("���"), new JButton("ѧ����¼"), new JButton("�����¼")
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
		this.setTitle("��¼");
		this.setResizable(false);
		this.setBounds(100,100,400,350);
		this.setIconImage(image);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent event)
	{
		// TODO Auto-generated method stub
		String mgno = jtextArray[2].getText().trim();//�û���
		String mgIP = jtextArray[0].getText().trim();
		String port = jtextArray[1].getText().trim();
		String message = mgIP+":" + port;
		DataBase.log = this;
		DataBase.message = message;
		if (event.getSource() == jtextArray[0])//�û�IP
		{
			jtextArray[1].requestFocus();
		}
		if (event.getSource() == jtextArray[1])//�˿ں�
		{
			jtextArray[2].requestFocus();
		}
		if (event.getSource() == jtextArray[2])//�û���
		{
			jpassword.requestFocus();
		}
		else if (event.getSource() == jbArray[0])//��հ�ť
		{
			jlArray[4].setText("");
			jtextArray[2].setText("");
			jpassword.setText("");
			jtextArray[2].requestFocus();
		}
		else if ( event.getSource() == jbArray[2])//����Ա��¼
		{
			if (!mgno.matches("\\d+"))//ƥ������
			{
				JOptionPane.showMessageDialog(this, "�û�����ʽ����!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[0].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�û�IP��Ϊ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�˿ںŲ���Ϊ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
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
			String.valueOf(jpassword.getPassword()).equals(password))//��¼�ɹ�
				{
					jlArray[4].setText("��ϲ������¼�ɹ�!");
					new Root(mgNo,false);
					this.dispose();
				}
				else
				{
					jlArray[4].setText("��¼ʧ��!!!");
				}}
			catch ( Exception e1)
			{
				e1.printStackTrace();
			}
			db.dbClose();
		
			
		}
		else if(event.getSource() == jbArray[1])//ѧ���û���¼
		{
			if (!jtextArray[2].getText().trim().matches("\\d+"))
			{
				JOptionPane.showMessageDialog(this, "��������, ѧ��ֻ��Ϊ����!!!","��Ϣ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[0].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�û�IP��Ϊ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (jtextArray[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�˿ںŲ���Ϊ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//��ѯѧ���ı����������ѧ���Ƿ������STUDENT����
			sql = "select StuNO, Password from STUDENT where StuNo=" + Integer.parseInt(jtextArray[2].getText().trim());
			db = new DataBase();
			db.selectDB(sql);
			try
			{
				if (!(db.rs.next()))
					JOptionPane.showMessageDialog(this, "�޴�ѧ�ţ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				else
				{
					String stuNO = db.rs.getString(1).trim();
					String password = db.rs.getString(2).trim();
					if (jtextArray[2].getText().trim().equals(stuNO) && String.valueOf(jpassword.getPassword()).equals(password))
					{
						jlArray[4].setText("��ϲ������¼�ɹ�!!!");
						new Root(stuNO,true);
						this.dispose();
					}
					else//��¼ʧ��
					{
						jlArray[4].setText("�Բ��𣬵�¼ʧ��!!!");
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
