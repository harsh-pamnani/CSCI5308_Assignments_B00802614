
public class Mexico implements ICountry {
	private String name = "Mexico";
	
	public String getName() {
		return name;
	}
	
	public String getAgriculture() {
		return "$50000000 MXN";
	}

	private String getTourism() {
		return "$100000 MXN";
	}
	
	@Override
	public void printGDPReport() {
		System.out.println("- " + getName() + ":\n");
		
		System.out.println("   - Agriculture: " + getAgriculture());
		
		System.out.println("   - Tourism: " + getTourism());
	}
}