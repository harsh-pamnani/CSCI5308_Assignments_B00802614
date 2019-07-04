public class InitializeBoardCommand extends Command
{
	public InitializeBoardCommand(Object receiver, String args[])
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		// The receiver of InitializeBoardCommand is the GameBoard.
		GameBoard board = (GameBoard)receiver;
		// args[0] is the width of the board, args[1] is the height
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		board.SetBoard(board.GetFactory().MakeBoard(height, width));
	}
}