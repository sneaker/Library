/**
 * 
 */
package presentation.view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class CustomerEditCancelAction extends AbstractAction {
	private static final long serialVersionUID = 2687724652972916779L;
	private final ModelController controller;

	public CustomerEditCancelAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.action_model.editUserSettingsOk();
	}
}