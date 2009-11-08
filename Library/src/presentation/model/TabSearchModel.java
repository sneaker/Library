package presentation.model;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 * Tracks the content of the search mask and updates the its view when needed.
 */
public class TabSearchModel extends Observable {

	private static final String DEFAULT_SEARCH_STRING = "Benutzernamen, Buchtitel oder Autor eingeben";
	private String searchFieldText = DEFAULT_SEARCH_STRING;
	private ModelController controller;
	private boolean hasFocus;

	public TabSearchModel(ModelController controller) {
		this.controller = controller;
	}

	/**
	 * Sets the text to be displayed on the search field to the default
	 * "please-type-here" value.
	 */
	public void resetSearchText() {
		controller.resultlist_model.resetSearch();
		searchFieldText = DEFAULT_SEARCH_STRING;
		setChanged();
		notifyObservers(searchFieldText);
	}

	/**
	 * Returns the text which should be displayd on the search field.
	 * 
	 * @return current text for the display
	 */
	public String getSearchText() {
		return searchFieldText;
	}

	public void setSearchText(String newText, boolean shouldUpdate) {
		searchFieldText = newText;
		if (shouldUpdate) {
			setChanged();
			notifyObservers(searchFieldText);
		}
	}

	public boolean isDefaultSearchText() {
		return DEFAULT_SEARCH_STRING.equals(searchFieldText);
	}

	public Color getSearchFieldColor() {
		if (isDefaultSearchText())
			return Color.GRAY;
		return Color.BLACK;
	}

	public void setRequestFocus() {
		hasFocus = true;
		setChanged();
		notifyObservers();
	}

	public void fowardDeleteEvent(String text) {
		controller.resultlist_model.delchar(text);
	}

	public void forwardKeyEvent(KeyEvent e) {
		controller.resultlist_model.addchar(e.getKeyChar());
	}

	public String getStatus() {
		return "Benutzernamen, Buchtitel oder Autor suchen";
	}

	public boolean hasFocus() {
		return hasFocus;
	}
	
	public void resetFocus() {
		hasFocus = false;
	}

	public void showavailableBooks() {
		controller.resultlist_model.showavailableBooks();
	}

	public void showDefektBooks() {
		controller.resultlist_model.showDefektBook();
	}
	
	public void showLentBooks() {
		controller.resultlist_model.showLentBooks();
	}

	public void showUser() {
		controller.resultlist_model.showUser();
	}
}
