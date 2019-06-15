
public class Account extends AccountOperations {
	
	@Override
	public void credit(float amount) {
		balance += amount;
	}

	@Override
	public void debit(float amount) {
		balance -= amount;
	}
}
