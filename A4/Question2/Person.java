public class Person
{
	private String name;
	private ContactInfo contactInfo;
	private LoginCredentials loginCredentials;

	public Person(String name)
	{
		this.name = name;
		loginCredentials = new LoginCredentials();
		contactInfo = new ContactInfo();
	}

	public void setAreaCode(String areaCode)
	{
		contactInfo.setAreaCode(areaCode);
	}
	public String getAreaCode()
	{
		return contactInfo.getAreaCode();
	}
	public void setPhoneNumber(String phoneNumber)
	{
		contactInfo.setPhoneNumber(phoneNumber);
	}
	public String getPhoneNumber()
	{
		return contactInfo.getPhoneNumber();
	}

	public void setLoginCredentials(String userName, String password)
	{
		loginCredentials.setLoginCredentials(userName, password);
	}
	
	public boolean authenticateUser()
	{
		return loginCredentials.authenticateUser();
	}
}