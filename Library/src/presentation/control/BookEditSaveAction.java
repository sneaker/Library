/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;

public class BookEditSaveAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final ControllerFacade controller;

	public BookEditSaveAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.booktab_model.setEditing(false);
		controller.status_model.setTempStatus("Änderungen am Buchtitel wurden gesichert");
		controller.library.fireDataChanged();
	}
}