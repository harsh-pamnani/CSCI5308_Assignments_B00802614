// DO NOT MODIFY THIS FILE IN ANY WAY
/*
	This is the interface the prodops team will use to process the request.  In the final product
	they will instantiate your RequestProcessor class and call processRequest() with the incoming
	JSON.  They will pass in the real, final implementations of the authentication, ShipMate and
	database objects. You will test your object by passing in mock objects that implement the
	authentication, ShipMate and datatabase interfaces.

	You must implement processRequest() to move the request through the stages:
		- Authentication and authorization: Use the API key, user name and action to authenticate
		  the request and authorize the user.
		- Validation:  Verify the drug name exists, verify the shipping address is a known
		  address, the warehouse has enough stock to satisfy the ship request.
		- Processing: Process the request by calling into the database to return count or to claim
		  stock and to issue the shipping request to ShipMate.

	processRequest() must return the correct JSON result.

	You have been provided with a starting RequestProcessor class. Implement all of your logic in
	that class.  Do not write your own RequestProcessor from scratch.
*/
public interface IRequestProcessor
{
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database);
}