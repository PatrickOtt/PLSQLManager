package de.professional_webworkx.blog.sp.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class JDBCConnector {

	public static JDBCConnector INSTANCE;
	
	/*
	 * We need some connection information
	 */
	private static final String USER	= "USER";
	private static final String PASS	= "PASS";
	private static final String HOST	= "localhost";
	private static final String DB		= "DATABASE";
	private static final int	PORT	= 3306;
	private static final String URL		= "jdbc:mysql://"+HOST+":"+PORT+"/"+DB;
	private Connection connection;
	
	private java.sql.PreparedStatement statement;
	
	private JDBCConnector() {
		connect();
	}
	
	private void connect() {
		
		try {
			Driver driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(URL, USER, PASS);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Something went wrong while closing the connection");
			}
		}
	}

	public void getCustomerCount() {
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM customer;");
			while(rs != null && rs.next()){
				System.out.println(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addStoredProcedure() {
		try {
			String sql = "create procedure myProc() "
					+ "BEGIN "
					+ "SELECT COUNT(*) as total FROM customer;"
					+ "END";
			statement = connection.prepareStatement(sql);
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static JDBCConnector getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new JDBCConnector();
		}
		
		return INSTANCE;
	}
}
