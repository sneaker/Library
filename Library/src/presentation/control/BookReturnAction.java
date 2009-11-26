/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class BookReturnAction extends AbstractAction {
	private static final long serialVersionUID = -8090599104239713157L;
	private final ModelController controller;

	public BookReturnAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.returnBook();
	}
}