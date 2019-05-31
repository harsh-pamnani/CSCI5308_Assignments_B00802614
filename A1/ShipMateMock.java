import java.util.ArrayList;
import java.util.List;

public class ShipMateMock implements IShipMate {

	private List<Address> addresses;
	
	public ShipMateMock() {
		addresses = new ArrayList<Address>();
		addAddresses();
	}
	
	private void addAddresses() {
		Address a1 = new Address();
		a1.customer = "David Sean";
		a1.street = "2408 Arbor Court";
		a1.city = "Halifax";
		a1.province = "Nova Scotia";
		a1.country = "Canada";
		a1.postalCode = "B3L 1K6";
		
		Address a2 = new Address();
		a2.customer = "Lewis Thomas";
		a2.street = "14 Modoc Alley";
		a2.city = "Toronto";
		a2.province = "Ontario";
		a2.country = "Canada";
		a2.postalCode = "Y8H 5C1";
		
		Address a3 = new Address();
		a3.customer = "Jean Harris";
		a3.street = "3817 Parker Drive";
		a3.city = "Calgary";
		a3.province = "Alberta";
		a3.country = "Canada";
		a3.postalCode = "K1S 6D3";
		
		addresses.add(a1);
		addresses.add(a2);
		addresses.add(a3);
	}

	/*
		Given an address, returns:
			TRUE - The address is a known address we are allowed to ship to
			FALSE - The address is unknown
	*/
	@Override
	public boolean isKnownAddress(Address address) {
		
		// Check if any of the fields of address is null.
		if(address.city != null && address.country != null 
				&& address.customer != null && address.postalCode != null 
				&& address.province != null && address.street != null) {
			
			// Check if any of the fields of address is empty string.
			if(! (address.city.equals("") || address.country.equals("") 
					|| address.customer.equals("") || address.postalCode.equals("") 
					|| address.province.equals("") || address.street.equals("")) ) {	
			
				// Check given address is one of know the address from list of known addresses.
				for(Address testAddress : addresses) {
					if(testAddress.city.equals(address.city) &&  testAddress.country.equals(address.country) &&
							testAddress.customer.equals(address.customer) && testAddress.postalCode.equals(address.postalCode) &&
							testAddress.province.equals(address.province) && testAddress.street.equals(address.street)) {
						// If all of the above validations passes, return true
						return true;
					}
				}
			}
		}
		
		// If any of the above validations fails, return false
		return false;
	}

	/*
		Given an address, ships count of drug name to that address.
		Will throw an exception if the address is NOT a known address.
		Returns:
			String - The estimated delivery date, in string format DD-MM-YYYY
	 */
	@Override
	public String shipToAddress(Address address, int count, String drugName) throws Exception {
		if(isKnownAddress(address)) {
			return "22-05-2019";
		} else {
			throw new Exception();
		}
	}
}