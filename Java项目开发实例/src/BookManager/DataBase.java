package BookManager;

import java.sql.*;

import javax.swing.JOptionPane;

public class DataBase
{
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;
	int count;
	public static String message;
	public static Login log;
	
	public DataBase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			// "jdbc:mysql://localhost:3306/mysql";
			conn = DriverManager.getConnection("jdbc:mysql://" + message + "/test?characterEncoding=UTF-8&useSSL=false", "root", "123456");
			stat = conn.createStatement();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(log, "用户IP或端口号错误!!!","信息",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void selectDB(String sql)
	{
		System.out.println(this.getClass().getName()+".selectDB(): "+sql);
		try {
			rs = stat.executeQuery(sql);
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	public int updateDB(String sql)
	{
		try
		{
			//sql = new String(sql.getBytes(),"utf-8");
			System.out.println(this.getClass().getName()+".updateDB(): "+sql);
			count = stat.executeUpdate(sql);
			
		}
		catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e)
		{
			JOptionPane.showMessageDialog(log, e.toString()+"\n已有这个主键记录，不能重复!!!\n","消息",JOptionPane.INFORMATION_MESSAGE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	
	public void dbClose()
	{
		try{
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
