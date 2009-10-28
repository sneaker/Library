package presentation.view;

import java.util.Observable;

import domain.Book;

public class LibTabPaneModel extends Observable {

	private int activeTab = 0;
	private Book activeBook;

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int newIndex) {
		this.activeTab = newIndex;
		setChanged();
		notifyObservers();
	}

	public void setActiveBook(Book newBook) {
		this.activeBook = newBook;
		setChanged();
		notifyObservers(newBook);
	}

	public Book getActiveBook() {
		return activeBook;
	}
}
