
public class ContactInfo {
	private String areaCode;
	private String phoneNumber;
	
	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}
	public String getAreaCode()
	{
		return areaCode;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber()
	{
		if (areaCode != null && areaCode != "")
		{
			return "(" + areaCode + ") " + phoneNumber; 
		}
		return phoneNumber;
	}
}
