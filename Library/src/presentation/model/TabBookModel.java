package presentation.model;

import java.util.Observable;

import domain.Book;

public class TabBookModel extends Observable {

	private Book activeBook;

	public TabBookModel(ModelController controller) {
	}

	public void setActiveBook(Book activeBook) {
		this.activeBook = activeBook;
		setChanged();
		notifyObservers();
	}

	public Book getActiveBook() {
		return activeBook;
	}

	public String getStatus() {
		if (getActiveBook() == null)
			return "Buchdetails (kein Buch ausgewählt)";
		return "Anzeigen der Buchdetails von \""
				+ getActiveBook().getTitle().getName() + "\"";
	}

}
