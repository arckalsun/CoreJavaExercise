package mysqlTest;

import java.sql.*;

/**
 * This program shows how to use mysql connector for java.
 * 
 * @author john
 *
 */
public class ConnectorTest
{

	public static void main(String[] args)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动！");
		} catch (ClassNotFoundException e)
		{
			System.out.println("找不到MySQL驱动!");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/mysql";
		// 调用DriverManager对象的getConnection()方法，获得一个Connection对象
		Connection conn;
		try
		{
			conn = DriverManager.getConnection(url, "root", "123456");
			Statement stmt = conn.createStatement();
			System.out.print("成功连接到数据库！");
			stmt.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
