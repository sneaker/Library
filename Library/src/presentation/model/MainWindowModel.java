package presentation.model;

import java.util.Observable;

import presentation.view.TabsPanel;
import domain.Book;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	// TODO: evtl Konstanten hierher verschieben wegen Aufwärtszugriff?
	private int activeTab = TabsPanel.SEARCH;
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
