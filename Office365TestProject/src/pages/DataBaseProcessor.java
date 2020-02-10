package pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import com.mysql.jdbc.PreparedStatement;

public class DataBaseProcessor {
	
	private Connection connection;
	java.sql.PreparedStatement preparedStatement = null;
	private static final String INSERT_NEW = "INSERT INTO table VALUES(?,?,?,?) ";
	private static final String USER_NAME = "";
	private static final String PASSWORD = "";
	private static final String URL = "jdbc:mysql://localhost/";
	
	public DataBaseProcessor() throws SQLException {
		DriverManager.registerDriver(new FabricMySQLDriver());
	}
	
	public Connection getConnection(String url, String username, String password) throws SQLException {
		if(connection != null) 
			{return connection;}
		connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
  

// v testovom classe nuzno sozdat DataBaseProcessor db = new DataBaseProcessor();
//Connection conn = db.getConnection(URL, USER, PASSWORD);
// String query = "select ...";
// Statement statement = conn.createStatement();
// ResultSet resSet = statement.executeQuery(query);
//statement.close(); 
//conn.close();

/*
 * while(resSet.next()){
 * resSet.getInt("columnName");
 * ....
 * }
 * */

	
	public void setConnection() throws SQLException, FileNotFoundException {
		Connection connectionPS = null;
		FabricMySQLDriver driverFab = new FabricMySQLDriver();
		DriverManager.registerDriver(driverFab);
		connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		preparedStatement = connectionPS.prepareStatement(INSERT_NEW);
		preparedStatement.setInt(1,2); 
		preparedStatement.setString(2, "someValue");
		preparedStatement.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
		preparedStatement.setBlob(4, new FileInputStream("somePicture.png"));
		preparedStatement.execute();
		
	}
}