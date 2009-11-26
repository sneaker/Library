/**
 * 
 */
package presentation.view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class CustomerEditAction extends AbstractAction {
	private static final long serialVersionUID = 2562822302051064209L;
	private final ModelController controller;

	public CustomerEditAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.editUserSettings();
	}
}