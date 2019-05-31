
public class ResponseSuccsessShip extends Response {
	public String estimateddeliverydate;
	
	public String toJSONString() {
		
		return "{" 
				+ "\"status\":" + status + "," 
				+ "\"estimateddeliverydate\":\"" + estimateddeliverydate + "\"" 
				+ "}";
	}
}