

public class AuthenticationMock implements IAuthentication {
	
	@Override
	public boolean authenticate(String apiKey) {
		if(apiKey.indexOf("FALSE") != -1) {
			return false;
		}
		if(apiKey.indexOf("TRUE") != -1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean authorize(String username, RequestAction action) {
		if(!username.equals("") && username.indexOf(action.toString()) != -1 ) {
			return true;
		}
		return false;
	}
}