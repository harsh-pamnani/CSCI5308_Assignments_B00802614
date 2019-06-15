
public class Student {
	private String bannerID;
	private String firstName;
	private String lastName;
	private String email;

	public Student() {
		bannerID = null;
		firstName = null;
		lastName = null;
		email = null;
	}

	public void setBannerID(String bannerID) {
		this.bannerID = bannerID;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBannerID() {
		return bannerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
}