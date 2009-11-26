/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class BookEditCancelAction extends AbstractAction {
	private static final long serialVersionUID = 1695749765069389958L;
	private final ModelController controller;

	public BookEditCancelAction(ModelController controller) {
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