
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SubjectAsteroidImpact implements ISubjectAsteroidImpact {
	private final List<IObserver> observers;

	public SubjectAsteroidImpact() {
		observers = new ArrayList<IObserver>();
	}

	// Attach an observer
	@Override
	public void attach(IObserver observer) {
		observers.add(observer);
	}

	// Detach an observer
	@Override
	public void detach(IObserver observer) {
		observers.remove(observer);
	}

	// Notify all observers that event has happened
	@Override
	public void notifyObservers(BoardComponent squareAttacked) {
		List<IObserver> observersCopy = new ArrayList<IObserver>(observers);
		
		ListIterator<IObserver> iter = observersCopy.listIterator();
		while (iter.hasNext()) {
			IObserver square = iter.next();
			square.update(squareAttacked);	
		}
	}
}