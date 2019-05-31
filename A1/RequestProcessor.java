
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class RequestProcessor implements IRequestProcessor
{
	/**
	 * This is the method which processes the input request based on the objects passed to it.
	 * For testing purpose, method has been verified using mock objects.
	 */
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database)
	{
		String response = "";
		String apiKey, username, action, drugName;
		Long quantity = (long) 0;
		Address address = new Address();
		
		try {
			Object obj = new JSONParser().parse(json);
			JSONObject requestJsonObj = (JSONObject) obj;
			
			RequestAction reqAction = RequestAction.QUERY;
			apiKey = (String) requestJsonObj.get("apiKey");
			username = (String) requestJsonObj.get("username");
			action = (String) requestJsonObj.get("action");
			drugName = (String) requestJsonObj.get("drug");
			
			if(action.equals(RequestAction.SHIP.toString())) {
				reqAction = RequestAction.SHIP;
				quantity = (Long) requestJsonObj.get("quantity");
				JSONObject addressJsonObject = (JSONObject) requestJsonObj.get("address");
				
				address.customer = (String) addressJsonObject.get("customer");
				address.street = (String) addressJsonObject.get("street");
				address.city = (String) addressJsonObject.get("city");
				address.province = (String) addressJsonObject.get("province");
				address.country = (String) addressJsonObject.get("country");
				address.postalCode = (String) addressJsonObject.get("postalCode");	
			}
			
			if (authentication.authenticate(apiKey)) {
				if(authentication.authorize(username, reqAction)) {
					if(database.checkDrugExists(drugName)) {
						if(action.equals(RequestAction.QUERY.toString())) {
							ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
							resSuccessQuery.status = (long) 200;
							resSuccessQuery.count = (long) database.getDrugCount(drugName);
							response = resSuccessQuery.toJSONString();
						} else {
							if(shipMate.isKnownAddress(address)) {
								int quantityInt = Math.toIntExact(quantity);

								if (database.claimDrug(drugName, quantityInt)) {								
									ResponseSuccsessShip reqSuccessShip = new ResponseSuccsessShip();
									reqSuccessShip.status = (long) 200;
									reqSuccessShip.estimateddeliverydate = shipMate.shipToAddress(address, quantityInt, drugName);
									
									response = reqSuccessShip.toJSONString();
								} else {
									ResponseFailure responseFailure = new ResponseFailure();
									responseFailure.status = (long) 500;
									responseFailure.error = "Insufficient Stock";
									
									response = responseFailure.toJSONString();
								}
							} else {
								ResponseFailure responseFailure = new ResponseFailure();
								responseFailure.status = (long) 500;
								responseFailure.error = "Unknown Address";
								response = responseFailure.toJSONString();
							}
						}
					} else {
						ResponseFailure responseFailure = new ResponseFailure();
						responseFailure.status = (long) 500;
						responseFailure.error = "Unknown Drug";
						response = responseFailure.toJSONString();
					}
				} else {
					ResponseFailure responseFailure = new ResponseFailure();
					responseFailure.status = (long) 500;
					responseFailure.error = "Not Authorized";
					response = responseFailure.toJSONString();
				}
			}
			else {
				ResponseFailure responseFailure = new ResponseFailure();
				responseFailure.status = (long) 500;
				responseFailure.error = "Authentication Failure";
				response = responseFailure.toJSONString();
			}
		} catch (Exception e) {
			System.out.println("Some error occurred:" + e.getMessage());
		}
		
		return response;
	}
	
	public static void authenticateFailureTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88FALSEklasdf8u22j","brendan",RequestAction.QUERY.toString(),"Clonazepam");
		String requestJson = reqQuery.toQueryRequestString();

		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		String expectedResponseJson = DefaultFailureResponses.AUTHENTICATION_FAILURE;
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authenticate Failure");
		} else {
			System.out.println("FAIL - Authenticate Failure");
		}
	
	}
	
	public static void authenticateFailureTestEmptyAPIKey(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("","brendanQUERY",RequestAction.QUERY.toString(),"Triazolam");
		String requestJson = reqQuery.toQueryRequestString();

		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		String expectedResponseJson = DefaultFailureResponses.AUTHENTICATION_FAILURE;
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authenticate Failure - Empty API Key");
		} else {
			System.out.println("FAIL - Authenticate Failure - Empty API Key");
		}
	
	}
	
	public static void authenticateSuccessTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","pamnaniQUERY",RequestAction.QUERY.toString(),"Triazolam");
		String requestJson = reqQuery.toQueryRequestString();

		ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
		resSuccessQuery.status = (long) 200;
		resSuccessQuery.count = (long) 85;
		
		String expectedResponseJson = resSuccessQuery.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authenticate Success");
		} else {
			System.out.println("FAIL - Authenticate Success");
		}
	}
		
	public static void authorizationFailureTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","rhawkey",RequestAction.QUERY.toString(),"Tylenol");
		String requestJson = reqQuery.toQueryRequestString();
		
		String expectedResponseJson = DefaultFailureResponses.AUTHORIZATION_FAILURE;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authorization Failure");
		} else {
			System.out.println("FAIL - Authorization Failure");
		}
	}
	
	public static void authorizationFailureTestEmptyUsername(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","",RequestAction.QUERY.toString(),"Alprazolam");
		String requestJson = reqQuery.toQueryRequestString();
		
		String expectedResponseJson = DefaultFailureResponses.AUTHORIZATION_FAILURE;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authorization Failure - Empty Username");
		} else {
			System.out.println("FAIL - Authorization Failure - Empty Username");
		}
	}
	
	public static void authorizationSuccessTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","markQUERY",RequestAction.QUERY.toString(),"Librium");
		String requestJson = reqQuery.toQueryRequestString();
		
		ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
		resSuccessQuery.status = (long) 200;
		resSuccessQuery.count = (long) 5;
		
		String expectedResponseJson = resSuccessQuery.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Authorization Success");
		} else {
			System.out.println("FAIL - Authorization Success");
		}
	}
		
	public static void drugNameInvalidTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {		
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","rhawkeyQUERY",RequestAction.QUERY.toString(),"asdfqwer");
		String requestJson = reqQuery.toQueryRequestString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_DRUG;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Drug Name Invalid");
		} else {
			System.out.println("FAIL - Drug Name Invalid");
		}	
	}
	
	public static void drugNameEmptyTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {		
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","rhawkeyQUERY",RequestAction.QUERY.toString(),"");
		String requestJson = reqQuery.toQueryRequestString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_DRUG;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Drug Name Invalid - Empty Drug Name");
		} else {
			System.out.println("FAIL - Drug Name Invalid - Empty Drug Name");
		}	
	}
	
	public static void drugNameValidTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","bobQUERYmike",RequestAction.QUERY.toString(),"Dimeah");
		String requestJson = reqQuery.toQueryRequestString();
		
		ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
		resSuccessQuery.status = (long) 200;
		resSuccessQuery.count = (long) 147;
		String expectedResponseJson = resSuccessQuery.toJSONString();
		
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Drug Name Valid");
		} else {
			System.out.println("FAIL - Drug Name Valid");
		}
	}
	
	public static void drugNameValidTestZero(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","bobQUERYmike",RequestAction.QUERY.toString(),"Valium");
		String requestJson = reqQuery.toQueryRequestString();
		
		ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
		resSuccessQuery.status = (long) 200;
		resSuccessQuery.count = (long) 0;
		String expectedResponseJson = resSuccessQuery.toJSONString();
		
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Drug Name Valid - Zero Stock");
		} else {
			System.out.println("FAIL - Drug Name Valid - Zero Stock");
		}
	}
	
	public static void addressUnknownEmptyStreetTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","","Halifax","Nova Scotia","Canada","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty Street Name");
		} else {
			System.out.println("FAIL - Address Unknown - Empty Street Name");
		}
	}
	
	public static void addressUnknownEmptyCityTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","14 Modoc Alley","","Nova Scotia","Canada","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty City");
		} else {
			System.out.println("FAIL - Address Unknown - Empty City");
		}
	}
	
	public static void addressUnknownEmptyProvinceTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","3817 Parker Drive","Halifax","","Canada","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty Province");
		} else {
			System.out.println("FAIL - Address Unknown - Empty Province");
		}
	}
	
	public static void addressUnknownEmptyCountryTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","2408 Arbor Court","Halifax","Nova Scotia","","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty Country");
		} else {
			System.out.println("FAIL - Address Unknown - Empty Country");
		}
	}
	
	public static void addressUnknownEmptyPostalCodeTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","3817 Parker Drive","Halifax","Nova Scotia","Canada","");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty Postal Code");
		} else {
			System.out.println("FAIL - Address Unknown - Empty Postal Code");
		}
	}
	
	public static void addressUnknownEmptyCustomerNameTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"","3817 Parker Drive","Halifax","Nova Scotia","Canada","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Empty Customer Name");
		} else {
			System.out.println("FAIL - Address Unknown - Empty Customer Name");
		}
	}
	
	public static void addressUnknownTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","hpamnaniSHIP",RequestAction.SHIP.toString(),"Rohypnol",(long) 35,"Harsh Pamnani","","Halifax","Nova Scotia","Canada","R1A 5E3");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.UNKNOWN_ADDRESS;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Unknown - Address Not Known as per Mock Data");
		} else {
			System.out.println("FAIL - Address Unknown - Address Not Known as per Mock Data");
		}
	}
	
	public static void addressKnownTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","SHIPbradley",RequestAction.SHIP.toString(),"Xanaxnol",(long) 225,"David Sean","2408 Arbor Court","Halifax","Nova Scotia","Canada","B3L 1K6");
		String requestJson = reqShip.toJSONString();

		ResponseSuccsessShip reqSuccessShip = new ResponseSuccsessShip();
		reqSuccessShip.status = (long) 200;
		reqSuccessShip.estimateddeliverydate = "22-05-2019";
		
		String expectedResponseJson = reqSuccessShip.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Address Known");
		} else {
			System.out.println("FAIL - Address Known");
		}
	}
	
	public static void stockInsufficientTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","patelSHIPnick",RequestAction.SHIP.toString(),"Rohypnol",(long) 600,"Lewis Thomas","14 Modoc Alley","Toronto","Ontario","Canada","Y8H 5C1");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.INSUFFICIENT_STOCK;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
	
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Stock Insufficient");
		} else {
			System.out.println("FAIL - Stock Insufficient");
		}
	}
	
	public static void stockInsufficientZeroTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","patelSHIPnick",RequestAction.SHIP.toString(),"Valium",(long) 10,"Lewis Thomas","14 Modoc Alley","Toronto","Ontario","Canada","Y8H 5C1");
		String requestJson = reqShip.toJSONString();
		
		String expectedResponseJson = DefaultFailureResponses.INSUFFICIENT_STOCK;
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
	
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Stock Insufficient - Zero Stock");
		} else {
			System.out.println("FAIL - Stock Insufficient - Zero Stock");
		}
	}
	
	public static void stockSufficientTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","TedSHIP",RequestAction.SHIP.toString(),"Alprazolam",(long) 75,"Jean Harris","3817 Parker Drive","Calgary","Alberta","Canada","K1S 6D3");
		String requestJson = reqShip.toJSONString();
						
		ResponseSuccsessShip reqSuccessShip = new ResponseSuccsessShip();
		reqSuccessShip.status = (long) 200;
		reqSuccessShip.estimateddeliverydate = "22-05-2019";
		
		String expectedResponseJson = reqSuccessShip.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Stock Sufficient");
		} else {
			System.out.println("FAIL - Stock Sufficient");
		}
	}	

	private static void stockInsufficientShipDrugTwiceTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","dominicSHIPjoe",RequestAction.SHIP.toString(),"Madroix",(long) 275,"David Sean","2408 Arbor Court","Halifax","Nova Scotia","Canada","B3L 1K6");
		String requestJson = reqShip.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		RequestShip reqShip2 = new RequestShip("ab88TRUEklasdf8u22j","dominicSHIPjoe",RequestAction.SHIP.toString(),"Madroix",(long) 150,"David Sean","2408 Arbor Court","Halifax","Nova Scotia","Canada","B3L 1K6");
		String requestJson2 = reqShip2.toJSONString();
		processedResponse = rp.processRequest(requestJson2, authFactroyMock, shipFactoryMock, drugFactoryMock);

		String expectedResponseJson = DefaultFailureResponses.INSUFFICIENT_STOCK;
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Stock Insufficient - Ship Drug twice to check drug count is maintained");
		} else {
			System.out.println("FAIL - Stock Insufficient - Ship Drug twice to check drug count is maintained");
		}
	}

	public static void querySuccessTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestQuery reqQuery = new RequestQuery("ab88TRUEklasdf8u22j","QUERYnoah",RequestAction.QUERY.toString(),"Ketamine");
		String requestJson = reqQuery.toQueryRequestString();
		
		ResponseSuccessQuery resSuccessQuery = new ResponseSuccessQuery();
		resSuccessQuery.status = (long) 200;
		resSuccessQuery.count = (long) 7;
		String expectedResponseJson = resSuccessQuery.toJSONString();
		
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Query Success");
		} else {
			System.out.println("FAIL - Query Success");
		}
	}
	
	public static void shipSuccessTest(RequestProcessor rp, IAuthentication authFactroyMock, IDatabase drugFactoryMock, IShipMate shipFactoryMock) {
		RequestShip reqShip = new RequestShip("ab88TRUEklasdf8u22j","dominicSHIPjoe",RequestAction.SHIP.toString(),"Dimeah",(long) 117,"David Sean","2408 Arbor Court","Halifax","Nova Scotia","Canada","B3L 1K6");
		String requestJson = reqShip.toJSONString();
						
		ResponseSuccsessShip reqSuccessShip = new ResponseSuccsessShip();
		reqSuccessShip.status = (long) 200;
		reqSuccessShip.estimateddeliverydate = "22-05-2019";
		
		String expectedResponseJson = reqSuccessShip.toJSONString();
		String processedResponse = rp.processRequest(requestJson, authFactroyMock, shipFactoryMock, drugFactoryMock);
		
		if(processedResponse.equals(expectedResponseJson)) {
			System.out.println("PASS - Ship Success");
		} else {
			System.out.println("FAIL - Ship Success");
		}
	}
	
	/**
	 * This method will run numerous unit tests to verify the functionality of the Pharmacy API.
	 */
	static public void runUnitTests() {
		// Test Setup - Initializing all the mock objects		
		IAuthentication authFactroyMock = new AuthenticationMock();
		IDatabase drugFactoryMock = new DatabaseMock();
		IShipMate shipFactoryMock = new ShipMateMock();
		RequestProcessor rp = new RequestProcessor();

		// Test cases for Authentication
		authenticateFailureTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);	
		authenticateFailureTestEmptyAPIKey(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		authenticateSuccessTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		
		// Test cases for Authorization
		authorizationFailureTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		authorizationFailureTestEmptyUsername(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		authorizationSuccessTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);		
		
		// Test cases for Drug Name Validity
		drugNameInvalidTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		drugNameEmptyTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		drugNameValidTestZero(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		drugNameValidTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		
		// Test cases for Address Known
		addressUnknownEmptyStreetTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);	
		addressUnknownEmptyCityTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressUnknownEmptyProvinceTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressUnknownEmptyCountryTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressUnknownEmptyPostalCodeTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressUnknownEmptyCustomerNameTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressUnknownTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		addressKnownTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		
		// Test cases for Stock Checking
		stockInsufficientTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		stockInsufficientZeroTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		stockInsufficientShipDrugTwiceTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		stockSufficientTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		
		// Test cases for Successful QUERY and SHIP requests.
		querySuccessTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);
		shipSuccessTest(rp, authFactroyMock, drugFactoryMock, shipFactoryMock);		
	}
}
