/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ControllerFacade;
import domain.Book.Condition;

public final class DisableGlassPaneListener extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private ControllerFacade controller;
	private Condition oldCondition = null;

	public DisableGlassPaneListener(ControllerFacade controller) {
		this.controller = controller;
	}
	
	public DisableGlassPaneListener(ControllerFacade controller,
			Condition oldCondition) {
				this.controller = controller;
				this.oldCondition = oldCondition;
	}

	public void actionPerformed(ActionEvent e) {
		if (oldCondition != null)
			controller.booktab_model.resetConditionCombo(oldCondition);
		controller.status_model.setTempStatus("Vorgang abgebrochen");
		controller.main_model.hideGlassPane();
	}
}