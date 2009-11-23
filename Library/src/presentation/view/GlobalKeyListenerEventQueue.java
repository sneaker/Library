package presentation.view;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import presentation.model.ModelController;
import util.LookBackKeyBuffer;

/**
 * Listen for entered keystrokes and react to book and user ids to automatically
 * switch tabs and display information on the enterd id. Indended for
 * demonstration purposes and for use with barcode scanners.
 */
public final class GlobalKeyListenerEventQueue extends EventQueue {
	LookBackKeyBuffer lookBackForID = new LookBackKeyBuffer();
	private LibraryMainWindow mainWindow;
	private ModelController controller;

	public GlobalKeyListenerEventQueue(ModelController controller, LibraryMainWindow mainwindow) {
		this.mainWindow = mainwindow;
		this.controller = controller;
	}

	protected void dispatchEvent(AWTEvent e) {
		try {
			KeyEvent keyEvent = (KeyEvent) e;
			if (!keyWasTypedAnywhere(keyEvent))
				return;
			if (keyEvent.getKeyChar() == KeyEvent.VK_ESCAPE)
				return;
			lookBackForID.addChar(keyEvent.getKeyChar());
			handleIdScaned();
		} catch (Exception ex) {
		} finally {
			super.dispatchEvent(e);
		}
	}

	private boolean keyWasTypedAnywhere(KeyEvent keyEvent) {
		return keyEvent.getID() == KeyEvent.KEY_TYPED
				&& keyEvent.getSource() instanceof JFrame;
	}

	/**
	 * How to cope with entered characters and ids. If glass panes are
	 * disappearing on key type for no reason, here it's shown why.
	 */
	private void handleIdScaned() {
		mainWindow.getGlassPane().setVisible(false);
		handleTagPending();
		handleLastIdCharacter();
		handleBookId();
		handleUserID();
	}

	/**
	 * How to react to entered user ids. Probably the system should switch tabs
	 * here and display corresponding data for the id entered.
	 */
	private void handleUserID() {
		if (lookBackForID.isUserID()) {
			controller.setUserTabActive();
		}
	}

	private void handleBookId() {
		if (lookBackForID.isBookID()) {
			controller.setBookTabActive();
		}
	}

	/**
	 * Make the quick search window disappear after a few milliseconds the last
	 * character of an id has been typed. This will look great with a barcode
	 * scanner.
	 */
	private void handleLastIdCharacter() {
		if (!lookBackForID.wasLastIdCharacter())
			return;
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
				} finally {
					mainWindow.getGlassPane().setVisible(false);
				}
			}
		}).start();
	}

	private void handleTagPending() {
		if (lookBackForID.idTagPending()) {
			mainWindow.findAsYouTypeGlassPane.setText(lookBackForID
					.getRecentId());
			mainWindow.setGlassPane(mainWindow.findAsYouTypeGlassPane
					.getGlassPane());
			mainWindow.getGlassPane().setVisible(true);
		}
	}
}