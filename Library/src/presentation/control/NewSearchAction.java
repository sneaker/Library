/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class NewSearchAction implements ActionListener {
	private final ControllerFacade controller;

	public NewSearchAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.setSearchTabActive();
		controller.resetSearchText();
	}
}