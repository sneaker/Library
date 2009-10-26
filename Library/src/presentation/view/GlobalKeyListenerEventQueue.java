package presentation.view;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import util.LookBackKeyBuffer;

public final class GlobalKeyListenerEventQueue extends EventQueue {
	LookBackKeyBuffer lookBackForID = new LookBackKeyBuffer();
	private MainWindow mainWindow;

	public GlobalKeyListenerEventQueue(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
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

	private void handleIdScaned() {
		if (lookBackForID.idTagPending()) {
			mainWindow.findAsYouTypeGlassPane.setText(lookBackForID.getRecentId());
			mainWindow.setGlassPane(mainWindow.findAsYouTypeGlassPane.getGlassPane());
			mainWindow.getGlassPane().setVisible(true);
			mainWindow.repaint();			
		}
		if (lookBackForID.isBookID()) {
			mainWindow.model.getTabs().setSelectedIndex(1);
		}
	}
}