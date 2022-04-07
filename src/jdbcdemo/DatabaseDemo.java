package jdbcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseDemo {
	// The URL of the database. You can specify a path to the database.
	// Windows: use forward slash (as on every other OS!) not backslash.
	// Example:   "jdbc:sqlite:/home/Foo/workspace/courselist/sample.db"
	static final String databaseUrl = "jdbc:sqlite:sample.db";
	
	// A shared connection to the database.
	// In well designed code, this will not be static.
	static Connection connection = null;
	
	// Scanner for reading console input
	static final Scanner console = new Scanner(System.in);
	
	
	/**
	 * Print all courses in the database.
	 * @throws SQLException
	 */
	public static void findAllCourses() throws SQLException {
		Statement statement = connection.createStatement();
		// execute should return true for a query operation
		ResultSet results = statement.executeQuery("SELECT * FROM courses");
		
		if (results.isAfterLast()) {
			System.out.println("No courses found");
		}
		
		while(results.next()) {
			// you can get columns from a ResultSet using either an index (int) or column name
			// the programmer must choose a compatible type for the getxxx method.
			// For example: courseNumber = results.getString(2) 
			// This requires knowing the column order in the table. First column has index 1.
			String courseNumber = results.getString("course_number");
			String title = results.getString("title");
			int credits = results.getInt("credits");
			double difficulty = results.getDouble("difficulty");
			System.out.printf("%10s %-24.24s (%d) difficulty %.1f\n",
					courseNumber, title, credits, difficulty);
		}
		statement.close();
		
	}
	
	public static void findCourse() throws SQLException {
		System.out.print("Course number to find:");
		String criteria = console.nextLine().trim();
		
		// Use a Prepared Statement to protect against SQL injection.
		// A ? in a positional parameter for which a value will be inserted later.
		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM courses c WHERE c.course_number is ?");
		// Replace positional parameter with actual value
		statement.setString(1, criteria);
		ResultSet results = statement.executeQuery();
		// should only have 1 match
		while(results.next()) {
			String courseNumber = results.getString("course_number");
			String title = results.getString("title");
			int credits = results.getInt("credits");
			double difficulty = results.getDouble("difficulty");
			System.out.printf("%10s %-24.24s (%d) difficulty %.1f\n",
					courseNumber, title, credits, difficulty);
		}
		statement.close();
		
		
	}
	
	
	public static void main(String[] args) {

		
		// Get a connection to the database, even if the database doesn't exist yet
		connection = null;
		try {
			connection = DriverManager.getConnection(databaseUrl);
			
			// What kind of connection object did we actually get?
			System.out.println("Got connection of type "+ connection.getClass().getName());
			
			// Try some standard database operations using JDBC.
			System.out.println("All courses in the database");
			findAllCourses();
			
			System.out.println("\nFind a course");
			findCourse();
			
		}
		catch (SQLException ex) {
			System.out.println("Exception: " + ex.getMessage());
			System.exit(1);
		}
		
		
		
		
		// When done, close the connection
		try {
			connection.close();
		}
		catch (SQLException ex) {
			System.out.println("Exception closing connection: " + ex.getMessage());
		}
	}

}
