// DO NOT MODIFY THIS FILE IN ANY WAY
public interface IShipMate
{
	/*
		Given an address, returns:
			TRUE - The address is a known address we are allowed to ship to
			FALSE - The address is unknown
	*/
	public boolean isKnownAddress(Address address);

	/*
		Given an address, ships count of drug name to that address.
		Will throw an exception if the address is NOT a known address.
		Returns:
			String - The estimated delivery date, in string format DD-MM-YYYY
	*/
	public String shipToAddress(Address address, int count, String drugName) throws Exception;
}