/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;

public class UserShowLoanBookAction extends AbstractAction {
	private static final long serialVersionUID = 2562822302051064209L;
	private final ModelController controller;

	public UserShowLoanBookAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		if (controller.usertab_model.isLoanSelected()) {
			controller.setActiveBook(controller.usertab_model.getActiveLoan().getBook());
			controller.setBookTabActive();
			controller.status_model.setTempStatus("Zeige Buchdetails für die gewählte Ausleihe.");
		} else {
			controller.status_model.setTempStatus("Kein Buch ausgewählt zum Anzeigen.");
		}
	}
}