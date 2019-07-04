
public class SpawnShieldCommand extends Command {

	public SpawnShieldCommand(Object receiver, String[] args)
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		// The receiver for the SpawnShieldCommand is the Square to shield.
		Square square = (Square) receiver;
		
		// The args for SpawnShieldCommand are the X,Y coordinate for the square
		int shieldX = Integer.parseInt(args[0]);
		int shieldY = Integer.parseInt(args[1]);
		IAsteroidGameFactory factory = GameBoard.Instance().GetFactory();
		System.out.println("Spawning shield at ("	+ shieldX + "," + shieldY + ")");
		square.parent = factory.MakeShield(square, shieldX, shieldY);;
	}
}
