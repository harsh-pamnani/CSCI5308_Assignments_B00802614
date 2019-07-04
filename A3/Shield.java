
public class Shield extends SquareDecoratorAbstract {

	private int shieldHealth;
	private int shieldX, shieldY;

	public Shield(Square square, int shieldX, int shieldY) {
		super(square);
		shieldHealth = 2;
		this.shieldX = shieldX;
		this.shieldY = shieldY;
		
		GameBoard.Instance().getSubjectAsteroidImpact().detach(this.squareToDecorate);
		GameBoard.Instance().getSubjectAsteroidImpact().attach(this);
		
		GameBoard.Instance().GetBoard().get(shieldY).set(shieldX, this);
	}

	@Override
	public void Operation() {
		squareToDecorate.Operation();
	}

	@Override
	public void Add(BoardComponent child) {
		squareToDecorate.Add(child);
	}

	@Override
	public void Remove(BoardComponent child) {
		squareToDecorate.Remove(child);
	}

	@Override
	public void update(BoardComponent squareAttacked) {
		if(this.squareToDecorate == squareAttacked) {
			shieldHealth--;

			if (0 == shieldHealth) {
				GameBoard.Instance().getSubjectAsteroidImpact().detach(this);
				GameBoard.Instance().getSubjectAsteroidImpact().attach(this.squareToDecorate);
				
				GameBoard.Instance().GetBoard().get(shieldY).set(shieldX, this.squareToDecorate);
				this.squareToDecorate.parent = null;
			}
		}
	}
}
