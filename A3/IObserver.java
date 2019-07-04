
public interface IObserver {
	
	// This is the method called by the asteroid impact subject when the subject is notified
	public void update(BoardComponent squareAttacked);
}
