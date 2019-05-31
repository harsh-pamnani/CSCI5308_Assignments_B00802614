
public class DefaultFailureResponses {
	public static final String AUTHENTICATION_FAILURE = "{" 
														+ "\"status\":500," 
														+ "\"error\":\"Authentication Failure\"" 
														+ "}";
	
	public static final String AUTHORIZATION_FAILURE = "{" 
													+ "\"status\":500," 
													+ "\"error\":\"Not Authorized\"" 
													+ "}";
	
	public static final String UNKNOWN_DRUG = "{" 
											+ "\"status\":500," 
											+ "\"error\":\"Unknown Drug\"" 
											+ "}";
	
	public static final String UNKNOWN_ADDRESS = "{" 
												+ "\"status\":500," 
												+ "\"error\":\"Unknown Address\"" 
												+ "}";
	
	public static final String INSUFFICIENT_STOCK = "{" 
													+ "\"status\":500," 
													+ "\"error\":\"Insufficient Stock\"" 
													+ "}";
}
