package main;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * The Database API to select,insert,delete etc from database
 * connect() should be always called first to initiliZe the database connection
 * executUpdate() for updates like update,insert and delete
 * executeStatement() for select commands
 * every other method ist for a particular request and returns some kind of succes and or json object for response
 * 
 * @author jonas
 *
 */
public class DatabaseManager {

	private static Connection connection;
	/**
	 * Creates a connection to the database
	 * @param host the url to the database file
	 * @param user the username from the database acces user
	 * @param pass the password from the database acces user
	 */
	public static void connect(String host,String user,String pass) {
		
		if(connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(host,user,pass);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			throw new DatabaseException("Already Initilized Database!");
		}
	}
	/**
	 * Executes a Statement (Used for Select commands)
	 * @param command the command to execute
	 * @return the result set from the database
	 */
	public static ResultSet executeStatement(String command) {
		if(connection == null) {
			throw new DatabaseException("No Connection to Database!");
		}
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Executes Database updates used for everything else but select like update delete and insert
	 * @param command the command to be updated on database
	 */
	public static void executeUpdate(String command) {
		if(connection == null) {
			throw new DatabaseException("No Connection to Database!");
		}
		try {
			Statement statement = connection.createStatement();
			LogUI.print(command);
			statement.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isConnected() {

		return connection != null;
	}
	
	public static boolean login(String email,String password) throws SQLException {
		ResultSet rs= executeStatement("Select * from user where email = '"+email+"' AND password = '"+password+"';");
		
		if(rs.next()) {
			rs.close();
			return true;
		}else {
			rs.close();
			return false;
		}
		
		
	}
	
	public static boolean register(String email,String username,String password) {
		String statement = "INSERT into user(email,password ,username ) VALUES ('"+email+"','"+password+"','"+username+"')";
		try {
			executeUpdate(statement);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
