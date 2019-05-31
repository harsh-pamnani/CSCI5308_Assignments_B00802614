public interface IDatabase
{
	/*
		Given a drug name from an incoming JSON request to the API, returns:
			TRUE - The drug is valid drug
			FALSE - The drug is not valid drug
	 */
	public boolean checkDrugExists(String drugName);
	
	/*
 		Given a drug name from an incoming JSON request to the API, returns:
 			Returns the stock count of the drug in the inventory.
	 */
	public int getDrugCount(String drugName);
	
	/*
	 	Given a drug name from an incoming JSON request to the API, returns:
	 		TRUE - The drug quantity is successfully claimed
	 		FALSE - The drug quantity is not claimed successfully
	 */
	public boolean claimDrug(String drugName, int quantity);
}