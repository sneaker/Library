package presentation.model;

import java.util.Observable;

public class LibraryTabbedPaneModel extends Observable {

	private int activeTab = 0;
	private ModelController controller;
	public String[][] tabInformation = {
			{ "Recherche", "search.png",
					"Suchen nach Benutzern oder Büchern" },
			{ "Buch", "book64x64.png", "Details eines Buches anzeigen" },
			{ "Benutzer", "user64x64.png",
					"Personalien und Ausleihen eines Benutzers anzeigen" } };
	private final static int SEARCH_TAB = 0;
	private final static int BOOK_TAB = 1;
	private final static int USER_TAB = 2;

	public LibraryTabbedPaneModel(ModelController controller) {
		this.controller = controller;
	}

	public int getActiveTab() {
		return activeTab;
	}

	private void setActiveTab(int newIndex) {
		this.activeTab = newIndex;
		controller.main_model.setTitle(getActiveTabTitle());
		controller.status_model.setStatus(getActiveTabStatus());
		setChanged();
		notifyObservers(newIndex);
	}

	public String getActiveTabTitle() {
		if (getActiveTab() > tabInformation.length)
			return "";
		return tabInformation[getActiveTab()][0];
	}

	private String getActiveTabStatus() {
		if (getActiveTab() == SEARCH_TAB) {
			return controller.searchtab_model.getStatus();
		}
		if (getActiveTab() == BOOK_TAB) {
			return controller.booktab_model.getStatus();
		}
		if (getActiveTab() == USER_TAB) {
			return controller.usertab_model.getStatus();
		}
		return "Bereit.";
	}

	public String[][] getTabInformation() {
		return tabInformation;
	}

	public void setSearchTabActive() {
		setActiveTab(SEARCH_TAB);
	}

	public void setBookTabActive() {
		setActiveTab(BOOK_TAB);
	}

	public void setUserTabActive() {
		setActiveTab(USER_TAB);
	}

	public boolean isUserTabActive() {
		return getActiveTab() == USER_TAB;
	}

}
