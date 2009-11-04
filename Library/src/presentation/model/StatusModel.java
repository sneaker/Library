package presentation.model;

import java.util.Observable;

/**
 * Represents state of status panel of the main window. 
 */
public class StatusModel extends Observable {
	
	private static final String DEFAULT_TEXT = "Bereit.";
	private String status = DEFAULT_TEXT;

	public StatusModel(ModelController modelController) {
	}

	public void setStatus(String newStatus) {
		this.status = newStatus;
		setChanged();
		notifyObservers();
	}

	public String getStatus() {
		return status;
	}
}
