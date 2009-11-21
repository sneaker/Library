/**
 * 
 */
package domain;

import javax.swing.Action;
import javax.swing.KeyStroke;

public class Message {

	private final String dialogText;
	private final String[] buttonNames;
	private final Action[] buttonActions;
	private final KeyStroke[] buttonKeys;

	public Message(String dialogText, String[] buttonNames,
			Action[] buttonActions, KeyStroke[] buttonKeys) {
				this.dialogText = dialogText;
				this.buttonNames = buttonNames;
				this.buttonActions = buttonActions;
				this.buttonKeys = buttonKeys;
	}

	public String getDialogText() {
		return dialogText;
	}

	public String[] getButtonNames() {
		return buttonNames;
	}

	public Action[] getButtonActions() {
		return buttonActions;
	}

	public KeyStroke[] getButtonKeys() {
		return buttonKeys;
	}
}