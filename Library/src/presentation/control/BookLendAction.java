/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

/**
 * Lends the book which is currently displayed in the book panel.
 */
public class BookLendAction extends AbstractAction {
	private static final long serialVersionUID = -941531974216578248L;
	private final ModelController controller;

	public BookLendAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.lendBook();
	}
}