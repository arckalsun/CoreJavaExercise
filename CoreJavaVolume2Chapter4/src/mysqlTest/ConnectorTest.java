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
			System.out.println("�ɹ�����MySQL������");
		} catch (ClassNotFoundException e)
		{
			System.out.println("�Ҳ���MySQL����!");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/mysql";
		// ����DriverManager�����getConnection()���������һ��Connection����
		Connection conn;
		try
		{
			conn = DriverManager.getConnection(url, "root", "123456");
			Statement stmt = conn.createStatement();
			System.out.print("�ɹ����ӵ����ݿ⣡");
			stmt.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}