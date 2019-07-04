
public abstract class SquareDecoratorAbstract extends BoardComponent {

	protected Square squareToDecorate;
	
	public SquareDecoratorAbstract(Square squareToDecorate) {
		this.squareToDecorate = squareToDecorate;
	}
	
	@Override
	public abstract void Operation();
	
	@Override
	public abstract void Add(BoardComponent child);
	
	@Override
	public abstract void Remove(BoardComponent child);
}
