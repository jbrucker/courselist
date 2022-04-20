package courselist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import courselist.persistence.CourseDao;

/**
 * Console tests of CourseDao methods.
 */
public class DaoTest {
	public static final String DB_URL = "jdbc:sqlite:sample.db";

	
	/**
	 * Console based test of CourseDao.get(id)
	 * @throws SQLException if cannot create a connection to database
	 */
	public static void testGet(CourseDao dao) {
		System.out.println("id  Course");
		for(int id=1; id<20; id++) {		
			Course course = dao.get(id);
			System.out.printf("%2d  %s\n", id, course);
		}
	}
	
	public static void main(String[] args) throws SQLException {
		final Scanner console = new Scanner(System.in);
		Connection connection = DriverManager.getConnection(DB_URL);
		
		CourseDao dao = new CourseDao(connection);
		
		testGet(dao);
	}
}
