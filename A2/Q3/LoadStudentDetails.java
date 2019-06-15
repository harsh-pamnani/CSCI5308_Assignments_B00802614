
import java.io.FileReader;
import java.util.Scanner;

public class LoadStudentDetails {

	public void loadStudent(Student student) {
		Scanner in = null;
		try {
			in = new Scanner(new FileReader("student.txt"));
			student.setBannerID(in.next());
			student.setFirstName(in.next());
			student.setLastName(in.next());
			student.setEmail(in.next());
		} catch (Exception e) {
			System.out.println("Error occured in loading the student's details.");
			System.out.println("Error Message: " + e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
