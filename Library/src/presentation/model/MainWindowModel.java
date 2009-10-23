package presentation.model;

import java.util.Observable;

import domain.Book;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	public final int SEARCH_TAB = 0;
	public final int BOOK_TAB = 1;
	public final int USER_TAB = 2;
	private int activeTab = SEARCH_TAB;
	private Book activeBook;

	public Book getActiveBook() {
		return activeBook;
	}

	public void setActiveBook(Book book) {
		this.activeBook = book;
		setChanged();
		notifyObservers();
	}

	public void setActiveTab(int newTabIndex) {
		this.activeTab = newTabIndex;
		setChanged();
		notifyObservers();
	}

	public int getActiveTabIndex() {
		return activeTab;
	}

	// Statusbar-Text
	// aktive Ansicht / Tab / Modus
	// aktiver User

}
