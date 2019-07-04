// In this state the game is over when the board doesn't have any buildings left alive!
public class GameState implements IState
{
	@Override
	public boolean IsGameOver()
	{
		return (GameBoard.Instance().GetBuildingCount() == 0);
	}	
}