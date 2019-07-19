
public abstract class MathOperation
{
	private int leftOperand;
	private int rightOperand;
	
	public MathOperation(int leftOperand, int rightOperand)
	{
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	public int getLeftOperand() {
		return leftOperand;
	}
	
	public int getRightOperand() {
		return rightOperand;
	}
	
	public abstract int getResult();
}