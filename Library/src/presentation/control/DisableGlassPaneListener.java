/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public final class DisableGlassPaneListener extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final ModelController controller;

	public DisableGlassPaneListener(ModelController controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		controller.status_model.setTempStatus("Vorgang abgebrochen");
		controller.main_model.hideGlassPane();
	}
}