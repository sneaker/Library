/**
 * 
 */
package presentation.view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;

public class CustomerEditAction extends AbstractAction {
	private static final long serialVersionUID = 2562822302051064209L;
	private final ControllerFacade controller;

	public CustomerEditAction(ControllerFacade controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.editUserSettings();
	}
}