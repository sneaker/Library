/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;
import domain.Book;

public final class MarkWasteAbstractAction extends AbstractAction {
	private final String book;
	private static final long serialVersionUID = 1L;
	private final ModelController controller;

	MarkWasteAbstractAction(ModelController controller, String book) {
		this.controller = controller;
		this.book = book;
	}

	public void actionPerformed(ActionEvent e) {
		controller.status_model.setTempStatus("Das Buch \"" + book + "\" wurde ausgemustert");
		controller.booktab_model.getActiveBook().setCondition(Book.Condition.WASTE);
		controller.booktab_model.fireDataChanged();
		controller.main_model.hideGlassPane();
	}
}