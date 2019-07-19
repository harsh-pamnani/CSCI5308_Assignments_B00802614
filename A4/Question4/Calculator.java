public class Calculator
{
	public static int divide(int left, int right)
	{
		MathOperation op = new Division(left, right);
		return op.getResult();
	}

	public static int multiply(int left, int right)
	{
		MathOperation op = new Multiplication(left, right);
		return op.getResult();
	}

	public static int add(int left, int right)
	{
		MathOperation op = new Addition(left, right);
		return op.getResult();
	}

	public static int subtract(int left, int right)
	{
		MathOperation op = new Subtraction(left, right);
		return op.getResult();
	}
}