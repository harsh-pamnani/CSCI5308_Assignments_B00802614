
import java.util.ArrayList;

// Implementation of the Abstract Factory pattern's interface
public class AsteroidGameFactory implements IAsteroidGameFactory
{	
	@Override
	public BoardComponent MakeShield(Square square, int shieldX, int shieldY)
	{
		return new Shield(square, shieldX, shieldY);
	}
	
	@Override
	public BoardComponent MakeSquare()
	{
		return new Square();
	}

	@Override
	public BoardComponent MakeBuilding()
	{
		Building building = new Building();
		return building;
	}

	@Override
	public Asteroid MakeAsteroid(int height)
	{
		Asteroid asteroid = new Asteroid(height);
		return asteroid;
	}
	
	@Override
	public ArrayList<ArrayList<BoardComponent>> MakeBoard(int height, int width)
	{
		ArrayList<ArrayList<BoardComponent>> board = new ArrayList<>();
		for (int i = 0; i < height; i++)
		{
			// Make an array for each row.
			ArrayList<BoardComponent> row = new ArrayList<>();
			// Add squares equal to the width to the row.
			for (int j = 0; j < width; j++)
			{
				BoardComponent square = MakeSquare();
				ISubjectAsteroidImpact subjectAsteroidImpact = GameBoard.Instance().getSubjectAsteroidImpact();
				subjectAsteroidImpact.attach(square);
				row.add(square);
			}
			board.add(row);
		}
		return board;
	}
	
	@Override
	public Command MakeCommand(String commandLine)
	{
		String commandToMake;
		String[] args = null;
		String commandLineArgs = "";
		if (commandLine.contains(" "))
		{
			commandToMake = commandLine.split(" ")[0];
			commandLineArgs = commandLine.substring(commandToMake.length() + 1);
			args = commandLineArgs.split(" ");
		}
		else
		{
			commandToMake = commandLine;
		}
		switch (commandToMake.toUpperCase())
		{
			case "INIT":
			{
				return new InitializeBoardCommand(GameBoard.Instance(), args);
			}
			case "SPAWN_ASTEROID":
			{
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnAsteroidCommand(square, args);
			}
			case "TICK":
			{
				return new TickCommand(GameBoard.Instance(), args);
			}
			case "START_GAME":
			{
				return new StartGameCommand(GameBoard.Instance(), args);
			}
			case "SPAWN_BUILDING":
			{
				// Implemented a command to spawn a building.  It is similar
				// to SPAWN_ASTEROID above.  The command is incrementing the building count!
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnBuildingCommand(square, args);
			}
			case "SPAWN_SHIELD":
			{
				// Implemented a command that uses the Decorator pattern to decorate
				// a Square object with a shield.  The shield will have health,
				// like a building, hard coded to 2 health in the decorator object.
				// While the shield is alive buildings in the square do not take damage from
				// asteroid impacts.  When the shield health hits 0 it is destroyed and
				// removed from decorating the Square.
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnShieldCommand(square, args);
			}
		}
		return null;
	}
}
