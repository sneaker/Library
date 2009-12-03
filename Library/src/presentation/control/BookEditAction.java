/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ControllerFacade;

public class BookEditAction implements ActionListener {
	private final ControllerFacade controller;

	public BookEditAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.booktab_model.setEditing(true);
		controller.booktab_model.backupBookContent();
		controller.status_model.setTempStatus("Buchdetails editieren");
	}
}