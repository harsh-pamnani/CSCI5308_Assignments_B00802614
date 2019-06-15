import java.util.ArrayList;

public class CountryGDPReport {
	
	ArrayList<ICountry> countries;

	public CountryGDPReport(ArrayList<ICountry> countries) {
		this.countries = countries;
	}

	public void printCountryGDPReport() {
		System.out.println("GDP By Country:\n");
		
		for (int i = 0; i < countries.size(); i++) {
			countries.get(i).printGDPReport();
		}
	}
}