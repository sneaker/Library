/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presentation.model.ModelController;

public class BookEditAction implements ActionListener {
	private final ModelController controller;

	public BookEditAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.booktab_model.setEditing(true);
		controller.booktab_model.backupBookContent();
		controller.status_model.setTempStatus("Buchdetails editieren");
	}
}