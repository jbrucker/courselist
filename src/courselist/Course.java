package courselist;

/**
 * A course in the university catalog.
 * This class contains some NONSENSE methods (in addition to the required methods)
 * to obfuscate the solution to the quiz.
 */
public class Course {
	// id is the Entity id and database primary key field.
	// id = 0 for objects not yet stored to database.
	private int id;
	private String courseNumber;
	private String title;
	private int credits;
	private double difficulty;


	/**
	 * Initialize a new Course instance.
	 */
	public Course(String courseNumber, String title, int credits) {
		this.courseNumber = courseNumber;
		this.title = title;
		this.credits = credits;
		this.difficulty = 0;
		this.id = 0;
	}

	/**
	 * Get the course title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get the course credits.
	 */
	public int getCredits() {
		return credits;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%d)", courseNumber, title, credits);
	}
	

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
}
