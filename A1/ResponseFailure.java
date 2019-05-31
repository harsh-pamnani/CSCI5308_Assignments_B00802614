
public class ResponseFailure extends Response {
	public String error;
	
	public String toJSONString() {
		
		return "{" 
				+ "\"status\":" + status + "," 
				+ "\"error\":\"" + error + "\"" 
				+ "}";
	
	}
}
