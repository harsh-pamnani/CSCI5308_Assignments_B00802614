
public class EmailSpecialOffer {
	public static final String MESSAGE = "Congratulations! Your purchase history has earned you a 10% discount on your next purchase!";
	public static final String SUBJECT = "10% off your next order!";
	
	public void emailCustomerSpecialOffer(EmailSender emailSender, Customer customer)
	{
		emailSender.sendEmail(customer.getEmail(), SUBJECT, MESSAGE);
	} 
}
