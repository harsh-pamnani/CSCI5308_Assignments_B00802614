// In this state the game is never over.
public class SetupState implements IState
{
	@Override
	public boolean IsGameOver()
	{
		return false;
	}	
}