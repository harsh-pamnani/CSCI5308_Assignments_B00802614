
import java.io.PrintWriter;

public class SaveStudentDetails {
	
	public void save(Student student) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("student.txt", "UTF-8");
			writer.println(student.getBannerID());
			writer.println(student.getFirstName());
			writer.println(student.getLastName());
			writer.println(student.getEmail());
		} catch (Exception e) {
			System.out.println("Error occured in saving the student's details.");
			System.out.println("Error Message: " + e.getMessage());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
