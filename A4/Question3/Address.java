
public class Address {
	private String street;
	private String city;
	private String province;
	private String postalCode;
	
	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getProvince() {
		return province;
	}

	public String getPostalCode() {
		return postalCode;
	}
	
	public boolean isRobsAddress()
	{
		return street.equals("Rob street") &&
				city.equals("Rob city") &&
				province.equals("Rob province") &&
				postalCode.equals("Rob postalcode");
	}
}
