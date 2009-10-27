package presentation.view;

import java.util.Observable;

import domain.Book;

public class TabBookPanelModel extends Observable{

	private Book activeBook;

	public void setActiveBook(Book activeBook) {
		this.activeBook = activeBook;
		setChanged();
		notifyObservers();
	}

	public Book getActiveBook() {
		return activeBook;
	}

}
