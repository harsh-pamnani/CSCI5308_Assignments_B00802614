
public class RequestShip {
	public String apiKey;
	public String username;
	public String action;
	public String drug;
	public Long quantity;
	public String addressCustomer;
	public String addressStreet;
	public String addressCity;
	public String addressProvince;
	public String addressCountry;
	public String addressPostalCode;	
	
	public RequestShip(String apiKey, String username, String action, String drug, Long quantity,
			String addressCustomer, String addressStreet, String addressCity, String addressProvince,
			String addressCountry, String addressPostalCode) {
		this.apiKey = apiKey;
		this.username = username;
		this.action = action;
		this.drug = drug;
		this.quantity = quantity;
		this.addressCustomer = addressCustomer;
		this.addressStreet = addressStreet;
		this.addressCity = addressCity;
		this.addressProvince = addressProvince;
		this.addressCountry = addressCountry;
		this.addressPostalCode = addressPostalCode;
	}



	public String toJSONString() {
		return 		"{" 
				+ "\"apiKey\":\"" + apiKey + "\"," 
				+ "\"username\":\"" + username + "\"," 
				+ "\"action\":\"" + action + "\"," 
				+ "\"drug\":\"" + drug + "\"," 
				+ "\"quantity\":" + quantity + ","
				+ "\"address\":{ "
					+ "\"customer\":\"" + addressCustomer + "\","
					+ "\"street\":\"" + addressStreet + "\","
					+ "\"city\":\"" + addressCity + "\","
					+ "\"province\":\"" + addressProvince + "\","
					+ "\"country\":\"" + addressCountry + "\","
					+ "\"postalCode\":\"" + addressPostalCode + "\""
					+ "}"
				+ "}"; 
	}
}
