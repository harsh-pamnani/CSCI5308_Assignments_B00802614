public class Main
{
	public static void main(String[] args)
	{
		boolean pass = true;
		Person person = new Person("joe");
		person.setAreaCode("902");
		person.setPhoneNumber("123-4567");
		person.setLoginCredentials("joe", "joepass");
		pass = person.getPhoneNumber().equals("(902) 123-4567") && person.authenticateUser();
		if (pass)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
	}
}