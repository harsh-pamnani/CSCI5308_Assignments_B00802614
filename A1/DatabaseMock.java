import java.util.HashMap;
import java.util.Map;

public class DatabaseMock implements IDatabase {
	
	private static Map<String, Integer> drugs;
	
	public DatabaseMock() {
		drugs = new HashMap<String, Integer>();
		addNewDrugs();
	}
	
	private void addNewDrugs() {
		drugs.put("Triazolam", 85);
		drugs.put("Clonazepam", 50);
		drugs.put("Rohypnol", 125);
		drugs.put("Valium", 0);
		drugs.put("Librium", 5);
		drugs.put("Xanaxnol", 900);
		drugs.put("Alprazolam", 200);
		drugs.put("Dimeah", 147);
		drugs.put("Ketamine", 7);
		drugs.put("Madroix", 350);
		drugs.put("", 15);
	}
	
	/*
		Given a drug name from an incoming JSON request to the API, returns:
	  		TRUE - The drug is valid drug
	  		FALSE - The drug is not valid drug
	 */
	@Override
	public boolean checkDrugExists(String drugName) {
		if(drugs.containsKey(drugName)) {
			return true;
		}
		return false;
	}

	/*
		Given a drug name from an incoming JSON request to the API, returns:
			Returns the stock count of the drug in the inventory.
	 */
	@Override
	public int getDrugCount(String drugName) {
		if(checkDrugExists(drugName)) {
			int drugCount = drugs.get(drugName);
			return drugCount;
		}
		return 0;
	}

	/*
 		Given a drug name from an incoming JSON request to the API, returns:
 			TRUE - The drug quantity is successfully claimed
 			FALSE - The drug quantity is not claimed successfully
	 */
	@Override
	public boolean claimDrug(String drugName, int quantity) {
		int drugCount = getDrugCount(drugName);
		
		if(drugCount != 0 && quantity <= drugCount) {
			drugs.put(drugName, drugCount - quantity);
			return true;
		}
		
		return false;
	}
}