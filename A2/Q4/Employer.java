import java.util.ArrayList;

public class Employer {

	ArrayList<IWorker> workers; 
	
	public Employer(ArrayList<IWorker> workers) {
		this.workers = workers;
	}

	public void outputWageCostsForAllStaff(int hours) {
		float cost = 0.0f;

		for (int i = 0; i < workers.size(); i++) {
			IWorker worker = workers.get(i);
			cost += worker.calculatePay(hours);
		}

		System.out.println("Total wage cost for all staff = $" + cost);
	}
}