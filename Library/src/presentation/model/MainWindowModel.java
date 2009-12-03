package presentation.model;

import java.util.ArrayList;
import java.util.Observable;

import domain.Message;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	private static final String PROGRAM_NAME = " - BücherBox";
	private String title = "Recherche";
	private ArrayList<Message> activeMessages;

	public MainWindowModel(ControllerFacade controller) {
		title = "Recherche";
		activeMessages = new ArrayList<Message>();
	}

	public String getTitle() {
		return title + PROGRAM_NAME;
	}

	protected void setTitle(String title) {
		this.title = title;
		fireDataChanged();
	}

	private void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public Message getActiveMessage() {
		if (!activeMessages.isEmpty()) {
			return activeMessages.get(activeMessages.size()-1);
		}
		else
			return null;
	}

	public void addActiveMessage(Message msg) {
		activeMessages.add(msg);
		fireDataChanged();
	}

	public void hideGlassPane() {
		if (!activeMessages.isEmpty())
			activeMessages.remove(activeMessages.size()-1);
		fireDataChanged();
	}
}
