package presentation.model;

import java.util.Observable;

import javax.swing.JPanel;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	private static final String PROGRAM_NAME = " - BücherBox";
	private String title = "Recherche";
	private JPanel activeMessage;

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

	public JPanel getActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(JPanel activeMessage) {
		this.activeMessage = activeMessage;
		fireDataChanged();
	}

	public void hideGlassPane() {
		this.activeMessage = null;
		fireDataChanged();
	}
}
