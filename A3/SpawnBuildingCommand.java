
public class SpawnBuildingCommand extends Command {

	public SpawnBuildingCommand(Object receiver, String[] args)
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		// The receiver for the SpawnBuildingCommand is Square or Shield, 
		// where we have to spawn the building.
		BoardComponent boardComponent = (BoardComponent) receiver;
		
		// The args for SpawnBuildingCommand are the X,Y coordinate for the building
		// used by the factory
		IAsteroidGameFactory factory = GameBoard.Instance().GetFactory();
		System.out.println("Spawning building at (" + args[0] + "," + args[1] + ")");
		boardComponent.Add(factory.MakeBuilding());
		GameBoard.Instance().IncrementBuildingCount();
	}
}
