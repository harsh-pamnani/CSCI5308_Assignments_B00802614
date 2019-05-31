
public class ResponseSuccessQuery extends Response {
	public Long count;
	
	public String toJSONString() {
		
		return "{" 
				+ "\"status\":" + status + "," 
				+ "\"count\":" + count 
				+ "}";
	}
}
