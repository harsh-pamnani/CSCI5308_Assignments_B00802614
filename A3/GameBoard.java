import java.io.IOException;
import java.util.*;
import java.nio.file.*;

// This is the Singleton that implements the game, it glues all the patterns together.
public class GameBoard
{
	// The ONE instance of GameBoard.
	private static GameBoard instance = null;
	
	private  ArrayList<ArrayList<BoardComponent>> board;
	private final IAsteroidGameFactory factory;
	private int buildingCount;
	private IState gameState;
	
	private ISubjectAsteroidImpact subjectAsteroidImpact;
	
	// The one way to get the Singleton
	public static GameBoard Instance()
	{
		if (null == instance)
		{
			instance = new GameBoard();
		}
		return instance;
	}
	
	// Notice how this is private, this enforces the use of the singleton through
	// the Instance() accessor.  You can't instantiate this class outside of this
	// class.  Imagine if you could!  You'd have multiple game boards, multiple
	// game states, ugh what a mess.
	private GameBoard()
	{
		board = null;
		factory = new AsteroidGameFactory();
		buildingCount = 0;
		gameState = new SetupState();
		subjectAsteroidImpact = new SubjectAsteroidImpact();
	}
	
	public ArrayList<ArrayList<BoardComponent>> GetBoard()
	{
		return board;
	}
	
	public void SetBoard(ArrayList<ArrayList<BoardComponent>> board)
	{
		this.board = board;
	}
	
	public IAsteroidGameFactory GetFactory()
	{
		return factory;
	}
	
	// This processses the input command file, using the factory to make
	// commands, and then executes the commands the factory makes.
	// Notice how this code has NO IDEA what command is created, or what the
	// command evend does.  We can add any commands we want and this code will
	// just keep working.  This is the INVOKER portion of the Command UML for
	// this program.
	public void ProcessCommands(String commandFileName)
	{
		try
		{
			List<String> commandLines = Files.readAllLines(Paths.get(commandFileName));
			Iterator<String> iter = commandLines.iterator();
			while (iter.hasNext())
			{
				String commandLine = iter.next();
				Command command = factory.MakeCommand(commandLine);
				if (command != null)
				{
					command.Execute();
				}
				// After every command check whether the game is over!
				if (gameState.IsGameOver())
				{
					// Game over, stop processing commands even if there are some left
					System.out.println("GAME OVER!!");
					break;
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("Failed to parse command file: " + e.getMessage());
		}
	}
	
	// This tells all the components in the board composite to perform
	// their Operation()
	public void DoTick()
	{
		for (int i = 0; i < board.size(); i++)
		{
			ArrayList<BoardComponent> row = board.get(i);
			for (int j = 0; j < row.size(); j++)
			{
				BoardComponent square = row.get(j);
				square.Operation();
			}
		}
	}
	
	// Increment the building count.
	public void IncrementBuildingCount()
	{
		buildingCount += 1;
	}
	
	// Decrement the building count.
	public void DecrementBuildingCount()
	{
		buildingCount -= 1;
	}
	
	public int GetBuildingCount()
	{
		return buildingCount;
	}
	
	// Start the game.
	public void StartGame()
	{
		gameState = new GameState();
	}
	
	// Return the subject for asteroid impact
	public ISubjectAsteroidImpact getSubjectAsteroidImpact() {	
		return subjectAsteroidImpact;
	}
}