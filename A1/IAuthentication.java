// DO NOT MODIFY THIS FILE IN ANY WAY
public interface IAuthentication
{
	/*
		Given an API key from an incoming JSON request to the API, returns:
			TRUE - The request is authenticated
			FALSE - The request is not authenticated
	*/
	public boolean authenticate(String apiKey);

	/*
		Given a username and a request action, returns:
			TRUE - The user is authorized to perform the request action
			FALSE - The user is not authorized to perform the request action
	*/
	public boolean authorize(String username, RequestAction action);
}