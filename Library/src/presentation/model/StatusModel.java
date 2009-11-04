package presentation.model;

import java.util.Observable;

import javax.swing.SwingUtilities;

/**
 * Represents state of status panel of the main window.
 */
public class StatusModel extends Observable {
	private static final String DEFAULT_TEXT = "Bereit.";
	private String status = DEFAULT_TEXT;
	private String tempStatus;

	public StatusModel(ModelController modelController) {
	}

	public void setStatus(String newStatus) {
		this.status = newStatus;
		fireDataChanged();
	}

	public void setTemporaryStatus(final String newStatus) {
		this.tempStatus = newStatus;
		SwingUtilities.invokeLater(new TempStatusResetter(newStatus));
		fireDataChanged();
	}

	private void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public String getStatus() {
		return (tempStatus == null ? status : tempStatus);
	}

	public void resetStatus() {
		status = DEFAULT_TEXT;
	}

	private final class TempStatusResetter implements Runnable {
		private final String newStatus;

		private TempStatusResetter(String newStatus) {
			this.newStatus = newStatus;
		}

		public void run() {
			String before = newStatus;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (!before.equals(tempStatus))
				return;
			tempStatus = null;
			fireDataChanged();
		}
	}
}
