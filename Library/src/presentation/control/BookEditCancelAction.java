/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;

public class BookEditCancelAction extends AbstractAction {
	private static final long serialVersionUID = 1695749765069389958L;
	private final ControllerFacade controller;

	public BookEditCancelAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		if (controller.booktab_model.restoreBookContent())
			controller.status_model.setTempStatus("Änderungen am Buchtitel wurden zurückgesetzt");
		else
			controller.status_model.setTempStatus("Fehler: Änderungen am Buchtitel konnten nicht zurückgesetzt werden");
		controller.booktab_model.setEditing(false);
	}
}