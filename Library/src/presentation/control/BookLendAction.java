/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;

/**
 * Lends the book which is currently displayed in the book panel.
 */
public class BookLendAction extends AbstractAction {
	private static final long serialVersionUID = -941531974216578248L;
	private final ControllerFacade controller;

	public BookLendAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.lendBook();
	}
}