package BookManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ExceedTime extends JPanel implements ActionListener
{
	private JPanel panel = new JPanel();
	
	private JLabel [] labels = { new JLabel("������ѧ��"), new JLabel("��������")};
	private JTextField[] textFields = { new JTextField(), new JTextField()};
	private JButton [] buttons = { new JButton("��ѯǷ��"), new JButton("����")};
	
	
	public ExceedTime()
	{
		this.setLayout(new GridLayout(1,1));
		panel.setLayout(null);
		// TODO Auto-generated constructor stub
		for (int i=0; i<2; i++)
		{
			panel.add(labels[i]);
			panel.add(textFields[i]);
			panel.add(buttons[i]);
			labels[i].setBounds(10, 40+i*40, 120, 20);
			textFields[i].setBounds(130, 40+i*40, 200, 20);
			if (Root.isStudent)
			{
				textFields[0].setText(Root.stuNo);
				textFields[0].setEditable(false);
			}
			buttons[i].setBounds(20+i*200,150, 100, 20);
			
			buttons[i].addActionListener(this);
		}
		
		this.add(panel);
		this.setBounds(3,10,700,400);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		int day=0;
		DataBase db = new DataBase();
		String sno= textFields[0].getText().trim();
		if (sno.equals(""))
		{
			JOptionPane.showMessageDialog(this, "ѧ�Ų���Ϊ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (sno.matches("\\D"))
		{
			JOptionPane.showMessageDialog(this, "ѧ��ֻ��Ϊ����!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sql = "select DelayTime from exceedtime where StuNO="+sno;
		db.selectDB(sql);
		try{
			int flag = 0;
			while (db.rs.next())
			{
				flag++;
				day+=db.rs.getInt(1);
			}
			if (flag ==0)
			{
				JOptionPane.showMessageDialog(this, "��������黹û�г���!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		if (e.getSource()==buttons[0])//��ѯǷ��
		{
			if (day>0)
			{
				JOptionPane.showMessageDialog(this, "�����Ѿ����ڣ�Ӧ�ý��ɷ���Ϊ: "+day*0.1+"Ԫ,","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(this, "��������黹û�г���!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		else if(e.getSource()==buttons[1])//���� ��ť
		{
			if (textFields[1].getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(this, "�����뽻����!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int k=JOptionPane.showConfirmDialog(this, "Ӧ�ý��ɷ���Ϊ: "+day*0.1+"Ԫ, �Ƿ��ɷ���?","��Ϣ",JOptionPane.YES_NO_OPTION);
			if (k==JOptionPane.YES_OPTION)
			{
				int ii = Integer.parseInt(textFields[1].getText().trim());
				if (ii<(day*0.1))
				{
					sql = "update exceedtime set delaytime=delaytime-"+ii/0.1+" where stuNO="+sno;
					db = new DataBase();
					int i = db.updateDB(sql);
					if (i==1)
					{
						JOptionPane.showMessageDialog(this, "���ѳɹ�����  "+ii+"Ԫ�����轻��"+(day*0.1-ii)+"Ԫ!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else
					{
						JOptionPane.showMessageDialog(this, "�Բ��𣬽���ʧ��!!!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "���ѳɹ�����"+day*0.1+"Ԫ!","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
					textFields[1].setText("");
					sql = "delete from exceedtime where StuNO="+sno;
					db.updateDB(sql);
					sql = "update STUDENT set permitted='��' where StuNO="+sno;
					db.updateDB(sql);
					
					
				}
			}
			
		}
		db.dbClose();
	}
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new ExceedTime();
	}

}
