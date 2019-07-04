public class SpawnAsteroidCommand extends Command
{
	public SpawnAsteroidCommand(Object receiver, String[] args)
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		// The receiver for the SpawnAsteroidCommand is the Square or Shield
		// where we have to spawn the asteroid.
		BoardComponent boardComponent = (BoardComponent) receiver;
		
		// The args for SpawnAsteroidCommand are the X,Y coordinate for the asteroid
		// used by the factory, and the height of the asteroid.
		int height = Integer.parseInt(args[2]);
		IAsteroidGameFactory factory = GameBoard.Instance().GetFactory();
		System.out.println("Spawning asteroid at (" + args[0] + "," + args[1] + ") with height = " + height);
		boardComponent.Add(factory.MakeAsteroid(height));
	}
}