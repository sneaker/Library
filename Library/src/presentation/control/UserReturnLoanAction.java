/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import presentation.model.ModelController;
import domain.Loan;

public class UserReturnLoanAction extends AbstractAction {
	private static final long serialVersionUID = 2562822302051064209L;
	private final ModelController controller;

	public UserReturnLoanAction(ModelController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		Loan loan = controller.usertab_model.getActiveLoan();
		controller.library.returnBook(loan.getBook());
		controller.status_model.setTempStatus("Buch zurückgegeben: " + loan.getBook().getInventoryNumber());
		controller.loanModel.removeElement(loan);
	}
}