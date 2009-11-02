package presentation.model;

import java.util.Observable;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	private static final String PROGRAM_NAME = " - BücherBox";
	private ModelController controller;

	public MainWindowModel(ModelController controller) {
		this.controller = controller;
	}

	public void fireDataChange() {
		setChanged();
		notifyObservers();
	}

	public String getWindowTitle() {
		return controller.tabbed_model.getTabbedTitle() + PROGRAM_NAME;
	}

	// Statusbar-Text
	// aktive Ansicht / Tab / Modus
	// aktiver User

}
