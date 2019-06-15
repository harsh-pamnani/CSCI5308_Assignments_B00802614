
public class Book implements IBook, ILibraryItem {

	@Override
	public String getAuthor() {
		return "Hemingway";
	}

	@Override
	public String getTitle() {
		return "For Whom The Bell Tolls";
	}

	@Override
	public boolean isDigitalOnly() {
		// Some logic for determining whether a Book is digital or not will go here.
		return false;
	}
}