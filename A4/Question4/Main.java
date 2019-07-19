public class Main
{
	public static void main(String[] args)
	{
		boolean pass = (Calculator.divide(10, 2) == 5) &&
					   (Calculator.multiply(5, 2) == 10) &&
					   (Calculator.add(2, 2) == 4) &&
					   (Calculator.subtract(10, 2) == 8);
		if (pass)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
	}
}