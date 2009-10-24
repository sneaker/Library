package presentation.view;

import java.awt.Color;
import java.util.Observable;

/**
 * Tracks the content of the search mask and updates the its view when needed.
 */
public class SearchTabPanelModel extends Observable {

	private static final String DEFAULT_SEARCH_STRING = "Benutzernamen, Buchtitel oder Autor eingeben";
	private String searchFieldText = DEFAULT_SEARCH_STRING;

	/**
	 * Sets the text to be displayed on the search field to the default
	 * "please-type-here" value.
	 */
	public void resetSearchText() {
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
}
