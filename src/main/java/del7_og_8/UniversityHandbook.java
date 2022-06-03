package del7_og_8;

import java.io.InputStream;
import java.util.*;

public class UniversityHandbook {

	private List<Course> courses = new ArrayList<>();

	/**
	 * Reads all the courses from a given input stream. The courses are on this
	 * form: courseName, averageGrade, prerequisite 1, prerequisite 2,
	 * prerequisite 3....
	 * 
	 * See courses.txt in src/main/resources/del7_og_8 for an example file.
	 * 
	 * Calling this method should remove any existing courses from the handbook.
	 * 
	 * A given course can have anything from 0 to unlimited number of prerequisites.
	 * The courses do not necessary come in order. Meaning that a course may appear
	 * in the prerequisite list as a never before seen course. The method should read
	 * in all courses, and set the courseName, averageGrade and prerequisites of all
	 * courses and add the courses to the courses field of this class.
	 * 
	 * A skeleton code to read from file is provided to you but feel free to write
	 * your own code for this.
	 * 
	 * You can assume that all lines from the file will be on the correct format.
	 * 
	 * @param stream InputStream containing the course data
	 */
	public void readFromInputStream(InputStream stream) {
		try (Scanner scanner = new Scanner(stream)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] details = line.split(",");

				Course course = null;

				if (this.getCourse(details[0]) != null) {
					course = this.getCourse(details[0]);
					course.setAverageGrade(Double.parseDouble(details[1]));
				} else {
					course = new Course(details[0], Double.parseDouble(details[1]));
					courses.add(course);
				}


				if (details.length > 2) {
					for (int i = 2; i < details.length; i++) {
						if (this.getCourse(details[i]) == null) {
							// Dersom faget ikke har blitt lagt til fra før av
							courses.add(new Course(details[i]));
						}

						course.addPrequisite(this.getCourse(details[i]));
					}
				}
			}
		}
	}

	/**
	 * Gets the course with the courseName
	 * 
	 * @param courseName The name of the course
	 * 
	 * @return The course with the given name
	 */
	public Course getCourse(String courseName) {
		return courses.stream().filter(c -> c.getCourseName().equals(courseName)).findFirst().orElse(null);
	}

	public static void main(String[] args) {
		UniversityHandbook handbook = new UniversityHandbook();
		// Reads inn all the files from the course
		handbook.readFromInputStream(handbook.getClass().getResourceAsStream("courses.txt"));
		System.out.println(handbook.getCourse("TDT1337").getPrerequisites());
	}

}
