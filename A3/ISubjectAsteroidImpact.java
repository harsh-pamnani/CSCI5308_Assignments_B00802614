
public interface ISubjectAsteroidImpact {

	public void attach(IObserver observer);

	public void detach(IObserver observer);

	public void notifyObservers(BoardComponent square);
}
