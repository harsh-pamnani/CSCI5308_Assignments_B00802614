
public class Canada implements ICountry {
	private String name = "Canada";
	
	public String getName() {
		return name;
	}
	
	public String getAgriculture() {
		return "$50000000 CAD";
	}

	private String getManufacturing() {
		return "$100000 CAD";
	}
	
	@Override
	public void printGDPReport() {
		System.out.println("- " + getName() + ":\n");
		
		System.out.println("   - Agriculture: " + getAgriculture());
		
		System.out.println("   - Manufacturing: " + getManufacturing());
	}
}