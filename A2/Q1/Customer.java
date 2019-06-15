public class Customer
{
	private String email;
	private String country;
	private String province;

	public Customer()
	{
		email = "joe@email.com";
		country = "Canada";
		province = "Nova Scotia";
	}

	public boolean isCanadian()
	{
		return country.equals("Canada");
	}

	public boolean isNovaScotian()
	{
		return province.equals("Nova Scotia");
	}

	public String getEmail() {
		return email;
	}
}