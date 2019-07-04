import java.util.ArrayList;

// Square is a composite, making up the Composite pattern (contains components)
public class Square extends BoardComponent 
{
	private final ArrayList<BoardComponent> children;

	public Square() 
	{
		super();
		children = new ArrayList<BoardComponent>();
	}

	@Override
	public void Operation() 
	{
		ArrayList<BoardComponent> childrenCopy = new ArrayList<BoardComponent>(children);
		for (int i = 0; i < childrenCopy.size(); i++) {
			BoardComponent child = childrenCopy.get(i);
			child.Operation();
		}
	}

	@Override
	public void Add(BoardComponent child) 
	{
		// I am now this child's parent.
		children.add(child);
		child.SetParent(this);
	}

	@Override
	public void Remove(BoardComponent child) 
	{
		children.remove(child);
	}

	@Override
	public void update(BoardComponent squareAttacked) 
	{
		if(this == squareAttacked) {
			ArrayList<BoardComponent> squareChildrenCopy = new ArrayList<BoardComponent>(this.children);
			for(BoardComponent child: squareChildrenCopy) {
				child.update(squareAttacked);
			}
		}
	}
}