
public class SalaryWorker implements IWorker {
	private float salary;
	private int hoursPerWeek;

	public SalaryWorker() {
		salary = 50000.0f;
		hoursPerWeek = 40;
	}

	@Override
	public float calculatePay(int hours) {
		float hourlyRate = salary / (52 * hoursPerWeek);
		return hourlyRate * hours;
	}
}