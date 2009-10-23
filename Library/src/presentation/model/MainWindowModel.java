package presentation.model;

import java.util.Observable;

import presentation.view.MainWindow;
import presentation.view.TabsPanel;
import domain.Book;

public class MainWindowModel extends Observable {
	public enum MenuTab {
		RECHERCHE, BUCHINFO, BENUTZERINFO
	}

	private int activeTab = TabsPanel.SEARCH; // TODO: evtl daten hierher verschieben wegen aufwärtszugriff?
	private Book activeBook;

	public MainWindowModel() {
	}

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
