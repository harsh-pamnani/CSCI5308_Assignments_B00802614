/*
	A "Tick" is a time concept, think of the second hand on a clock, each time a
	second elapses the hand "ticks".  In video games ticks are like turns in the
	game, a chance for all objects to update themselves.  Tick in this program
	will tell all of the components in the Composite hierarchy on the GameBoard()
	to perform their Operation(), it doesn't know what those Operation()'s do.
*/
public class TickCommand extends Command
{
	public TickCommand(Object receiver, String[] args)
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		// The receiver of the Tick command is the GameBoard singleton.
		GameBoard board = (GameBoard)receiver;
		
		// The args for the Tick command are the number of times the command should tick
		// meaning we could trigger more than one tick at once.
		int numTicks = Integer.parseInt(args[0]);
		System.out.println("Ticking " + numTicks + " times.");
		for (int i = 0; i < numTicks; i++)
		{
			board.DoTick();
		}
	}	
}