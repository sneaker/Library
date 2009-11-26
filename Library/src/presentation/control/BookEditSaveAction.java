/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class BookEditSaveAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final ModelController controller;

	public BookEditSaveAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.booktab_model.setEditing(false);
		controller.status_model.setTempStatus("Änderungen am Buchtitel wurden gesichert");
		controller.library.fireDataChanged();
	}
}