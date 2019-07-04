// This is the State pattern
public interface IState
{
	// This is our state's "Handle()" equivalent, we simply ask the state
	// whether the game is over according to the current state we are in.
	public boolean IsGameOver();
}