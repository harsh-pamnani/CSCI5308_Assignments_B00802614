
public class Subtraction extends MathOperation {

	public Subtraction(int leftOperand, int rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public int getResult() {
		return getLeftOperand() - getRightOperand();
	}
}
