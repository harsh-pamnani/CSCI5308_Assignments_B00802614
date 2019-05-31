
public class RequestQuery {
	public String apiKey;
	public String username;
	public String action;
	public String drug;
	
	public RequestQuery(String apiKey, String username, String action, String drug) {
		this.apiKey = apiKey;
		this.username = username;
		this.action = action;
		this.drug = drug;
	}
	
	public String toQueryRequestString() {
		return 		"{" 
				+ "\"apiKey\":\"" + apiKey + "\"," 
				+ "\"username\":\"" + username + "\"," 
				+ "\"action\":\"" + action + "\"," 
				+ "\"drug\":\"" + drug + "\"" 
				+ "}"; 
	}
}
