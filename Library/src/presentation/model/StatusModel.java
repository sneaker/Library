package presentation.model;

import java.util.Observable;

/**
 * Represents state of status panel of the main window.
 */
public class StatusModel extends Observable {
	private String status;
	private String tempStatus;

	public StatusModel(ModelController controller) {
		setStatus(controller.searchtab_model.getStatus());
	}

	public void setStatus(String newStatus) {
		this.status = newStatus;
		fireDataChanged();
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Use this to set a static 
	 * @return
	 */
	public String getStatus() {
		return (getTempStatus() == null ? status : getTempStatus() + "...");
	}

	/**
	 * Use this to set a temporary status message that disappears after some
	 * time.
	 * 
	 * @param tempStatus
	 *            The status to be shown temporarily
	 */
	public void setTempStatus(String tempStatus) {
		this.tempStatus = tempStatus;
		fireDataChanged();
	}

	public String getTempStatus() {
		return tempStatus;
	}

}
