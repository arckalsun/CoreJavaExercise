package mysqlTest;

import java.nio.file.*;
import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * This program tests that the database and the JDBC driver are correctly
 * configured.
 * 
 * @author john
 *
 */
public class TestDB
{

	public static void main(String[] args) throws IOException
	{
		try
		{
			runTest();
		} catch (SQLException ex)
		{
			for (Throwable t : ex)
				t.printStackTrace();
		}

	}

	/**
	 * Run a test by creating a table, adding a value, showing the table
	 * contents, and removing the table.
	 */
	public static void runTest() throws IOException, SQLException
	{
		try (Connection conn = getConnection())
		{
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			/*
			 * stat.executeUpdate("CREATE TABLE Greetings (message CHAR(20))");
			 * stat.
			 * executeUpdate("INSERT INTO Greetings VALUES ('Hello, World!')");
			 */

			try (ResultSet rs = stat.executeQuery("SELECT * FROM books"))
			{
				
				
					if (rs.next())
					{
						rs.relative(5);
						System.out.print(rs.getRow() + "\t");
						System.out.println(rs.getString(1));
					}
				

			}
			// stat.executeUpdate("DROP TABLE Greetings");

		}

	}

	/**
	 * Gets a connection from the properties specified in the file
	 * database.properties.
	 * 
	 * @return the database connection
	 * @throws IOException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws IOException, SQLException
	{
		Properties props = new Properties();
		try (InputStream in = Files.newInputStream(Paths.get("database.properties")))
		{
			props.load(in);
		}
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
			System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
	}

}
