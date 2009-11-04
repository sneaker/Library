package presentation.model;

import java.util.Observable;

/**
 * Manages the current status of the main window with its active user, tab,
 * menubar and status panel.
 */
public class MainWindowModel extends Observable {

	private static final String PROGRAM_NAME = " - BücherBox";
//	private ModelController controller;
	private String title = "Recherche - BücherBox";

	public MainWindowModel(ModelController controller) {
//		this.controller = controller;
	}

	public String getTitle() {
		return title + " " + PROGRAM_NAME;
	}

	// TODO: Use it... [Martin, bis 4.10.09]
	public void setTitle(String title) {
		this.title = title;
		setChanged();
		notifyObservers();
	}
	// Statusbar-Text
	// aktive Ansicht / Tab / Modus
	// aktiver User

}
