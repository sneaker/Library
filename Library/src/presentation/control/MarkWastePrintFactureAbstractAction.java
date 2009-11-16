/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;
import domain.Book;

public final class MarkWastePrintFactureAbstractAction extends AbstractAction {
	private final String customer;
	private static final long serialVersionUID = 1L;
	private final ModelController controller;

	MarkWastePrintFactureAbstractAction(ModelController controller, String customer) {
		this.controller = controller;
		this.customer = customer;
	}

	public void actionPerformed(ActionEvent e) {
		controller.status_model.setTempStatus("Rechnung für " + customer + " wird gedruckt");
		controller.main_model.hideGlassPane();
		controller.booktab_model.getActiveBook().setCondition(Book.Condition.WASTE);
		controller.booktab_model.fireDataChanged();
	}
}