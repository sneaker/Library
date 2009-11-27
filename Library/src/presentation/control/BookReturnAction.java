/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;

public class BookReturnAction extends AbstractAction {
	private static final long serialVersionUID = -8090599104239713157L;
	private final ControllerFacade controller;

	public BookReturnAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.returnBook();
	}
}