
public class Multiplication extends MathOperation {

	public Multiplication(int leftOperand, int rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public int getResult() {
		return getLeftOperand() * getRightOperand();
	}
}
