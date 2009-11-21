package presentation.model;

import java.util.Observable;

import domain.Message;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	private static final String PROGRAM_NAME = " - BücherBox";
	private String title = "Recherche";
	private Message activeMessage;

	public MainWindowModel(ModelController controller) {
		title = "Recherche";
	}

	public String getTitle() {
		return title + PROGRAM_NAME;
	}

	public void setTitle(String title) {
		this.title = title;
		fireDataChanged();
	}

	private void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public Message getActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(Message msg) {
		this.activeMessage = msg;
		fireDataChanged();
	}

	public void hideGlassPane() {
		this.activeMessage = null;
		fireDataChanged();
	}
}
